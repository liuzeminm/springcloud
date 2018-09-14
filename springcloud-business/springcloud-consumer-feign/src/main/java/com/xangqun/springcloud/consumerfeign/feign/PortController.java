package com.xangqun.springcloud.consumerfeign.feign;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PortController {

    @Value("${redis.cache.expireSeconds:'df'}")
    String test;

    @RequestMapping("data")
    public String getData() {
        return "Hello World, I'm from time : " + test;// + new GsonBuilder().create().toJson(user);
    }



}
