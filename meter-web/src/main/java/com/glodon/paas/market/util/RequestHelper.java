package com.glodon.paas.market.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.tj.meter.exception.MeterExceptionFactory;

public class RequestHelper {
	public static final boolean exceptionIfAbsent = true;
	public static final boolean exceptionIfUnMatch = true;
	
	static Map<String, Boolean> booleanKeyValues = new HashMap<String, Boolean>();
	static{
		booleanKeyValues.put("true", Boolean.TRUE);
		booleanKeyValues.put("1", Boolean.TRUE);
		booleanKeyValues.put("false", Boolean.FALSE);
		booleanKeyValues.put("0", Boolean.FALSE);
	}	
	
	/**
	 * 
	 * @param paraName				要获取的参数名称
	 * @param defaultValue			参数不存在或转换异常时返回的默认值
	 * @param request				参数列表
	 * @param exceptionIfAbsent		参数不存在：true抛异常，false返回defaultValue
	 * @param exceptionIfUnMatch	参数不匹配(转换异常)：true抛异常，false返回defaultValue
	 * @return
	 */
	public static Integer getInteger(
			String paraName, 
			Integer defaultValue, 
			HttpServletRequest request,  
			boolean exceptionIfAbsent,
			boolean exceptionIfUnMatch
			){
		String requesttr = getString(paraName, null, request, exceptionIfAbsent);
		if(StringUtils.isBlank(requesttr)){
			return defaultValue;
		}
		
		try{
			return Integer.valueOf(requesttr);
		}catch(Exception e){
			if(exceptionIfUnMatch){
				throw MeterExceptionFactory.applicationException("convert to int error:" + requesttr, null);
			}
		}
		return defaultValue;
	}

	/**
	 * 将参数转换为Integer数组，
	 * 
	 * @param paraName				要获取的参数名称
	 * @param defaultValue			参数不存在或转换异常时使用的默认值
	 * @param request				参数列表
	 * @param exceptionIfAbsent		参数不存在：true抛异常，false返回defaultValue
	 * @param exceptionIfUnMatch	参数不匹配(转换异常)：true抛异常，false设置defaultValue
	 * @param split					字符串分隔符，null时使用","
	 * @return
	 */
	public static Integer[] getIntegerArray(
			String paraName, 
			Integer defaultValue, 
			HttpServletRequest request,  
			boolean exceptionIfAbsent,
			boolean exceptionIfUnMatch,
			String split
			){
		String requesttr = getString(paraName, null, request, exceptionIfAbsent);
		if(StringUtils.isBlank(requesttr)){
			return null;
		}
		if(split == null){
			split = ",";
		}
		
		String[] strArray = requesttr.split(split);
		Integer[] rtArray = new Integer[strArray.length];

		for (int i = 0; i < strArray.length; i++) {
			try {
				rtArray[i] = Integer.parseInt(strArray[i]);
			} catch (Exception e) {
				if (exceptionIfUnMatch) {
					throw MeterExceptionFactory.applicationException("convert to int error:" + strArray[i], null);
				} else {
					rtArray[i] = defaultValue;
				}
			}
		}

		return rtArray;
	}
	
	/**
	 * 参考getIntegerArray 
	 * @param paraName
	 * @param defaultValue
	 * @param request
	 * @param exceptionIfAbsent
	 * @param exceptionIfUnMatch
	 * @param split
	 * @return
	 */
	public static Long[] getLongArray(
			String paraName, 
			Long defaultValue, 
			HttpServletRequest request,  
			boolean exceptionIfAbsent,
			boolean exceptionIfUnMatch,
			String split
			){
		String requesttr = getString(paraName, null, request, exceptionIfAbsent);
		if(StringUtils.isBlank(requesttr)){
			return null;
		}
		if(split == null){
			split = ",";
		}
		
		String[] strArray = requesttr.split(split);
		Long[] rtArray = new Long[strArray.length];

		for (int i = 0; i < strArray.length; i++) {
			try {
				rtArray[i] = Long.parseLong(strArray[i]);
			} catch (Exception e) {
				if (exceptionIfUnMatch) {
					throw MeterExceptionFactory.applicationException("convert to long error:" + strArray[i], null);
				} else {
					rtArray[i] = defaultValue;
				}
			}
		}

		return rtArray;
	}
	
	/**
	 * 
	 * @param paraName				要获取的参数名称
	 * @param defaultValue			参数不存在或转换异常时返回的默认值
	 * @param request				参数列表
	 * @param exceptionIfAbsent		参数不存在：true抛异常，false返回defaultValue
	 * @param exceptionIfUnMatch	参数不匹配(转换异常)：true抛异常，false返回defaultValue
	 * @return
	 */
	public static Long getLong(
			String paraName, 
			Long defaultValue, 
			HttpServletRequest request,  
			boolean exceptionIfAbsent,
			boolean exceptionIfUnMatch
			){
		String requesttr = getString(paraName, null, request, exceptionIfAbsent);
		if(StringUtils.isBlank(requesttr)){
			return defaultValue;
		}
		
		try{
			return Long.valueOf(requesttr);
		}catch(Exception e){
			if(exceptionIfUnMatch){
				throw MeterExceptionFactory.applicationException("convert to long error:" + requesttr, null);
			}
		}
		return defaultValue;
	}
	
	/**
	 * 
	 * @param paraName				要获取的参数名称
	 * @param defaultValue			参数不存在或转换异常时返回的默认值
	 * @param request				参数列表
	 * @param exceptionIfAbsent		参数不存在：true抛异常，false返回defaultValue
	 * @param exceptionIfUnMatch	参数不匹配(转换异常)：true抛异常，false返回defaultValue
	 * @return
	 */
	public static Boolean getBoolean(
			String paraName, 
			Boolean defaultValue, 
			HttpServletRequest request,  
			boolean exceptionIfAbsent, 
			boolean exceptionIfUnMatch
			){
		String requesttr = getString(paraName, null, request, exceptionIfAbsent);
		if(StringUtils.isBlank(requesttr)){
            return defaultValue;
        }
		
		Boolean rt = booleanKeyValues.get(requesttr.toLowerCase());
		
		if(rt == null){
			if(exceptionIfUnMatch){
                throw MeterExceptionFactory.applicationException("convert to boolean error:" + requesttr, null);
			}
			return defaultValue;
		}
		return rt;
	}
	
	/**
	 * 获取参数值
	 * @param paraName				参数名
	 * @param defaultValue			如果不存在，返回默认值
	 * @param request				传入的参数源集合
	 * @param exceptionIfAbsent		如果不存在，是否抛异常
	 * @return
	 */
	public static String getString(
			String paraName, 
			String defaultValue, 
			HttpServletRequest request,
			boolean exceptionIfAbsent
			){
		String requestStr = request.getParameter(paraName);
		if(requestStr == null){
			if(exceptionIfAbsent){
                throw MeterExceptionFactory.applicationException("缺少必须的参数:"+paraName, null);
			}
			return defaultValue;
		}
		return requestStr;
	}
	
	/**
	 * 将&分割的参数解析成Map
	 * @param paraName
	 * @param defaultValue
	 * @param request
	 * @param exceptionIfAbsent
	 * @return
	 */
	
	/**
	 * 将&分割的参数解析成Map
	 * toLowerCase=true是，参数名(key)将转换成小写
	 * 
	 * @param parameters
	 * @param toLowerCase
	 * @return not null
	 */
	public static Map<String, String> parseParameter(String parameters, boolean paramNameToLowerCase) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		if(parameters == null){
			return parameterMap;
		}
		
		if(parameters.indexOf("==") > 0){
            throw MeterExceptionFactory.applicationException("parameter unmatched: " + parameters, null);
		}
		
		String[] arrays = parameters.split("&");
		int len = arrays.length;
		
		if(paramNameToLowerCase){
			for(int i = 0; i < len; i++){
				String[] kv = arrays[i].split("=");
				if(kv.length != 2){
		            throw MeterExceptionFactory.applicationException("parameter unmatched: " + arrays[i], null);
				}
				parameterMap.put(kv[0].trim().toLowerCase(), kv[1]);
			}
		}else{
			for(int i = 0; i < len; i++){
				String[] kv = arrays[i].split("=");
				if(kv.length == 1){
					parameterMap.put(kv[0].trim(), null);
				}
				if(kv.length > 1){
					parameterMap.put(kv[0].trim(), kv[1]);
				}
				//throw MeterExceptionFactory.applicationException("parameter unmatched: " + arrays[i], null);
				
				
			}
		}
		return parameterMap;
	}
	
	
	 /**
	  * 从request中获取bean的属性并填充bean
     * @param request
     * @param bean 创建封装数据的对象
     * @return
     */   
	public static <T> T request2Bean(HttpServletRequest request, T bean) {
        try {
            @SuppressWarnings("rawtypes")
			Map map = request.getParameterMap();
            BeanUtils.populate(bean, map);
            return bean;
        } catch (Exception e) {
            throw new RuntimeException("request2Bean error",e);
        }
    }
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getParameterMap(HttpServletRequest request) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
        try {
        	String name = null;
        	Enumeration<String> names = request.getParameterNames();
        	while (names.hasMoreElements()){
        		name = names.nextElement();
        		paramMap.put(name, request.getParameter(name));
        	}
        } catch (Exception e) {
            throw new RuntimeException("request2Bean error",e);
        }
        return paramMap;
    }
}
