package com.cdutcm.register.enums;

import lombok.Getter;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/1/15 18:02 星期二
 * Description:
 * 管理员类型
 */
@Getter
public enum  AdminTypeEnum implements CodeEnum{
    SYSTEM(0, "系统管理员"),
    CLINIC(1, "医院管理员");

    private Integer code;
    private String msg;

    AdminTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}