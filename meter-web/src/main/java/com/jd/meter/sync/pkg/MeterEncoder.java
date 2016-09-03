package com.jd.meter.sync.pkg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by hujintao on 2016/8/6.
 */
public class MeterEncoder extends MessageToByteEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        //ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes());
        byte[] body = msg.getBytes();
        //out.writeInt(body.length);
        out.writeBytes(body);
    }
}
