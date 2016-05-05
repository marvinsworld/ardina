package com.eason.netty.demo;

import com.eason.netty.demo.client.EchoClient;
import com.eason.netty.demo.server.EchoServer;

/**
 *
 * Created by eason on 4/23/16.
 */
public class ClientTest {

    public static void main(String[] args) throws Exception {
        EchoServer server = new EchoServer(8088);
        server.start();

        EchoClient client = new EchoClient("127.0.0.1",8088);
        client.start();


    }
}
