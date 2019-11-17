package com.cdutcm.register.config;

import com.cdutcm.register.properties.RegisterProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/13 18:24 星期三
 * Description:
 */
@Configuration
@EnableConfigurationProperties(RegisterProperties.class)
public class PropertiesConfig {
}
