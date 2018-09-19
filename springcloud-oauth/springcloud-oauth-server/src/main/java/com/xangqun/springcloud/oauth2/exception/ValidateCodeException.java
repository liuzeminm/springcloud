/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.oauth2.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author laixiangqun
 * @version 1.0.0
 * @ClassName ValidateCodeException.java
 * @Description 自定义 验证码异常类
 * @createTime 2018年09月19日 15:24
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg) {
        super(msg);
    }
}
