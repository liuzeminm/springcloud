package com.xangqun.springcloud.framework.service.web;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加fastjson的转换
 */
@Configuration
public class FastjsonConverter {

    @Bean
    public HttpMessageConverters customConverters() {
        // 定义一个转换消息的对象

        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        // 添加fastjson的配置信息 比如 ：是否要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);

        // 处理中文乱码问题
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.DisableCircularReferenceDetect
        );
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // 在转换器中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);

        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        //增加解析spring boot actuator结果的解析
        fastMediaTypes.add(MediaType.valueOf("application/vnd.spring-boot.actuator.v2+json"));
        fastConverter.setSupportedMediaTypes(fastMediaTypes);

        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setDefaultCharset(Charset.forName("UTF-8"));
        stringConverter.setSupportedMediaTypes(fastMediaTypes);

        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // 将转换器添加到converters中
        return new HttpMessageConverters(stringConverter, fastConverter);
    }
}