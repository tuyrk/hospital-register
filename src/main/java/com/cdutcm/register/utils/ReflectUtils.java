package com.cdutcm.register.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/16 13:20 星期六
 * Description:
 */
public class ReflectUtils {
    private static Logger log = LoggerFactory.getLogger(ReflectUtils.class);

    public static void setFieldValue(Object object, String fieldName, Object fieldValue) {
        try {
            // 1.根据属性名称就可以获取其set方法
            String setMethodName = "set" + getFieldName(fieldName);
            // 2.获取方法对象
            Class c = object.getClass();
            Method m = c.getMethod(setMethodName, fieldValue.getClass());
            // 3.通过方法的反射操作方法
            m.invoke(object, fieldValue);
        } catch (Exception e) {
            log.error("【反射异常】反射参数异常，object = {}，fieldName = {}，fieldValue = {}", object.getClass(), fieldName, fieldValue);
        }
    }

    private static String getFieldName(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
