package com.cdutcm.register.dataobject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/3 11:50 星期日
 * Description:
 * 医院科室
 */
@Data
public class DepartmentInfo {
    // 科室ID
    @TableId(type = IdType.AUTO)
    private Integer departmentId;
    // 科室编号
    private String departmentNumber;
    // 科室名称
    private String departmentName;
    // 科室负责人
    private String principal;
    // 负责人手机号码
    private String principalPhone;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
}
