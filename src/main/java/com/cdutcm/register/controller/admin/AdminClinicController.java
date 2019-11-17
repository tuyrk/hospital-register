package com.cdutcm.register.controller.admin;

import com.cdutcm.register.dataobject.form.admin.ClinicForm;
import com.cdutcm.register.dataobject.vo.ResultVO;
import com.cdutcm.register.service.ClinicService;
import com.cdutcm.register.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/17 11:48 星期日
 * Description:
 */
@Api(tags = "【管理员-医院】")
@Slf4j
@RestController
@RequestMapping("/admin/clinic")
public class AdminClinicController {

    @Autowired
    private ClinicService clinicService;

    @ApiOperation("【修改医院信息】修改医院信息")
    @PutMapping("/modify")
    public ResultVO modify(@RequestBody ClinicForm clinicForm) {
        clinicService.modifyClinic(clinicForm);
        return ResultVOUtil.success();
    }
}
