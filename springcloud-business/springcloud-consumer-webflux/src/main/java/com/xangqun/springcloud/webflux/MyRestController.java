/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.webflux;

import com.xangqun.springcloud.mapper.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author laixiangqun
 * @since 2018-8-17
 */
@RestController
@RequestMapping("/users")
public class MyRestController {

    @Autowired
    private TestService testService;

    @GetMapping("/{user}")
    public Mono<User> getUser(@PathVariable Long user) {
        return testService.getUserBeanById(user);
    }

    @GetMapping("/{user}/customers")
    public Flux<User> getUserCustomers(@PathVariable Long user) {
        return Flux.error(new RuntimeException());
    }

    @DeleteMapping("/{user}")
    public Mono<User> deleteUser(@PathVariable Long user) {
        return Mono.just(new User());
    }

}