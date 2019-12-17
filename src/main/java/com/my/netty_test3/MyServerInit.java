package com.my.netty_test3;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyServerInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline= ch.pipeline(); //管道模式，请求过来被一个个handler 按顺序处理

        pipeline.addLast(new IdleStateHandler(5,7,
                10, TimeUnit.SECONDS)); //空闲检测handler

        pipeline.addLast(new MyServerHandler());
    }
}
