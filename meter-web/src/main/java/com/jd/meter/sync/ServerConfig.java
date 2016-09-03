package com.jd.meter.sync;

import org.springframework.stereotype.Component;

/**
 * 服务器的一些配置
 * 
 * @author hc
 *
 */
@Component
public class ServerConfig {
	public static String localServerType_client="client";
	public static String localServerType_server="server";
	
	// 当前服务器是端服务还是汇总服务：
	public String localServerType="client";		// client|server
	
	// 当前服务器是端服务还是汇总服务：
	public boolean connectServerAble = false;		// 是否可以直连
	
	
}
