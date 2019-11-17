package com.cdutcm.register.exception;

import com.cdutcm.register.enums.ResultEnum;
import lombok.Getter;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/11 12:53 星期一
 * Description:
 */
@Getter
public class RegisterException extends RuntimeException {
    private Integer code;

    public RegisterException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public RegisterException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
