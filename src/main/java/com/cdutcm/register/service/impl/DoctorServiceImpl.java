package com.cdutcm.register.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.register.dao.DepartmentInfoDao;
import com.cdutcm.register.dao.DoctorInfoDao;
import com.cdutcm.register.dataobject.entity.DepartmentInfo;
import com.cdutcm.register.dataobject.form.DoctorConditionForm;
import com.cdutcm.register.dataobject.form.admin.DoctorForm;
import com.cdutcm.register.dataobject.form.PageForm;
import com.cdutcm.register.dataobject.form.admin.DoctorInfoForm;
import com.cdutcm.register.dataobject.vo.admin.AdminDoctorVO;
import com.cdutcm.register.dataobject.vo.patient.DoctorVO;
import com.cdutcm.register.dataobject.entity.DoctorInfo;
import com.cdutcm.register.dataobject.vo.IPageVO;
import com.cdutcm.register.enums.DoctorStatusEnum;
import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.cdutcm.register.properties.RegisterProperties;
import com.cdutcm.register.service.DoctorService;
import com.cdutcm.register.utils.KeyUtil;
import com.cdutcm.register.utils.MD5Utils;
import com.cdutcm.register.utils.ReflectUtils;
import com.cdutcm.register.utils.converter.DoctorInfo2AdminDoctorVO;
import com.cdutcm.register.utils.converter.Info2VO;
import com.cdutcm.register.utils.converter.PageForm2Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/13 12:25 星期三
 * Description:
 */
@Slf4j
@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorInfoDao doctorInfoDao;
    @Autowired
    private DepartmentInfoDao departmentInfoDao;
    @Autowired
    private RegisterProperties registerProperties;

    @Override
    public IPage<DoctorVO> getByDepartmentId(Integer departmentId, PageForm page) {
        IPage<DoctorInfo> doctorInfoIPage = doctorInfoDao.selectPage(
                PageForm2Page.convert(page, registerProperties.getPage().getPage(), registerProperties.getPage().getSize()),
                new LambdaQueryWrapper<DoctorInfo>().eq(DoctorInfo::getDepartmentId, departmentId));
        return Info2VO.convert(doctorInfoIPage, DoctorVO.class);
    }

    //获取当前排班医生列表
    //只能提前15分钟预约挂号
    @Override
    public List<DoctorVO> getScheduleByDepartmentId(String departmentId) {
        //定义15分钟后的时间。以获取15分钟以后的排班医生列表
        //只能提前15分钟预约挂号
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 15);
        return doctorInfoDao.selectSchedule(departmentId, calendar);
    }

    @Override
    public Map getDoctorByDoctorId(String doctorId) {
        DoctorInfo doctorInfo = doctorInfoDao.selectById(doctorId);
        Map<String, Object> map = new HashMap<>();
        if (doctorInfo == null) {
//            log.error("【医生简介】医生不存在，doctorId = {}", doctorId);
//            throw new RegisterException(ResultEnum.DOCTOR_NOT_EXIST);
            return null;
        }
        map.put("doctorPhoto", doctorInfo.getDoctorPhoto());
        map.put("doctorName", doctorInfo.getDoctorName());
        map.put("doctorPost", doctorInfo.getDoctorPost());
        map.put("doctorAdept", doctorInfo.getDoctorAdept());
        map.put("doctorDetail", doctorInfo.getDoctorDetail());
        return map;
    }

    @Override
    public void saveDoctor(DoctorForm doctorForm) {
        // 查询医生用户名是否重复
        DoctorInfo doctorInfo = doctorInfoDao.selectByUsername(doctorForm.getUsername());
        if (doctorInfo != null) {
            log.error("【新增医生】医生用户名重复，doctorForm = {}", doctorForm);
            throw new RegisterException(ResultEnum.DOCTOR_USERNAME_EXIST);
        }
        // 验证新增科室是否存在
        DepartmentInfo departmentInfo = departmentInfoDao.selectByDepartmentName(doctorForm.getDepartment());
        if (departmentInfo == null) {
            log.error("【新增医生】科室名不存在，doctorForm = {}", doctorForm);
            throw new RegisterException(ResultEnum.DEPARTMENT_NOT_EXIST);
        }
        // 创建DoctorInfo对象
        doctorInfo = new DoctorInfo();
        BeanUtils.copyProperties(doctorForm, doctorInfo);
        doctorInfo.setDoctorId(KeyUtil.getUniqueKeyTime());
        doctorInfo.setDepartmentId(departmentInfo.getDepartmentId());
        doctorInfo.setPassword(MD5Utils.encrypt(registerProperties.getDoctor().getPassword(), MD5Utils.SHA));
        if (!doctorInfoDao.insert(doctorInfo)) {
            log.error("【新增医生】插入数据错误，doctorForm = {}", doctorForm);
            throw new RegisterException(ResultEnum.DOCTOR_SAVE_ERROR);
        }
    }

    @Override
    @Transactional
    public void saveDoctor(MultipartFile multipartFile) {
        Map<String, Integer> departmentNameMap = departmentInfoDao.selectDepartmentMapNameId();
        List<String> allUsername = doctorInfoDao.selectAllUsername();
        List<DoctorInfo> doctorInfoList;
        try {
            doctorInfoList = getDoctorInfoList(multipartFile.getInputStream(), departmentNameMap, allUsername);
        } catch (IOException e) {
            log.error("【新增医生】文件加载失败，multipartFile = {}", multipartFile);
            throw new RegisterException(ResultEnum.FILE_DATA_ERROR);
        }
        try {
            Boolean insertBatch = doctorInfoDao.insertBatch(doctorInfoList);
            if (!insertBatch) {
                throw new Exception("批量新增医生失败");
            }
        } catch (Exception e) {
            log.error("【新增医生】保存医生失败，doctorInfoList = {}", doctorInfoList);
            throw new RegisterException(ResultEnum.DOCTOR_SAVE_ERROR);
        }
    }

    private List<DoctorInfo> getDoctorInfoList(InputStream inputStream, Map<String, Integer> departmentNameMap, List<String> allUsername) throws IOException {
        String fields[] = {"username", "doctorName", "doctorSex", "doctorCard", "department", "doctorPhone", "doctorMail"};
        String fieldNames[] = {"用户名*", "医生姓名*", "性别", "身份证号*", "科室*", "联系电话", "邮箱"};
        Set<Integer> fieldIdx = new HashSet<>();
        Collections.addAll(fieldIdx, 0, 1, 3, 4);
        // 创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        // 读取sheet页
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<DoctorInfo> doctorInfoList = new ArrayList<>();
        DoctorInfo doctorInfo;
        DoctorForm doctorForm;
        //读取工作表中的数据
        int firstRowNum = 2;
        int lastRowNum = sheet.getLastRowNum();
        //检查表头是否符合要求
        int firstCellNum = 0;
        XSSFRow row = sheet.getRow(1);
        int lastCellNum = row.getLastCellNum();//获取当前行最后单元格列号
        try {
            for (int j = firstCellNum; j < lastCellNum; j++) {
                String cellValue = row.getCell(j).getStringCellValue();
                if (!fieldNames[j].equals(cellValue)) {
                    throw new RegisterException(ResultEnum.FILE_DATA_ERROR);
                }
            }
        } catch (Exception e) {
            throw new RegisterException(ResultEnum.FILE_DATA_ERROR);
        }
        //循环读取每一行数据
        for (int i = firstRowNum; i <= lastRowNum; i++) {
            row = sheet.getRow(i);
            lastCellNum = row.getLastCellNum();//获取当前行最后单元格列号
            doctorInfo = new DoctorInfo();
            doctorForm = new DoctorForm();
            //循环读取当前行中的每一个单元格
            for (int j = firstCellNum; j < lastCellNum; j++) {
                XSSFCell cell = row.getCell(j);
                String cellValue;
                if (CellType.NUMERIC == cell.getCellType()) {//如果是数值型数据需要通过getNumericCellValue获取数据
                    cellValue = String.valueOf(cell.getNumericCellValue());
                } else {
                    cellValue = cell.getStringCellValue();
                }
                //检查用户名是否重复
                if (j == 0 && allUsername.contains(cellValue)) {
                    log.error("【新增医生】用户名存在重复，username = {}", cellValue);
                    throw new RegisterException(ResultEnum.DOCTOR_USERNAME_EXIST.getCode(), ResultEnum.DOCTOR_USERNAME_EXIST.getMsg() + " " + cellValue);
                }
                //检查必填字段是否为空
                if (fieldIdx.contains(j) && cellValue.isEmpty()) {
                    throw new RegisterException(ResultEnum.PARAM_IS_EMPTY.getCode(), ResultEnum.PARAM_IS_EMPTY.getMsg() + " " + fields[j]);
                }
                ReflectUtils.setFieldValue(doctorForm, fields[j], cellValue);//向doctorForm.fields[j]设置值cellValue
            }
            //检查科室名是否存在
            Integer departmentId = checkDepartmentName(departmentNameMap, doctorForm.getDepartment());
            if (departmentId > 0) {//
                doctorInfo.setDepartmentId(departmentId);//设置departmentId
            } else {
                log.error("【新增医生】科室不存在，row = {}，departmentName = {}", i, doctorForm.getDepartment());
                throw new RegisterException(ResultEnum.DEPARTMENT_NOT_EXIST.getCode(), ResultEnum.DEPARTMENT_NOT_EXIST.getMsg() + " " + doctorForm.getDepartment());// 提示哪个科室名不存在
            }
            BeanUtils.copyProperties(doctorForm, doctorInfo);
            doctorInfo.setDoctorId(KeyUtil.getUniqueKeyTime());//设置doctorId
            doctorInfo.setPassword(MD5Utils.encrypt(registerProperties.getDoctor().getPassword(), MD5Utils.SHA));//设置password
            doctorInfoList.add(doctorInfo);
        }
        return doctorInfoList;
    }

    /**
     * 判断科室名是否存在
     * 存在，返回科室Id；不存在，返回0
     */
    private Integer checkDepartmentName(Map<String, Integer> departmentNameMap, String departmentName) {
        if (departmentNameMap.containsKey(departmentName)) {//如果键中包含这个科室名，则获取该键名的值(科室ID)
            return departmentNameMap.get(departmentName);
        }
        return 0;
    }

    @Override
    public IPageVO getDoctor(DoctorConditionForm conditionForm, PageForm page) {
        Map<String, Integer> departmentNameMap = departmentInfoDao.selectDepartmentMapNameId();
        // 组合查询条件
        LambdaQueryWrapper<DoctorInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (conditionForm != null) {
            if (conditionForm.getUsername() != null) {
                queryWrapper.eq(DoctorInfo::getUsername, conditionForm.getUsername());
            }
            if (conditionForm.getDoctorName() != null) {
                queryWrapper.eq(DoctorInfo::getDoctorName, conditionForm.getDoctorName());
            }
            if (conditionForm.getDepartment() != null) {
                //检查科室名是否存在，并获取科室名响相应的ID
                Integer departmentId = checkDepartmentName(departmentNameMap, conditionForm.getDepartment());
                if (departmentId == 0) {
                    log.error("【医生列表】科室名不存在，conditionForm = {}", conditionForm);
                    throw new RegisterException(ResultEnum.DEPARTMENT_NOT_EXIST);
                }
                queryWrapper.eq(DoctorInfo::getDepartmentId, departmentId);
            }
            if (conditionForm.getDoctorCard() != null) {
                queryWrapper.eq(DoctorInfo::getDoctorCard, conditionForm.getDoctorCard());
            }
        }
        IPage<DoctorInfo> doctorInfoIPage = doctorInfoDao.selectPage(
                PageForm2Page.convert(page, registerProperties.getPage().getPage(), registerProperties.getPage().getSize()),
                queryWrapper);
        return DoctorInfo2AdminDoctorVO.convertIPage(doctorInfoIPage, getDepartmentIdNameMap(departmentNameMap));
    }

    private Map<Integer, String> getDepartmentIdNameMap(Map<String, Integer> departmentNameMap) {
        Map<Integer, String> map = new HashMap<>();
        Set<String> nameSet = departmentNameMap.keySet();
        for (String name : nameSet) {
            map.put(departmentNameMap.get(name), name);
        }
        return map;
    }

    @Override
    public AdminDoctorVO getDoctor(String doctorId) {
        DoctorInfo doctorInfo = doctorInfoDao.selectById(doctorId);
        if (doctorInfo == null) {
            log.error("【医生简介】医生不存在，doctorId = {}", doctorId);
            throw new RegisterException(ResultEnum.DOCTOR_NOT_EXIST);
        }
        AdminDoctorVO adminDoctorVO = Info2VO.convert(doctorInfo, AdminDoctorVO.class);
        // 设置科室
        Map<Integer, String> departmentIdNameMap = departmentInfoDao.selectDepartmentMapIdName();
        adminDoctorVO.setDepartment(departmentIdNameMap.get(doctorInfo.getDepartmentId()));
        return adminDoctorVO;
    }

    @Override
    public void deleteDoctor(String doctorId) {
        DoctorInfo doctorInfo = doctorInfoDao.selectById(doctorId);
        if (doctorInfo == null) {
            log.error("【医生信息管理】医生不存在，doctorId = {}", doctorId);
            throw new RegisterException(ResultEnum.DOCTOR_NOT_EXIST);
        }
        doctorInfo.setStatus(DoctorStatusEnum.REMOVE.getCode());//0 false 删除
        try {
            doctorInfoDao.updateById(doctorInfo);
        } catch (Exception e) {
            log.error("【医生信息管理】删除医生失败，doctorId = {}", doctorId);
            throw new RegisterException(ResultEnum.DOCTOR_REMOVE_ERROR);
        }
    }

    @Override
    public void saveDoctor(DoctorInfoForm doctorInfoForm) {
        DoctorInfo doctorInfo = doctorInfoDao.selectById(doctorInfoForm.getDoctorId());
        if (doctorInfo == null) {
            log.error("【医生简介信息】医生不存在，doctorInfoForm = {}", doctorInfoForm);
            throw new RegisterException(ResultEnum.DOCTOR_NOT_EXIST);
        }
        if (doctorInfoForm.getDoctorPost() != null) {
            doctorInfo.setDoctorPost(doctorInfoForm.getDoctorPost());
        }
        if (doctorInfoForm.getDoctorAdept() != null) {
            doctorInfo.setDoctorAdept(doctorInfoForm.getDoctorAdept());
        }
        if (doctorInfoForm.getDoctorDetail() != null) {
            doctorInfo.setDoctorDetail(doctorInfoForm.getDoctorDetail());
        }
        try {
            doctorInfoDao.updateById(doctorInfo);
        } catch (Exception e) {
            log.error("【医生简介信息】保存医生失败，doctorInfoForm = {}", doctorInfoForm);
            throw new RegisterException(ResultEnum.DOCTOR_SAVE_ERROR);
        }
    }

    @Override
    public List<String> getDoctorIdByNameDepartmentName(String doctorName, String departmentName) {
        LambdaQueryWrapper<DoctorInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (doctorName != null) {
            queryWrapper.in(DoctorInfo::getDoctorName, doctorName);
        }
        if (departmentName != null) {
            queryWrapper.eq(DoctorInfo::getDepartmentId, departmentInfoDao.selectDepartmentIdByName(departmentName));
        }
        List<DoctorInfo> doctorInfoList = doctorInfoDao.selectList(queryWrapper);
        return doctorInfoList.stream().map(DoctorInfo::getDoctorId).collect(Collectors.toList());
    }
}