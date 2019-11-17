package com.cdutcm.register.enums;

import lombok.Getter;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/11 12:39 星期一
 * Description:
 */
@Getter
public enum ResultEnum {
    SUCCESS(0, "成功"),
    INTERNAL_ERROR(1, "内部错误"),
    PARAM_ERROR(1, "参数错误"),
    PARAM_IS_EMPTY(1, "参数为空"),
    FORMAT_CONVERT_ERROR(6, "格式转换错误"),

    ADMIN_SAVE_ERROR(1, "保存管理员错误"),
    ADMIN_USERNAME_EXIST(1, "管理员用户名重复"),
    ADMIN_NOT_EXIST(1, "管理员用户不存在"),
    ADMIN_DELETE_ERROR(1, "删除管理员错误"),

    DEPARTMENT_NOT_EXIST(7, "科室名不存在"),
    DEPARTMENT_SAVE_ERROR(1, "保存科室错误"),
    DEPARTMENT_NUMBER_EXIST(1, "科室编号已存在"),
    DEPARTMENT_NAME_EXIST(1, "科室编号已存在"),

    DOCTOR_SAVE_ERROR(1, "保存医生错误"),
    DOCTOR_NOT_EXIST(7, "医生不存在"),
    DOCTOR_USERNAME_EXIST(1, "医生用户名重复"),
    DOCTOR_REMOVE_ERROR(4, "医生删除失败"),

    FILE_INIT_ERROR(1, "文件初始化失败"),
    FILE_DATA_ERROR(1, "文件数据错误"),
    FILE_IS_EMPTY(1, "文件为空"),
    FILE_LOAD_ERROR(1, "文件加载失败"),

    ORDER_NOT_EXIST(1, "订单不存在"),
    ORDER_IS_ERROR(1, "订单信息错误"),
    ORDER_IS_PAY(1, "订单已支付"),
    ORDER_IS_TIMEOUT(1, "订单已过期"),

    PATIENT_SAVE_ERROR(2, "保存患者错误"),
    PATIENT_NOT_EXIST(3, "患者信息不存在"),
    PATIENT_REMOVE_ERROR(4, "患者解绑失败"),
    PATIENT_IS_PLACE(1, "病人已经有挂号"),

    SCHEDULE_SAVE_ERROR(5, "保存排班信息错误"),
    SCHEDULE_NOT_EXIST(5, "排班信息不存在"),
    SCHEDULE_IS_EXPIRE(5, "排班信息已过期"),
    SCHEDULE_IS_PLACE(5, "排班号源已预定"),
    SCHEDULE_IS_EXIST(1, "排班信息已存在"),

    WECHAT_MP_ERROR(1, "微信公众号错误"),

    AUTH_ADMIN_ERROR(1, "管理员未登录"),
    AUTH_PATIENT_ERROR(1, "用户未登录"),
    USERNAME_PASSWORD_ERROR(1, "用户名或密码错误"),;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
