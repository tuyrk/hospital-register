package com.cdutcm.register.dataobject.vo.patient;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/12 13:08 星期二
 * Description:
 */
@Data
public class SchedulePatientVO {
    private String department;
    private List<Map> data;

    public SchedulePatientVO() {
    }

    public SchedulePatientVO(String department, List<Map> data) {
        this.department = department;
        this.data = data;
    }
}