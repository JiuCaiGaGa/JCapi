package com.jcgg.jcapi_test.controller;


import com.jcgg.jcapiclientsdk.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


/**
 * 名称接口
 */

@RestController
@RequestMapping("/name")
public class NameController {

//    @Resource
//    private InnerUserService innerUserService;

    @GetMapping("/get")
    public String getNameByGet(String name){
        return "Get : 你的名字是" + name;
    }
    @PostMapping("/post")
    public String getNameByPost_1(@RequestParam String name){
        return "Post_1: 你的名字是" + name;
    }

    @PostMapping("/user")
    public String getNameByPost_2(@RequestBody User user, HttpServletRequest request) throws UnsupportedEncodingException {
//        String accessKey = request.getHeader("accessKey");
//        String nonce = request.getHeader("nonce");
//        String timestamp = request.getHeader("timestamp");
//        String body = URLDecoder.decode( request.getHeader("body"),"utf-8");
//        String sign = request.getHeader("sign"); // 获取浏览器
//
//        boolean userAkIsRight = innerUserService.HaveAk(accessKey);
//        if(!userAkIsRight ){// 该AK为非法ak
//            throw new RuntimeException("无权限");
//        }
//
//        // todo 校验随机数 思路: 保存使用过的随机数 5分种内再次使用该随机数和该请求会拒绝
//        // nonce
//
//        //
//        // 时间戳为毫秒
//        final long ONE_MINUTES = 60 * 1000L;
//        if( System.currentTimeMillis()/ONE_MINUTES - Long.parseLong(timestamp)/ONE_MINUTES > 5){throw new RuntimeException("无权限");}
//
//        // 校验签名
//
//        String userSk = innerUserService.getSkByAk(accessKey);
//        String userSign = SignUtils.getSign(body, userSk);
//        if(StringUtils.isBlank(userSign)){
//            throw new RuntimeException("参数错误");
//        }
//        if(!userSign.equals(sign)){
//            throw new RuntimeException("无权限");
//        }

//        System.out.println(user.toString() + "user.");

        return "Post_2 : 你的名字是" + user.getUsername();
    }
}
