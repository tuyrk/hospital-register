package com.cdutcm.register.dataobject.form.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/16 11:47 星期六
 * Description:
 */
@Slf4j
@Data
public class ScheduleForm {
    @ApiModelProperty("排班时间,格式如：2019-02-14 上午")
    @NotBlank(message = "排班时间不能为空")
    private String scheduleTime;
    @ApiModelProperty("间隔时间，单位分钟")
    @Min(value = 1, message = "间隔时间必须大于0")
    private Integer intervalTime;
    @ApiModelProperty("医生ID")
    @NotBlank(message = "医生ID不能为空")
    private String doctorId;
}
