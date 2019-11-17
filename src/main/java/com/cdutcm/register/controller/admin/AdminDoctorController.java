package com.cdutcm.register.controller.admin;

import com.cdutcm.register.dataobject.form.DoctorConditionForm;
import com.cdutcm.register.dataobject.form.admin.DoctorForm;
import com.cdutcm.register.dataobject.form.PageForm;
import com.cdutcm.register.dataobject.form.admin.DoctorInfoForm;
import com.cdutcm.register.dataobject.vo.admin.AdminDoctorVO;
import com.cdutcm.register.dataobject.vo.ResultVO;
import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.cdutcm.register.service.DoctorService;
import com.cdutcm.register.service.StorageService;
import com.cdutcm.register.utils.ResultVOUtil;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/15 17:09 星期五
 * Description:
 */
@Api(tags = "【管理员-医生】")
@Slf4j
@RestController
@RequestMapping("/admin/doctor")
public class AdminDoctorController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private StorageService storageService;

    @ApiOperation("【新增医生】通过页面添加单个医生信息")
    @PostMapping("/save")
    public ResultVO save(@Valid @RequestBody DoctorForm doctorForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【新增医生】参数不正确，doctorForm = {}", doctorForm);
            throw new RegisterException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        doctorService.saveDoctor(doctorForm);
        return ResultVOUtil.success();
    }

    @ApiOperation("【新增医生】下载批量添加医生信息的Excel模板")
    @GetMapping("/sample")
    public ResponseEntity<Resource> sample() {
        Resource resource = storageService.loadAsResource("医生信息导入数据样表.xlsx");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + resource.getFilename())
                .body(resource);
    }

    @ApiOperation("【新增医生】通过Excel批量导入医生信息")
    @PostMapping("/batch")
    public ResultVO batch(@RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            log.error("【上传医生信息】文件为空，multipartFile = {}", multipartFile);
            throw new RegisterException(ResultEnum.FILE_IS_EMPTY);
        }
        doctorService.saveDoctor(multipartFile);
        return ResultVOUtil.success();
    }

    @ApiOperation("【用户管理-医生信息管理】获取医生信息列表")
    @GetMapping("/detail")
    @JsonView(AdminDoctorVO.DoctorDetailView.class)
    public ResultVO detail(DoctorConditionForm conditionForm, PageForm page) {
        return ResultVOUtil.success(doctorService.getDoctor(conditionForm, page));
    }

    @ApiOperation("【用户管理-医生信息管理】通过doctorId删除医生")
    @DeleteMapping("/delete")
    public ResultVO delete(@ApiParam("医生ID") @RequestParam("doctorId") String doctorId) {
        doctorService.deleteDoctor(doctorId);
        return ResultVOUtil.success();
    }

    @ApiOperation("【基础信息管理-医生简介】获取医生信息列表")
    @GetMapping({"/simple", "/simple/{doctorId}"})
    @JsonView(AdminDoctorVO.DoctorSimpleView.class)
    public ResultVO simple(@ApiParam("医生ID") @PathVariable(value = "doctorId", required = false) String doctorId,
                           DoctorConditionForm conditionForm, PageForm page) {
        if (doctorId != null) {
            return ResultVOUtil.success(doctorService.getDoctor(doctorId));
        }
        return ResultVOUtil.success(doctorService.getDoctor(conditionForm, page));
    }

    @ApiOperation("【医生简介信息】获取医生简介信息")
    @GetMapping("/info/{doctorId}")
    @JsonView(AdminDoctorVO.DoctorInfoView.class)
    public ResultVO info(@ApiParam("医生ID") @PathVariable(value = "doctorId", required = false) String doctorId) {
        return ResultVOUtil.success(doctorService.getDoctor(doctorId));
    }

    @ApiOperation("【医生简介信息】修改医生简介信息")
    @PutMapping("/modify")
    public ResultVO modify(@Valid @RequestBody DoctorInfoForm doctorInfoForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【医生简介信息】参数不正确，doctorInfoForm = {}", doctorInfoForm);
            throw new RegisterException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        doctorService.saveDoctor(doctorInfoForm);
        return ResultVOUtil.success();
    }
}