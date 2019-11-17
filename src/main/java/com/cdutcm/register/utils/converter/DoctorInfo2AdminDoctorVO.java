package com.cdutcm.register.utils.converter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdutcm.register.dataobject.entity.DoctorInfo;
import com.cdutcm.register.dataobject.vo.admin.AdminDoctorVO;
import com.cdutcm.register.dataobject.vo.IPageVO;
import com.cdutcm.register.dataobject.vo.PageVO;
import org.springframework.beans.BeanUtils;

import java.util.*;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/16 22:30 星期六
 * Description:
 */
public class DoctorInfo2AdminDoctorVO {
    public static AdminDoctorVO convert(DoctorInfo doctorInfo, Map<Integer, String> departmentNameMap) {
        AdminDoctorVO adminDoctorVO = new AdminDoctorVO();
        BeanUtils.copyProperties(doctorInfo, adminDoctorVO);
        adminDoctorVO.setDepartment(departmentNameMap.get(doctorInfo.getDepartmentId()));
        return adminDoctorVO;
    }

    public static List<AdminDoctorVO> convert(List<DoctorInfo> doctorInfoList, Map<Integer, String> departmentNameMap) {
        List<AdminDoctorVO> adminDoctorVOList = new ArrayList<>();
        for (DoctorInfo doctorInfo : doctorInfoList) {
            adminDoctorVOList.add(convert(doctorInfo, departmentNameMap));
        }
        return adminDoctorVOList;
    }

    public static IPage<AdminDoctorVO> convert(IPage<DoctorInfo> doctorInfoPage, Map<Integer, String> departmentNameMap) {
        IPage<AdminDoctorVO> doctorVOPage = new Page<>();
        BeanUtils.copyProperties(doctorInfoPage, doctorVOPage);
        doctorVOPage.setRecords(convert(doctorInfoPage.getRecords(), departmentNameMap));
        return doctorVOPage;
    }
    public static IPageVO<AdminDoctorVO> convertIPage(IPage<DoctorInfo> doctorInfoPage, Map<Integer, String> departmentNameMap) {
        IPageVO<AdminDoctorVO> doctorVOPage = new PageVO<>();
        BeanUtils.copyProperties(doctorInfoPage, doctorVOPage);
        doctorVOPage.setRecords(convert(doctorInfoPage.getRecords(), departmentNameMap));
        return doctorVOPage;
    }
}
