package com.jcgg.service;

import com.jcgg.model.entity.InterfaceInfo;

/**
* @author GaG
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-08-14 16:32:02
*/
public interface InnerInterfaceInfoService {
    /**
     *  查询对应的接口是否存在
     * @param url 请求路径
     * @param method 请求方法
     * @return 接口信息
     */
    InterfaceInfo getInterfaceInfo(String url, String method);
}
