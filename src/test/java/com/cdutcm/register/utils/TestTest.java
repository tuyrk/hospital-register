package com.cdutcm.register.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class TestTest {

    @Test
    public void test() {
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
            System.out.println("calendar = " + calendar.getTime());
            String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
            System.out.println(weeks[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
        }
    }
}