package com.cdutcm.register.controller.admin;

import com.cdutcm.register.dataobject.form.admin.AdminAuthForm;
import com.cdutcm.register.dataobject.form.admin.AdminConditionForm;
import com.cdutcm.register.dataobject.form.admin.AdminForm;
import com.cdutcm.register.dataobject.form.PageForm;
import com.cdutcm.register.dataobject.vo.ResultVO;
import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.cdutcm.register.service.AdminService;
import com.cdutcm.register.utils.ResultVOUtil;
import com.sun.org.apache.bcel.internal.classfile.PMGClass;
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
 * Create: 2019/2/15 12:28 星期五
 * Description:
 */
@Api(tags = "【管理员】")
@Slf4j
@RestController
@RequestMapping("/admin/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation("【新增管理员】新增管理员账号信息")
    @PostMapping("/save")
    public ResultVO save(@Valid @RequestBody AdminForm adminForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【新增管理员】参数不正确，adminForm = {}", adminForm);
            throw new RegisterException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        adminService.saveAdmin(adminForm);
        return ResultVOUtil.success();
    }

    @ApiOperation("【删除管理员】删除管理员账号信息")
    @DeleteMapping("/delete")
    public ResultVO save(@ApiParam("管理员ID") @RequestParam("adminId") String adminId) {
        adminService.deleteAdmin(adminId);
        return ResultVOUtil.success();
    }

    @ApiOperation("【管理员列表】根据条件查询管理员账号信息")
    @GetMapping("/list")
    public ResultVO list(AdminConditionForm adminCondition, PageForm page) {
        return ResultVOUtil.success(adminService.getAdminList(adminCondition, page));
    }

    @ApiOperation("【管理员登录】管理员利用账号密码登录系统")
    @PostMapping("/login")
    public ResultVO login(@RequestBody @Valid AdminAuthForm adminAuthForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【管理员登录】参数不正确，adminAuthForm = {}", adminAuthForm);
            throw new RegisterException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultVOUtil.success(adminService.checkLogin(adminAuthForm));
    }
}
