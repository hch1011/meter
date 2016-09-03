package com.jd.meter.sync.handler;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.jd.meter.sync.pkg.PackageBasic;
import com.jd.meter.sync.pkg.PackageType;

import io.netty.channel.ChannelHandlerContext;
/**
 * 业务处理类
 */
public class PackageBasicHandlerPing extends PackageBasicHandler {

	@Override
	public int matchPackageType() {
		return PackageType.ping;
	}

	@Override
	public void process(ChannelHandlerContext ctx, PackageBasic msg) {
		ctx.channel().writeAndFlush("pang");
	}
  

}
