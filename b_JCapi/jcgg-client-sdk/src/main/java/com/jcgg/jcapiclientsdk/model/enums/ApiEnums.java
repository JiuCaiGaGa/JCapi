package com.jcgg.jcapiclientsdk.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ApiEnums {
    weather("/api/weather/localWeather","getWeather"),
    name("/api/name/user","getUsernameByPost");

    private final String path;

    private final String method;

    ApiEnums(String path, String method) {
        this.path = path;
        this.method = method;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.method).collect(Collectors.toList());
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }
}
