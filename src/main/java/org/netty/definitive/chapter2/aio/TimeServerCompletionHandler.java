package org.netty.definitive.chapter2.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class TimeServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, TimeServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel result, TimeServerHandler attachment) {
        attachment.socketChannel.accept(attachment, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        result.read(buffer, buffer, new TimeServerReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, TimeServerHandler attachment) {
        attachment.latch.countDown();
    }
}
