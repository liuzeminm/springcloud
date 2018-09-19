/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.oauth2.service;

import com.xangqun.springcloud.oauth2.model.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author laixiangqun
 * @version 1.0.0
 * @ClassName ValidateCodeGenerator.java
 * @Description 验证码生成器
 * @createTime 2018年09月19日 15:22
 */
public interface ValidateCodeGenerator {
    /**
     * 创建验证码
     */
    ImageCode createCode(ServletWebRequest request);
}
