package com.cdutcm.register.service.impl;

import ch.qos.logback.core.rolling.helper.RenameUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.register.constant.RedisConstant;
import com.cdutcm.register.dao.AdminInfoDao;
import com.cdutcm.register.dataobject.entity.AdminInfo;
import com.cdutcm.register.dataobject.form.admin.AdminAuthForm;
import com.cdutcm.register.dataobject.form.admin.AdminConditionForm;
import com.cdutcm.register.dataobject.form.admin.AdminForm;
import com.cdutcm.register.dataobject.form.PageForm;
import com.cdutcm.register.dataobject.vo.admin.AdminVO;
import com.cdutcm.register.enums.AdminTypeEnum;
import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.cdutcm.register.properties.RegisterProperties;
import com.cdutcm.register.service.AdminService;
import com.cdutcm.register.service.RedisOperator;
import com.cdutcm.register.utils.EnumUtil;
import com.cdutcm.register.utils.KeyUtil;
import com.cdutcm.register.utils.MD5Utils;
import com.cdutcm.register.utils.converter.Info2VO;
import com.cdutcm.register.utils.converter.PageForm2Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/15 12:36 星期五
 * Description:
 */
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminInfoDao adminInfoDao;
    @Autowired
    private RegisterProperties registerProperties;
    @Autowired
    private RedisOperator redisOperator;


    @Override
    public void saveAdmin(AdminForm adminForm) {
        AdminInfo adminInfo = adminInfoDao.selectByUsername(adminForm.getUsername());
        if (adminInfo != null) {
            log.error("【新增管理员】管理员用户名重复，adminForm = {}", adminForm);
            throw new RegisterException(ResultEnum.ADMIN_USERNAME_EXIST);
        }
        adminInfo = new AdminInfo();
        BeanUtils.copyProperties(adminForm, adminInfo);
        adminInfo.setAdminId(KeyUtil.getUniqueKeyTime());
        adminInfo.setAdminSex(adminForm.getAdminSex());
        adminInfo.setAdminType(adminForm.getAdminType());
        adminInfo.setPassword(MD5Utils.encrypt(registerProperties.getAdmin().getPassword(), MD5Utils.SHA));
        if (!adminInfoDao.insertAdmin(adminInfo)) {
            log.error("【新增管理员】插入数据错误，adminForm = {}", adminForm);
            throw new RegisterException(ResultEnum.ADMIN_SAVE_ERROR);
        }
    }

    @Override
    public void deleteAdmin(String adminId) {
        AdminInfo adminInfo = adminInfoDao.selectById(adminId);
        if (adminInfo == null) {
            log.error("【删除管理员】该管理员用户不存在，adminId = {}", adminId);
            throw new RegisterException(ResultEnum.ADMIN_NOT_EXIST);
        }
        if (!adminInfoDao.deleteById(adminId)) {
            log.error("【删除管理员】删除数据错误，adminId = {}", adminId);
            throw new RegisterException(ResultEnum.ADMIN_DELETE_ERROR);
        }
    }

    @Override
    public IPage<AdminVO> getAdminList(AdminConditionForm adminCondition, PageForm page) {
        LambdaQueryWrapper<AdminInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (adminCondition.getUsername() != null) {
            queryWrapper.eq(AdminInfo::getUsername, adminCondition.getUsername());
        }
        if (adminCondition.getAdminName() != null) {
            queryWrapper.eq(AdminInfo::getAdminName, adminCondition.getAdminName());
        }
        if (adminCondition.getAdminType() != null) {
            queryWrapper.eq(AdminInfo::getAdminType, adminCondition.getAdminTypeCode());
        }
        IPage<AdminInfo> adminInfoIPage = adminInfoDao.selectPage(
                PageForm2Page.convert(page, registerProperties.getPage().getPage(), registerProperties.getPage().getSize()),
                queryWrapper);
        return Info2VO.convert(adminInfoIPage, AdminVO.class);
    }

    @Override
    public Map<String, String> checkLogin(AdminAuthForm adminAuthForm) {
        LambdaQueryWrapper<AdminInfo> queryWrapper = new LambdaQueryWrapper<>();
        //构建 用户名+密码 查询条件
        queryWrapper.eq(AdminInfo::getUsername, adminAuthForm.getUsername())
                .eq(AdminInfo::getPassword, MD5Utils.encrypt(adminAuthForm.getPassword(), MD5Utils.SHA));
        AdminInfo adminInfo = adminInfoDao.selectOne(queryWrapper);
        if (adminAuthForm == null) {
            log.error("【管理员登录】用户名或密码错误，adminAuthForm = {}", adminAuthForm);
            throw new RegisterException(ResultEnum.USERNAME_PASSWORD_ERROR);
        }
        //创建token
        String token = UUID.randomUUID().toString();
        redisOperator.set(String.format(RedisConstant.TOKEN_ADMIN, token), adminInfo.getAdminId(), RedisConstant.EXPIRE);
        Map<String, String> map = new HashMap<>(2);
        map.put("token", token);
        map.put("authority", EnumUtil.getByCode(adminInfo.getAdminType(), AdminTypeEnum.class).getMsg());
        return map;
    }
}
