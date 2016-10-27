package com.jd.meter.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.jd.meter.entity.CameraCaptureVo;
import com.jd.meter.entity.CameraInfo;
import com.jd.meter.entity.DeviceInfo;
import com.jd.meter.entity.DeviceType;
import com.jd.meter.exception.MeterExceptionFactory;
import com.jd.meter.util.ImageUtils;
import com.jd.meter.util.NativeWinExe;
import com.jd.meter.util.ObjectUtil;
import com.jd.meter.util.TimeUtils;
import com.jd.meter.ys.sdk.YsClientProxy;

/**
 * Created by hujintao on 2016/8/13.
 */
@Component
public class CameraService {

	@Value("${meter.camera.image.folder}")
	private String cameraImageFolder;
	
	@Autowired
    DeviceService deviceService;
	@Autowired
    YsClientProxy ysClientProxy;

	/**
	 * 拍照,识别
	 */
	public CameraCaptureVo paizhao(CameraCaptureVo param){
		// 拍照
		if(param.isOpCapture() ){
			_capture(param);
		}
		
		if(param.isOpSave()){
		
		}

		if(param.isOpRecognition()){
			_opRecognition(param);
 		}
		
		return param;
	}
	
	// 拍照
	void _capture(CameraCaptureVo param){
		param.setCaptureTime(TimeUtils.getDateString(new Date(), TimeUtils.fullNumberFormat));
		if(StringUtils.isBlank(param.getCamaraChannelNo())){
			CameraInfo camera = deviceService.queryCameraById(param.getCamaraSerial(), true);
			if(camera == null){
				throw MeterExceptionFactory.applicationException("未获取到摄像头信息", null);
			}else{
				param.setCamaraChannelNo(camera.getChannelNo());
				if(camera.getDeviceInfo() != null){
					param.setDeviceId(camera.getDeviceInfo().getId());
					param.setDeviceType(camera.getDeviceInfo().getType());
					genFileName(param);
				}
			}
		}			
		ysClientProxy.capture(param);
	}
	
	// 存盘，需要ysUrl，deviceType等
	void _opSave(CameraCaptureVo param){
		if(StringUtils.isBlank(param.getFileName())){
			genFileName(param);
		}
		ysClientProxy.writeFileFromUrl(param.getYsUrl(), cameraImageFolder + param.getFileName());
	}
	
	// 裁剪
	void _opCut(CameraCaptureVo param){
		if(!param.canCut()){
			throw MeterExceptionFactory.applicationException("设置裁剪范围错误，不能裁剪图片", null);
		}
		// 裁剪
		try {
			ImageUtils.cutImage(cameraImageFolder + param.getFileName(), cameraImageFolder + param.getFileName2(), 
				param.getX(), param.getY(), param.getW(), param.getH());
		} catch (IOException e) {
			throw MeterExceptionFactory.applicationException("裁剪图片错误", e);
		}
	}
		
	// 识别
	void _opRecognition(CameraCaptureVo param){
		if(StringUtils.isBlank(param.getRecognitionProgramPath())){
			// 先获取deviceType，然后获取识别程序路径
			if(param.getDeviceType() == null && param.getDeviceId() != null ){
				DeviceInfo device = deviceService.queryDeviceInfoById(param.getDeviceId(), true);
				if(device != null){
					param.setDeviceType(device.getType());
				}
			}
			// 从文件名获取deviceType
			if(param.getDeviceType() == null && StringUtils.isNotEmpty(param.getFileName())){
				parseFileName(param);
			}
			
			if(param.getDeviceType() == null){
				throw MeterExceptionFactory.applicationException("获取不到设备类型，无法选择识别程序", null);
			}
			
			DeviceType type = deviceService.queryDeviceTypeByType(param.getDeviceType(), true);
			if(type != null){
				param.setRecognitionProgramPath(type.getRecognitionProgramPath());
			}
		}
		if(StringUtils.isBlank(param.getRecognitionProgramPath())){
			throw MeterExceptionFactory.applicationException("获取不到识别程序", null);
		}
		
		String resultFileName = cameraImageFolder + param.getFileName()+".txt";
		File f = new File(resultFileName);
		if(f.exists()){
			f.delete();
		}
		NativeWinExe.call(
				param.getRecognitionProgramPath(), 
				cameraImageFolder + param.getFileName2(), 
				cameraImageFolder + param.getFileName()+".txt");
		
		readFile(param, resultFileName);
	}
	
	static void readFile(CameraCaptureVo param, String resultFileName){
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(resultFileName));
			String str = bf.readLine();
			if(str.startsWith("true")){
				str = str.replaceAll("true", "");
				str = str.trim();
				param.setSuccess(true);
				param.setCode(str);
				param.setValue(Float.parseFloat(bf.readLine()));
			}else{
				str = str.replaceAll("false", "");
				str = str.trim();
				param.setSuccess(false);
				param.setCode(str);
				param.setMsg("");
				while((str = bf.readLine()) != null)
				param.setMsg(param.getMsg() + "\br" + str);
			}
		}catch(Exception e){
			param.setCode("9999");
			param.setMsg("读取结果错误:"+e.getMessage());
			throw MeterExceptionFactory.applicationException("读取结果错误:"+e.getMessage(), e);
		} finally {
			ObjectUtil.close(bf, true);
		}
		
	}
	
	public static void main(String[] args) {
		CameraCaptureVo param = new CameraCaptureVo();
		readFile(param, "C:\\D\\0_sxj\\halcondot_f\\a.txt");
		System.out.println(JSON.toJSONString(param, true));
	}
	
	// 生成文件名
	void genFileName(CameraCaptureVo param){
		if(StringUtils.isNotBlank(param.getFileName())){
			return;
		}
		String folder = TimeUtils.getDateString(new Date(), "yyyy\\MM\\dd\\");
		// yyyy\MM\dd\time_deviceType_deviceId_camaraSerial.jpg
		String str = folder + String.format("{}_{}_{}_{}.jpg",param.getCaptureTime(), param.getDeviceType(), param.getDeviceId(), param.getCamaraSerial());
		param.setFileName(str);
		if(param.canCut()){
			str = folder + String.format("{}_{}_{}_{}_{}_{}_{}_{}.jpg",
					param.getCaptureTime(), param.getDeviceType(), param.getDeviceId(), param.getCamaraSerial(),
					param.getX(),param.getY(),param.getW(),param.getH());
			param.setFileName2(str);
		}
	}
	
	// 解析文件名得到各元数据
	void parseFileName(CameraCaptureVo param){
		if(StringUtils.isBlank(param.getFileName())){
			return;
		}
		String[] arrays = param.getFileName().split("[_.]");//["yyMMddhhmmss","deviceType","deviceId","camaraSerial","jpg"]
		if(arrays.length < 5){
			throw MeterExceptionFactory.applicationException("文件名不合法，无法解析", null);
		}
		param.setCaptureTime(arrays[0]);
		param.setDeviceType(Long.parseLong(arrays[1]));
		param.setDeviceId(Long.parseLong(arrays[2]));
		param.setCamaraSerial(arrays[3]);
	}
}
