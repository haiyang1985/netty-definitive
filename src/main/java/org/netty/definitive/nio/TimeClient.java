package org.netty.definitive.nio;

public class TimeClient {
    public static void main(String[] args) {
        int port = 8080;
        TimeClientHandler clientHandler = new TimeClientHandler("127.0.0.1", port);
        new Thread(clientHandler, "TimeClient-001").start();
    }
}
