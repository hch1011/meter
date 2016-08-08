package com.jd.meter.communication;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by hujintao on 2016/8/6.
 */
public class NettyDecoder extends ByteToMessageDecoder{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if(in.readableBytes() < 4) {//服务端发送包大小
            return;
        }

        //int dataLength = in.readInt();
        /*if(dataLength < 0) {
            ctx.close();
        }*/

        /*if(dataLength < in.readableBytes()){
            //ctx.close();
        }*/

        //传输正常
        byte[] body = new byte[in.readableBytes()];
        in.readBytes(body);

        String recMsg = new String(body, "UTF-8");

        out.add(recMsg);
    }
}
