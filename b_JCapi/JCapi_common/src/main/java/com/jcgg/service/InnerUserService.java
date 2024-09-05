package com.jcgg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jcgg.model.entity.User;

/**
 * 用户服务
 *
 */
public interface InnerUserService {

    /**
     *  从数据苦衷查询是否已经给用户分配了 AK 和 SK
     * @param ak 用户的ak
     * @return 用户信息 没找到则为 null
     */
    public User getInvokeUser(String ak);

    public String getSkByAk(String ak);

    public boolean HaveAk(String ak);

}
