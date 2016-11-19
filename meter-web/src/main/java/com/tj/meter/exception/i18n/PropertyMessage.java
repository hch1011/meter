package com.tj.meter.exception.i18n;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.tj.meter.exception.MeterException;

public class PropertyMessage implements Message {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(PropertyMessage.class);

    @Autowired
    private MessageSource messageSource;
	
	private static Map<Locale, Map<String, String>> infoMap = new HashMap<Locale, Map<String,String>>();
	
	private static Locale defauleLocale = new Locale("zh", "CN");

	
	private String getText(Locale locale, String code) {
        return messageSource.getMessage(code, null, locale);
	}

	private String getText(Locale locale, String code, List<String> args) {
        return messageSource.getMessage(code, args.toArray(), locale);
	}

	@Override
	public String getText(MeterException e) {
		return getText(e, defauleLocale);
	}
	
	@Override
	public String getText(MeterException e, Locale locale) {
		String text = e.getMessage();	 
		if(StringUtils.isNotBlank(text)){
			return text;
		}
		
		return text;
	}
}
