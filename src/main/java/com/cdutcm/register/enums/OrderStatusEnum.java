package com.cdutcm.register.enums;

import lombok.Getter;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/1/15 18:13 星期二
 * Description:
 * 挂号记录类型
 */
@Getter
public enum OrderStatusEnum implements CodeEnum {
    WAIT(0, "待缴费"),
    SUCCESS(1, "缴费成功"),
    FAILURE(2, "已过期");

    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
