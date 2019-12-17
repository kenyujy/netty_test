package com.my.netty_test2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override //receiveMsg  接收到的消息被定义为是String 类型， 也可以定义为其他类型
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println(ctx.channel().remoteAddress()+", "+ msg);
        ctx.channel().writeAndFlush("from server: "+ UUID.randomUUID());
    }

    @Override //处理异常， 一般是关闭channel
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
