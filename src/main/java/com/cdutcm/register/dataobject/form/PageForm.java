package com.cdutcm.register.dataobject.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/15 15:57 星期五
 * Description:
 */
@Data
public class PageForm {
    @ApiModelProperty("分页-当前页数（默认为1）")
    private Integer page;
    @ApiModelProperty("分页-每页数量（默认为10）")
    private Integer size;
}
