package com.cdutcm.register.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.register.dataobject.form.PageForm;
import com.cdutcm.register.dataobject.form.admin.DepartmentForm;
import com.cdutcm.register.dataobject.vo.common.DepartmentVO;

import javax.validation.Valid;
import java.util.List;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/12 23:14 星期二
 * Description:
 */
public interface DepartmentService {
    List<DepartmentVO> getDepartmentVO();

    void saveDepartment(DepartmentForm departmentForm);

    IPage<DepartmentForm> getDepartmentPage(String departmentName, PageForm page);

    Integer getDepartmentIdByName(String departmentName);
}
