package com.my.netty_test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override  //读取客户端发过来的请求， 并且响应
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {

        if( httpObject instanceof HttpRequest) {

            //转换请求
            HttpRequest request= (HttpRequest) httpObject;
            URI uri= new URI(request.uri());

            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求favicon");
                return;  //如果是 请求favicon， 不执行下面的代码
            }
            System.out.println("process request");
            ByteBuf content = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, content);
            //设置回复 httpheader
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            ctx.writeAndFlush(response); //写回到客户端
        }
    }
}
