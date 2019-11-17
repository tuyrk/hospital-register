package com.cdutcm.register.controller.admin;

import com.cdutcm.register.dataobject.form.PageForm;
import com.cdutcm.register.dataobject.vo.patient.OrderVO;
import com.cdutcm.register.dataobject.vo.ResultVO;
import com.cdutcm.register.service.OrderService;
import com.cdutcm.register.utils.ResultVOUtil;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/13 23:12 星期三
 * Description:
 */
@Api(tags = "【管理员-订单】")
@Slf4j
@RestController
@RequestMapping("/admin/order")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation("【挂号信息管理】获取挂号记录列表")
    @GetMapping("/list")
    @JsonView(OrderVO.OrderInfoView.class)
    public ResultVO list(@ApiParam("挂号医生") @RequestParam(value = "doctorName", required = false) String doctorName,
                         @ApiParam("挂号科室") @RequestParam(value = "departmentName", required = false) String departmentName,
                         PageForm page) {
        return ResultVOUtil.success(orderService.getOrderList(doctorName, departmentName, page));
    }
    @ApiOperation("【挂号信息统计】获取挂号记录统计情况，科室及其对应挂号数")
    @GetMapping("/census")
    public ResultVO census(@ApiParam("挂号月份，格式：'yyyyMM'") @RequestParam(value = "month") String month) {
        return ResultVOUtil.success(orderService.getOrderCensus(month));
    }
}
