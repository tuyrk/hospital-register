package com.cdutcm.register.controller.patient;

import com.cdutcm.register.constant.RedisConstant;
import com.cdutcm.register.dataobject.form.patient.OrderForm;
import com.cdutcm.register.dataobject.form.patient.PayForm;
import com.cdutcm.register.dataobject.vo.patient.OrderVO;
import com.cdutcm.register.dataobject.vo.ResultVO;
import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.cdutcm.register.service.OrderService;
import com.cdutcm.register.service.RedisOperator;
import com.cdutcm.register.utils.ResultVOUtil;
import com.fasterxml.jackson.annotation.JsonView;
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
 * Create: 2019/2/13 23:12 星期三
 * Description:
 */
@Api(tags = "【患者-订单】")
@Slf4j
@RestController
@RequestMapping("/patient/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation("【点击号源】下订单")
    @PostMapping("/place")
    public ResultVO place(@Valid @RequestBody OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【点击号源/下订单】参数不正确，patientForm = {}", orderForm);
            throw new RegisterException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultVOUtil.success(orderService.placeOrder(orderForm));
    }

    @ApiOperation("【确认支付/挂号详情】获取订单详情")
    @GetMapping("/detail")
    @JsonView(OrderVO.OrderDetailView.class)
    public ResultVO detail(@ApiParam("订单ID") @RequestParam("orderId") String orderId) {
        return ResultVOUtil.success(orderService.getOrderVOById(orderId));
    }

    @ApiOperation("【挂号记录】获取挂号记录列表")
    @GetMapping("/list")
    @JsonView(OrderVO.OrderSimpleView.class)
    public ResultVO list(@ApiParam("登录凭证token") @CookieValue("token") String token) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_PATIENT, token));
        return ResultVOUtil.success(orderService.getOrderVOListByOpenid(openid));
    }

    @ApiOperation("【缴费记录】获取缴费记录（缴费成功）列表")
    @GetMapping("/pay_list")
    public ResultVO payList(@ApiParam("登录凭证token") @CookieValue("token") String token) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_PATIENT, token));
        return ResultVOUtil.success(orderService.getPaidListByOpenid(openid));
    }

    @ApiOperation("【我的缴费】获取待缴费记录列表")
    @GetMapping("/paying")
    public ResultVO paying(@ApiParam("登录凭证token") @CookieValue("token") String token) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_PATIENT, token));
        return ResultVOUtil.success(orderService.getPayingListByOpenid(openid));
    }

    @ApiOperation("【支付订单】支付订单")
    @PutMapping("/pay")
    public ResultVO pay(@RequestBody @Valid PayForm payForm, BindingResult bindingResult,
                        @ApiParam("登录凭证token") @CookieValue("token") String token) {
        if (bindingResult.hasErrors()) {
            log.error("【支付订单】参数不正确，payForm = {}", payForm);
            throw new RegisterException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_PATIENT, token));
        orderService.payOrder(openid, payForm.getOrderId());
        return ResultVOUtil.success();
    }
}
