package com.jd.meter.sync.handler;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.jd.meter.sync.pkg.PackageBasic;

import io.netty.channel.ChannelHandlerContext;
/**
 * 业务处理类
 */
public abstract class AbstractPackageProcessor {
	@Autowired
	MetterDispatcherHander metterDispatcherHander;
	
	@PostConstruct
	public void register(){
		metterDispatcherHander.register(this);
	}
	
	public abstract int matchPackageType();	
	public abstract void process (ChannelHandlerContext ctx, PackageBasic msg);

}
