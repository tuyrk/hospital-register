package com.cdutcm.register.dataobject.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/10 23:23 星期日
 * Description:
 */
@Data
public class PatientForm {
    @ApiModelProperty("患者姓名")
    @NotBlank(message = "患者姓名不能为空")
    private String patientName;
    @ApiModelProperty("身份证号")
    @NotBlank(message = "身份证号不能为空")
    private String patientCard;
    @ApiModelProperty("患者手机号码")
    @NotBlank(message = "患者手机号码不能为空")
    private String patientPhone;
    @ApiModelProperty("患者邮箱")
    @NotBlank(message = "患者邮箱不能为空")
    private String patientMail;
}
