package com.my.websocket_demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

public class MyServer {

    public static void main(String[] args) {

        EventLoopGroup bossGroup= new NioEventLoopGroup(); //获取连接, 返回给 worker 处理
        EventLoopGroup workerGroup= new NioEventLoopGroup(); //事件循环组, 处理业务代码

        try{
            ServerBootstrap serverBootstrap= new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))  //处理bossGroup线程的 handler
                    .childHandler(new WebSocketChannelInit()); //启动器，启动器又包含handler
                    //childHandler 处理workerGroup的线程
            ChannelFuture channelFuture= serverBootstrap.bind(new InetSocketAddress(8888)).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
