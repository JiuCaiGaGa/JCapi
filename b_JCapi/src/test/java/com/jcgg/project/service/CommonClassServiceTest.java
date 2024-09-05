package com.jcgg.project.service;

import com.jcgg.model.entity.InterfaceInfo;
import com.jcgg.project.common.ErrorCode;
import com.jcgg.project.exception.BusinessException;
import com.jcgg.service.InnerInterfaceInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class CommonClassServiceTest {
    @Resource
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @Test
    public void InnerInterfaceInfoServiceGetInterfaceInfo(){


        String url = "localhost:1234";
        String method = "Test";

        InterfaceInfo interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(url,method);

        if(interfaceInfo == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        System.out.println(interfaceInfo);


    }
}
