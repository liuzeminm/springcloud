/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.consumerfeign.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

/**
 * @author laixiangqun
 * @version 1.0.0
 * @ClassName HelloJob.java
 * @Description TODO
 * @createTime 2018年09月13日 18:23
 */
@JobHandler(value = "helloJob")
@Component
public class HelloJob extends IJobHandler {

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        //打印日志使用该方法
        XxlJobLogger.log("HelloJob run-begins.");
        return SUCCESS; //返回执行成功
    }
}
