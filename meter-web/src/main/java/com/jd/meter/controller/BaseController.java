package com.jd.meter.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


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
	
	Map<String, Object> fail(String msg){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "fail");
		if(StringUtils.isNotBlank(msg)){
			map.put("msg", msg);
		}
		return map;
	}
} 
