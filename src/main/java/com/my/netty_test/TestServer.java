package com.my.netty_test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup bossGroup= new NioEventLoopGroup(); //接收连接, 返回给 worker 处理
        EventLoopGroup workerGroup= new NioEventLoopGroup(); //事件循环组
        try{
            ServerBootstrap serverBootstrap= new ServerBootstrap(); //服务端启动
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer()); //!启动器，加入用户定义的启动器

            ChannelFuture channelFuture= serverBootstrap.bind(8888).sync();//绑定监听端口
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
