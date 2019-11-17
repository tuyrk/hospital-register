package com.cdutcm.register.controller.patient;

import com.cdutcm.register.dataobject.vo.ResultVO;
import com.cdutcm.register.dataobject.vo.patient.SchedulePatientVO;
import com.cdutcm.register.service.ScheduleService;
import com.cdutcm.register.utils.GsonUtil;
import com.cdutcm.register.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/12 13:01 星期二
 * Description:
 */
@Api(tags = "【患者-排班表】")
@Slf4j
@RestController
@RequestMapping("/patient/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation("【排班表】获取排班表信息")
    @GetMapping("/list")
    public ResultVO list() {
        return ResultVOUtil.success(scheduleService.getScheduleList());
    }

    @ApiOperation("【挂号号源】获取挂号号源信息")
    @GetMapping("/detail")
    public ResultVO detail(@ApiParam("医生ID") @RequestParam("doctorId") String doctorId) {
        return ResultVOUtil.success(scheduleService.getScheduleByDoctorId(doctorId));
    }
}
