package com.jd.meter.ys.sdk;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jd.meter.util.SimpleHttpUtils;

/**
 * 添加设备,删除设备,修改设备名称,设备抓拍图片, 根据UUID查询抓拍图片,获取设备列表,获取单个设备信息,获取摄像头列表
 * 
 * @author hc
 *
 */
public class YsClientDevice extends YsClientBase{
	 /**
	  * 添加设备
	  * @param deviceSerial 设备序列号
	  * @param validateCode 设备验证码，设备机身上的六位大写字母
	  */
	 public static void deviceAdd(String deviceSerial, String validateCode){
		 Map<String, String> params = YsClientBase.huildParamsWithToken();
		 params.put("deviceSerial", deviceSerial);
		 params.put("validateCode", validateCode);
		 post(domainUrl+"/api/lapp/device/add", params, "添加设备");
	 }
	
	 /**
	  * 修改设备名称
	  * @param deviceSerial
	  * @param validateCode
	  */
	 public static void deviceUpdate(String deviceSerial, String deviceName){
		 Map<String, String> params = YsClientBase.huildParamsWithToken();
		 params.put("deviceSerial", deviceSerial);
		 params.put("deviceName", deviceName);
		 post(domainUrl+"/api/lapp/device/update", params, "修改设备名称");
	 }
	 

	 /**
	  * 设备抓拍图片,返回图片的url
	  * 抓拍设备当前画面，该接口仅适用于IPC或者关联IPC的NVR设备。
	  * @param deviceSerial 设备序列号
	  * @param deviceName 通道号，IPC设备填写1
	  */
	 public static String capture(String deviceSerial, String channelNo){
		 Map<String, String> params = YsClientBase.huildParamsWithToken();
		 params.put("deviceSerial", deviceSerial);
		 params.put("channelNo", channelNo);
		 JSONObject data = post(domainUrl+"/api/lapp/device/capture", params, "设备抓拍图片");
		 return data.getString("picUrl");
	 }
	 
	/**
	 * 根据UUID查询抓拍图片（设备互联互通使用）,返回图片url
	 * 
	 * @param uuid: 设备sdk抓拍返回的uuid
	 * @param size：图片大小,范围在【0-1280】
	 * @return
	 */
	 public static String pictureByUuid(String uuid, int size){
		 Map<String, String> params = YsClientBase.huildParamsWithToken();
		 params.put("uuid", uuid);
		 params.put("size", String.valueOf(size));
		 JSONObject data = post(domainUrl+"/api/lapp/device/uuid/picture", params, "设备抓拍图片");
		 return data.getString("picUrl");
	 }
	 
	 /**
	  * 获取二进制数据
	  * @param url
	  * @return
	  */
	 public static byte[] readByteArrayFromUrl(String url){
		 return SimpleHttpUtils.getByteByUrl(url);
	 }
	 /**
	  * 将url内容写入文件
	  * @param url
	  * @param filePath
	  * @return
	  */
	 public static boolean writeFileFromUrl(String url, String filePath){
		 return SimpleHttpUtils.saveToFile(url, filePath);
	 }

	 /**
	  * 获取设备列表
	  * @param pageStart
	  * @param pageSize
	  * @return
	  */
	 public static  List<DeviceInfo> deviceList(){
		 Map<String, String> params = YsClientBase.huildParamsWithToken();
		 params.put("pageStart", "0");
		 params.put("pageSize", "100");
		 JSONObject data = post(domainUrl+"/api/lapp/device/list", params, "获取设备列表");
		 
		 List<DeviceInfo> list = JSON.parseArray(data.toJSONString(), DeviceInfo.class);
		 return list;
	 }
	 
	 /**
	  * 获取单个设备信息
	  * @param deviceSerial 设备序列号
	  * @return
	  */
	 public static DeviceInfo deviceInfo( String deviceSerial){
		 Map<String, String> params = YsClientBase.huildParamsWithToken();
		 params.put("deviceSerial", deviceSerial); 
		 JSONObject data = post(domainUrl+"/api/lapp/device/info", params, "获取设备版本信息");
		 return JSON.parseObject(data.toJSONString(), DeviceInfo.class);
	 }
	 
	 
	 /**
	  * 获取摄像头列表
	  * @return
	  */
	 public static  List<DeviceInfo> cameraList(){
		 Map<String, String> params = YsClientBase.huildParamsWithToken();
		 params.put("pageStart", "0");
		 params.put("pageSize", "100");
		 JSONObject data = post(domainUrl+"/api/lapp/camera/list", params, "获取设备列表");
		 
		 List<DeviceInfo> list = JSON.parseArray(data.toJSONString(), DeviceInfo.class);
		 return list;
	 }
}
