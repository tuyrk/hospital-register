package com.cdutcm.register.controller.patient;

import com.cdutcm.register.dataobject.form.PageForm;
import com.cdutcm.register.dataobject.vo.ResultVO;
import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.cdutcm.register.service.DoctorService;
import com.cdutcm.register.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/13 12:26 星期三
 * Description:
 */
@Api(tags = "【患者-医生】")
@Slf4j
@RestController
@RequestMapping("/patient/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @ApiOperation("【医生列表】根据科室ID获取该科室下的所有医生列表")
    @GetMapping("/list")
    public ResultVO list(@ApiParam("科室ID") @RequestParam(value = "departmentId") Integer departmentId, PageForm page) {
        return ResultVOUtil.success(doctorService.getByDepartmentId(departmentId, page));
    }

    @ApiOperation("【医生列表】获取当前已排班医生的列表")
    @GetMapping("/schedule")
    public ResultVO schedule(@ApiParam("科室ID") @RequestParam("departmentId") String departmentId) {
        return ResultVOUtil.success(doctorService.getScheduleByDepartmentId(departmentId));
    }

    @ApiOperation("【医生简介】获取某个医生的详细信息")
    @GetMapping("/detail")
    public ResultVO detail(@ApiParam("医生ID") @RequestParam("doctorId") String doctorId) {
        return ResultVOUtil.success(doctorService.getDoctorByDoctorId(doctorId));
    }
}
