/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.service.filter;

import com.xangqun.springcloud.component.base.util.NetworkUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author laixiangqun
 * @version 1.0.0
 * @ClassName UVStatisticsFilter.java
 * @Description 统计PV
 * @createTime 2018年08月30日 17:34
 */
public class UVStatisticsFilter extends OncePerRequestFilter {

    private StringRedisTemplate stringRedisTemplate;

    /**
     * 使用Redis的HeyperLog统计PV，HeyperLog提供的去重功能较之zset有较小的数据偏差，但能节省90%以上的空间
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String ip = NetworkUtil.getIpAddress(request);

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        stringRedisTemplate.opsForHyperLogLog().add("pv:" + format.format(new Date()), ip);

        filterChain.doFilter(request, response);

    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
}
