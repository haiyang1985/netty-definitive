package org.netty.definitive.chapter4.chapter_4_2_decode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author hy_gu on 2018/3/6
 **/
public class TimeClientHandler extends ChannelHandlerAdapter {

    private byte[] req;
    private volatile int counter;

    public TimeClientHandler() {
//        req = "QUERY TIME ORDER".getBytes();
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf) msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req, "UTF-8");
        String body = (String) msg;
        System.out.println("Now is:" + body + ", Counter is:" + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
