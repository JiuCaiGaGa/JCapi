package com.jcgg.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jcgg.model.entity.UserInterfaceInfo;

/**
* @author GaG
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-08-26 18:26:38
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);





    /**
     * 接口调用次数统计
     *
     * @param interfaceId
     * 接口id
     * @param userId
     * 用户id
     * @return 某用户调用某接口的次数
     */
    boolean invokeCount(long interfaceId,long userId);
}
