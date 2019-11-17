package com.cdutcm.register.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.register.dataobject.form.DoctorConditionForm;
import com.cdutcm.register.dataobject.form.admin.DoctorForm;
import com.cdutcm.register.dataobject.form.PageForm;
import com.cdutcm.register.dataobject.form.admin.DoctorInfoForm;
import com.cdutcm.register.dataobject.vo.admin.AdminDoctorVO;
import com.cdutcm.register.dataobject.vo.patient.DoctorVO;
import com.cdutcm.register.dataobject.vo.IPageVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/13 12:07 星期三
 * Description:
 */
public interface DoctorService {
    //获取所有医生列表
    IPage<DoctorVO> getByDepartmentId(Integer departmentId, PageForm page);

    //获取当前排班医生列表
    //只能提前15分钟预约挂号
    List<DoctorVO> getScheduleByDepartmentId(String departmentId);

    //获取某个医生信息
    Map getDoctorByDoctorId(String doctorId);

    // 保存基本医生信息
    void saveDoctor(DoctorForm doctorForm);

    // 通过Excel批量导入医生信息
    void saveDoctor(MultipartFile multipartFile);

    // 通过条件获取医生的信息。分页
    IPageVO getDoctor(DoctorConditionForm conditionForm, PageForm page);

    // 通过医生ID获取医生信息
    AdminDoctorVO getDoctor(String doctorId);

    void deleteDoctor(String doctorId);

    // 保存医生信息
    void saveDoctor(DoctorInfoForm doctorInfoForm);

    //获取 List<DoctorId>
    List<String> getDoctorIdByNameDepartmentName(String doctorName, String departmentName);
}
