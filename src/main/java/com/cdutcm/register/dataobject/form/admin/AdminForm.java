package com.cdutcm.register.dataobject.form.admin;

import com.cdutcm.register.enums.AdminTypeEnum;
import com.cdutcm.register.enums.SexEnum;
import com.cdutcm.register.utils.EnumUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/15 12:35 星期五
 * Description:
 */
@Data
public class AdminForm {
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @ApiModelProperty("用户姓名")
    @NotBlank(message = "用户姓名不能为空")
    private String adminName;
    @ApiModelProperty("性别：男、女")
    @NotBlank(message = "性别不能为空")
    private String adminSex;
    @ApiModelProperty("联系方式")
    @NotBlank(message = "联系方式不能为空")
    private String adminPhone;
    @ApiModelProperty("用户类型：系统管理员、医院管理员")
    @NotBlank(message = "用户类型不能为空")
    private String adminType;

    public Integer getAdminType() {
        return EnumUtil.getByMsg(adminType, AdminTypeEnum.class).getCode();
    }

    public Integer getAdminSex() {
        return EnumUtil.getByMsg(adminSex, SexEnum.class).getCode();
    }
}
