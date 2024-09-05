package com.jcgg.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jcgg.model.entity.InterfaceInfo;
import com.jcgg.project.common.ErrorCode;
import com.jcgg.project.exception.BusinessException;
import com.jcgg.project.service.InterfaceInfoService;
import com.jcgg.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    InterfaceInfoService interfaceInfoService;

    private static final String HOTS_ADDRESS = "http://localhost:9527";
    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if(StringUtils.isAnyBlank(url,method)){
            // 当参数为null 或者空白时 抛出参数错误异常
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // todo 新建管理员对已删除接口的查看页面
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", HOTS_ADDRESS + url);
        queryWrapper.eq("method",method);
//        queryWrapper.eq("isDelete", InterfaceStatusEnum.OFFLINE.getValue());

        InterfaceInfo one = interfaceInfoService.getOne(queryWrapper);
        return one;
    }
}
