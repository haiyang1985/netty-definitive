package org.netty.definitive.chapter4.chapter_4_2_decode;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author hy_gu on 2018/3/6
 **/
public class TimeServerNetty {

    //粘包的场景，客户端100个请求，服务端只收到两个报文，MSS为1024
    public static void main(String[] args) throws Exception {
        int port = 8080;
        new TimeServerNetty().bind(port);
    }

    public void bind(int port) throws Exception {
        // NioEventLoopGroup 线程组包含一组NIO线程， 专门用于处理网络的事件。
        // 两个线程组，一个用于处理接受客户端的连接，一个用于进行 SocketChannel 的网络读取。
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChildChannelHandler());

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel arg0) throws Exception {
            arg0.pipeline().addLast(new LineBasedFrameDecoder(1024)); //添加 换换解码器
            arg0.pipeline().addLast(new StringDecoder());
            arg0.pipeline().addLast(new TimeServerHandler());
        }
    }
}
