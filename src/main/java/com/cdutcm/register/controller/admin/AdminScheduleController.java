package com.cdutcm.register.controller.admin;

import com.cdutcm.register.dataobject.form.admin.ScheduleForm;
import com.cdutcm.register.dataobject.vo.ResultVO;
import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.cdutcm.register.service.ScheduleService;
import com.cdutcm.register.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/16 11:44 星期六
 * Description:
 */
@Api(tags = "【管理员-排班表】")
@Slf4j
@RestController
@RequestMapping("/admin/schedule")
public class AdminScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation("【排班维护】添加排班信息")
    @PostMapping("/maintain")
    public ResultVO maintain(@RequestBody @Valid List<ScheduleForm> scheduleFormList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【排班维护】参数不正确，scheduleFormList = {}", scheduleFormList);
            throw new RegisterException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        scheduleService.maintainSchedule(scheduleFormList);
        return ResultVOUtil.success();
    }

    @ApiOperation("【排班维护】已添加排班列表（未提供删除接口，看情况待定）")
    @GetMapping("/list")
    public ResultVO list(@ApiParam("医生ID") @RequestParam("doctorId") String doctorId) {
        return ResultVOUtil.success(scheduleService.getSchedule(doctorId));
    }
}
