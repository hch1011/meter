package com.jd.meter.exception;


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

}
