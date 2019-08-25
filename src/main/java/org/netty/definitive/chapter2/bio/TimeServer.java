package org.netty.definitive.chapter2.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port :" + port);
            Socket socket = null;
            while (true) {
                socket = server.accept();
                new Thread(new TimeServerHandler(socket)).start();

                if (1 == 2) {
                    break;
                }
            }
        } finally {
            if (server != null) {
                server.close();
                server = null;
            }
        }
    }
}
