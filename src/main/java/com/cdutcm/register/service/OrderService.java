package com.cdutcm.register.service;

import com.cdutcm.register.dataobject.form.patient.OrderForm;
import com.cdutcm.register.dataobject.form.PageForm;
import com.cdutcm.register.dataobject.vo.IPageVO;
import com.cdutcm.register.dataobject.vo.patient.OrderVO;
import com.cdutcm.register.dataobject.vo.patient.PaidVO;

import java.util.List;
import java.util.Map;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/13 23:16 星期三
 * Description:
 */
public interface OrderService {

    //支付订单
    void payOrder(String openid, String orderId);

    //下订单，添加挂号记录
    String placeOrder(OrderForm orderForm);

    //查询某条挂号记录
    OrderVO getOrderVOById(String orderId);

    //查询挂号记录的列表
    List<OrderVO> getOrderVOListByOpenid(String openid);

    //查询缴费成功的订单列表
    List<PaidVO> getPaidListByOpenid(String openid);

    //查询待缴费的订单列表
    List<PaidVO> getPayingListByOpenid(String openid);

    IPageVO<OrderVO> getOrderList(String doctorName, String departmentName, PageForm page);

    Map<String, Integer> getOrderCensus(String month);
}
