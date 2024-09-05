package com.jcgg.project.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  用户调用接口状态枚举
 *
 */
public enum UserInterfaceStatusEnum {


    AVAIL("可用",1),
    NA("不可用",0);

    private final String text;

    private final int value;

    UserInterfaceStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
