/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author laixiangqun
 * @version 1.0.0
 * @ClassName SecurityPermitAllConfig.java
 * @Description 关闭登录验证
 * @createTime 2018年09月12日 12:25
 */
@Configuration
public class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll()
                .and().csrf().disable();
    }
}
