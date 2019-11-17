package com.cdutcm.register.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.register.config.JobFactory;
import com.cdutcm.register.constant.ScheduleStatusConstant;
import com.cdutcm.register.dao.*;
import com.cdutcm.register.dataobject.entity.*;
import com.cdutcm.register.dataobject.form.patient.OrderForm;
import com.cdutcm.register.dataobject.form.PageForm;
import com.cdutcm.register.dataobject.vo.IPageVO;
import com.cdutcm.register.dataobject.vo.patient.OrderVO;
import com.cdutcm.register.dataobject.vo.patient.PaidVO;
import com.cdutcm.register.enums.OrderStatusEnum;
import com.cdutcm.register.enums.PatientStatusEnum;
import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.cdutcm.register.properties.RegisterProperties;
import com.cdutcm.register.service.DoctorService;
import com.cdutcm.register.service.OrderService;
import com.cdutcm.register.utils.KeyUtil;
import com.cdutcm.register.utils.converter.OrderInfo2OrderVO;
import com.cdutcm.register.utils.converter.PageForm2Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/13 23:17 星期三
 * Description:
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private PatientInfoDao patientInfoDao;
    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private ScheduleInfoDao scheduleInfoDao;
    @Autowired
    private DoctorInfoDao doctorInfoDao;
    @Autowired
    private DepartmentInfoDao departmentInfoDao;
    @Autowired
    private JobFactory jobFactory;
    @Autowired
    private DoctorService doctorService;

    private Integer page;
    private Integer size;

    public OrderServiceImpl(RegisterProperties registerProperties) {
        page = registerProperties.getPage().getPage();
        size = registerProperties.getPage().getSize();
    }

    //支付订单
    @Override
    @Transactional
    public void payOrder(String openid, String orderId) {
        //查询订单
        LambdaQueryWrapper<OrderInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderInfo::getOrderId, orderId).eq(OrderInfo::getOpenid, openid);
        OrderInfo orderInfo = orderInfoDao.selectOne(queryWrapper);
        if (orderInfo == null) {
            log.error("【支付订单】订单不存在，openid = {}，orderId = {}", openid, orderId);
            throw new RegisterException(ResultEnum.ORDER_NOT_EXIST);
        }
        if (OrderStatusEnum.SUCCESS.getCode().equals(orderInfo.getOrderStatus())) {
            log.error("【支付订单】订单已支付，openid = {}，orderId = {}", openid, orderId);
            throw new RegisterException(ResultEnum.ORDER_IS_PAY);
        }
        if (OrderStatusEnum.FAILURE.getCode().equals(orderInfo.getOrderStatus())) {
            log.error("【支付订单】订单已过期，openid = {}，orderId = {}", openid, orderId);
            throw new RegisterException(ResultEnum.ORDER_IS_TIMEOUT);
        }
        //修改订单状态
        orderInfo.setPayTime(new Date());
        orderInfo.setOrderStatus(OrderStatusEnum.SUCCESS.getCode());
        //保存订单信息
        orderInfoDao.updateById(orderInfo);
        //微信支付
        // todo 2019/5/13 2:01 涂元坤 微信支付
    }

    //下订单，添加挂号记录
    @Override
    @Transactional
    public String placeOrder(OrderForm orderForm) {
        PatientInfo patientInfo = patientInfoDao.selectById(orderForm.getPatientId());
        if (patientInfo == null || patientInfo.getStatus().equals(PatientStatusEnum.REMOVE.getCode())) {
            log.error("【预约挂号下订单】患者信息不存在，patientId = {}", orderForm.getPatientId());
            throw new RegisterException(ResultEnum.PATIENT_NOT_EXIST);
        }
        if (patientInfo.getPlace()) {// 0false未挂号，1true已挂号
            log.error("【预约挂号下订单】病人已经有挂号，orderForm = {}", orderForm);
            throw new RegisterException(ResultEnum.PATIENT_IS_PLACE);
        }
        ScheduleInfo scheduleInfo = scheduleInfoDao.selectById(orderForm.getScheduleId());
        if (scheduleInfo == null) {
            log.error("【预约挂号下订单】排班信息不存在，scheduleId = {}", orderForm.getScheduleId());
            throw new RegisterException(ResultEnum.SCHEDULE_NOT_EXIST);
        }
        if (scheduleInfo.getStatus()) {//true为号源已使用
            log.error("【预约挂号下订单】排班信息已过期，scheduleId = {}", orderForm.getScheduleId());
            throw new RegisterException(ResultEnum.SCHEDULE_IS_PLACE);
        }

        //设置订单信息
        OrderInfo orderInfo = new OrderInfo();
        String orderId = KeyUtil.getUniqueKeyPrettyTime();
        orderInfo.setOrderId(orderId);//订单ID
        //预约号源时间
        Calendar scheduleTime = Calendar.getInstance();//号源时间
        scheduleTime.setTime(scheduleInfo.getScheduleTime());
        orderInfo.setScheduleTime(scheduleTime.getTime());//设置号源时间
        orderInfo.setOrderMoney(scheduleInfo.getScheduleMoney());//挂号费用
        orderInfo.setOrderStatus(OrderStatusEnum.WAIT.getCode());//订单状态
        orderInfo.setPatientId(patientInfo.getPatientId());//挂号用户
        orderInfo.setDoctorId(scheduleInfo.getDoctorId());//挂号医生
        orderInfo.setOpenid(patientInfo.getOpenid());//挂号用户openid
        //创建订单
        orderInfoDao.insert(orderInfo);

        scheduleInfo.setStatus(ScheduleStatusConstant.ORDER);//true为已预约
        //修改号源
        scheduleInfoDao.updateById(scheduleInfo);

        // 超过20分钟将订单状态改成2（缴费过期），，，并且将排班信息改成未预约状态
        Map<String, Object> param = new HashMap<>();
        param.put("orderId", orderId);// 医生ID
        param.put("scheduleId", orderForm.getScheduleId());// 号源ID
        param.put("jobFactory", jobFactory);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 3);
        Thread thread = new Thread(new SchedulerRunnable<>(OrderTimeoutJob.class, calendar, param));
        thread.start();

        return orderId;
    }

    //查询某条挂号记录
    @Override
    public OrderVO getOrderVOById(String orderId) {
        return orderInfoDao.selectOrderVOById(orderId);
    }

    //查询挂号记录的列表
    @Override
    public List<OrderVO> getOrderVOListByOpenid(String openid) {
        return orderInfoDao.selectByOpenid(openid);
    }

    @Override
    public List<PaidVO> getPaidListByOpenid(String openid) {
        return orderInfoDao.selectPaidByOpenid(openid);
    }

    @Override
    public List<PaidVO> getPayingListByOpenid(String openid) {
        return orderInfoDao.selectPayingByOpenid(openid);
    }

    @Override
    public IPageVO<OrderVO> getOrderList(String doctorName, String departmentName, PageForm pageForm) {
        //查找订单信息List<OrderInfo>
        IPage<OrderInfo> orderInfoPage = orderInfoDao.selectPage(
                PageForm2Page.convert(pageForm, page, size),
                new LambdaQueryWrapper<OrderInfo>()
                        .in(OrderInfo::getDoctorId, doctorService.getDoctorIdByNameDepartmentName(doctorName, departmentName)));

        List<OrderInfo> orderInfoList = orderInfoPage.getRecords();
        //根据订单信息收集订单中的PatientId
        Set<String> patientIdSet = orderInfoList.stream().map(OrderInfo::getPatientId).collect(Collectors.toSet());
        //根据patientIdSet查找PatientInfo，从而获取PatientName，PatientCard
        Map<String, PatientInfo> patientInfoMap = patientInfoDao.selectList(new LambdaQueryWrapper<PatientInfo>().in(PatientInfo::getPatientId, patientIdSet)).stream()
                .collect(Collectors.toMap(PatientInfo::getPatientId, Function.identity()));
        //根据订单信息收集订单中的DoctorId
        Set<String> doctorIdSet = orderInfoList.stream().map(OrderInfo::getDoctorId).collect(Collectors.toSet());
        //根据doctorIdSet查找DoctorInfo，从而获取DepartmentName，DoctorName
        Map<String, DoctorInfo> doctorInfoMap = doctorInfoDao.selectList(new LambdaQueryWrapper<DoctorInfo>().in(DoctorInfo::getDoctorId, doctorIdSet)).stream()
                .collect(Collectors.toMap(DoctorInfo::getDoctorId, Function.identity()));

        //获取Map<departmentId，departmentName>
        Map<Integer, String> departmentIdNameMap = departmentInfoDao.selectDepartmentMapIdName();

        //将orderInfoPage（patientInfoMap，doctorInfoMap）转换成OrderVO
        return OrderInfo2OrderVO.convert(orderInfoPage, patientInfoMap, doctorInfoMap, departmentIdNameMap);
    }

    @Override
    public Map<String, Integer> getOrderCensus(String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        try {
            sdf.parse(month);
        } catch (ParseException e) {
            log.error("【挂号信息统计】月份参数错误，month = {}", month);
            throw new RegisterException(ResultEnum.PARAM_ERROR);
        }
        Map<String, Integer> orderCensusMap = orderInfoDao.selectOrderCensus(month);
        Set<String> departmentInfoSet = departmentInfoDao.selectAllList().stream().map(DepartmentInfo::getDepartmentName).collect(Collectors.toSet());
        for (String entity : orderCensusMap.keySet()) {
            departmentInfoSet.remove(entity);
        }
        for (String departmentName : departmentInfoSet) {
            orderCensusMap.put(departmentName, 0);
        }
        return orderCensusMap;
    }
}