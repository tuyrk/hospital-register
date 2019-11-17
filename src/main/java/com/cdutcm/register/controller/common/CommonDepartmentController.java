package com.cdutcm.register.controller.common;

import com.cdutcm.register.dataobject.vo.ResultVO;
import com.cdutcm.register.service.DepartmentService;
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
 * Create: 2019/2/12 23:13 星期二
 * Description:
 */
@Api(tags = "【公共-科室】")
@Slf4j
@RestController
@RequestMapping("/common/department")
public class CommonDepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @ApiOperation("【医院科室】获取科室列表")
    @GetMapping("/list")
    public ResultVO list() {
        return ResultVOUtil.success(departmentService.getDepartmentVO());
    }
}
