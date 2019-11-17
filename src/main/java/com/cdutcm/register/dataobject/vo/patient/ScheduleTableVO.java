package com.cdutcm.register.dataobject.vo.patient;

import lombok.Data;

import java.util.List;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/12 13:21 星期二
 * Description:
 */
@Data
public class ScheduleTableVO {
    private String scheduleTime;
    private List<String> doctors;
}
