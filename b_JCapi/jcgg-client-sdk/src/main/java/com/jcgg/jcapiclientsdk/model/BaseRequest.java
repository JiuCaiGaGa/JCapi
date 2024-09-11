package com.jcgg.jcapiclientsdk.model;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 基础请求
 */
@Data
public class BaseRequest {

    private String path;

    private String method;

    private Map<String, Object> requestParams;

    private HttpServletRequest userRequest;
}
