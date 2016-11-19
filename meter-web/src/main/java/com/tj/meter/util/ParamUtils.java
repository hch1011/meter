package com.tj.meter.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ParamUtils {
	public static Map<String,String> toMapParam(Object obj){
		if(obj == null){
			return null;
		}
		
		Map<String,String> map = new HashMap<String,String>();
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			Object value;
			for(Field f : fields){
				f.setAccessible(true);
				value = f.get(obj);
				if(value != null){
					map.put(f.getName(), value.toString());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("对象转Map错误",e);
		}
		return map;
	}
}
