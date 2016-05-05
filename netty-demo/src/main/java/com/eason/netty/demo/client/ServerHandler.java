package com.eason.netty.demo.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ConcurrentHashMap;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private ConcurrentHashMap<String, Channel> sessionChannelMap = new ConcurrentHashMap<String, Channel>();


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("from client:");
        super.channelActive(ctx);

        sessionChannelMap.put("1", ctx.channel());
        //ctx.writeAndFlush(JsonUtils.toString("111"));//write bytes to socket,and flush(clear) the buffer cache.
    }

//    @Override
//    public void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
//        System.out.println("from client:" + message);
//
//        HashMap json = JsonUtils.toObject(message, HashMap.class);
//
////        JSONObject json = JSONObject.fromObject(message);
//        String source = (String) json.get("source");
//
//        String md5 = source + "md5";
//        //解析成JSON
//        json.put("md5Hex", md5);
//        ctx.writeAndFlush(JsonUtils.toString(json));//write bytes to socket,and flush(clear) the buffer cache.
//    }



    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public void call(String msg){
        Channel channel = sessionChannelMap.get("1");
        channel.writeAndFlush(msg);

        //channel.writeAndFlush(msg);
    }
}