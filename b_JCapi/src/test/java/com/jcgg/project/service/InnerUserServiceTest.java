package com.jcgg.project.service;

import com.jcgg.service.InnerUserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class InnerUserServiceTest {
    @DubboReference
    private InnerUserService innerUserService;

    @Test
    public void getSkByAkTest(){
        String ak = "e2234245fb68a3fb5de28421557fb32f";

        String sk = innerUserService.getSkByAk(ak);
        Assertions.assertNotNull(sk);
        System.out.println(sk);
    }

    @Test
    public void HaveAkTest(){
//        String ak = "nia";    // 瞎编的
        String ak = "";    // 空 在执行 HaveAk 方法时 报错
//        String ak = "4611183b3cff78e488e7bdc5bb6c2c55"; // 数据库里有的
        boolean flag = innerUserService.HaveAk(ak);
        Assertions.assertTrue(flag);
        System.out.println(flag);
        if(!flag){
            System.out.println("逻辑错啦");
        }
    }
}
