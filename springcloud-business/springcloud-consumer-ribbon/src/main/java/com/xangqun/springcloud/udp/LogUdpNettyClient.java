/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.udp;

import com.alibaba.fastjson.JSON;
import com.xangqun.springcloud.mapper.entity.User;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author laixiangqun
 * @since 2018-6-7
 */
@Slf4j
public class LogUdpNettyClient implements DisposableBean {

    private static Channel ch;
    private static EventLoopGroup group;

    static {
        group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .option(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, 64 * 1024)
                    .channel(NioDatagramChannel.class)
                    .handler(new ChannelInitializer<DatagramChannel>() {
                        @Override
                        protected void initChannel(DatagramChannel ch) throws Exception {
                            ch.pipeline().addLast(new UdpClientHandler());
                        }
                    });
            ch = b.bind(0).sync().channel();
        } catch (Exception e) {
            log.error("启动失败:", e);
        }
    }

    public void run(String data, String ip, int port) {
        try {
            // 向网段类所有机器广播发UDP
//            String data = JsonUtil.toJsonString(logData);
            ByteBuf byteBuf = Unpooled.copiedBuffer(data, CharsetUtil.UTF_8);
            ch.writeAndFlush(
                    new DatagramPacket(
                            byteBuf,
                            new InetSocketAddress(ip, port
                            ))

            ).sync();
            ch.closeFuture();
        } catch (Exception e) {
            log.error("发送失败:", e);
        }
    }

    @Override
    public void destroy() throws Exception {
        group.shutdownGracefully();
    }

    private static class UdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
        private Logger logger = LoggerFactory.getLogger(UdpClientHandler.class);

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
            logger.error(e.getMessage(), e);
            ctx.close();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket msg) throws Exception {
//            String resp = msg.content().toString(CharsetUtil.UTF_8);
//            if (resp.equals("ok")){
//                logger.error(resp);
//                channelHandlerContext.close();
//            }
        }
    }

    public static void main(String[] args) throws Exception {
        AtomicInteger threadNumber = new AtomicInteger();
        AtomicInteger index = new AtomicInteger(0);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 50, 200,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r,
                        "udp_client-" + threadNumber.incrementAndGet());
                thread.setDaemon(true);
                return thread;
            }
        });
        long time = System.currentTimeMillis();
        System.err.println(time + ":" + index.get());
        int count = 0;
        while (count < 1) {
            count++;
            try {
                threadPoolExecutor.execute(() -> {
                    index.incrementAndGet();
                    int port = 8080;
                    new LogUdpNettyClient().run(JSON.toJSONString(new User()), "172.24.11.139", port);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (index.get() % 50 == 0) {
                long time2 = System.currentTimeMillis();
                System.err.println(time2 - time);
            }
        }


    }

}
