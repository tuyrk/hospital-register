package com.cdutcm.register.dataobject.form.admin;

import com.cdutcm.register.enums.AdminTypeEnum;
import com.cdutcm.register.utils.EnumUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/15 15:53 星期五
 * Description:
 */
@Data
public class AdminConditionForm {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("用户姓名")
    private String adminName;
    @ApiModelProperty("用户类型：系统管理员、医院管理员")
    private String adminType;

    public Integer getAdminTypeCode() {
        return EnumUtil.getByMsg(adminType, AdminTypeEnum.class).getCode();
    }
}
