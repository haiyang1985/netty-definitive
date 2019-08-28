package org.netty.definitive.chapter5.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TestUserInfo {
    public static void main(String[] args) throws IOException {
        UserInfo info = new UserInfo();
        info.buildUserId(100).buildUserName("Welcome to Netty");
        System.out.println("The byte array serializable length is:" + info.codec().length);
        System.out.println("-------------------------------------");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(info);
        os.flush();
        os.close();
        byte[] b = bos.toByteArray();
        bos.close();
        System.out.println("The jdk serializable length is:" + b.length);
    }
}
