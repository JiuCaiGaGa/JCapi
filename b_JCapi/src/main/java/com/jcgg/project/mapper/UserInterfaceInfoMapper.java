package com.jcgg.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jcgg.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author GaG
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2024-08-26 18:26:38
* @Entity com.jcgg.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




