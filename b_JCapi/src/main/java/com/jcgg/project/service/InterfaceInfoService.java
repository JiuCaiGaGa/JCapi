package com.jcgg.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jcgg.model.entity.InterfaceInfo;

/**
* @author GaG
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-08-14 16:32:02
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    /**
     * 校验
     *
     * @param interfaceInfo
     * @param add 是否为创建校验
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
