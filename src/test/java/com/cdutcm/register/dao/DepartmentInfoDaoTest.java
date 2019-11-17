package com.cdutcm.register.dao;

import com.cdutcm.register.RegisterApplicationTests;
import com.cdutcm.register.utils.GsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentInfoDaoTest extends RegisterApplicationTests {
    @Autowired
    private DepartmentInfoDao departmentInfoDao;

    @Test
    public void getDepartmentMapNameId() {
        Map<String, Integer> departmentMapNameId = departmentInfoDao.selectDepartmentMapNameId();
        System.out.println("departmentMapNameId = " + GsonUtil.prettyPrinting(departmentMapNameId));
    }
}