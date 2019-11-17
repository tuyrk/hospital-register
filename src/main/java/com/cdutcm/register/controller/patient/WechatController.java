package com.cdutcm.register.controller.patient;

import com.cdutcm.register.constant.RedisConstant;
import com.cdutcm.register.dataobject.vo.ResultVO;
import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.cdutcm.register.properties.RegisterProperties;
import com.cdutcm.register.service.RedisOperator;
import com.cdutcm.register.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/26 15:44 星期二
 * Description:
 */
@Api(tags = "【患者-微信】")
@Slf4j
@Controller
@RequestMapping("/wechat")
public class WechatController {
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private RedisOperator redisOperator;

    private String wechatMpAuthorize;
    private String returnUrl;

    public WechatController(RegisterProperties registerProperties) {
        wechatMpAuthorize = registerProperties.getUrl().getWechatMpAuthorize();
        returnUrl = registerProperties.getUrl().getReturnUrl();
    }

    @ApiOperation("【授权JSSDK】获取授权信息")
    @ResponseBody
    @GetMapping("/jsapi")
    public ResultVO jsApiSignature(@ApiParam("当前页面链接") @RequestParam("url") String url) throws WxErrorException {
        return ResultVOUtil.success(wxMpService.createJsapiSignature(url));
    }

    @ApiOperation("【微信授权登录】")
    @GetMapping("/authorize")
    public String authorize() {
        //1. 配置
        //2. 调用方法
        String url = wechatMpAuthorize + "/wechat/login";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, "");
        return "redirect:" + redirectUrl;
    }

    @ApiIgnore
    @GetMapping("/login")
    public String login(@RequestParam("code") String code) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
            throw new RegisterException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }

        String openid = wxMpOAuth2AccessToken.getOpenId();

        String token = UUID.randomUUID().toString();
        //设置Redis
        redisOperator.set(String.format(RedisConstant.TOKEN_PATIENT, token), openid, RedisConstant.EXPIRE);
        return "redirect:" + returnUrl + "?token=" + token;
    }

    @ApiOperation("【模拟登录】测试时使用接口登录")
    @GetMapping("/test")
    @ResponseBody
    public ResultVO test() {
        String openid = "oYnw56OEcTV8oekci1lk-ss-YvoQ";
        String token = UUID.randomUUID().toString();
        //设置Redis
        redisOperator.set(String.format(RedisConstant.TOKEN_PATIENT, token), openid, RedisConstant.EXPIRE);
        return ResultVOUtil.success(token);
    }
}