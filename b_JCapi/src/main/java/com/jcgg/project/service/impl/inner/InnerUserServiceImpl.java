package com.jcgg.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jcgg.model.entity.User;
import com.jcgg.project.common.ErrorCode;
import com.jcgg.project.exception.BusinessException;
import com.jcgg.project.mapper.UserMapper;
import com.jcgg.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    UserMapper userMapper;
    @Override
    public User getInvokeUser(String ak) {
        if(StringUtils.isAnyBlank(ak)){
            // 当参数为null 或者空白时 抛出参数错误异常
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey",ak);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public String getSkByAk(String ak) {
        if(StringUtils.isBlank(ak)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey",ak);
        return userMapper.selectOne(queryWrapper).getSecretKey();
    }

    /**
     *
     * @param ak 用户的 AK
     * @return 是否存在此 AK
     */
    @Override
    public boolean HaveAk(String ak) {
        if (StringUtils.isBlank(ak)){throw new BusinessException(ErrorCode.PARAMS_ERROR);}
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey",ak);
        User user = userMapper.selectOne(queryWrapper);
        return user != null && ak.equals(user.getAccessKey());
    }
}
