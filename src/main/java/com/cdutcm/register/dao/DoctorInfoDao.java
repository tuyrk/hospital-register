package com.cdutcm.register.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdutcm.register.dataobject.service.DoctorInfoService;
import com.cdutcm.register.dataobject.vo.patient.DoctorVO;
import com.cdutcm.register.dataobject.entity.DoctorInfo;
import com.cdutcm.register.dataobject.mapper.DoctorInfoMapper;
import com.cdutcm.register.enums.DoctorStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/10 11:00 星期日
 * Description:
 */
@Component
public class DoctorInfoDao {
    @Autowired
    private DoctorInfoMapper doctorInfoMapper;
    @Autowired
    private DoctorInfoService doctorInfoService;

    public Boolean insert(DoctorInfo doctorInfo) {
        return doctorInfoMapper.insert(doctorInfo) == 1;
    }

    public Boolean insertBatch(Collection<DoctorInfo> entityList) {
        return doctorInfoService.saveBatch(entityList);
//        return doctorInfoMapper.insertBatch(entityList);
    }

    public Boolean deleteById(String doctorId) {
        return doctorInfoMapper.deleteById(doctorId) > 0;
    }

    public Boolean updateById(DoctorInfo doctorInfo) {
        return doctorInfoMapper.updateById(doctorInfo) == 1;
    }

    public DoctorInfo selectById(String doctorId) {
        return doctorInfoMapper.selectById(doctorId);
    }

    public IPage<DoctorInfo> selectPage(Page<DoctorInfo> page, LambdaQueryWrapper<DoctorInfo> queryWrapper) {
        queryWrapper.eq(DoctorInfo::getStatus, DoctorStatusEnum.BIND.getCode());
        return doctorInfoMapper.selectPage(page, queryWrapper);
    }

    //查询已排班的医生列表
    public List<DoctorVO> selectSchedule(String departmentId, Calendar datetime) {
        return doctorInfoMapper.selectSchedule(departmentId, datetime.getTime());
    }

    public DoctorInfo selectByUsername(String username) {
        return doctorInfoMapper.selectOne(new LambdaQueryWrapper<DoctorInfo>()
                .eq(DoctorInfo::getUsername, username)
                .eq(DoctorInfo::getStatus, DoctorStatusEnum.BIND.getCode()));
    }

    public List<DoctorInfo> selectAllList() {
        LambdaQueryWrapper<DoctorInfo> queryWrapper = new LambdaQueryWrapper<DoctorInfo>().eq(DoctorInfo::getStatus, DoctorStatusEnum.BIND.getCode());
        return doctorInfoMapper.selectList(queryWrapper);
    }

    public List<String> selectAllUsername() {
        return doctorInfoMapper.selectAllUsername();
    }

    public List<String> getDoctorIdByNameDepartmentId(String doctorName) {
        return doctorInfoMapper.selectDoctorIdByName(doctorName);
    }

    public List<DoctorInfo> selectList(LambdaQueryWrapper<DoctorInfo> queryWrapper) {
        queryWrapper.eq(DoctorInfo::getStatus, DoctorStatusEnum.BIND.getCode());
        return doctorInfoMapper.selectList(queryWrapper);
    }
}
