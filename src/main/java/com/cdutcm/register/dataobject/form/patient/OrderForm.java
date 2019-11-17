package com.cdutcm.register.dataobject.form.patient;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/14 15:42 星期四
 * Description:
 */
@Data
public class OrderForm {
    @ApiModelProperty("患者ID")
    @NotBlank(message = "patientId不能为空")
    private String patientId;
    @ApiModelProperty("排班ID")
    @NotBlank(message = "scheduleId不能为空")
    private String scheduleId;
}
