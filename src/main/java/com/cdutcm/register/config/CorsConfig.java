package com.cdutcm.register.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/26 15:19 星期二
 * Description:
 * 配置跨域信息
 */
@Configuration
public class CorsConfig {
    public CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);//4允许使用Cookie
//        corsConfiguration.addAllowedOrigin("*");// 1允许任何域名使用
        corsConfiguration.addAllowedOrigin("http://10.200.116.218:5000");
        corsConfiguration.addAllowedOrigin("http://localhost:5000");
        corsConfiguration.addAllowedHeader("*");// 2允许任何头
        corsConfiguration.addAllowedMethod("*");// 3允许任何方法（post、get等）
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(0);
//        return bean;
        return new CorsFilter(source);
    }
}
