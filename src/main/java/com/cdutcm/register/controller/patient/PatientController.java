package com.cdutcm.register.controller.patient;

import com.cdutcm.register.constant.RedisConstant;
import com.cdutcm.register.dataobject.form.PatientForm;
import com.cdutcm.register.dataobject.vo.ResultVO;
import com.cdutcm.register.dataobject.vo.patient.PatientVO;
import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.cdutcm.register.service.PatientService;
import com.cdutcm.register.service.RedisOperator;
import com.cdutcm.register.utils.ResultVOUtil;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/3 17:08 星期日
 * Description:
 */
@Api(tags = "【患者-患者】")
@Slf4j
@RestController
@RequestMapping("/patient/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private WxMpService wxMpService;

    @ApiOperation("【微信信息】获取微信用户的信息")
    @GetMapping("/userinfo")
    @ResponseBody
    public ResultVO userinfo(@ApiParam("登录凭证token") @CookieValue("token") String token) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_PATIENT, token));
        try {
            WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openid);
            Map<String, String> map = new HashMap<>();
            map.put("nickname", wxMpUser.getNickname());
            map.put("headImgUrl", wxMpUser.getHeadImgUrl());
            return ResultVOUtil.success(map);
        } catch (WxErrorException e) {
            log.error("【微信信息】获取微信用户的信息，{}", e);
            throw new RegisterException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
    }

    @ApiOperation("【患者信息列表】获取患者信息列表")
    @JsonView(PatientVO.ListView.class)
    @GetMapping("/list")
    public ResultVO list(@ApiParam("登录凭证token") @CookieValue("token") String token) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_PATIENT, token));
        return ResultVOUtil.success(patientService.getPatientList(openid));
    }

    @ApiOperation("【绑定患者】绑定患者信息")
    @PostMapping("/bind")
    public ResultVO bind(@ApiParam("登录凭证token") @CookieValue("token") String token,
                         @Valid @RequestBody PatientForm patientForm,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【绑定患者】参数不正确，patientForm = {}", patientForm);
            throw new RegisterException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        patientService.savePatient(patientForm, token);
        return ResultVOUtil.success();
    }

    @ApiOperation("【解除绑定】解绑患者信息")
    @DeleteMapping("/remove")
    public ResultVO remove(@ApiParam("患者ID") @RequestParam("patientId") String patientId) {
        patientService.removePatient(patientId);
        return ResultVOUtil.success();
    }
}
