package com.tj.meter.ys.sdk;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tj.meter.entity.CameraCaptureVo;
import com.tj.meter.entity.CameraInfo;
import com.tj.meter.exception.MeterException;
import com.tj.meter.exception.MeterExceptionFactory;
import com.tj.meter.util.SimpleHttpUtils;
import com.tj.meter.util.SystemMessageReport;

/**
 * 添加设备,删除设备,修改设备名称,设备抓拍图片, 根据UUID查询抓拍图片,获取设备列表,获取单个设备信息,获取摄像头列表
 * 
 * @author hc
 *
 */
@Component("ysClientProxy")
public class YsClientProxy extends YsClientBase{
	
	 /**
	  * 添加设备
	  * @param deviceSerial 设备序列号
	  * @param validateCode 设备验证码，设备机身上的六位大写字母
	  */
	 public void deviceAdd(String deviceSerial, String validateCode){
		 Map<String, String> params = buildParamsWithToken();
		 params.put("deviceSerial", deviceSerial);
		 params.put("validateCode", validateCode);
		 post(domainUrl+"/api/lapp/device/add", params, "添加设备");
	 }
	
	 /**
	  * 修改设备名称
	  * @param deviceSerial
	  * @param validateCode
	  */
	 public void deviceUpdate(String deviceSerial, String deviceName){
		 Map<String, String> params = buildParamsWithToken();
		 params.put("deviceSerial", deviceSerial);
		 params.put("deviceName", deviceName);
		 post(domainUrl+"/api/lapp/device/update", params, "修改设备名称");
	 }
	 /**
	  * 设备抓拍图片,返回图片的url
	  * 
	  * 抓拍设备当前画面，该接口仅适用于IPC或者关联IPC的NVR设备。
	  * @param deviceSerial 设备序列号
	  * @param deviceName 通道号，IPC设备填写1
	  */
	 public String PictureUUID(String deviceSerial, String channelNo){
		 Map<String, String> params = buildParamsWithToken();
		 params.put("deviceSerial", deviceSerial);
		 params.put("channelNo", channelNo);
		 JSONObject data = post(domainUrl+"/api/lapp/device/uuid/picture ", params, "设备抓拍图片");
		 data = data.getJSONObject("data");	
		 return data.getString("picUrl");
	 }

	 
	 /**
	  * 设备抓拍图片,返回图片的url
	  * 
	  * 抓拍设备当前画面，该接口仅适用于IPC或者关联IPC的NVR设备。
	  * @param deviceSerial 设备序列号
	  * @param deviceName 通道号，IPC设备填写1
	  */
	 public String capture(String deviceSerial, String channelNo){
		 Map<String, String> params = buildParamsWithToken();
		 params.put("deviceSerial", deviceSerial);
		 params.put("channelNo", channelNo);
		 params.put("size", "1024");
		 JSONObject data = post(domainUrl+"/api/lapp/device/capture", params, "设备抓拍图片");
		 data = data.getJSONObject("data");	
		 return data.getString("picUrl");
	 }
	 
	 /**
	  * 设备抓拍图片,返回图片的url,参数及返回值都封装在param中
	  * 
	  * @param param
	  * @return
	  */
	 public CameraCaptureVo capture(CameraCaptureVo param){
		 Map<String, String> params = buildParamsWithToken();
		 params.put("deviceSerial", param.getCamaraSerial());
		 params.put("channelNo", param.getCamaraChannelNo());
		 params.put("size", "1024");
		 JSONObject data = post(domainUrl+"/api/lapp/device/capture", params, "设备抓拍图片");
		 data = data.getJSONObject("data");		 
		 param.setYsUrl(data.getString("picUrl"));
		 return param;
	 }
	 
	 
//	/**
//	 * 根据UUID查询抓拍图片（设备互联互通使用）,返回图片url
//	 * 
//	 * @param uuid: 设备sdk抓拍返回的uuid
//	 * @param size：图片大小,范围在【0-1280】
//	 * @return
//	 */
//	 public String pictureByUuid(String uuid, int size){
//		 Map<String, String> params = buildParamsWithToken();
//		 params.put("uuid", uuid);
//		 params.put("size", String.valueOf(size));
//		 JSONObject data = post(domainUrl+"/api/lapp/device/uuid/picture", params, "设备抓拍图片");
//		 data = data.getJSONObject("data");
//		 return data.getString("picUrl");
//	 }
	 
	 /**
	  * 获取二进制数据
	  * @param url
	  * @return
	  */
	 public byte[] readByteArrayFromUrl(String url){
		 return SimpleHttpUtils.getByteByUrl(url);
	 }
	 /**
	  * 将url内容写入文件
	  * @param url
	  * @param filePath
	  * @return
	  */
	 public boolean writeFileFromUrl(String url, String filePath){
		 return SimpleHttpUtils.saveToFile(url, filePath);
	 }
	 
	 /**
	  * 将图片内容写入文件
	  * 
	  * @param url
	  * @param filePath
	  * @param width		写入文件宽，不合法值将被忽略，取原始值，为了计算中间结果不会溢出，这里使用Long
	  * @param hight		写入文件高，不合法值将被忽略，根据width计算等比缩放的hight值
	  * @return
	  * @throws IOException 
	  * @throws FileNotFoundException 
	  */
	 public void writeImageFromUrl(String url, String filePath, Integer width, Integer hight){
		BufferedImage sourceImage = null;
		BufferedImage newImage;
		try {
			sourceImage = ImageIO.read(new ByteArrayInputStream(SimpleHttpUtils.getByteByUrl(url)));
		} catch (Exception e) {
			try {
				sourceImage = ImageIO.read(new ByteArrayInputStream(SimpleHttpUtils.getByteByUrl(url)));
			} catch (Exception e2) {
				throw MeterExceptionFactory.applicationException("获取图片失败", e2);
			} 
		}
		 
		 try {
			 int oldWidth = sourceImage.getWidth();
			 int oldHight = sourceImage.getHeight();
			 
			 if(width == null || width < 1){
				 width = oldWidth;
			 }
			 if(hight == null || hight < 1){
				 // 等比缩放
				 hight = oldHight * width / oldWidth;
			 }

			 newImage = new BufferedImage(width.intValue(), hight.intValue(), sourceImage.getType());
			 newImage.getGraphics().drawImage(sourceImage, 0, 0, width.intValue(), hight.intValue(), null);
		 } catch (Exception e) {
			throw MeterExceptionFactory.applicationException("有系统错误：缩放图片失败，请联系管理员", e);
		 }
		 
		 try {
			 File newFile = new File(filePath);
	 		 if(newFile.exists() && newFile.isFile()){
	 			newFile.delete();
	 		 }
	 		 ByteArrayOutputStream out = new ByteArrayOutputStream();
	 		 ImageIO.write(newImage, "jpg",  out);
	 		 FileUtils.writeByteArrayToFile(newFile, out.toByteArray());
		} catch (Exception e) {
			MeterException e2 = MeterExceptionFactory.applicationException("有系统错误：保存图片失败，请联系管理员", filePath, e);
			SystemMessageReport.globalMessage = e2.getScreenMessage();
			SystemMessageReport.globalException = e2;
			throw e2;
		} 
	 }
	 
	 /**
	  * 获取设备列表
	  * @param pageStart
	  * @param pageSize 最大值50
	  * @return
	  */
	 public  List<CameraInfo> deviceList(int pageStart, int pageSize){
		 Map<String, String> params = buildParamsWithToken();
		 params.put("pageStart", String.valueOf(pageStart));
		 params.put("pageSize", String.valueOf(pageSize));
		 JSONObject body = post(domainUrl+"/api/lapp/device/list", params, "获取设备列表");
		 JSONArray array = body.getJSONArray("data");
		 List<CameraInfo> list = JSON.parseArray(array.toJSONString(), CameraInfo.class);
		 return list;
	 }
	 
	 /**
	  * 获取摄像头列表
	  * @return
	  */
	 public Page<CameraInfo> cameraList(Pageable pageable){
		 Map<String, String> params = buildParamsWithToken();
		 params.put("pageStart", String.valueOf(pageable.getOffset()));
		 params.put("pageSize", String.valueOf(pageable.getPageSize()));
		 JSONObject body = post(domainUrl+"/api/lapp/camera/list", params, "获取设备列表");
		 List<CameraInfo> list = JSON.parseArray(body.getJSONArray("data").toJSONString(), CameraInfo.class);
		 JSONObject page = body.getJSONObject("page");  //{"page":0,"size":50,"total":1}
		 return new PageImpl<CameraInfo>(list, pageable, page.getInteger("total"));
	 }

}
