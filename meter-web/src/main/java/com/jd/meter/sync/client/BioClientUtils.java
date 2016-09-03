package com.jd.meter.sync.client;

import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jd.meter.exception.MeterExceptionFactory;
import com.jd.meter.sync.ServerConfig;


public class BioClientUtils{
    private static Logger LOGGER = LoggerFactory.getLogger(BioClientUtils.class);
 
    public static BioClient getCenterServerClient(){
    	
    	try {
    		Socket socket = new Socket( ServerConfig.instance.centerServerHost, ServerConfig.instance.centerServerPort);
    		return new BioClient(socket);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw MeterExceptionFactory.applicationException("获取链接异常", e);
		}
		 
    } 
}
