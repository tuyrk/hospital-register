package com.cdutcm.register.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/25 19:24 星期一
 * Description:
 */
public class WeekStartEnd {
    // 获得当前周周一的日期
    public static Date getCurrentMonday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.add(Calendar.DAY_OF_MONTH, getMondayPlus());
        return calendar.getTime();
    }


    // 获得当前周周日的日期
    public static Date getPreviousSunday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.add(Calendar.DAY_OF_MONTH, getMondayPlus() + 6);
        return calendar.getTime();
    }

    // 获得本周一与当前日期相差的天数
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }
}
