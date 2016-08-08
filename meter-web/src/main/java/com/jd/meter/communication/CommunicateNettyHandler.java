package com.jd.meter.communication;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hujintao on 2016/8/5.
 */
public class CommunicateNettyHandler extends SimpleChannelInboundHandler<String> {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 连接成功后向服务器发送数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String sendMsg = "Hello Server";
        ctx.writeAndFlush(sendMsg);
        /*ByteBuf buf = Unpooled.copiedBuffer(sendMsg.getBytes());

        final ChannelFuture f = ctx.writeAndFlush(buf); // (3)

        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                System.out.println("客户端发送消息：hello");
            }
        });*/
    }

    /**
     * 接收到服务器发来的数据进行处理
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception{
        logger.info("the received message is：" + msg);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
