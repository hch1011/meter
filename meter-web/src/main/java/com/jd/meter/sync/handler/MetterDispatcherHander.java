package com.jd.meter.sync.handler;

import com.jd.meter.sync.pkg.PackageBasic;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.Map;

/**
 * Created by hujintao on 2016/8/6.
 */
public class MetterDispatcherHander extends SimpleChannelInboundHandler<PackageBasic> {
    //static Map<Integer, SimpleChannelInboundHandler> map;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PackageBasic s) throws Exception {

    }
}
