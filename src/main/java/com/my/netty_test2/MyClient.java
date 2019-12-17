package com.my.netty_test2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup eventLoopGroup= new NioEventLoopGroup();

        try{
            Bootstrap bootStrap= new Bootstrap();
            bootStrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new MyClientInitializer());

            ChannelFuture channelFuture= bootStrap.connect("localhost", 8888).sync();
            channelFuture.channel().close().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
