package com.cdutcm.register.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.cdutcm.register.enums.SexEnum;
import com.cdutcm.register.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/1/15 17:47 星期二
 * Description:
 * 医生
 */
@Data
public class DoctorInfo {
    // 医生ID
    @TableId
    private String doctorId;
    // 医生照片
    private String doctorPhoto;
    // 医生姓名
    private String doctorName;
    // 医生性别 0男、1女
    private Integer doctorSex;
    // 医生身份证号
    private String doctorCard;
    // 科室ID
    private Integer departmentId;
    // 医生手机号
    private String doctorPhone;
    // 医生邮箱
    private String doctorMail;
    // 职务/职称
    private String doctorPost;
    // 擅长
    private String doctorAdept;
    // 介绍
    private String doctorDetail;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 状态 0 false 删除，1 true 存在
    private Integer status;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;

    @JsonIgnore
    public SexEnum getSexEnumByCode() {
        return EnumUtil.getByCode(doctorSex, SexEnum.class);
    }
}
