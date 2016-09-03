package com.jd.meter.sync.handler;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by hujintao on 2016/8/6.
 */
@Component
public class MetterDispatcherHander extends ChannelInboundHandlerAdapter {

	public Map<Integer, PackageBasicHandler> packageBasicHandlerCache = new HashMap<Integer, PackageBasicHandler>();
	public void register(PackageBasicHandler handler){
		packageBasicHandlerCache.put(handler.matchPackageType(),handler);
	} 
}
