package com.jd.meter.sync.handler;

import com.jd.meter.sync.pkg.PackageBasic;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理ping数据包的handler
 */
public class PingServerHandler extends SimpleChannelInboundHandler<PackageBasic> {
	

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PackageBasic msg) throws Exception {
		 
	}
     
 
}
