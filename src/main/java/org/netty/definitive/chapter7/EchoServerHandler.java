package org.netty.definitive.chapter7;

import java.util.List;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 打印接收到的消息
        List<Object> list = (List<Object>) msg;
        System.out.println("服务端收到客户端发来的消息是：" + list);
        ctx.writeAndFlush(list);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
