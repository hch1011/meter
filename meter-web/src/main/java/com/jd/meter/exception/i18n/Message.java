package com.jd.meter.exception.i18n;

import java.util.Locale;

import com.jd.meter.exception.MeterException;


public interface Message {
	public String getText(MeterException e);
	
	public String getText(MeterException e, Locale locale);
}
