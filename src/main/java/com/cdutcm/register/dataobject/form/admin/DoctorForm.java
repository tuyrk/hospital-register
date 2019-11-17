package com.cdutcm.register.dataobject.form.admin;

import com.cdutcm.register.enums.SexEnum;
import com.cdutcm.register.utils.EnumUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/15 16:59 星期五
 * Description:
 */
@Data
public class DoctorForm {
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @ApiModelProperty("医生姓名")
    @NotBlank(message = "医生姓名不能为空")
    private String doctorName;
    @ApiModelProperty("性别")
    private String doctorSex;
    @ApiModelProperty("身份证号")
    @NotBlank(message = "身份证号不能为空")
    private String doctorCard;
    @ApiModelProperty("科室")
    @NotBlank(message = "科室不能为空")
    private String department;
    @ApiModelProperty("联系电话")
    private String doctorPhone;
    @ApiModelProperty("邮箱")
    private String doctorMail;

    public Integer getDoctorSex() {
        if (doctorSex.isEmpty()) {
            return null;
        }
        return EnumUtil.getByMsg(doctorSex, SexEnum.class).getCode();
    }
}