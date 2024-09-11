package com.jcgg.jcapiclientsdk.client;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.jcgg.jcapiclientsdk.exception.ErrorCode;
import com.jcgg.jcapiclientsdk.exception.JCapiException;
import com.jcgg.jcapiclientsdk.model.BaseRequest;
import com.jcgg.jcapiclientsdk.model.enums.ApiEnums;
import com.jcgg.jcapiclientsdk.model.response.ErrorResponse;
import com.jcgg.jcapiclientsdk.model.entity.SentencesPerDay;
import com.jcgg.jcapiclientsdk.model.response.UserResponse;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.jcgg.jcapiclientsdk.model.response.WeatherResponse;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

import static com.jcgg.jcapiclientsdk.utils.SignUtils.getSign;

@Slf4j
public class JcApiClient {

    private static final String GATEWAY_HOST = "http://localhost:8090";

    private String accessKey;

    private String secretKey;

    public JcApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }


//    public OneStringCommonResponse

    // 处理 调用特定 API Begin
    /**
     * 发送请求
     *
     * @param url          请求地址
     * @param method       请求方式
     * @param params       请求参数
     * @param responseType 响应类型
     * @param <T>          响应类型
     * @throws JCapiException 异常
     * @return 响应结果
     */
    public <T> T sendRequest(String url, String method, Object params, Class<T> responseType) throws JCapiException {
        HttpRequest request = null;
        String jsonBody = JSON.toJSONString(params);

        switch (method) {
            case "GET":
                log.info("params:{}", params);
                request = HttpRequest.get(url);
                handleParamsAsQueryParams(request, params);
                break;
            case "POST":
                log.info("封装body:{}", jsonBody);
                request = HttpRequest.post(url);
                handleParamsAsBody(request, jsonBody);
                break;
        }

        // 添加通用请求头
        request.addHeaders(getHeaderMap(jsonBody));
        // 发送请求并获取响应
        HttpResponse response = request.execute();
        String responseBody = response.body();
        // 控制台打印
        log.info("返回responseBody:{}", responseBody);
        if (response.getStatus() != 200) {
            // 处理网关返回异常
            ErrorResponse errorResponse = JSONUtil.toBean(responseBody, ErrorResponse.class);
            throw new JCapiException(errorResponse.getCode(), errorResponse.getMessage());
        }
        // 获取响应体

        // 解析响应体
        return JSON.parseObject(responseBody, responseType);
    }

    private void handleParamsAsQueryParams(HttpRequest request, Object bodyAndParams) {
        if (bodyAndParams != null) {
            Map<String, Object> paramsMap = convertObjectToMap(bodyAndParams);
            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                if (entry.getValue() != null) {
                    request.form(entry.getKey(), entry.getValue().toString());
                }
            }
        }
    }

    private void handleParamsAsBody(HttpRequest request, String bodyJson) {
        if (bodyJson != null) {
            // Convert the entire bodyAndParams map to JSON and set it as the request body
            request.header("Content-Type", "application/json; charset=UTF-8")
                    .body(bodyJson);
        }
    }

    private Map<String, Object> convertObjectToMap(Object object) {
        // Use reflection to get all fields and their values from the object
        Map<String, Object> map = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                // Handle the exception as needed
            }
        }
        return map;
    }

    // 处理 调用特定 API  end

    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.get(GATEWAY_HOST + "/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    public String getNameByPost(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.post(GATEWAY_HOST + "/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    /**
     * 将 body 内封装的内容 存贮到 Map 中
     * @param body
     * @return
     */

    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);
        // 一定不能直接发送
//        hashMap.put("secretKey", secretKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body", body);
        // 时间戳存毫秒值 之后统一处理为分钟
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        hashMap.put("sign", getSign(body, secretKey));
        return hashMap;
    }

    public String getUsernameByPost(UserResponse user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/name/user")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
//        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
//        System.out.println(result);
        return result;
    }

    /**
     *  每日一言 API
     * @return SentencesPerDay 类对象
     * @throws JCapiException 异常
     */

    public SentencesPerDay getSentencesPerDay() throws JCapiException {
        // fixme 这里应该是 网关的路由 GATEWAY_HOST + 网关下的路由
        return sendRequest("https://img.8845.top/yiyan/index.php","GET",null, SentencesPerDay.class);
    }

    public WeatherResponse getWeather() throws JCapiException {
        return sendRequest(GATEWAY_HOST+"/api/weather/localWeather","GET",null,WeatherResponse.class);
    }

//    public static void main(String[] args) throws JCapiException {
//        JcApiClient jc = new JcApiClient("111","222");


//        System.out.println(jc.getWeather());
        /**
        SentencesPerDay sentencesPerDay = jc.getSentencesPerDay();
        if(sentencesPerDay != null){
            System.out.println(sentencesPerDay);
        }
         */

//    }
    /**
     * 解析地址和调用接口
     *
     * @param baseRequest
     * @return
     * @throws JCapiException
     */
    public Object parseAddressAndCallInterface(BaseRequest baseRequest) throws JCapiException {

        log.info(baseRequest.toString());

        String path = baseRequest.getPath();
        String method = baseRequest.getMethod();
        Map<String, Object> paramsList = baseRequest.getRequestParams();
        HttpServletRequest userRequest = baseRequest.getUserRequest();
        Class<?> clazz = JcApiClient.class;
        Object result = null;
        try {
            log.info("请求地址：{}，请求方法：{}，请求参数：{}", path, method, paramsList);
            // 获取名称
//            if (path.equals(ApiEnums.name.getPath())) {
//                return invokeMethod(ApiEnums.name.getMethod(), paramsList, User.class);
//            }
//            // 获取毒鸡汤
//            if (path.equals(ApiEnums.poisonousChickenSoup.getPath())) {
//                return invokeMethod(ApiEnums.poisonousChickenSoup.getMethod());
//            }
//            // 获取微博热搜榜
//            if (path.equals(ApiEnums.weiboHotSearch.getPath())) {
//                return invokeMethod(ApiEnums.weiboHotSearch.getMethod());
//            }
//            // 获取星座运势
//            if (path.equals(ApiEnums.horoscope.getPath())) {
//                return invokeMethod(ApiEnums.horoscope.getMethod(), paramsList, HoroscopeParams.class);
//            }
//            // 获取公网ip
//            if (path.equals(ApiEnums.publicIp.getPath())) {
//                // todo 上线时测试是否获取用户的公网ip，目前本机无法获取到X-Real-IP
//                String ipAddress = userRequest.getHeader("X-Real-IP");
//                if (ipAddress == null || ipAddress.isEmpty()) {
//                    log.info("未携带X-Real-IP请求头，尝试获取");
//                    ipAddress = userRequest.getRemoteAddr();
//                }
//                if (ipAddress == null || ipAddress.isEmpty()) {
//                    throw new JCapiException(ErrorCode.NOT_FOUND_ERROR, "获取公网ip失败");
//                }
//                log.info("获取到的公网ip：", ipAddress);
//                paramsList.put("ipAddress", ipAddress);
//                return invokeMethod(ApiEnums.publicIp.getMethod(), paramsList, PublicIpParams.class);
//            }
//            // 随机壁纸
//            if (path.equals(ApiEnums.randomWallpaper.getPath())) {
//                return invokeMethod(ApiEnums.randomWallpaper.getMethod());
//            }
//            // 土味情话
//            if (path.equals(ApiEnums.loveTalk.getPath())) {
//                return invokeMethod(ApiEnums.loveTalk.getMethod());
//            }

            {
                // todo 维护接口
                //
                // 天气信息
                if (path.equals(ApiEnums.weather.getPath())) {
                    return invokeMethod(ApiEnums.weather.getMethod());
                }
                //名称信息
                if (path.equals(ApiEnums.name.getPath())){
                    return invokeMethod(ApiEnums.name.getMethod(),paramsList, UserResponse.class);
                }
            }

            // todo 1.添加新的接口地址判断
        } catch (JCapiException e) {
            throw new JCapiException(e.getCode(), e.getMessage());
        } catch (Exception e){
            throw new JCapiException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
        if (ObjUtil.isEmpty(result)) {
            throw new JCapiException(ErrorCode.NOT_FOUND_ERROR, "未找到Url对应资源");
        }
        log.info("返回结果：{}", result);
        return result;
    }
    /**
     * 反射方法(不带参数)
     *
     * @param methodName
     * @return
     * @throws JCapiException
     */
    private Object invokeMethod(String methodName) throws JCapiException {
        return this.invokeMethod(methodName, null, null);
    }

    /**
     * 反射方法(带参)
     *
     * @param methodName
     * @param params
     * @param paramsType
     * @return
     * @throws JCapiException
     */
    private Object invokeMethod(String methodName, Map<String, Object> params, Class<?> paramsType) throws JCapiException {
        try {
            Map<String, Object> requestParams = null;
            // fixme 这里针对单条数据进行获取 因为所有信息都写进了里面 后续修复
            if(params != null){
                requestParams = JSON.parseObject((String) params.get("RequestParams"), Map.class);
            }

            Class<?> clazz = JcApiClient.class;
//            if (params == null) {
            if (requestParams == null) {
                Method method = clazz.getMethod(methodName);
                return method.invoke(this);
            } else {
//                log.info("接收到的参数 params:{} paramsType:{}", params, paramsType);
                log.info("接收到的参数 params:{} paramsType:{}", requestParams, paramsType);
                Method method = clazz.getMethod(methodName, paramsType);
                // map转Object
//                Object paramsObject = BeanUtil.mapToBean(params, paramsType, true, CopyOptions.create());
                Object paramsObject = BeanUtil.mapToBean(requestParams, paramsType, true, CopyOptions.create());
//                log.info("map转Object params:{} paramsType:{}", params, paramsType);
                log.info("map转Object params:{} paramsType:{}", requestParams, paramsType);
                return method.invoke(this, paramsObject);
            }
        } catch (NoSuchMethodException e){
            throw new JCapiException(ErrorCode.NOT_FOUND_ERROR, "通过url未找到对应方法");
        } catch (Exception e) {
            // 处理异常
            if (e.getCause() instanceof JCapiException) {
                JCapiException gigotApiException = (JCapiException) e.getCause();
                // 处理 JCapiException 异常
                throw new JCapiException(gigotApiException.getCode(),gigotApiException.getMessage());
            }
            throw new JCapiException(ErrorCode.NOT_FOUND_ERROR, e.getMessage());
        }
    }


}
