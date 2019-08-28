package org.netty.definitive.chapter7;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class EchoClient {
    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // TODO 请自行扩展.
            }
        }

        new EchoClient().connect(port, "localhost");
    }

    public void connect(int port, String host) {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
//                        ch.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
//                        ch.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
//                        ch.pipeline().addLast(new EchoClientHandler(10));

                        ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0,2,0,2));
                        ch.pipeline().addLast("msgpack decode", new MsgpackDecoder());
                        ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
                        ch.pipeline().addLast("msgpack encode", new MsgpackEncoder());
                        ch.pipeline().addLast(new EchoClientHandler(10));
                    }
                });


        try {
            ChannelFuture f = b.connect(host, port).sync(); // 发起异步连接操作
            f.channel().closeFuture().sync();// 等待客户端链路关闭。
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            group.shutdownGracefully();
        }
    }
}
