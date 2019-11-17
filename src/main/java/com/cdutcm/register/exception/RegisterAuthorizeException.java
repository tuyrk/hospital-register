package com.cdutcm.register.exception;

import com.cdutcm.register.enums.ResultEnum;
import lombok.Getter;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/26 16:43 星期二
 * Description:
 */
@Getter
public class RegisterAuthorizeException extends RuntimeException {
    private Integer code;

    public RegisterAuthorizeException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}
