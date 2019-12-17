package com.my.netty_chat_room;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyChatServer {

    public static void main(String[] args) {

        EventLoopGroup bossGroup= new NioEventLoopGroup(); //获取连接, 返回给 worker 处理
        EventLoopGroup workerGroup= new NioEventLoopGroup(); //事件循环组, 处理业务代码

        try{
            ServerBootstrap serverBootstrap= new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MyChatServerInit()); //启动器，启动器又包含handler
            //childHandler 处理workerGroup的线程
            ChannelFuture channelFuture= serverBootstrap.bind(8888).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
