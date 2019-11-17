package com.cdutcm.register.dao;

import com.cdutcm.register.RegisterApplicationTests;
import com.cdutcm.register.dataobject.dto.ScheduleDTO;
import com.cdutcm.register.dataobject.entity.ScheduleInfo;
import com.cdutcm.register.utils.GsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ScheduleInfoDaoTest extends RegisterApplicationTests {

    @Autowired
    private ScheduleInfoDao scheduleInfoDao;

    @Test
    public void selectList() {
        List<ScheduleInfo> scheduleInfoList = scheduleInfoDao.selectList(null);
        System.out.println("scheduleInfoList = " + GsonUtil.prettyPrinting(scheduleInfoList));
    }

    @Test
    public void selectEffectiveList() {
        List<ScheduleDTO> scheduleDTOList = scheduleInfoDao.selectScheduleTable();
        System.out.println("scheduleDTOList = " + scheduleDTOList);
    }
}
