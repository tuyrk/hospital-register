package com.cdutcm.register.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/1/15 17:45 星期二
 * Description:
 * 医院/诊所
 */
@Data
public class ClinicInfo {
    // 医院ID
    @TableId
    @JsonIgnore
    private Integer clinicId;
    // 医院名称
    @JsonIgnore
    private String clinicName;
    // 医院简介
    @JsonIgnore
    private String clinicSynopsis;
    // 楼层简介
    private String floorSynopsis;
    // 医院位置
    private String clinicPosition;
    // 创建时间
    @JsonIgnore
    private Date createTime;
    // 更新时间
    @JsonIgnore
    private Date updateTime;
}
