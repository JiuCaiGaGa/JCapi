package com.jcgg.project.service.impl.inner;

import com.jcgg.project.service.UserInterfaceInfoService;
import com.jcgg.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    UserInterfaceInfoService userInterfaceInfoService;
    @Override
    public boolean invokeCount(long interfaceId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceId,userId);
    }
}
