package com.my.netty_test3.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyChatClient {

    public static void main(String[] args) throws InterruptedException, IOException {

        EventLoopGroup eventLoopGroup= new NioEventLoopGroup(); //这个相当于selector?

        try{
            Bootstrap bootstrap= new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new MyChatClientInit());

            Channel channel= bootstrap.connect("localhost", 8888).sync().channel();

            BufferedReader br= new BufferedReader(new InputStreamReader(System.in)); //从控制台读取输入
            for(;;){
                channel.writeAndFlush(br.readLine()+"\r\n");  //这里才是阻塞的? 把控制台输入发送到服务端
            }
            //channelFuture.channel().close().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
