package com.xangqun.springcloud.redispubsub;

import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@DependsOn("publisherService")
public class PublisherController {

    @Resource
    private PublisherService publisherService;

    /**
     * push 消息
     * @param params
     * @return
     */
    @PostMapping("pushMsg")
    public String pushMsg(String params){
        return publisherService.pushMsg(params);
    }
}
