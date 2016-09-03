package com.jd.meter.sync.handler;

import com.jd.meter.sync.pkg.PackageBasic;
import com.jd.meter.sync.pkg.PackagePang;
import com.jd.meter.sync.pkg.PackageType;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

/**
 * 业务处理类
 */
@Component
public class PackageBasicHandlerPing extends PackageBasicHandler {

	@Override
	public int matchPackageType() {
		return PackageType.ping;
	}

	@Override
	public void process(ChannelHandlerContext ctx, PackageBasic msg) {
		//...
		PackagePang pang = new PackagePang();
		ctx.channel().writeAndFlush(pang);
	}
  

}
