package com.cdutcm.register.utils.converter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.register.dataobject.entity.DoctorInfo;
import com.cdutcm.register.dataobject.entity.OrderInfo;
import com.cdutcm.register.dataobject.entity.PatientInfo;
import com.cdutcm.register.dataobject.vo.patient.OrderVO;
import com.cdutcm.register.dataobject.vo.PageVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/19 11:45 星期二
 * Description:
 */
public class OrderInfo2OrderVO {

    public static PageVO<OrderVO> convert(IPage<OrderInfo> orderInfoPage,
                                          Map<String, PatientInfo> patientInfoMap, Map<String, DoctorInfo> doctorInfoMap,
                                          Map<Integer, String> departmentIdNameMap) {
        PageVO<OrderVO> pageVO = new PageVO<>();
        BeanUtils.copyProperties(orderInfoPage, pageVO);

        List<OrderInfo> orderInfoList = orderInfoPage.getRecords();
        List<OrderVO> orderVOList = new ArrayList<>();
        OrderVO orderVO;
        for (OrderInfo orderInfo : orderInfoList) {
            orderVO = new OrderVO();
            BeanUtils.copyProperties(orderInfo, orderVO);
            //patientInfo
            orderVO.setPatientName(patientInfoMap.get(orderInfo.getPatientId()).getPatientName());//根据patientId从patientInfoMap获取patientName，然后设置给orderVO
            orderVO.setPatientCard(patientInfoMap.get(orderInfo.getPatientId()).getPatientCard());//根据patientId从patientInfoMap获取patientCard，然后设置给orderVO
            //doctorInfo
            orderVO.setDoctorName(doctorInfoMap.get(orderInfo.getDoctorId()).getDoctorName());//根据doctorId从doctorInfoMap获取doctorName，然后设置给orderVO
            orderVO.setDepartmentName(departmentIdNameMap.get(doctorInfoMap.get(orderInfo.getDoctorId()).getDepartmentId()));//先根据doctorId从doctorInfoMap获取departmentId，再根据departmentId从departmentIdNameMap获取departmentName，然后设置给orderVO
            orderVOList.add(orderVO);
        }
        pageVO.setRecords(orderVOList);
        return pageVO;
    }
}
