package com.jcgg.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jcgg.model.entity.UserInterfaceInfo;
import com.jcgg.project.common.ErrorCode;
import com.jcgg.project.exception.BusinessException;
import com.jcgg.project.mapper.UserInterfaceInfoMapper;
import com.jcgg.project.model.enums.UserInterfaceStatusEnum;
import com.jcgg.project.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author GaG
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2024-08-26 18:26:38
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {

        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
         Long id = userInterfaceInfo.getId();
         Long userId = userInterfaceInfo.getUserId();
         Long interfaceInfoId = userInterfaceInfo.getInterfaceInfoId();
         Integer totalNum = userInterfaceInfo.getTotalNum();
         Integer leftNum = userInterfaceInfo.getLeftNum();
         Integer status = userInterfaceInfo.getStatus();
         Date createTime = userInterfaceInfo.getCreateTime();
         Date updateTime = userInterfaceInfo.getUpdateTime();
         Integer isDelete = userInterfaceInfo.getIsDelete();

         // todo 对参数进行校验
        if(interfaceInfoId <=0 || userId <= 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"未找到对应的参数");
        }


        // 创建时，所有参数必须非空
        /*
         * 测试
         * @Test
         * */
        if (add) {
//            if(isDelete == )
            // 用户调用该接口为 不可用状态
            if(status == UserInterfaceStatusEnum.NA.getValue()){
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"您的账号当前状态不可用.");
            }

            if (leftNum <= 0 ) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"当前接口可调用次数不足.");
            }
        }
    }



    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        // 判断 传递的参数 是否 有效
        if(interfaceInfoId <= 0 || userId <= 0) {throw new BusinessException(ErrorCode.PARAMS_ERROR);}

        // 判断 用户 和 调用的 接口ID 是否合法 但是 不合法的也更新不到

        // todo 先查询是否存在在 之后更新次数

        // todo 完善调用逻辑 保证更加安全  如并发 采用加锁操作
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId",interfaceInfoId);
        updateWrapper.eq("userid",userId);
        updateWrapper.ge("leftNum",0 );
        updateWrapper.setSql("leftNum = leftNum -1 , totalNum = totalNum + 1");

        return this.update(updateWrapper);
    }
}




