package com.jd.meter.ys.sdk;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

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
	@Value("${ys.sdk.domainUrl}")
	protected String domainUrl="https://open.ys7.com";
	@Value("${ys.sdk.appKey}")
	protected String appKey="b0acdca68ec04401b127195e5c85e171";
	@Value("${ys.sdk.appSecret}")
	protected String appSecret="ed1abe6f91fe6c7a28864db51fd96b8e";
	
	@Value("${ys.sdk.domainUrl}/api/lapp/token/get")
	private String tokenApi="https://open.ys7.com/api/lapp/token/get";
	private String accessToken="at.6r6ll8zo9pwxr05718eyqxwd5xvbic46-5tnt5mfu67-020eeey-gpqymwnac";
	private long expireTime=1477813141631L;
	
	
	private static Map<String, String> defaultHeader = new HashMap<String,String>();
	static{
		defaultHeader.put("content-type", "application/x-www-form-urlencoded");
	}
	/**
	 * 获取token，有缓存从缓存获取
	 * @return
	 */
	public String getToken(){
		if(expireTime > System.currentTimeMillis()){
			return accessToken;
		}
		
		//获取token
		Map<String, String> header = new HashMap<String,String>();
		header.put("content-type", "application/x-www-form-urlencoded");
		Map<String, String> params = new HashMap<String,String>();
		params.put("appKey", appKey);
		params.put("appSecret", appSecret);
		
		JSONObject data = post(tokenApi, params, "获取accessToken").getJSONObject("data");
		accessToken = data.getString("accessToken");
		expireTime = data.getLongValue("expireTime");
		
		System.out.println("accessToken="+accessToken);
		System.out.println("expireTime="+expireTime);
		return accessToken;
	}
	
	
	/**
	 * 重新获取token
	 * 
	 * @return
	 */
	public void cleanToken(){
		expireTime = 0;
		accessToken = null;
	}
	
	 /**
	  * 直接返回可用的数据：JSONObject
	  * 返回body对象
	  * 
	  * @param tokenApi
	  * @param params
	  * @param apiName,api描述，用于出错时的消息提示，可以为空
	  * @return
	  */
	public JSONObject post(String apiUrl, Map<String, String> params, String apiName){
		SimpleHttpResponse rt = SimpleHttpUtils.doPost(apiUrl, params, defaultHeader );
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
	
	public  Map<String, String> huildParamsWithToken(){
		Map<String, String> param = new HashMap<String,String>();
		param.put("accessToken", getToken());
		return param;
	}
	
	
	public  void main(String[] args) {
		System.out.println("main() start" );
		System.out.println("token=" + getToken());
		System.out.println("main() end" );
	}
	
	
}
