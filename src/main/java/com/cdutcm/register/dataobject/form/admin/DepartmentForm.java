package com.cdutcm.register.dataobject.form.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/17 10:23 星期日
 * Description:
 */
@Data
public class DepartmentForm {
    @ApiModelProperty("科室编号")
    @NotBlank(message = "科室编号不能为空")
    private String departmentNumber;
    @ApiModelProperty("科室名称")
    @NotBlank(message = "科室名称不能为空")
    private String departmentName;
    @ApiModelProperty("负责人")
    @NotBlank(message = "负责人不能为空")
    private String principal;
    @ApiModelProperty("联系方式")
    @NotBlank(message = "联系方式不能为空")
    private String principalPhone;
}
