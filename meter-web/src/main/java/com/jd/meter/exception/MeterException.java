package com.jd.meter.exception;

public class MeterException extends Exception {

    private static final long serialVersionUID = 1L;

    public MeterException() {
        super();
    }

    public MeterException(String message, Throwable cause) {
        super(message, cause);
    }

    public MeterException(String message) {
        super(message);
    }

    public MeterException(Throwable cause) {
        super(cause);
    }

}
