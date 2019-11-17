package com.cdutcm.register.dataobject.vo.common;

import lombok.Data;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/4/11 17:51 星期四
 * Description:
 */
@Data
public class DepartmentVO {
    // 科室ID
    private Integer departmentId;
    // 科室名称
    private String departmentName;

    public DepartmentVO(Integer departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }
}
