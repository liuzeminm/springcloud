/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.startrun;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * https://blog.csdn.net/gebitan505/article/details/55047819
 * 启动顺序
 * ApplicationStartingEvent
 * ApplicationEnvironmentPreparedEvent
 * ApplicationPreparedEvent
 * ApplicationStartedEvent <= 新增的事件
 * CommandLineRunner
 * ApplicationReadyEvent
 * ApplicationFailedEvent
 *
 * @author laixiangqun
 * @since 2018-8-16
 */
@Component
public class MyStartupRunner1 implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("<<<<<<<<<<<<这个是测试CommandLineRunn接口>>>>>>>>>>>>>>");

    }

}
