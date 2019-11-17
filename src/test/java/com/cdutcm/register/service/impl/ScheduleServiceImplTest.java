package com.cdutcm.register.service.impl;

import com.cdutcm.register.RegisterApplicationTests;
import com.cdutcm.register.dataobject.vo.admin.ScheduleAdminVO;
import com.cdutcm.register.dataobject.vo.patient.SchedulePatientVO;
import com.cdutcm.register.service.ScheduleService;
import com.cdutcm.register.utils.GsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ScheduleServiceImplTest extends RegisterApplicationTests {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleServiceImpl scheduleServiceImpl;

    @Test
    public void getScheduleList() {
        List<SchedulePatientVO> scheduleList = scheduleService.getScheduleList();
        System.out.println("scheduleList = " + GsonUtil.prettyPrinting(scheduleList));
    }

    @Test
    public void getScheduleTime() {
//        //需将getScheduleTime方法设置为public
//        Calendar scheduleTime = scheduleServiceImpl.getScheduleTime("2019-02-14 上午","09:00","13:30");//上/下午开始时间
//        System.out.println("scheduleTime = " + scheduleTime);
//        scheduleTime = scheduleServiceImpl.getScheduleTime("2019-02-14 下午","12:00","17:00");//上/下午结束时间
//        System.out.println("scheduleTime = " + scheduleTime);
    }

    @Test
    public void getSchedule() {
        List<ScheduleAdminVO> schedule = scheduleService.getSchedule("94127411098336876272");
        System.out.println("schedule = " + GsonUtil.prettyPrinting(schedule));
    }
}