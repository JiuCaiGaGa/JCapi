package com.jcgg.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jcgg.model.entity.InterfaceInfo;

import java.util.List;

/**
* @author GaG
* @description 针对表【interface_info(接口信息)】的数据库操作Mapper
* @createDate 2024-08-14 16:32:02
* @Entity com.jcgg.project.model.entity.InterfaceInfo
*/
public interface InterfaceInfoMapper extends BaseMapper<InterfaceInfo> {
    List<InterfaceInfo> listDeletedInterfaceInfo();
}




