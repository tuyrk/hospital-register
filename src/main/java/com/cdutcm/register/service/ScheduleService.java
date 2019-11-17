package com.cdutcm.register.service;

import com.cdutcm.register.dataobject.form.admin.ScheduleForm;
import com.cdutcm.register.dataobject.vo.admin.ScheduleAdminVO;
import com.cdutcm.register.dataobject.vo.patient.SchedulePatientVO;
import com.cdutcm.register.dataobject.vo.patient.SourceNumberMasterVO;

import java.util.List;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/12 13:04 星期二
 * Description:
 */
public interface ScheduleService {
    //获取所有排班信息
    List<SchedulePatientVO> getScheduleList();

    //通过医生ID获取该医生的排班信息，即号源
    List<SourceNumberMasterVO> getScheduleByDoctorId(String doctorId);

    //维护排班信息，即添加排班信息
    void maintainSchedule(List<ScheduleForm> scheduleFormList);

    List<ScheduleAdminVO> getSchedule(String doctorId);
}
