/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.oauth2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author laixiangqun
 * @version 1.0.0
 * @ClassName ValidateCodeProperties.java
 * @Description TODO
 * @createTime 2018年09月19日 15:25
 */
@Data
@ConfigurationProperties(prefix = "oauth.code")
public class ValidateCodeProperties {
    /**
     * 图形验证码 配置属性
     */
    private ImageCodeProperties image = new ImageCodeProperties();


    /**
     * 图形验证码  配置读取类
     * Created by Fant.J.
     */
    @Data
    public class ImageCodeProperties {

        /**
         * 验证码宽度
         */
        private int width = 67;
        /**
         * 高度
         */
        private int height = 23;
        /**
         * 长度（几个数字）
         */
        private int length = 4;
        /**
         * 过期时间
         */
        private int expireIn = 60;

        /**
         * 需要图形验证码的 url
         */
        private String url;

    }
}
