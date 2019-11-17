package com.cdutcm.register.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/1/15 17:53 星期二
 * Description:
 * 排班信息
 */
@Data
public class ScheduleInfo {
    // 排班ID
    @TableId
    private String scheduleId;
    // 排班时间
    private Date scheduleTime;
    // 医生ID
    private String doctorId;
    // 挂号金额
    private Double scheduleMoney;
    // 号源状态(false未使用，true已使用)
    private Boolean status;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
}
