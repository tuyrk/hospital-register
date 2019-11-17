package com.cdutcm.register.dataobject.vo;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.io.Serializable;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/3 17:09 星期日
 * Description:
 */
@Data
@JsonView(BaseView.class)
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = -5859421979210160864L;
    private Integer code;
    private String msg;
    private T data;
}
