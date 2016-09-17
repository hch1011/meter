package com.jd.meter.exception;


public class MeterExceptionFactory{

	public static MeterException applicationException(String message, Throwable cause) {
		return new MeterException(message,null, cause);
	}
	public static MeterException applicationException(String message,String debug, Throwable cause) {
		return new MeterException(message, debug, cause);
	}
	public static MeterException readSocketError(String message){
		return new MeterException(message);
	}

}
