package com.tj.meter.exception;

import org.apache.commons.lang.StringUtils;

public class MeterExceptionFactory{

	public static MeterException applicationException(String screenMessage, Throwable cause) {
		return new MeterException(screenMessage,null, cause);
	}
	public static MeterException applicationException(String screenMessage,String debugMessage, Throwable cause) {
		return new MeterException(screenMessage, debugMessage, cause);
	}
	public static MeterException readSocketError(String message){
		return new MeterException(message);
	}
	
	
	public static void checkNull(Object obj, String screenMessage){
		if(obj == null || StringUtils.isBlank(obj.toString())){
			throw new MeterException(screenMessage);
		}
	}

}
