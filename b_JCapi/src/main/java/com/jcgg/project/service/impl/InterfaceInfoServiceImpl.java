package com.jcgg.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jcgg.model.entity.InterfaceInfo;
import com.jcgg.project.common.ErrorCode;
import com.jcgg.project.exception.BusinessException;
import com.jcgg.project.service.InterfaceInfoService;
import com.jcgg.project.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author GaG
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2024-08-14 16:32:02
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {

        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
         Long id = interfaceInfo.getId();
         String name = interfaceInfo.getName();
         String description = interfaceInfo.getDescription();
         String url = interfaceInfo.getUrl();
         String requestHeader = interfaceInfo.getRequestHeader();
         String responseHeader = interfaceInfo.getResponseHeader();
         Integer status = interfaceInfo.getStatus();
         String method = interfaceInfo.getMethod();
         Long userId = interfaceInfo.getUserId();
         Date createTime = interfaceInfo.getCreateTime();
         Date updateTime = interfaceInfo.getUpdateTime();
         Integer isDelete = interfaceInfo.getIsDelete();

        // 创建时，所有参数必须非空
        /*
        * 测试
        * @Test
        * */
        if (add) {
            if (StringUtils.isAnyBlank(name) || name.length() > 50) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
    }
}




