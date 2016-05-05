package com.eason.netty.demo;

import com.eason.netty.demo.client.EchoClient;
import com.eason.netty.demo.server.EchoServer;


public class ClientTest {

    public static void main(String[] args) throws Exception {
        EchoServer server = new EchoServer();
        server.start();

        new EchoClient("127.0.0.1", 8080).start();


    }
}
