package com.xangqun.springcloud;

import com.xangqun.springcloud.framework.service.web.BaseWebController;
import com.xangqun.springcloud.mapper.UserMapper;
import com.xangqun.springcloud.stream.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortService extends BaseWebController {

    @Value("${server.port}")
    String port;

    @LocalServerPort
    private int localport;

    @Value("${data.test.port:'df'}")
    String test;
    @Autowired
    private StreamClient streamClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("data")
    public String getData() {
//        RLock rLock = RedisLockUtil.lock("testXANGQUN", TimeUnit.SECONDS, 5);
//        long time = redisTemplate.getExpire("test00");
//        rLock.unlock();
//        userMapper.deleteByPrimaryKey(1L);
        return "Hello World, I'm from time : " + test;// + new GsonBuilder().create().toJson(user);
    }

    @GetMapping("send")
    public void send() {
        com.xangqun.springcloud.stream.User user = new com.xangqun.springcloud.stream.User("张三", "154shmj");
        streamClient.output().send(MessageBuilder.withPayload("Hello World...").build());
//        streamClient.output().send(MessageBuilder.withPayload(user).build());
    }

    @RequestMapping("port")
    public String getPort() {
        return "Hello World, I'm from port : " + port;
    }

}
