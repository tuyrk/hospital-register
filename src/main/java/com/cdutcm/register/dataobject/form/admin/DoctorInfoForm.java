package com.cdutcm.register.dataobject.form.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/1/15 17:47 星期二
 * Description:
 * 医生
 */
@Data
public class DoctorInfoForm {
    @ApiModelProperty("医生ID")
    @NotBlank(message = "医生ID不能为空")
    private String doctorId;
    @ApiModelProperty("职务/职称")
    private String doctorPost;
    @ApiModelProperty("擅长")
    private String doctorAdept;
    @ApiModelProperty("介绍")
    private String doctorDetail;
}
