package com.my.websocket_demo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketChannelInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline= ch.pipeline();

        pipeline.addLast(new HttpServerCodec()); //http编码解析器
        pipeline.addLast(new ChunkedWriteHandler()); //以块方式写入的处理器
        pipeline.addLast(new HttpObjectAggregator(8192));
        //http聚合处理器, 把块聚合成一个完整的http request 或response

        pipeline.addLast(new WebSocketServerProtocolHandler("/ws")); //websocket 虚拟地址
        //处理websocket 的处理器, 服务器的虚拟路径

        pipeline.addLast(new WebSocketTextHandler());
    }
}
