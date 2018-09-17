package com.xangqun.springcloud.consumerfeign.websocket;

/**
 * WsResponse
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2018/3/22
 */
public class WsResponse<T> {
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
