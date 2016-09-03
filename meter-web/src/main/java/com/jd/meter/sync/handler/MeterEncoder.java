package com.jd.meter.sync.handler;

import com.jd.meter.sync.pkg.PackageBasic;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by hujintao on 2016/8/6.
 */
public class MeterEncoder extends MessageToByteEncoder<PackageBasic> {
    @Override
    protected void encode(ChannelHandlerContext ctx, PackageBasic msg, ByteBuf out) throws Exception {
        //ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes());
        out.writeByte(PackageBasic.DELIMITER);
        out.writeInt(msg.resetLength());
        out.writeInt(msg.getPackageType());
        out.writeByte(0);
        if(msg.getLength() > 0) {
            out.writeBytes(msg.getBody());
        }
    }
}
