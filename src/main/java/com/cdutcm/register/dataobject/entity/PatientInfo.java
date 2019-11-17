package com.cdutcm.register.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.cdutcm.register.enums.PatientStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/1/15 17:51 星期二
 * Description:
 * 患者信息
 */
@Data
public class PatientInfo {
    // 患者ID
    @TableId
    private String patientId;
    // 患者姓名
    private String patientName;
    // 患者身份证号
    private String patientCard;
    // 患者手机号
    private String patientPhone;
    // 患者邮箱
    private String patientMail;
    // 绑定微信号openid
    private String openid;
    //当前是否已经挂号 0false未挂号，1true已挂号
    private Boolean place;
    //绑定为1，解绑为0
    private Integer status = PatientStatusEnum.BIND.getCode();
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
}
