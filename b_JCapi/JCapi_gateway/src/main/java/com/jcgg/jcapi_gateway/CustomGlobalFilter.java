package com.jcgg.jcapi_gateway;

import com.jcgg.model.entity.InterfaceInfo;
import com.jcgg.model.entity.User;
import com.jcgg.service.InnerInterfaceInfoService;
import com.jcgg.service.InnerUserInterfaceInfoService;
import com.jcgg.service.InnerUserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * 全局过滤
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1","0:0:0:0:0:0:0:1");

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @DubboReference
    private InnerInterfaceInfoService interfaceInfoService;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        String method = Objects.requireNonNull(request.getMethod()).toString();

        System.out.println("req 的 ID: "+request.getId());
        System.out.println("req 的 Path: "+ path);
        System.out.println("req 的 请求方法: "+ method);
        System.out.println("req 的 请求参数: "+ request.getQueryParams());
        String localAddress = request.getLocalAddress().getHostString();
        System.out.println("req 的 来源地址: "+request.getRemoteAddress());

        ServerHttpResponse response = exchange.getResponse();
        // 2. 黑白名单 这里使用白名单
        if(!IP_WHITE_LIST.contains(localAddress)){
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }

        // 3. 用户鉴权 (判断ak sk 是否合法)
        HttpHeaders headers = request.getHeaders();

        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String body = URLDecoder.decode( headers.getFirst("body"),"utf-8");
        String sign = headers.getFirst("sign"); // 获取浏览器

        boolean userAkIsRight = innerUserService.HaveAk(accessKey);
        User invokeUser = new User();
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);
        }catch (Exception e){
            log.info("找不到用户, " + e);
        }
        if(!userAkIsRight && invokeUser != null){return handleNoAuth(response);}

        // todo 校验随机数 思路: 保存使用过的随机数 5分种内再次使用该随机数和该请求会拒绝
        // nonce

        //
        // 时间戳为毫秒
        final long ONE_MINS = 60 * 1000L;
        long currentTime = System.currentTimeMillis()/ONE_MINS;
        if( currentTime - Long.parseLong(timestamp)/ONE_MINS > 5){
            return handleNoAuth(response);
        }

        // 校验签名
        String sk = innerUserService.getSkByAk(accessKey);


        // 4. 判断请求的模拟接口是否存在
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        try {
            interfaceInfo = interfaceInfoService.getInterfaceInfo(path, method);
        }catch (Exception e){
            log.info("getInterfaceInfo Error , " + e);
        }
        System.out.println("==================================== interfaceInfo : "+interfaceInfo);

        // 5. 转发请求 调用模拟接口
//        Mono<Void> filter = chain.filter(exchange);
        // 6. 响应日志
//        log.info();
        return handleResponse(exchange,chain,interfaceInfo.getId(),invokeUser.getId());

    }

    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里写数据
                            // 拼接字符串
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        // 7. 调用成功，接口调用次数 + 1 invokeCount
                                        try {
                                            innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                        } catch (Exception e) {
                                            log.error("invokeCount error", e);
                                        }
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8); //data
                                        sb2.append(data);
                                        // 打印日志
                                        log.info("响应结果：" + data);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            // 8. 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange); // 降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }


    @Override
    public int getOrder() {
        return -1;
    }

    private Mono<Void> handleNoAuth(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    private Mono<Void> handleInvokeError(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}