package com.cdutcm.register.dataobject.mapper;

import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;
import com.cdutcm.register.RegisterApplicationTests;
import com.cdutcm.register.dataobject.entity.AdminInfo;
import com.cdutcm.register.enums.AdminTypeEnum;
import com.cdutcm.register.enums.SexEnum;
import com.cdutcm.register.utils.KeyUtil;
import com.cdutcm.register.utils.MD5Utils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class AdminInfoMapperTest extends RegisterApplicationTests {
    @Autowired
    private AdminInfoMapper adminInfoMapper;

    @Test
    public void insert() {
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setAdminId(KeyUtil.getUniqueKeyTime());
        adminInfo.setAdminName("涂元坤");
        adminInfo.setAdminSex(SexEnum.MALE.getCode());
        adminInfo.setAdminPhone("18382471393");
        adminInfo.setAdminType(AdminTypeEnum.SYSTEM.getCode());
        adminInfo.setUsername("766564616");
        adminInfo.setPassword(MD5Utils.encrypt("123456", MD5Utils.SHA));
        Integer result = adminInfoMapper.insert(adminInfo);
        assertEquals(Integer.valueOf(1), result);
    }
}