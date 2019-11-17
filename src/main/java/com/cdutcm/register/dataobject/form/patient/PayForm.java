package com.cdutcm.register.dataobject.form.patient;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/25 21:39 星期一
 * Description:
 */
@Data
public class PayForm {
    @ApiModelProperty("订单号")
    @NotBlank(message = "orderId不能为空")
    private String orderId;
}
