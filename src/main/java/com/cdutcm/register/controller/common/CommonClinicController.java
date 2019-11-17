package com.cdutcm.register.controller.common;

import com.cdutcm.register.dataobject.vo.ResultVO;
import com.cdutcm.register.service.ClinicService;
import com.cdutcm.register.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/14 21:58 星期四
 * Description:
 */
@Api(tags = "【公共-医院】")
@RestController
@RequestMapping("/common/clinic")
public class CommonClinicController {
    @Autowired
    private ClinicService clinicService;

    @ApiOperation("【医院导航】获取楼层简介和医院位置")
    @GetMapping("/detail")
    public ResultVO detail() {
        return ResultVOUtil.success(clinicService.getFloorDetail());
    }

    @ApiOperation("【医院简介】获取医院简介")
    @GetMapping("/synopsis")
    public ResultVO synopsis() {
        return ResultVOUtil.success(clinicService.getClinicSynopsis());
    }
}
