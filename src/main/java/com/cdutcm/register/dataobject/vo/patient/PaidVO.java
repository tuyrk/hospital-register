package com.cdutcm.register.dataobject.vo.patient;

import com.cdutcm.register.enums.OrderStatusEnum;
import com.cdutcm.register.utils.EnumUtil;
import com.cdutcm.register.utils.serializer.Date2StringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/14 17:48 星期四
 * Description:
 * 缴费记录
 */
@Data
public class PaidVO {
    private String orderId;
    private String patientName;
    @JsonProperty("department")
    private String departmentName;
    @JsonSerialize(using = Date2StringSerializer.class)
    private Date payTime;
    @JsonIgnore
    private Date createTime;
    private BigDecimal orderMoney;
    @JsonIgnore
    private Integer orderStatus;

    @JsonProperty("orderStatus")
    public String getOrderStatusMsg() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class).getMsg();
    }
}
