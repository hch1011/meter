package com.jd.meter.sync.pkg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by hujintao on 2016/9/3.
 */
public class MeterDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < PackageBasic.minPackageLength) {  //这个HEAD_LENGTH是我们用于表示头长度的字节数。  由于上面我们传的是一个int类型的值，所以这里HEAD_LENGTH的值为4.
            return;
        }
        in.markReaderIndex();                  //我们标记一下当前的readIndex的位置
        byte delimit = in.readByte();
        int dataLength=0;

        if(delimit == 0xf0){//采集
            //read from cam
            dataLength = in.readShort();
            if (in.readableBytes() < dataLength+2) {  //这个HEAD_LENGTH是我们用于表示头长度的字节数。  由于上面我们传的是一个int类型的值，所以这里HEAD_LENGTH的值为4.
                return;
            }

            if (dataLength < 0) { // 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
                ctx.close();
            }
            byte[] body = new byte[dataLength];  //  嗯，这时候，我们读到的长度，满足我们的要求了，把传送过来的数据，取出来吧~~
            in.readBytes(body);  //
            in.readByte();
            in.readByte();
            PackageBasic packageBasic = new PackageBasic();
            packageBasic.setPackageType(PackageType.data_source_pic);
            packageBasic.setLength(dataLength);//
            packageBasic.setBody(body);
            out.add(packageBasic);
            return;
        }

        if(delimit != 0xff){//同步数据

        }


        dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {  //这个HEAD_LENGTH是我们用于表示头长度的字节数。  由于上面我们传的是一个int类型的值，所以这里HEAD_LENGTH的值为4.
            return;
        }

        if (dataLength < 0) {
            ctx.close();
        }

        if (in.readableBytes() < dataLength+5) { //读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
            in.resetReaderIndex();
            return;
        }
        int packageType = in.readInt();
        in.readByte();
        byte[] body = new byte[dataLength];  //  嗯，这时候，我们读到的长度，满足我们的要求了，把传送过来的数据，取出来吧~~
        in.readBytes(body);  //
        PackageBasic packageBasic = new PackageBasic();
        packageBasic.setPackageType(packageType);
        packageBasic.setLength(dataLength);//
        packageBasic.setBody(body);
        out.add(packageBasic);

    }
}
