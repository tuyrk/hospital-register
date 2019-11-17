package com.cdutcm.register.dataobject.vo.patient;

import com.cdutcm.register.dataobject.vo.BaseView;
import com.cdutcm.register.utils.serializer.Date2StringSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/14 15:55 星期四
 * Description:
 * 挂号记录
 */
@Data
public class OrderVO {

    public interface OrderSimpleView extends BaseView {
    }

    public interface OrderDetailView extends OrderSimpleView {
    }

    public interface OrderInfoView extends OrderSimpleView {
    }

    @JsonView(OrderSimpleView.class)
    private String orderId;
    @JsonView(OrderSimpleView.class)
    private String doctorName;
    @JsonProperty("department")
    @JsonView(OrderSimpleView.class)
    private String departmentName;
    @JsonSerialize(using = Date2StringSerializer.class)
    @JsonView(OrderSimpleView.class)
    private Date scheduleTime;
    @JsonView(OrderDetailView.class)
    private BigDecimal orderMoney;
    @JsonView(OrderSimpleView.class)
    private String patientName;
    @JsonView({OrderDetailView.class, OrderInfoView.class})
    private String patientCard;
    @JsonView(OrderDetailView.class)
    private String patientPhone;
    @JsonView(OrderInfoView.class)
    private String patientId;
}
