package com.eason.netty.demo;

import com.eason.netty.demo.client.NettyClient;
import com.eason.netty.demo.client.NettyServer;

public class NettyTestMain {

    public static void main(String[] args) throws Exception {
        NettyServer server = new NettyServer();
        server.start();//启动server
        Thread.sleep(3000);

        NettyClient nettyClient = new NettyClient();
        nettyClient.start();

        server.push("服务端");

//        try {
//            for (int i = 0; i < 5; i++) {
//                String response = nettyClient.send("111111111111");
//                System.out.println("response:" + response);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            nettyClient.close();
//        }
//
//        nettyServer.close();

    }
}