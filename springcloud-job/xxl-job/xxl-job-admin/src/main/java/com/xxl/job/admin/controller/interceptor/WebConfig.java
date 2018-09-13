/**
 * Copyright 2017-2025 schedule Group.
 */
package com.xxl.job.admin.controller.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author laixiangqun
 * @since 2018-6-28
 */
@Configuration
public class WebConfig  implements WebMvcConfigurer {

    @Bean
    public CookieInterceptor getCookieInterceptor() {
        return new CookieInterceptor();
    }

    @Bean
    @DependsOn("xxlJobAdminConfig")
    public PermissionInterceptor getPermissionInterceptor() {
        return new PermissionInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptor = registry.addInterceptor(getCookieInterceptor());
        InterceptorRegistration permissionInterceptor = registry.addInterceptor(getPermissionInterceptor());
        // 排除配置
        permissionInterceptor.excludePathPatterns("/error");

        // 拦截配置
        interceptor.addPathPatterns("/**");
        permissionInterceptor.addPathPatterns("/**");

    }


//    @Bean
//    public FreeMarkerConfigurer freemarkerConfig() {
//        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
//        configurer.setTemplateLoaderPaths("/static/static/WEB-INF/template/");
//        configurer.setDefaultEncoding("UTF-8");
//        return configurer;
//    }
//
//    @Bean
//    public FreeMarkerViewResolver freeMarkerViewResolver() {
//        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
//        resolver.setCache(false);
//        resolver.setPrefix("");
//        resolver.setSuffix(".ftl");
//        resolver.setContentType("text/html; charset=UTF-8");
//        return resolver;
//    }
}
