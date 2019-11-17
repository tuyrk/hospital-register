package com.cdutcm.register.dataobject.form.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/26 19:56 星期二
 * Description:
 * 管理员登录表单
 */
@Data
public class AdminAuthForm {
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
