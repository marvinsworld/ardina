package com.eason.netty.demo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {

    private Bootstrap bootstrap;
    private ChannelFuture future;
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);//1 is OK
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class) //create SocketChannel transport
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new EchoClientHandler());//the same as ServerBootstrap
                        }
                    });
            //keep the connection with server，and blocking until closed!
            future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 18080)).sync();
            //init = true;
        } finally {
            System.out.println("client finally");
            //workerGroup.shutdownGracefully();

        }


//        EventLoopGroup group = new NioEventLoopGroup(2);
//        try {
//            Bootstrap b = new Bootstrap();
//            b.group(group);
//            b.channel(NioSocketChannel.class);
//            //b.remoteAddress(new InetSocketAddress(host, port));
//            b.handler(new ChannelInitializer<SocketChannel>() {
//
//                public void initChannel(SocketChannel ch) throws Exception {
//                    ch.pipeline().addLast(new EchoClientHandler());
//                }
//            });
//            ChannelFuture f = b.connect(new InetSocketAddress(host, port)).sync();
////            f.addListener(new ChannelFutureListener() {
////
////                public void operationComplete(ChannelFuture future) throws Exception {
////                    if (future.isSuccess()) {
////                        System.out.println("client connected");
////                    } else {
////                        System.out.println("server attemp failed");
////                        future.cause().printStackTrace();
////                    }
////
////                }
////            });
////            f.channel().closeFuture().sync();
//        } finally {
//            group.shutdownGracefully().sync();
//        }
    }

    public static void main(String[] args) throws Exception {

        new EchoClient("127.0.0.1", 8080).start();
    }
}