package com.eason.netty.demo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
    private static final int port = 8080;
    private ServerBootstrap bootstrap;
    private ChannelFuture future;
    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);//more than 1 is not needed!
        EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() - 1);
        try {
            bootstrap = new ServerBootstrap();//create ServerSocket transport。
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ServerHandler());
                        }
                    }).childOption(ChannelOption.TCP_NODELAY, true);
            future = bootstrap.bind(18080).sync();
            //init = true;
            //
            System.out.println("server started");
        }  finally {
            System.out.println("server finally");
//                workerGroup.shutdownGracefully();
//                bossGroup.shutdownGracefully();
//                System.out.println("server closed");
        }


//        ServerBootstrap b = new ServerBootstrap();// 引导辅助程序
//        EventLoopGroup group = new NioEventLoopGroup();// 通过nio方式来接收连接和处理连接
//        try {
//            b.group(group);
//            b.channel(NioServerSocketChannel.class);// 设置nio类型的channel
//            //b.localAddress(new InetSocketAddress(port));// 设置监听端口
//            b.childHandler(new ChannelInitializer<SocketChannel>() {//有连接到达时会创建一个channel
//                protected void initChannel(SocketChannel ch) throws Exception {
//                    // pipeline管理channel中的Handler，在channel队列中添加一个handler来处理业务
//                    ch.pipeline().addLast("myHandler", new EchoServerHandler());
//                }
//            }).childOption(ChannelOption.TCP_NODELAY, true);
//            ChannelFuture f = b.bind(port).sync();// 配置完成，开始绑定server，通过调用sync同步方法阻塞直到绑定成功
//            System.out.println(EchoServer.class.getName() + " started and listen on " + f.channel().localAddress());
//            //f.channel().closeFuture().sync();// 应用程序会一直等待，直到channel关闭
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            group.shutdownGracefully().sync();//关闭EventLoopGroup，释放掉所有资源包括创建的线程
//        }
    }

    public static void main(String[] args) {
        try {
            new EchoServer().start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}