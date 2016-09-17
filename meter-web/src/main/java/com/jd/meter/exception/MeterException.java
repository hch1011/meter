package com.jd.meter.exception;

public class MeterException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String debugInfo;

    public MeterException() {
        super();
    }
 
    public MeterException(String message, String debugInfo, Throwable cause) {
        super(message, cause);
        this.debugInfo = debugInfo;
    }
    public MeterException(String message) {
        super(message);
    }

    public MeterException(Throwable cause) {
        super(cause);
    }

	public String getDebugInfo() {
		return debugInfo;
	}

	public void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}
}
