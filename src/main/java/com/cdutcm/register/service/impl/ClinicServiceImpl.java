package com.cdutcm.register.service.impl;

import com.cdutcm.register.dao.ClinicInfoDao;
import com.cdutcm.register.dataobject.entity.ClinicInfo;
import com.cdutcm.register.dataobject.form.admin.ClinicForm;
import com.cdutcm.register.properties.RegisterProperties;
import com.cdutcm.register.service.ClinicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/14 22:00 星期四
 * Description:
 */
@Service
public class ClinicServiceImpl implements ClinicService {
    @Autowired
    private ClinicInfoDao clinicInfoDao;

    private Integer clinicId;
    private String clinicName;

    public ClinicServiceImpl(RegisterProperties registerProperties) {
        this.clinicId = registerProperties.getClinic().getClinicId();
        this.clinicName = registerProperties.getClinic().getClinicName();
    }

    @Override
    public ClinicForm getFloorDetail() {
        return clinicInfoDao.selectFloorDetailById(clinicId);
    }

    @Override
    public String getClinicSynopsis() {
        return clinicInfoDao.selectSynopsisById(clinicId).getClinicSynopsis();
    }

    @Override
    public void modifyClinic(ClinicForm clinicForm) {
        ClinicInfo clinicInfo = clinicInfoDao.selectById(clinicId);
        if (clinicInfo == null) {
            clinicInfo = new ClinicInfo();
            BeanUtils.copyProperties(clinicForm, clinicInfo);
            clinicInfo.setClinicId(clinicId);//设置医院ID
            clinicInfo.setClinicName(clinicName);
            clinicInfoDao.insert(clinicInfo);
            return;
        }
        if (clinicForm.getClinicSynopsis() != null) {
            clinicInfo.setClinicSynopsis(clinicForm.getClinicSynopsis());
        }
        if (clinicForm.getFloorSynopsis() != null) {
            clinicInfo.setFloorSynopsis(clinicForm.getFloorSynopsis());
        }
        if (clinicForm.getClinicPosition() != null) {
            clinicInfo.setClinicPosition(clinicForm.getClinicPosition());
        }
        clinicInfoDao.updateById(clinicInfo);

    }
}
