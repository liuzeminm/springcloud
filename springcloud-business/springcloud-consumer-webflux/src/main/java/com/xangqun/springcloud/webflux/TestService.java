/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.webflux;

import com.xangqun.springcloud.mapper.entity.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author laixiangqun
 * @since 2018-8-17
 */
@Service
public class TestService {

    private Map<Long, User> UserBeanMap = new ConcurrentHashMap<>();


    public Flux<User> getUserBeanList(Flux<Long> ids) {
        return ids.flatMap(id -> Mono.justOrEmpty(this.UserBeanMap.get(id)));
    }

    public Mono<User> getUserBeanById(Long id) {
        return Mono.justOrEmpty(this.UserBeanMap.get(id));
    }

    public Flux<User> createOrUpdate(Flux<User> user) {
        return user.doOnNext(user1 -> this.UserBeanMap.put(user1.getId(), user1));
    }

    public Mono<User> createOrUpdate(User user) {
        return Mono.just(user);
    }

    public Mono<User> deleteById(Long id) {
        return Mono.justOrEmpty(this.UserBeanMap.remove(id));
    }

}

