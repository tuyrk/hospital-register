package com.cdutcm.register.handler;

import com.cdutcm.register.dataobject.vo.ResultVO;
import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterAuthorizeException;
import com.cdutcm.register.exception.RegisterException;
import com.cdutcm.register.properties.RegisterProperties;
import com.cdutcm.register.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/11 12:59 星期一
 * Description:
 */
@ControllerAdvice
public class RegisterExceptionHandler {
    @Autowired
    private RegisterProperties registerProperties;

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(RegisterAuthorizeException.class)
    public ModelAndView handlerRegisterAuthorizeException(RegisterAuthorizeException e) {
        if (e.getCode().equals(ResultEnum.AUTH_ADMIN_ERROR.getCode())) {//管理员未登录
            return new ModelAndView("redirect:"
                    .concat(registerProperties.getUrl().getAdminLogin()));
        } else if (e.getCode().equals(ResultEnum.AUTH_PATIENT_ERROR.getCode())) {//患者未登录
            return new ModelAndView("redirect:"
                    .concat(registerProperties.getUrl().getWechatMpAuthorize())
                    .concat("/wechat/authorize"));
        }
        throw new RegisterException(ResultEnum.INTERNAL_ERROR);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(RegisterException.class)
    public ResultVO handlerRegisterException(RegisterException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = Exception.class)
    public ResultVO handlerRegisterException(Exception e) {
        return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(), e.getMessage());
    }
}
