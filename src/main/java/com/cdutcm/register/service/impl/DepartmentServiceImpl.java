package com.cdutcm.register.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.cdutcm.register.dao.DepartmentInfoDao;
import com.cdutcm.register.dataobject.entity.DepartmentInfo;
import com.cdutcm.register.dataobject.form.PageForm;
import com.cdutcm.register.dataobject.form.admin.DepartmentForm;
import com.cdutcm.register.dataobject.vo.common.DepartmentVO;
import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.cdutcm.register.properties.RegisterProperties;
import com.cdutcm.register.service.DepartmentService;
import com.cdutcm.register.utils.converter.Info2VO;
import com.cdutcm.register.utils.converter.PageForm2Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/12 23:15 星期二
 * Description:
 */
@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentInfoDao departmentInfoDao;
    @Autowired
    private RegisterProperties registerProperties;

    @Override
    public List<DepartmentVO> getDepartmentVO() {
        List<String> departmentNameList = new ArrayList<>();
        List<DepartmentInfo> departmentInfoList = departmentInfoDao.selectAllList();

        return departmentInfoList.stream().map(e ->
                new DepartmentVO(e.getDepartmentId(), e.getDepartmentName())
        ).collect(Collectors.toList());
    }

    @Override
    public void saveDepartment(DepartmentForm departmentForm) {

        List<DepartmentInfo> departmentInfoList = departmentInfoDao.selectAllList();
        List<String> departmentNumberList = new ArrayList<>();
        List<String> departmentNameList = new ArrayList<>();
        for (DepartmentInfo departmentInfo : departmentInfoList) {
            departmentNumberList.add(departmentInfo.getDepartmentNumber());
            departmentNameList.add(departmentInfo.getDepartmentName());
        }
        // 检查科室编号是否重复
        if (departmentNumberList.contains(departmentForm.getDepartmentNumber())) {
            log.error("【新增科室】科室编号已存在，departmentForm = {}", departmentForm);
            throw new RegisterException(ResultEnum.DEPARTMENT_NUMBER_EXIST);
        }
        //检查科室名称是否重复
        if (departmentNameList.contains(departmentForm.getDepartmentName())) {
            log.error("【新增科室】科室名称已存在，departmentForm = {}", departmentForm);
            throw new RegisterException(ResultEnum.DEPARTMENT_NAME_EXIST);
        }
        try {
            DepartmentInfo departmentInfo = Info2VO.convert(departmentForm, DepartmentInfo.class);
            departmentInfoDao.insert(departmentInfo);
        } catch (Exception e) {
            log.error("【新增科室】保存科室失败，departmentForm = {}，e = {}", departmentForm, e);
            throw new RegisterException(ResultEnum.DEPARTMENT_SAVE_ERROR);
        }
    }

    @Override
    public IPage<DepartmentForm> getDepartmentPage(String departmentName, PageForm page) {
        LambdaQueryWrapper<DepartmentInfo> queryWrapper = new LambdaQueryWrapper<>();
        //如果有查询条件，则将查询条件放入LambdaQueryWrapper
        if (departmentName != null) {
            queryWrapper.eq(DepartmentInfo::getDepartmentName, departmentName);
        }
        IPage<DepartmentInfo> departmentInfoPage = departmentInfoDao.selectPage(PageForm2Page.convert(page, registerProperties.getPage().getPage(), registerProperties.getPage().getSize()),
                queryWrapper);
        return Info2VO.convert(departmentInfoPage, DepartmentForm.class);
    }

    @Override
    public Integer getDepartmentIdByName(String departmentName) {
        return departmentInfoDao.selectDepartmentIdByName(departmentName);
    }
}
