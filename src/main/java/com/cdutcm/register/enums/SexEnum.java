package com.cdutcm.register.enums;

import lombok.Getter;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/1/15 17:58 星期二
 * Description:
 * 性别
 */
@Getter
public enum SexEnum implements CodeEnum {
    MALE(0, "男"),
    FEMALE(1, "女");

    private Integer code;
    private String msg;

    SexEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}