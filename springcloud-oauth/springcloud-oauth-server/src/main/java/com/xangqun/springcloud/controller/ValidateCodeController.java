/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.controller;

import com.xangqun.springcloud.oauth2.model.ImageCode;
import com.xangqun.springcloud.oauth2.service.ImageCodeGenerator;
import com.xangqun.springcloud.oauth2.service.ValidateCodeGenerator;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author laixiangqun
 * @version 1.0.0
 * @ClassName ValidateCodeController.java
 * @Description TODO
 * @createTime 2018年09月19日 15:35
 */
@RestController
public class ValidateCodeController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
    /**
     * 引入 session
     */
//    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private ImageCodeGenerator imageCodeGenerator;

    @GetMapping("/oauth/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = imageCodeGenerator.createCode(new ServletWebRequest(request));
        //TODO 将随机数 放到Session中
//        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);
        redisTemplate.opsForValue().set(SESSION_KEY+request.getParameter("random"),imageCode);
        //写给response 响应
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(imageCode.getImage(), "JPEG", out);
        IOUtils.closeQuietly(out);
    }
}
