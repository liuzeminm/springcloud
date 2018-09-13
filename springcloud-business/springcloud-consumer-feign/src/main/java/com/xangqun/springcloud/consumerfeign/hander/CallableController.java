/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.consumerfeign.hander;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author laixiangqun
 * @since 2018-8-16
 */
@Controller
@Slf4j
public class CallableController {
    /**
     * 线程池
     */
    public static ExecutorService FIXED_THREAD_POOL = Executors.newFixedThreadPool(30);


    @RequestMapping("/callable")
    public Callable<String> callable() {
        log.info("外部线程：" + Thread.currentThread().getName());
        return () -> {
            log.info("内部线程：" + Thread.currentThread().getName());
            return "callable!";
        };
    }

    @RequestMapping("/deferredresult")
    public DeferredResult<String> deferredResult() {
        log.info("外部线程：" + Thread.currentThread().getName());
        //设置超时时间
        DeferredResult<String> result = new DeferredResult<String>(60 * 1000L);
        //处理超时事件 采用委托机制
        result.onTimeout(() -> {
            log.error("DeferredResult超时");
            result.setResult("超时了!");
        });
        result.onCompletion(() -> {
            //完成后
            log.info("调用完成");
        });
        FIXED_THREAD_POOL.execute(() -> {

            //处理业务逻辑
            log.info("内部线程：" + Thread.currentThread().getName());
            //返回结果
            result.setResult("DeferredResult!!");
        });
        return result;
    }

    @RequestMapping("/webAsyncTask")
    public WebAsyncTask<String> webAsyncTask() {
        log.info("外部线程：" + Thread.currentThread().getName());
        WebAsyncTask<String> result = new WebAsyncTask<>(60 * 1000L, () -> {
            log.info("内部线程：" + Thread.currentThread().getName());
            return "WebAsyncTask!!!";
        });
        result.onTimeout(() ->
                "WebAsyncTask超时!!!"
        );
        result.onCompletion(() -> {
            //超时后 也会执行此方法
            log.info("WebAsyncTask执行结束");
        });
        return result;
    }
}
