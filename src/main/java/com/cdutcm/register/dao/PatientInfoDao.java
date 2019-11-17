package com.cdutcm.register.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdutcm.register.dataobject.entity.PatientInfo;
import com.cdutcm.register.dataobject.mapper.PatientInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/10 11:02 星期日
 * Description:
 */
@Component
public class PatientInfoDao {
    @Autowired
    private PatientInfoMapper patientInfoMapper;
    public Boolean insert(PatientInfo patientInfo) {
        return patientInfoMapper.insert(patientInfo) == 1;
    }

    public Boolean deleteById(String patientId) {
        return patientInfoMapper.deleteById(patientId) > 0;
    }

    public Boolean updateById(PatientInfo patientInfo) {
        return patientInfoMapper.updateById(patientInfo) == 1;
    }

    public PatientInfo selectById(String patientId) {
        return patientInfoMapper.selectById(patientId);
    }

    public List<PatientInfo> selectList(Wrapper<PatientInfo> queryWrapper) {
        return patientInfoMapper.selectList(queryWrapper);
    }

    public IPage<PatientInfo> selectPage(Page<PatientInfo> page, Wrapper<PatientInfo> queryWrapper) {
        return patientInfoMapper.selectPage(page, queryWrapper);
    }
}
