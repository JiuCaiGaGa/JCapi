package com.jcgg.project.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
public class UserInterfaceInfoServiceTest {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Test
    public void invokeCount() {

        long userid = 1L;
        long interfaceInfoId = 1L;

        boolean b = userInterfaceInfoService.invokeCount(interfaceInfoId, userid);
        Assert.assertTrue(b);
    }
}