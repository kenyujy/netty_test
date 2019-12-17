package com.my.netty_chat_room;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {
    //channel组 保存当前server 的所有active channel
    private static ChannelGroup chGroup= new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel= ctx.channel();
        // 把对应事件的channel 挑出来 单独发送消息
        System.out.println(msg);
        System.out.println(channel.remoteAddress());
        chGroup.forEach(ch->{
            System.out.println(chGroup.size());
            if(!(ch==channel)){ //不是发送消息的channel
                ch.writeAndFlush(channel.remoteAddress()+" 发送消息 "+msg+"\r\n"); //要加入换行符，delimiter才会识别
            }else{
             channel.writeAndFlush("[self] "+msg+"\r\n"); //write back to self
            }
        });
    }

    @Override //新连接加入
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel= ctx.channel(); //获取连接
        chGroup.writeAndFlush("client: "+channel.remoteAddress()+"加入\r\n"); //向channelGroup 内所有的channel群发消息
        chGroup.add(channel); //把新建立的channel 加入chGroup
    }

    @Override //连接退出
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel=ctx.channel();  //获取连接

        chGroup.writeAndFlush("client: "+channel.remoteAddress()+"退出\r\n");
        //无需手动移除channel
    }

    @Override //channel处于活动状态
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel= ctx.channel();
        System.out.println(channel.remoteAddress()+" 上线\r\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
