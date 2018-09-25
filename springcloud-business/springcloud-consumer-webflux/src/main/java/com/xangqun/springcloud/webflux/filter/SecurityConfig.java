///**
// * Copyright 2017-2025 Evergrande Group.
// */
//package com.xangqun.springcloud.webflux.filter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.ReactiveAuthenticationManager;
//import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.context.ServerSecurityContextRepository;
//
///**
// * @author laixiangqun
// * @version 1.0.0
// * @ClassName SecurityConfig.java
// * @Description TODO
// * @createTime 2018年09月25日 15:51
// */
//@EnableWebFluxSecurity
//@EnableReactiveMethodSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private ReactiveAuthenticationManager authenticationManager;
//
//    @Autowired
//    private ServerSecurityContextRepository securityContextRepository;
//
//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
//        return http.csrf().disable()
//                .formLogin().disable()
//                .httpBasic().disable()
//                .authenticationManager(authenticationManager)
//                .securityContextRepository(securityContextRepository)
//                .authorizeExchange()
//                .pathMatchers("/api/auth").permitAll()
//                .pathMatchers("/api/public").permitAll()
//                .anyExchange().authenticated()
//                .and().build();
//    }
//}
//
