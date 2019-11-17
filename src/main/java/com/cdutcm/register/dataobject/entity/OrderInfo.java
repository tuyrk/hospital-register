package com.cdutcm.register.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.cdutcm.register.enums.OrderStatusEnum;
import com.cdutcm.register.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/1/15 17:49 星期二
 * Description:
 * 挂号记录
 */
@Data
public class OrderInfo {
    // 挂号ID
    @TableId
    private String orderId;
    // 预约问诊时间
    private Date scheduleTime;
    // 挂号金额
    private Double orderMoney;
    // 挂号状态 0待缴费、1缴费成功
    private Integer orderStatus;
    //支付时间
    private Date payTime;
    // 患者ID
    private String patientId;
    // 医生ID
    private String doctorId;
    // 绑定微信号openid
    private String openid;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnumByCode() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }
}
