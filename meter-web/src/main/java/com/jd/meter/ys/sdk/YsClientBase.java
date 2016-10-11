package com.jd.meter.ys.sdk;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jd.meter.exception.MeterExceptionFactory;
import com.jd.meter.util.SimpleHttpUtils;
import com.jd.meter.util.SimpleHttpUtils.SimpleHttpResponse;

/**
 * https://open.ys7.com/
 * 
 * @author hc
 *
 */
public class YsClientBase {
	public static String domainUrl="https://open.ys7.com";
	
	private static String appKey="b0acdca68ec04401b127195e5c85e171";
	private static String appSecret="ed1abe6f91fe6c7a28864db51fd96b8e";
	
	private static String tokenApi=domainUrl+"/api/lapp/token/get";
	private static String accessToken="at.7so6qaiscane1kzf0avfu0bh52atpgie-1hoyj0k4rd-0lhblg5-xdkemcazq";
	private static long expireTime=1474725429529L;
	
	
	private static  Map<String, String> header = new HashMap<String,String>();
	static{
		header.put("content-type", "application/x-www-form-urlencoded");
	}
	/**
	 * 获取token，有缓存从缓存获取
	 * @return
	 */
	public static String getToken(){
		if(expireTime > System.currentTimeMillis()){
			return accessToken;
		}
		
		//获取token
		Map<String, String> header = new HashMap<String,String>();
		header.put("content-type", "application/x-www-form-urlencoded");
		Map<String, String> params = new HashMap<String,String>();
		params.put("appKey", appKey);
		params.put("appSecret", appSecret);
		
		JSONObject obj = post(tokenApi, params, "获取accessToken");
		accessToken = obj.getString("accessToken");
		expireTime = obj.getLongValue("expireTime");
		
		System.out.println("accessToken="+accessToken);
		System.out.println("expireTime="+expireTime);
		return accessToken;
	}
	
	
	/**
	 * 重新获取token
	 * 
	 * @return
	 */
	public static void cleanToken(){
		expireTime = 0;
		accessToken = null;
	}
	
	 /**
	  * 直接返回可用的数据：JSONObject
	  * 
	  * @param tokenApi
	  * @param params
	  * @param apiName,api描述，用于出错时的消息提示，可以为空
	  * @return
	  */
	public static JSONObject post(String apiUrl, Map<String, String> params, String apiName){
		SimpleHttpResponse rt = SimpleHttpUtils.doPost(apiUrl, params, header );
		//{"body":"{\"data\":{\"accessToken\":\"at.dhvefzyc5jyl6u0r4qoxygbh5m14u09i-51aw86185p-10ntvuo-7pgi0mkyj\",\"expireTime\":1474685789581},\"code\":\"200\",\"msg\":\"操作成功!\"}","status":200}
		if(rt.getStatus() != 200){
			throw MeterExceptionFactory.applicationException("http调用(获取token异常)", "http status="+rt.getStatus(), null);
		}
		JSONObject obj = JSON.parseObject(rt.getBody());
		
		if(obj.getIntValue("code") == YsErrorCodeEnum.e10002.code){
			cleanToken();
			return post(tokenApi, params, apiName);
 		}
		if(obj.getIntValue("code") != 200){
			throw MeterExceptionFactory.applicationException(apiName+"异常:"+YsErrorCodeEnum.getMsgByCode(obj.getIntValue("code")),"return code="+obj.getString("code"), null);
		}
		
		return  obj;
	}
	
	public static Map<String, String> huildHeader(){
		Map<String, String> header = new HashMap<String,String>();
		header.put("content-type", "application/x-www-form-urlencoded");
		return header;
	}
	
	public static Map<String, String> huildParamsWithToken(){
		Map<String, String> param = new HashMap<String,String>();
		param.put("accessToken", getToken());
		return param;
	}
	
	
	public static void main(String[] args) {
		System.out.println("main() start" );
		System.out.println("token=" + getToken());
		System.out.println("main() end" );
	}
}
