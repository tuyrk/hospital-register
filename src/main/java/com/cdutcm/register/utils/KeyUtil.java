package com.cdutcm.register.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/1/15 20:23 星期二
 * Description:
 */
public class KeyUtil {
    public static synchronized String getUniqueKeyPrettyTime() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        Calendar currentTime = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(currentTime.getTime()) + String.valueOf(number);
    }

    public static synchronized String getUniqueKeyTime() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.nanoTime() + String.valueOf(number);
    }
}
