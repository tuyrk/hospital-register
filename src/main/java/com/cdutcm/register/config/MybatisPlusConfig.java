package com.cdutcm.register.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/15 16:42 星期五
 * Description:
 * mybatis plus分页pages和total为0
 */

@Configuration
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }
}
