package com.cdutcm.register.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdutcm.register.RegisterApplicationTests;
import com.cdutcm.register.dataobject.entity.AdminInfo;
import com.cdutcm.register.enums.AdminTypeEnum;
import com.cdutcm.register.enums.SexEnum;
import com.cdutcm.register.utils.GsonUtil;
import com.cdutcm.register.utils.KeyUtil;
import com.cdutcm.register.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@Slf4j
public class AdminInfoDaoTest extends RegisterApplicationTests {

    @Autowired
    private AdminInfoDao adminInfoDao;

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
        Boolean result = adminInfoDao.insertAdmin(adminInfo);
        assertEquals(true, result);
    }

    @Test
    public void deleteById() {
        Boolean result = adminInfoDao.deleteById("1549789322796907486");
        assertEquals(true, result);
    }

    @Test
    public void updateById() {
        AdminInfo adminInfo = adminInfoDao.selectById("1549789437403462234");
        adminInfo.setAdminName("TYK");
        Boolean result = adminInfoDao.updateById(adminInfo);
        assertEquals(true, result);
    }

    @Test
    public void selectPage() {
        Page<AdminInfo> page = new Page<>(1, 10);
        LambdaQueryWrapper<AdminInfo> queryWrapper = new LambdaQueryWrapper<>();
        IPage<AdminInfo> adminInfoIPage = adminInfoDao.selectPage(page, queryWrapper);
        String adminInfoIPageStr = GsonUtil.prettyPrinting(adminInfoIPage);
        log.info("adminInfoIPageStr = {}", adminInfoIPageStr);
        assertNotEquals(null, adminInfoIPage);
    }
}