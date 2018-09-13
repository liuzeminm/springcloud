package com.xangqun.springcloud.consumerfeign;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.adapter.servlet.CommonTotalFilter;
import com.alibaba.csp.sentinel.init.InitExecutor;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


import java.util.Collections;

@SpringBootApplication(exclude = {WebSocketReactiveAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrix
@ServletComponentScan //针对@webFilter @WebListener扫描
@ComponentScan(basePackages = {"com.xangqun.springcloud"})
public class ConsumerFeignApplication {

    private static final String RES_KEY = "com.xangqun.springcloud.consumerfeign.feign:hello()";

    public static void main(String[] args) {
        System.setProperty("csp.sentinel.dashboard.server","localhost:8082");
        initFlowRule();
        InitExecutor.doInit();
        new SpringApplicationBuilder().sources(ConsumerFeignApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

    private static void initFlowRule() {
        FlowRule flowRule = new FlowRule();
        flowRule.setResource(RES_KEY);
        flowRule.setCount(10);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setLimitApp("default");
        FlowRuleManager.loadRules(Collections.singletonList(flowRule));
    }

    @Bean
    public FilterRegistrationBean<CommonFilter> commonFilter() {
        FilterRegistrationBean<CommonFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CommonFilter());//添加过滤器
        registration.addUrlPatterns("/*");//设置过滤路径，/*所有路径
        registration.setName("CommonFilter");//设置优先级
        registration.setOrder(2);//设置优先级
        return registration;
    }

    @Bean
    public FilterRegistrationBean<CommonTotalFilter> commonTotalFilter() {
        FilterRegistrationBean<CommonTotalFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CommonTotalFilter());//添加过滤器
        registration.addUrlPatterns("/*");//设置过滤路径，/*所有路径
        registration.setName("commonTotalFilter");//设置优先级
        registration.setOrder(2);//设置优先级
        return registration;
    }
}
