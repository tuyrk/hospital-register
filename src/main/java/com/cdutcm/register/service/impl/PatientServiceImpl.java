package com.cdutcm.register.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.register.constant.RedisConstant;
import com.cdutcm.register.dao.PatientInfoDao;
import com.cdutcm.register.dataobject.entity.PatientInfo;
import com.cdutcm.register.dataobject.form.PageForm;
import com.cdutcm.register.dataobject.form.PatientConditionForm;
import com.cdutcm.register.dataobject.form.PatientForm;
import com.cdutcm.register.dataobject.vo.patient.PatientVO;
import com.cdutcm.register.enums.PatientStatusEnum;
import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.cdutcm.register.properties.RegisterProperties;
import com.cdutcm.register.service.PatientService;
import com.cdutcm.register.service.RedisOperator;
import com.cdutcm.register.utils.KeyUtil;
import com.cdutcm.register.utils.converter.Info2VO;
import com.cdutcm.register.utils.converter.PageForm2Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/10 22:04 星期日
 * Description:
 * 患者Service
 */
@Slf4j
@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientInfoDao patientInfoDao;
    @Autowired
    private RegisterProperties registerProperties;
    @Autowired
    private RedisOperator redisOperator;

    @Override
    public List<PatientVO> getPatientList(String openid) {
        LambdaQueryWrapper<PatientInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PatientInfo::getOpenid, openid)
                .eq(PatientInfo::getStatus, PatientStatusEnum.BIND.getCode());//绑定状态
        List<PatientInfo> patientInfoList = patientInfoDao.selectList(queryWrapper);
        return Info2VO.convert(patientInfoList, PatientVO.class);
    }

    @Override
    public void savePatient(PatientInfo patientInfo) {
        try {
            patientInfoDao.insert(patientInfo);
        } catch (Exception e) {
            log.error("【绑定患者】添加插入错误，patientInfo = {}", patientInfo);
            throw new RegisterException(ResultEnum.PATIENT_SAVE_ERROR.getCode(), e.getMessage());
        }
    }

    @Override
    public void savePatient(PatientForm patientForm, String token) {
        PatientInfo patientInfo = Info2VO.convert(patientForm, PatientInfo.class);
        patientInfo.setPatientId(KeyUtil.getUniqueKeyTime());
        patientInfo.setOpenid(redisOperator.get(String.format(RedisConstant.TOKEN_PATIENT, token)));
        savePatient(patientInfo);
    }

    @Override
    public void removePatient(String patientId) {
        PatientInfo patientInfo = patientInfoDao.selectById(patientId);
        if (patientInfo == null) {
            log.error("【解除绑定】患者不存在，patientId = {}", patientId);
            throw new RegisterException(ResultEnum.PATIENT_NOT_EXIST);
        }
        patientInfo.setStatus(PatientStatusEnum.REMOVE.getCode());
        try {
            patientInfoDao.updateById(patientInfo);
        } catch (Exception e) {
            log.error("【解除绑定】患者解绑失败，patientId = {}", patientId);
            throw new RegisterException(ResultEnum.PATIENT_REMOVE_ERROR);
        }
    }

    @Override
    public IPage<PatientVO> getPatientList(PatientConditionForm conditionForm, PageForm page) {
        LambdaQueryWrapper<PatientInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (conditionForm.getPatientName() != null) {
            queryWrapper.eq(PatientInfo::getPatientName, conditionForm.getPatientName());
        }
        if (conditionForm.getPatientCard() != null) {
            queryWrapper.eq(PatientInfo::getPatientCard, conditionForm.getPatientCard());
        }
        if (conditionForm.getPatientPhone() != null) {
            queryWrapper.eq(PatientInfo::getPatientPhone, conditionForm.getPatientPhone());
        }
        IPage<PatientInfo> patientInfoPage = patientInfoDao.selectPage(
                PageForm2Page.convert(page, registerProperties.getPage().getPage(), registerProperties.getPage().getSize()),
                queryWrapper);
        return Info2VO.convert(patientInfoPage, PatientVO.class);
    }
}
