package com.xangqun.springcloud.consumerfeign.feign;

import org.springframework.stereotype.Component;

/**
 * Created by laixiangqun on 2018/5/28.
 */
@Component
public class ErrorHystrix implements SpringDemoFeignService {

    @Override
    public String hello() {
        return "sorry, it's error!";
    }
}
