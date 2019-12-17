package com.my.netty_test;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline= socketChannel.pipeline();

        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        //加入用户定义的处理器 HttpServerHandler
        pipeline.addLast("testHttpServerHandler",new TestHttpServerHandler()); //用户定义的handler
    }
}
