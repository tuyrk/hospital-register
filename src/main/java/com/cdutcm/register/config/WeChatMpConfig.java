package com.cdutcm.register.config;

import com.cdutcm.register.properties.RegisterProperties;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/26 15:48 星期二
 * Description:
 */
@Component
public class WeChatMpConfig {
    private String appId;
    private String appSecret;
    private String token;

    public WeChatMpConfig(RegisterProperties registerProperties) {
        appId = registerProperties.getWechat().getMpAppId();
        appSecret = registerProperties.getWechat().getMpAppSecret();
        token = registerProperties.getWechat().getToken();
    }

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(appId);
        wxMpInMemoryConfigStorage.setSecret(appSecret);
        wxMpInMemoryConfigStorage.setToken(token);
        return wxMpInMemoryConfigStorage;
    }
}
