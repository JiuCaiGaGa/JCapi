package com.jcgg.jcapiclientsdk.model.response;

import lombok.Data;


/**
 * 单个字符串返回值的通用类型
 *
 */
@Data
public class OneStringCommonResponse {

    /**
     * 返回状态
     */
    private String status;

    /**
     *  返回值
     */
    private String res;
}
