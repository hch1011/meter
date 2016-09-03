package com.jd.meter.sync.handler;


import com.jd.meter.exception.MeterExceptionFactory;
import com.jd.meter.sync.pkg.PackageBasic; 

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.SimpleChannelInboundHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hujintao on 2016/8/6.
 */
@Component 
@Sharable
public class MetterDispatcherHander extends SimpleChannelInboundHandler<PackageBasic> {
	private static Logger LOGGER = LoggerFactory.getLogger(MetterDispatcherHander.class);

	public Map<Integer, AbstractPackageProcessor> packageProcessorCache = new HashMap<Integer, AbstractPackageProcessor>();
	public void register(AbstractPackageProcessor processor){
		if(packageProcessorCache.containsKey(processor.matchPackageType())){
			String msg = "mult PackageProcessor for PackageType："+processor.matchPackageType()+":"
			+processor.getClass()+" and "+packageProcessorCache.get(processor.matchPackageType());
			throw MeterExceptionFactory.applicationException(msg,null);
		}
		packageProcessorCache.put(processor.matchPackageType(),processor);
	}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PackageBasic packageBasic) throws Exception {
    	AbstractPackageProcessor hander = packageProcessorCache.get(packageBasic.getPackageType());
    	if(hander != null){
    		hander.process(channelHandlerContext, packageBasic);
        }else{
        	LOGGER.error("not found packageProcessor for packageType:" + packageBasic.getPackageType());
        }
    }
}
