package com.jcgg.project.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户视图
 *
 * @TableName user
 */
@Data
public class UserAkSkVO implements Serializable {

    /**
     *  校验用户ID
     */
    private long id;

    /**
     *  用户 AK
     */
    private String accessKey;

    /**
     * 用户 SK
     */

    private String secretKey;

    private static final long serialVersionUID = 1L;
}