package org.netty.definitive.chapter2.nio;

public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        TimeServerHandler timeServer = new TimeServerHandler(port);
        new Thread(timeServer, "NIO-TimeServerHandler-001").start();
    }
}
