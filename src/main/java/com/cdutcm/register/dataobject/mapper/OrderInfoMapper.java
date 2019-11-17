package com.cdutcm.register.dataobject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdutcm.register.dataobject.dto.CensusDTO;
import com.cdutcm.register.dataobject.entity.OrderInfo;
import com.cdutcm.register.dataobject.vo.patient.OrderVO;
import com.cdutcm.register.dataobject.vo.patient.PaidVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/3 12:04 星期日
 * Description:
 */
@Component
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    @Select("SELECT " +
            "doctor_name, department_name, schedule_time, order_money, patient_name, patient_card, patient_phone " +
            "FROM order_info o " +
            "LEFT JOIN doctor_info d ON d.doctor_id = o.doctor_id " +
            "LEFT JOIN department_info de ON d.department_id = de.department_id " +
            "LEFT JOIN patient_info p ON p.patient_id = o.patient_id " +
            "WHERE order_id = #{orderId}")
    OrderVO selectOrderById(@Param("orderId") String orderId);

    @Select("SELECT " +
            "order_id, doctor_name, department_name, schedule_time, patient_name " +
            "FROM order_info o " +
            "LEFT JOIN doctor_info d ON d.doctor_id = o.doctor_id " +
            "LEFT JOIN department_info de ON d.department_id = de.department_id " +
            "LEFT JOIN patient_info p ON p.patient_id = o.patient_id " +
            "WHERE o.openid = #{openid} AND (order_status = 1 OR order_status = 2)")
    List<OrderVO> selectByOpenid(@Param("openid") String openid);

    @Select("SELECT " +
            "patient_name, department_name, pay_time, o.create_time, order_money, order_status " +
            "FROM order_info o " +
            "LEFT JOIN doctor_info d ON d.doctor_id = o.doctor_id " +
            "LEFT JOIN department_info de ON d.department_id = de.department_id " +
            "LEFT JOIN patient_info p ON p.patient_id = o.patient_id " +
            "WHERE o.openid = #{openid} AND (order_status = 1 OR order_status = 2)")// 1缴费成功 2缴费过期
    List<PaidVO> selectPaidByOpenid(@Param("openid") String openid);

    @Select("SELECT " +
            "order_id, patient_name, department_name, order_money, order_status " +
            "FROM order_info o " +
            "LEFT JOIN doctor_info d ON d.doctor_id = o.doctor_id " +
            "LEFT JOIN department_info de ON d.department_id = de.department_id " +
            "LEFT JOIN patient_info p ON p.patient_id = o.patient_id " +
            "WHERE o.openid = #{openid} AND order_status = 0")// 0待缴费
    List<PaidVO> selectPayingByOpenid(@Param("openid") String openid);

    @Select("SELECT " +
            "count(department_name) count, department_name " +
            "FROM order_info, department_info, doctor_info " +
            "WHERE order_info.doctor_id = doctor_info.doctor_id AND doctor_info.department_id = department_info.department_id AND order_id LIKE CONCAT(#{month}, '%') " +
            "GROUP BY department_name")
    List<CensusDTO> selectOrderCensus(@Param("month") String month);
}
