package com.cdutcm.register.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdutcm.register.dataobject.entity.ClinicInfo;
import com.cdutcm.register.dataobject.form.admin.ClinicForm;
import com.cdutcm.register.dataobject.mapper.ClinicInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/10 10:58 星期日
 * Description:
 */
@Component
public class ClinicInfoDao {
    @Autowired
    private ClinicInfoMapper clinicInfoMapper;

    public Boolean insert(ClinicInfo clinicInfo) {
        return clinicInfoMapper.insert(clinicInfo) == 1;
    }

    public Boolean deleteById(String clinicId) {
        return clinicInfoMapper.deleteById(clinicId) > 0;
    }

    public Boolean updateById(ClinicInfo clinicInfo) {
        return clinicInfoMapper.updateById(clinicInfo) == 1;
    }

    public ClinicInfo selectById(Integer clinicId) {
        return clinicInfoMapper.selectById(clinicId);
    }

    public IPage<ClinicInfo> selectPage(Page<ClinicInfo> page, LambdaQueryWrapper<ClinicInfo> queryWrapper) {
        return clinicInfoMapper.selectPage(page, queryWrapper);
    }

    public ClinicForm selectFloorDetailById(Integer clinicId) {
        return clinicInfoMapper.selectFloorDetailById(clinicId);
    }

    public ClinicForm selectSynopsisById(Integer clinicId) {
        return clinicInfoMapper.selectSynopsisById(clinicId);
    }
}
