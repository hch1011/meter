package com.jd.meter.sync;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 服务器的一些配置
 * 
 * @author hc
 *
 */
@Component
public class ServerConfig {
	
	public static ServerConfig instance;
	
	public String localServerType_client="client";
	public String localServerType_server="server";
	
	@Value("${meter.centerServer.syncdata.host:127.0.0.1}")
	public String centerServerHost = "192.168.1.108";
	@Value("${meter.centerServer.syncdata.port:9101}")
	public int centerServerPort = 9101;
	
	// 当前服务器是端服务还是汇总服务：
	public String localServerType="client";		// client|server
	
	// 当前服务器是端服务还是汇总服务：
	public boolean connectServerAble = false;		// 是否可以直连
	
	public ServerConfig(){
		instance = this;
	}
	
	
}
