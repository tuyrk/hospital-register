package com.cdutcm.register.controller.admin;

import com.cdutcm.register.dataobject.form.PageForm;
import com.cdutcm.register.dataobject.form.admin.DepartmentForm;
import com.cdutcm.register.dataobject.vo.ResultVO;
import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.cdutcm.register.service.DepartmentService;
import com.cdutcm.register.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/17 10:31 星期日
 * Description:
 */
@Api(tags = "【管理员-科室】")
@Slf4j
@RestController
@RequestMapping("/admin/department")
public class AdminDepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation("【新增科室】添加科室信息")
    @PostMapping("/save")
    public ResultVO save(@Valid @RequestBody DepartmentForm departmentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【新增科室】参数不正确，departmentForm = {}", departmentForm);
            throw new RegisterException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        departmentService.saveDepartment(departmentForm);
        return ResultVOUtil.success();
    }

    @ApiOperation("【科室列表】查询某个科室信息的列表")
    @GetMapping("/list")
    public ResultVO list(@ApiParam("科室名称") @RequestParam(value = "departmentName", required = false) String departmentName,
                         PageForm page) {
        return ResultVOUtil.success(departmentService.getDepartmentPage(departmentName, page));
    }
}
