package com.cdutcm.register.service;

import com.cdutcm.register.dataobject.form.admin.ClinicForm;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/14 21:59 星期四
 * Description:
 */
public interface ClinicService {
    ClinicForm getFloorDetail();

    String getClinicSynopsis();

    void modifyClinic(ClinicForm clinicForm);
}
