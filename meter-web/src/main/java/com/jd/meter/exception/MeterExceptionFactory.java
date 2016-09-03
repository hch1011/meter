package com.jd.meter.exception;


public class MeterExceptionFactory{

	public static MeterException applicationException(String message, Throwable cause) {
		return new MeterException(message, cause);
	}

	public static MeterException readSocketError(String message){
		return new MeterException(message);
	}

}
