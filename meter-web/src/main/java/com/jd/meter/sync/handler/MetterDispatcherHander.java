package com.jd.meter.sync.handler;


import com.jd.meter.sync.pkg.PackageBasic;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hujintao on 2016/8/6.
 */
@Component
public class MetterDispatcherHander extends SimpleChannelInboundHandler<PackageBasic> {

	public Map<Integer, PackageBasicHandler> packageBasicHandlerCache = new HashMap<Integer, PackageBasicHandler>();
	public void register(PackageBasicHandler handler){
		packageBasicHandlerCache.put(handler.matchPackageType(),handler);
	}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PackageBasic packageBasic) throws Exception {
        if(packageBasicHandlerCache.containsKey(packageBasic.getPackageType())){
            packageBasicHandlerCache.get(packageBasic.getPackageType()).process(channelHandlerContext, packageBasic);
        }
    }
}
