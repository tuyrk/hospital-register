package com.cdutcm.register.service.impl;

import com.cdutcm.register.constant.ScheduleStatusConstant;
import com.cdutcm.register.dao.OrderInfoDao;
import com.cdutcm.register.dao.ScheduleInfoDao;
import com.cdutcm.register.dataobject.entity.OrderInfo;
import com.cdutcm.register.dataobject.entity.ScheduleInfo;
import com.cdutcm.register.enums.OrderStatusEnum;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/5/12 15:47 星期日
 * Description:
 */
@Service
public class OrderTimeoutJob extends QuartzJobBean {
    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private ScheduleInfoDao scheduleInfoDao;

    @Transactional
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String orderId = jobDataMap.getString("orderId");
        String scheduleId = jobDataMap.getString("scheduleId");
        // 1. 先查询该订单是否已经支付。
        OrderInfo orderInfo = orderInfoDao.selectById(orderId);
        // 订单已支付。结束操作
        if (OrderStatusEnum.SUCCESS.getCode().equals(orderInfo.getOrderStatus())) {
            return;
        }
        // 2. 若订单超过20分钟未支付，将订单状态改成2（缴费过期）
        orderInfo.setOrderStatus(OrderStatusEnum.FAILURE.getCode());
        orderInfoDao.updateById(orderInfo);
        // 3. 并且将排班信息改成未预约状态
        ScheduleInfo scheduleInfo = scheduleInfoDao.selectById(scheduleId);
        scheduleInfo.setStatus(ScheduleStatusConstant.UNORDER);
        scheduleInfoDao.updateById(scheduleInfo);
    }
}
