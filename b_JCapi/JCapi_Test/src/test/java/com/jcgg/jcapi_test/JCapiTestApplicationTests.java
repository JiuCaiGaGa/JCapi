package com.jcgg.jcapi_test;

import com.jcgg.jcapiclientsdk.client.JcApiClient;
import com.jcgg.jcapiclientsdk.model.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

@SpringBootTest
class JCapiTestApplicationTests {

    @Resource
    private JcApiClient jcClient;

    @Test
    void contextLoads() throws UnsupportedEncodingException {

//        String name = jcClient.getNameByGet("韭菜gaga");
//        String name = jcClient.getNameByGet("gaga");
        String name = jcClient.getNameByGet("jcgg");
        System.out.println("name "+name);

        UserResponse user = new UserResponse();

        user.setUsername("jcgg?qqq");

        System.out.println("user:"+user);
        String user_name = jcClient.getUsernameByPost(user);
        System.out.println("user_name "+user_name);
    }

}
