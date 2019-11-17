package com.cdutcm.register.dataobject.form.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/1/15 17:45 星期二
 * Description:
 * 医院/诊所
 */
@Data
public class ClinicForm {
    @ApiModelProperty("医院简介")
    private String clinicSynopsis;
    @ApiModelProperty("楼层简介")
    private String floorSynopsis;
    @ApiModelProperty("医院位置")
    private String clinicPosition;
}
