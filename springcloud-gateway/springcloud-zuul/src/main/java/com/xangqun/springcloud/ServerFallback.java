/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ServerFallback
 * 
 * @author gaoyanlong
 * @since 2018年3月5日
 */
@Component
public class ServerFallback implements FallbackProvider {

  protected static final Logger logger = LoggerFactory.getLogger(ServerFallback.class);

  @Override
  public String getRoute() {
    // api服务id，如果需要所有调用都支持回退，则return "*"或return null
    return "*";
  }

  @Override
  public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
    logger.error("gateway fallback", cause);
    return newFallbackReponse();
  }

  private ClientHttpResponse newFallbackReponse() {
    return new DefaultClientHttpResponse();
  }

  public static class DefaultClientHttpResponse implements ClientHttpResponse {

    @Override
    public InputStream getBody() throws IOException {
      JSONObject r = new JSONObject();
      r.put("code", "00099");
      r.put("message", "系统调用错误");
      return new ByteArrayInputStream(r.toJSONString().getBytes("UTF-8"));
    }

    @Override
    public HttpHeaders getHeaders() {
      HttpHeaders headers = new HttpHeaders();
      // 和body中的内容编码一致，否则容易乱码
      headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
      return headers;
    }

    /**
     * 网关向api服务请求是失败了，但是消费者客户端向网关发起的请求是OK的， 不应该把api的404,500等问题抛给客户端 网关和api服务集群对于客户端来说是黑盒子
     */
    @Override
    public HttpStatus getStatusCode() throws IOException {
      return HttpStatus.OK;
    }

    @Override
    public int getRawStatusCode() throws IOException {
      return HttpStatus.OK.value();
    }

    @Override
    public String getStatusText() throws IOException {
      return HttpStatus.OK.getReasonPhrase();
    }

    @Override
    public void close() {
      logger.debug("close");
    }

  }

}
