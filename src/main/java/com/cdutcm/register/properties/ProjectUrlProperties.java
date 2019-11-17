package com.cdutcm.register.properties;

import lombok.Data;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/26 15:59 星期二
 * Description:
 */
@Data
public class ProjectUrlProperties {
    // 微信公众平台授权URL
    private String wechatMpAuthorize;
    //登录成功的URL
    private String returnUrl;
    //管理员登录URL
    private String adminLogin;
    // 预约挂号平台
    private String register;
}