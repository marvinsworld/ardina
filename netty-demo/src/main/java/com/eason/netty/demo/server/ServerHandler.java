package com.eason.netty.demo.server;

import com.eason.common.JsonUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;

public class ServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        System.out.println("from client:" + message);

        HashMap json = JsonUtils.toObject(message, HashMap.class);

//        JSONObject json = JSONObject.fromObject(message);
        String source = (String) json.get("source");

        String md5 = source + "md5";
        //解析成JSON
        json.put("md5Hex", md5);
        ctx.writeAndFlush(json.toString());//write bytes to socket,and flush(clear) the buffer cache.
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}