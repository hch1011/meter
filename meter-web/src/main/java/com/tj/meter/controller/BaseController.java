package com.tj.meter.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tj.meter.exception.MeterException;


public class BaseController {
	 
	Map<String, Object> success(){
		return success(null);
	}
	
	Map<String, Object> success(Object data){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "sucess");
		if(data != null){
			map.put("data", data);
		}
		
		return map;
	}
	
	Map<String, Object> fail(){
		return fail("系统错误");
	}
	
	Map<String, Object> fail(String screenMessage){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "fail");
		if(StringUtils.isNotBlank(screenMessage)){
			map.put("screenMessage", screenMessage);
		}
		return map;
	}
	

	Map<String, Object> fail(Exception e){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "fail");
		map.put("e",e);
		if(e instanceof MeterException){
			MeterException e2 = (MeterException)e;
			if(StringUtils.isNotBlank(e2.getScreenMessage())){
				map.put("screenMessage",e2.getScreenMessage());
				map.put("debugMessage",e2.getDebugMessage());
				return map;
			}
		}
		map.put("screenMessage","系统异常");
		map.put("debugMessage",e.getMessage());
		return map;
	}
} 
