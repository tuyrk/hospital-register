package com.cdutcm.register.dataobject.dto;

import lombok.Data;

import java.util.Date;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/12 18:12 星期二
 * Description:
 */
@Data
public class ScheduleDTO {
    private String departmentName;
    private Date scheduleTime;
    private String doctorName;
    private String sourceNumber;
}
