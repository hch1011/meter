package com.jd.meter.sync.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理ping数据包的handler
 */
public class PingServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf recbuf = (ByteBuf) msg;
        String recMsg = null;
        if (recbuf.hasArray()) {
            byte[] array = recbuf.array();
            recMsg = new String(array);
        }

        if (!recbuf.hasArray()) {
            int length = recbuf.readableBytes();
            byte[] array = new byte[length];
            recbuf.getBytes(recbuf.readerIndex(), array);
            recMsg = new String(array);
        }

        System.out.println("服务器端接收消息：" + recMsg);




        final ChannelFuture f = ctx.writeAndFlush(msg);
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                System.out.println("服务端发送消息");
            }
        });
//        ctx.write(msg); // (1)
//        ctx.flush(); // (2)

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
