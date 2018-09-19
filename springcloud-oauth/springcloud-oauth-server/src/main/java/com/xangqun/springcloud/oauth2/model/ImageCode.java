/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.oauth2.model;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author laixiangqun
 * @version 1.0.0
 * @ClassName ImageCode.java
 * @Description 验证码信息类
 * @createTime 2018年09月19日 15:20
 */
@Data
public class ImageCode implements Serializable {
    /**
     * 图片
     */
    private transient BufferedImage image;
    /**
     * 随机数
     */
    private String code;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        this.image = image;
        this.code = code;
        this.expireTime = expireTime;
    }
    public ImageCode(BufferedImage image, String code, int  expireIn) {
        this.image = image;
        this.code = code;
        //当前时间  加上  设置过期的时间
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ImageCode() {
    }

    public boolean isExpried(){
        //如果 过期时间 在 当前日期 之前，则验证码过期
        return LocalDateTime.now().isAfter(expireTime);
    }

}
