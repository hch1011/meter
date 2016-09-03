package com.jd.meter.communication;

import com.jd.meter.sync.pkg.PackageBasic;
import com.jd.meter.sync.pkg.PackageType;
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
public class CommunicateNettyHandler extends SimpleChannelInboundHandler<PackageBasic> {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 连接成功后向服务器发送数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        PackageBasic packageBasic = new PackageBasic();
        packageBasic.setBody("sjkfj".getBytes());
        packageBasic.setLength(8);
        packageBasic.setPackageType(PackageType.data_source_pic);

        ctx.writeAndFlush(packageBasic);
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
    public void channelRead0(ChannelHandlerContext ctx, PackageBasic msg) throws Exception{
        logger.info("the received message is：" + msg);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
