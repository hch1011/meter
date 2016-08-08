package com.jd.meter.communication;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by hujintao on 2016/8/6.
 */
public class NettyEncoder extends MessageToByteEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        //ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes());
        byte[] body = msg.getBytes();
        //out.writeInt(body.length);
        out.writeBytes(body);
    }
}
