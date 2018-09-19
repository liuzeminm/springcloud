/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.component.redis;

import com.alibaba.fastjson.parser.ParserConfig;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * https://blog.csdn.net/xiaolyuh123/article/details/78682200
 *
 * @author laixiangqun
 * @since 2018-8-1
 */
@Configuration
public class RedisConfig {

    /**
     * 重写Redis序列化方式，使用Json方式:
     * 当我们的数据存储到Redis的时候，我们的键（key）和值（value）都是通过Spring提供的Serializer序列化到数据库的。
     * RedisTemplate默认使用的是JdkSerializationRedisSerializer，StringRedisTemplate默认使用的是StringRedisSerializer。
     * Spring Data JPA为我们提供了下面的Serializer：
     * GenericToStringSerializer、Jackson2JsonRedisSerializer、JacksonJsonRedisSerializer、JdkSerializationRedisSerializer、OxmSerializer、StringRedisSerializer。
     * 在此我们将自己配置RedisTemplate并定义Serializer。
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RedisTemplate.class)
    @Autowired
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

//        使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
//        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        serializer.setObjectMapper(mapper);
//        template.setValueSerializer(serializer);
//        template.setHashValueSerializer(serializer);

//        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        // 全局开启AutoType，不建议使用
//         ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // 建议使用这种方式，小范围指定白名单
//        ParserConfig.getGlobalInstance().addAccept("com.xangqun.");
        // 设置值（value）的序列化采用FastJsonRedisSerializer。
//        template.setValueSerializer(fastJsonRedisSerializer);
//        template.setHashValueSerializer(fastJsonRedisSerializer);

//        KryoRedisSerializer<Object> kryoRedisSerializer=new KryoRedisSerializer<>(Object.class);
//        template.setValueSerializer(kryoRedisSerializer);
//        template.setHashValueSerializer(kryoRedisSerializer);

        // 设置键（key）的序列化采用StringRedisSerializer。
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @Autowired
    RedisUtil redisUtil(RedisTemplate redisTemplate) {
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }

    @Bean("stringRedisTemplate")
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    @Autowired
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory);
        template.setConnectionFactory(redisConnectionFactory);

//        使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
//        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        serializer.setObjectMapper(mapper);
//        template.setValueSerializer(serializer);
//        template.setHashValueSerializer(serializer);

//        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        // 全局开启AutoType，不建议使用
//         ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // 建议使用这种方式，小范围指定白名单
//        ParserConfig.getGlobalInstance().addAccept("com.xangqun.");

        // 设置值（value）的序列化采用FastJsonRedisSerializer。
//        template.setValueSerializer(fastJsonRedisSerializer);
//        template.setHashValueSerializer(fastJsonRedisSerializer);

//        KryoRedisSerializer<Object> kryoRedisSerializer=new KryoRedisSerializer<>(Object.class);
//        template.setValueSerializer(kryoRedisSerializer);
//        template.setHashValueSerializer(kryoRedisSerializer);
        // 设置键（key）的序列化采用StringRedisSerializer。
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
