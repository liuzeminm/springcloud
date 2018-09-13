/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import net.openhft.affinity.AffinityStrategies;
import net.openhft.affinity.AffinityThreadFactory;

import java.util.concurrent.ThreadFactory;


/**
 * @author laixiangqun
 * @since 2018-6-7
 */
public class LogUdpNettyServer {

    public void run(int port) throws Exception {
        //cpu绑定 减少上下文切换
        ThreadFactory threadFactory = new AffinityThreadFactory("atf_wrk", AffinityStrategies.DIFFERENT_CORE);
        EventLoopGroup group = new NioEventLoopGroup(3, threadFactory);
        Bootstrap b = new Bootstrap();
        //由于我们用的是UDP协议，所以要用NioDatagramChannel来创建
        //支持广播
        b.group(group)
                .option(ChannelOption.SO_BROADCAST, true)// 支持广播
                .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                .channel(NioDatagramChannel.class)
                .handler(new ChannelInitializer<DatagramChannel>() {
                    @Override
                    protected void initChannel(DatagramChannel ch) throws Exception {
                        ch.pipeline().addLast(new UdpServerHandler());
                    }
                });
        b.bind(port).sync().channel().closeFuture().await();
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        String data = "12345.67.890";
        int index = data.lastIndexOf(".");
        System.err.println(data.substring(0, index) + "." + 123);
        new LogUdpNettyServer().run(port);
    }


    private static class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
            String req = packet.content().toString(CharsetUtil.UTF_8);
            System.out.println("test" + req);
//            ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("ok",CharsetUtil.UTF_8), packet.sender()));
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
            cause.printStackTrace();
        }
    }
}
