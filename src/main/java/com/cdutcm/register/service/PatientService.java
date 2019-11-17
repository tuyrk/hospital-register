package com.cdutcm.register.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.register.dataobject.entity.PatientInfo;
import com.cdutcm.register.dataobject.form.PageForm;
import com.cdutcm.register.dataobject.form.PatientConditionForm;
import com.cdutcm.register.dataobject.form.PatientForm;
import com.cdutcm.register.dataobject.vo.patient.PatientVO;

import java.util.List;
import java.util.Map;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/10 22:16 星期日
 * Description:
 */
public interface PatientService {
    List<PatientVO> getPatientList(String openid);

    void savePatient(PatientInfo patientInfo);

    void savePatient(PatientForm patientForm, String token);

    void removePatient(String patientId);

    IPage<PatientVO> getPatientList(PatientConditionForm patientConditionForm, PageForm page);
}
