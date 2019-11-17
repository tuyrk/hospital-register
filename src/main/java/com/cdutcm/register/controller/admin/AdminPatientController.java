package com.cdutcm.register.controller.admin;

import com.cdutcm.register.dataobject.form.PageForm;
import com.cdutcm.register.dataobject.form.PatientConditionForm;
import com.cdutcm.register.dataobject.vo.ResultVO;
import com.cdutcm.register.service.PatientService;
import com.cdutcm.register.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/17 0:03 星期日
 * Description:
 */
@Api(tags = "【管理员-患者】")
@Slf4j
@RestController
@RequestMapping("/admin/patient")
public class AdminPatientController {

    @Autowired
    private PatientService patientService;

    @ApiOperation("【患者列表】获取患者信息的列表")
    @GetMapping("/list")
    public ResultVO list(PatientConditionForm patientConditionForm, PageForm page) {
        return ResultVOUtil.success(patientService.getPatientList(patientConditionForm, page));
    }
}
