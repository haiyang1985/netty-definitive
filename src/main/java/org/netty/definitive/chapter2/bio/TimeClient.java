package org.netty.definitive.chapter2.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TimeClient {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        BufferedReader in = null;
        PrintWriter out = null;

        try (Socket socket = new Socket("127.0.0.1", port)) {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("QUERY TIME ORDER");
            System.out.println("Send order 2 server succeed.");
            String resp = in.readLine();
            System.out.println("Now is : " + resp);
        } catch (Exception e) {

        } finally {
            if (out != null) {
                out.close();
                out = null;
            }

            if (in != null) {
                in.close();
                in = null;
            }
        }
    }
}
