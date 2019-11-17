package com.cdutcm.register.properties;

import lombok.Data;

import java.util.Map;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/26 15:58 星期二
 * Description:
 */
@Data
public class WeChatAccountProperties {
    //公众平台id
    private String mpAppId;
    //公众平台密钥
    private String mpAppSecret;
    //Token
    private String token;
    //微信模板Id
    private Map<String, String> templateId;
}