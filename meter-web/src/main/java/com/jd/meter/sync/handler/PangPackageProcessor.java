package com.jd.meter.sync.handler;


import com.jd.meter.sync.pkg.PackageBasic;
import com.jd.meter.sync.pkg.PackageType;

import io.netty.channel.ChannelHandlerContext;
/**
 * 业务处理类
 */
public class PangPackageProcessor extends AbstractPackageProcessor {

	@Override
	public int matchPackageType() {
		return PackageType.pang;
	}

	@Override
	public void process(ChannelHandlerContext ctx, PackageBasic msg) {
		//todo
		ctx.channel().write(new PangPackageProcessor());
	}
  

}