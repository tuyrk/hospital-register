package com.cdutcm.register.dataobject.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/15 16:59 星期五
 * Description:
 */
@Data
public class DoctorConditionForm {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("医生姓名")
    private String doctorName;
    @ApiModelProperty("科室")
    private String department;
    @ApiModelProperty("身份证号")
    private String doctorCard;
}