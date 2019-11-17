package com.cdutcm.register.service.impl;

import com.cdutcm.register.RegisterApplicationTests;
import com.cdutcm.register.dataobject.form.PageForm;
import com.cdutcm.register.service.OrderService;
import com.cdutcm.register.utils.GsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class OrderServiceImplTest extends RegisterApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    public void getOrderList() {
        orderService.getOrderList(null, null, new PageForm());
    }

    @Test
    public void getOrderCensus(){
        Map<String, Integer> orderCensusList = orderService.getOrderCensus("201902");
        System.out.println("orderCensusList = " + GsonUtil.prettyPrinting(orderCensusList));
    }
}
