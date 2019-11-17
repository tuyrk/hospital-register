package com.cdutcm.register.dataobject.vo.admin;

import com.cdutcm.register.enums.AdminTypeEnum;
import com.cdutcm.register.enums.SexEnum;
import com.cdutcm.register.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/15 15:48 星期五
 * Description:
 */
@Data
public class AdminVO {
    private String adminId;
    private String username;
    private String adminName;
    @JsonIgnore
    private Integer adminSex;
    private String adminPhone;
    @JsonIgnore
    private Integer adminType;

    @JsonProperty("adminType")
    public String getAdminType() {
        return EnumUtil.getByCode(adminType, AdminTypeEnum.class).getMsg();
    }

    @JsonProperty("adminSex")
    public String getAdminSex() {
        return EnumUtil.getByCode(adminSex, SexEnum.class).getMsg();
    }
}