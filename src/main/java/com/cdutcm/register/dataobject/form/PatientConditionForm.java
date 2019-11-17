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
public class PatientConditionForm {
    @ApiModelProperty("姓名")
    private String patientName;
    @ApiModelProperty("身份证号")
    private String patientCard;
    @ApiModelProperty("手机号码")
    private String patientPhone;
}
