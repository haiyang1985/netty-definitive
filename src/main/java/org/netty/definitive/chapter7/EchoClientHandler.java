package org.netty.definitive.chapter7;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoClientHandler extends ChannelHandlerAdapter {
    private final int sendNumber;

    public EchoClientHandler(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfo[] userInfos = new UserInfo[sendNumber];
        UserInfo userInfo = null;
        for (int i = 0; i < sendNumber; i++) {
            userInfo = new UserInfo();
            userInfo.setUserId(i);
            userInfo.setUserName("ABCDEFG-->" + i);
            userInfos[i] = userInfo;
        }

        for (UserInfo info : userInfos) {
            ctx.writeAndFlush(info);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("客户端收到服务端的返回数据: " + msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
