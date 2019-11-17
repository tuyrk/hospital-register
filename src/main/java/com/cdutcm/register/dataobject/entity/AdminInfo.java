package com.cdutcm.register.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.cdutcm.register.enums.AdminTypeEnum;
import com.cdutcm.register.enums.SexEnum;
import com.cdutcm.register.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/1/15 17:43 星期二
 * Description:
 * 管理员
 */
@Data
public class AdminInfo {
    // 管理员ID
    @TableId
    private String adminId;
    // 管理员姓名
    private String adminName;
    // 管理员性别 0男、1女
    private Integer adminSex;
    // 管理员手机号
    private String adminPhone;
    // 管理员类型 0系统管理员、1医院管理员
    private Integer adminType;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;

    @JsonIgnore
    public SexEnum getSexEnumByCode() {
        return EnumUtil.getByCode(adminSex, SexEnum.class);
    }

    @JsonIgnore
    public AdminTypeEnum getAdminTypeEnumByCode() {
        return EnumUtil.getByCode(adminType, AdminTypeEnum.class);
    }
}
