package com.cdutcm.register.dao;

import com.cdutcm.register.RegisterApplicationTests;
import com.cdutcm.register.utils.GsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class DoctorInfoDaoTest extends RegisterApplicationTests {
    @Autowired
    private DoctorInfoDao doctorInfoDao;

    @Test
    public void selectAllUsername() {
        List<String> allUsername = doctorInfoDao.selectAllUsername();
        System.out.println("allUsername = " + GsonUtil.prettyPrinting(allUsername));
    }
}