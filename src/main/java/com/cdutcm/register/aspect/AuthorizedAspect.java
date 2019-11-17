package com.cdutcm.register.aspect;

import com.cdutcm.register.constant.CookieConstant;
import com.cdutcm.register.constant.RedisConstant;
import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterAuthorizeException;
import com.cdutcm.register.service.RedisOperator;
import com.cdutcm.register.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/26 15:34 星期二
 * Description:
 */
@Slf4j
@Aspect
@Component
public class AuthorizedAspect {
    @Autowired
    private RedisOperator redisOperator;

    @Pointcut("execution(public * com.cdutcm.register.controller.patient.*.*(..))" +
            "&&!execution(public * com.cdutcm.register.controller.patient.WechatController.*(..))")
    public void verifyPatient() {
    }

    @Pointcut("execution(public * com.cdutcm.register.controller.admin.*.*(..))" +
            "&&!execution(public * com.cdutcm.register.controller.admin.AdminController.login(..))")
    public void verifyAdmin() {
    }

    @Before("verifyPatient()")
    public void doVerifyPatient() {
        //获取HttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询Cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("【登录校验】Cookie中查不到token");
            throw new RegisterAuthorizeException(ResultEnum.AUTH_PATIENT_ERROR);
        }

        //去Redis里查询
        String tokenValue = redisOperator.get(String.format(RedisConstant.TOKEN_PATIENT, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("【登录校验】Redis中查不到token");
            throw new RegisterAuthorizeException(ResultEnum.AUTH_PATIENT_ERROR);
        }
    }

    @Before("verifyAdmin()")
    public void doVerifyAdmin() {
        //获取HttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询Cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("【登录校验】Cookie中查不到token");
            throw new RegisterAuthorizeException(ResultEnum.AUTH_ADMIN_ERROR);
        }

        //去Redis里查询
        String tokenValue = redisOperator.get(String.format(RedisConstant.TOKEN_ADMIN, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("【登录校验】Redis中查不到token");
            throw new RegisterAuthorizeException(ResultEnum.AUTH_ADMIN_ERROR);
        }
    }
}
