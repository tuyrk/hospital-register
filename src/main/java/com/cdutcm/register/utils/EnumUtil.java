package com.cdutcm.register.utils;

import com.cdutcm.register.enums.CodeEnum;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/1/15 18:41 星期二
 * Description:
 */
public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T e : enumClass.getEnumConstants()) {
            if (code.equals(e.getCode())) {
                return e;
            }
        }
        return null;
    }

    public static <T extends CodeEnum> T getByMsg(String msg, Class<T> enumClass) {
        for (T e : enumClass.getEnumConstants()) {
            if (msg.equals(e.getMsg())) {
                return e;
            }
        }
        return null;
    }
}
