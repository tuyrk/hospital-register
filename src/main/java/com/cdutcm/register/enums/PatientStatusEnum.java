package com.cdutcm.register.enums;

import lombok.Getter;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/12 12:16 星期二
 * Description:
 */
@Getter
public enum PatientStatusEnum {

    REMOVE(0, "解绑状态"),
    BIND(1, "绑定状态");

    private Integer code;
    private String msg;

    PatientStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
