/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.startrun;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author laixiangqun
 * @since 2018-8-16
 */
@Component
public class MyStartupRunner2 implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("<<<<<<<<<<<<这个是测试ApplicationRunner接口>>>>>>>>>>>>>>");
    }



}
