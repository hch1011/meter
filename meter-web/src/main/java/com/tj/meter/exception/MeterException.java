package com.tj.meter.exception;

public class MeterException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private String exceptionId;
    private String screenMessage;
    private String debugMessage;

    public MeterException() {
        super();
    }
 
    public MeterException(String screenMessage, String debugMessage, Throwable cause) {
        super(screenMessage, cause);
        this.exceptionId = String.valueOf(System.currentTimeMillis());
        this.screenMessage = screenMessage;
        this.debugMessage = debugMessage;
    }
    public MeterException(String message) {
        super(message);
    }

    public MeterException(Throwable cause) {
        super(cause);
    }
    public String getMessage() {
    	if(screenMessage != null){
    		return screenMessage;
    	}
		return super.getMessage();
	}
    
	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public String getScreenMessage() {
		return screenMessage;
	}

	public void setScreenMessage(String screenMessage) {
		this.screenMessage = screenMessage;
	}

	public String getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}
    
}
