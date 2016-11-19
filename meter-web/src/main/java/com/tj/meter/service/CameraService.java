package com.tj.meter.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tj.meter.entity.CameraCaptureVo;
import com.tj.meter.entity.CameraInfo;
import com.tj.meter.entity.DeviceData;
import com.tj.meter.entity.DeviceInfo;
import com.tj.meter.entity.DeviceType;
import com.tj.meter.exception.MeterException;
import com.tj.meter.exception.MeterExceptionFactory;
import com.tj.meter.util.Constant;
import com.tj.meter.util.ImageUtils;
import com.tj.meter.util.NativeWinExe;
import com.tj.meter.util.ObjectUtil;
import com.tj.meter.util.TimeUtils;
import com.tj.meter.ys.sdk.YsClientProxy;

/**
 * 摄像头服务，快照服务
 */
@Service("cameraService")
public class CameraService {
	private static Logger LOGGER = LoggerFactory.getLogger(CameraService.class);
	
	@Value("${meter.camera.snapshot.saved.folder}")
	private String cameraSnapshotFolder;
	@Value("${meter.camera.snapshot.saved.width}")
	private int cameraSnapshotSavedWidth = 800;
	
	static int delta = 1;
 	
	@Autowired
    DeviceService deviceService;
	@Autowired
    YsClientProxy ysClientProxy;
	
	/**
	 * 拍照识别流程，所有跟快照相关的服务入口，包括抓拍，保存，裁剪，识别
	 * 
	 * 流程参数:
	 * NeedRecognition是否抓拍；
	 * NeedSave是否保存图片；
	 * NeedRecognition识别
	 * 
	 */
	public CameraCaptureVo captureHandle(CameraCaptureVo param){
		LOGGER.debug("captureFlow("); 
		try {
 			initAndvalidateParam(param);
 			
			if(param.isNeedCapture()){
				doCapture(param);
			}
			
			if(param.isNeedSave()){
				doSave(param);
			}
	
			if(param.isNeedRecognition()){
				doRecognition(param);
	 		}
			
			if(param.isNeedSubmitResult()){
				doSubmitDeviceData(param);
	 		}
		} catch (MeterException e) {
			param.setScreenMessage(e.getScreenMessage());
			param.setDebugMessage(e.getDebugMessage());
			throw e;
		} catch (Exception e2) {
			param.setScreenMessage("系统错误");
			param.setDebugMessage(e2.getMessage());
			throw e2;
		}finally {
			if(param.isNeedSubmitResult()){
				doSubmitDeviceData(param);
			}
		}
		return param;
	}
	
	void doSubmitDeviceData(CameraCaptureVo param){

		DeviceData deviceData = new DeviceData();
		deviceData.setDeviceId(param.getDeviceInfoId());
		deviceData.setSnapTime(param.getCurrentTime());
		deviceData.setDataType(1);
		deviceData.setCreateTime(deviceData.getSnapTime());
		deviceData.setUpdateTime(deviceData.getSnapTime());
		deviceData.setPictureUrl(param.getWholeFileName());
		if("200".equals(param.getCode()) || "0".equals(param.getCode())){
			deviceData.setSnapData(param.getValue());
		}else{
			deviceData.setWarningReason(param.getScreenMessage());
		}
		
		deviceService.submitData(deviceData);
	}
	/**
	 * 拍照，可以获得图片url
	 * @param param
	 */
	void doCapture(CameraCaptureVo param){
		param.setCaptureTime(TimeUtils.getDateString(new Date(), TimeUtils.fullNumberFormat));	
		ysClientProxy.capture(param);
	}
	
	/**
	 * 存盘原始文件，需要ysUrl，deviceType等
	 * 文件统一存为宽600px，高度等比缩放的格式
	 * 
	 * @param param
	 */
	void doSave(CameraCaptureVo param){		
		if(StringUtils.isBlank(param.getWholeFileName())){
			genFileName(param);
		}
		
		//ysClientProxy.writeFileFromUrl(param.getYsUrl(), cameraImageFolder + param.getWholeFileName());
		ysClientProxy.writeImageFromUrl(
				param.getYsUrl(), 
				cameraSnapshotFolder + param.getWholeFileName(),
				cameraSnapshotSavedWidth, 
				null);
	}
	
	/**
	 * 裁剪,裁剪前必须保存文件
	 * @param param
	 */
	void doCut(CameraCaptureVo param){
		// 判断裁剪后的文件名
		if(StringUtils.isBlank(param.getPartialFileName())){
			throw MeterExceptionFactory.applicationException("未设置裁剪后的文件名", null);
		}
		
		// 获取裁剪范围
		if(!param.canCut()){
			if(param.getDeviceInfoId() == null){
				parseWholeFileName(param);
			}
			DeviceInfo device = deviceService.queryDeviceInfoById(param.getDeviceInfoId(), true);
			param.setX(device.getX());
			param.setY(device.getY());
			param.setW(device.getW());
			param.setH(device.getH());
		}
		
		if(!param.canCut()){
			throw MeterExceptionFactory.applicationException("裁剪范围错误，不能裁剪图片", null);
		}
		
		// 裁剪文件存在,不重新裁剪
		File file = new File(cameraSnapshotFolder + param.getPartialFileName());
		if(file.exists()){//必须判断，防止误删其他文件
			return;
		}
		
		// 裁剪并存盘
		try {
			ImageUtils.cutImage(
					cameraSnapshotFolder + param.getWholeFileName(), 
					cameraSnapshotFolder + param.getPartialFileName(), 
					param.getX(), 
					param.getY(), 
					param.getW(), 
					param.getH()
					);
		} catch (IOException e) {
			throw MeterExceptionFactory.applicationException("裁剪图片错误", e);
		}
	}
		
	/**
	 * 调用识别程序，识别图像,必须要有原文件名
	 * @param param
	 */
	void doRecognition(CameraCaptureVo param){
		if(StringUtils.isBlank(param.getRecognitionProgramPath())){
			throw MeterExceptionFactory.applicationException("获取不到识别程序", null);
		}
		
		//2. 裁剪图片
		doCut(param);
		
		
		//3. 识别
		String resultFileName = cameraSnapshotFolder + param.getPartialFileName()+".txt";
		File f = new File(resultFileName);
		if(f.exists() ){
			if(f.isFile()){
				f.delete();
			}else{
				throw MeterExceptionFactory.applicationException("识别程序结果文件已存在，不能写结果文件", null);
			}
		}
		
		NativeWinExe.call(
				param.getRecognitionProgramPath(), 
				cameraSnapshotFolder + param.getPartialFileName(), 
				resultFileName);
		//4. 读结果
		readFile(param, resultFileName);
	}
	
	/**
	 * 初始化并校验各参数
	 * camaraSerial; CamaraChannelNo; deviceId; deviceType;
	 * @param param
	 */
	public void initAndvalidateParam(CameraCaptureVo param){
		param.setCurrentTime(new Date());
		// 如果要识别，必须先保存
		if(param.isNeedRecognition()){
			param.setNeedSave(true);
		}
		// 如果要保存，必须有url,如果没有url就要设置抓拍
		if(param.isNeedSave() && StringUtils.isBlank(param.getYsUrl())){
			param.setNeedCapture(true);
		}
		
		CameraInfo cameraInfo = null;
		DeviceInfo deviceInfo = null;
		
		// get camera By id
		if(param.getCamaraSerial() != null){
			cameraInfo = deviceService.queryCameraById( param.getCamaraSerial(), true);
			if(cameraInfo == null){
				throw MeterExceptionFactory.applicationException("获取不到摄像头信息 ", null);
			}
		}
		// get deviceInfo by Id
		if(param.getDeviceInfoId() != null){
			deviceInfo = deviceService.queryDeviceInfoById(param.getDeviceInfoId(), true);
			if(deviceInfo == null){
				throw MeterExceptionFactory.applicationException("获取不到仪表信息", null);
			}
		}
		
		//  get camera By device
		if(cameraInfo == null && deviceInfo != null){
			cameraInfo = deviceService.queryCameraById(deviceInfo.getCameraSerial(), false);
	 	}
		
		if(cameraInfo == null){
			throw MeterExceptionFactory.applicationException("获取不到摄像头信息 ", null);
		}
		
		// set ------------拍照信息
		if(StringUtils.isBlank(param.getCamaraSerial())){
			param.setCamaraSerial(cameraInfo.getDeviceSerial());
		}
		if(StringUtils.isBlank(param.getCamaraChannelNo())){
			param.setCamaraChannelNo(cameraInfo.getChannelNo());
		}
		
		// 如果不需要保存，返回
		if(!param.isNeedSave()){
			return;
		}
		
		//  get device By camera
		if(deviceInfo == null && cameraInfo != null){
			if(cameraInfo.getDeviceInfo() != null){
				deviceInfo = cameraInfo.getDeviceInfo();
			}else{
				deviceInfo = deviceService.queryDeviceInfoById(param.getDeviceInfoId(), true);
			}
 		}
		
		if(deviceInfo == null){
			throw MeterExceptionFactory.applicationException("获取不到仪表信息", null);
		}
		// set ------------保存信息
		if(param.getDeviceInfoId() == null){
			param.setDeviceInfoId(deviceInfo.getId());
		}
		if(param.getDeviceInfoType() == null){
			param.setDeviceInfoType(deviceInfo.getType());
		}
		
		// 如果不需要识别，返回
		if(!param.isNeedRecognition()){
			return;
		}
		// set ------------识别信息
		if(param.getX() == 0){
			param.setX(deviceInfo.getX());
		}
		if(param.getY() == 0){
			param.setY(deviceInfo.getY());
		}
		if(param.getW() == 0){
			param.setW(deviceInfo.getW());
		}
		if(param.getH() == 0){
			param.setH(deviceInfo.getH());
		}
		
		// 获取识别程序
		DeviceType deviceType = deviceService.queryDeviceTypeByType(param.getDeviceInfoType(),true);
		if(deviceType != null){
			param.setRecognitionProgramPath(deviceType.getRecognitionProgramPath());
		}
		if(param.getRecognitionProgramPath() == null){
			throw MeterExceptionFactory.applicationException("获取不到识别程序", null);
		}
	}
	
	//读取结果
	private static void readFile(CameraCaptureVo param, String resultFileName){
		File resultFile = new File(resultFileName);
		for(int i = 0; i < 6; i++){
			if(!resultFile.exists()){
				ObjectUtil.sleep(500, true);
			}
		}
		
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new InputStreamReader(new FileInputStream(resultFile),"UTF-8"));
			String str = bf.readLine();
			if(str.startsWith("true")){
				str = str.replaceAll("true", "");
				str = str.trim();
				param.setResult(Constant.result_success);
				param.setCode(str);
				param.setValue(Float.parseFloat(bf.readLine()));
			}else{
				//TODO 模拟结果
				delta = ++delta % 50;
				param.setValue(100.1f + delta);
				param.setResult(Constant.result_success);
				
//				str = str.replaceAll("false", "");
				str = str.trim();
//				param.setResult(Constant.result_fail);
				param.setCode(str);
				param.setScreenMessage("识别程序报错");
				while((str = bf.readLine()) != null){
					param.setDebugMessage(param.getDebugMessage() + "\r" + str);
				}
			}
		}catch(Exception e){
			param.setCode("500");
			param.setScreenMessage("读取结果错误:");
			param.setDebugMessage( e.getMessage());
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
	
	/**
	 * 生成文件名，如果原始文件有裁剪范围，生成裁剪文件，带相对目录
	 * yyyy\MM\dd\yyyyMMddhhmmss_deviceType_deviceId_camaraSerial.jpg
	 * yyyy\MM\dd\yyyyMMddhhmmss_deviceType_deviceId_camaraSerial_x_y_w_h.jpg  用于识别
	 * @param param
	 */
	void genFileName(CameraCaptureVo param){
		Date currentDate = new Date();
		String folder = TimeUtils.getDateString(currentDate, "yyyy_MM\\dd\\");
		if(StringUtils.isBlank(param.getCaptureTime())){
			param.setCaptureTime(TimeUtils.getDateString(currentDate, "yyyyMMddhhmmss"));
		}
		//原始文件
		String fileName = folder + String.format("%s_%s_%s_%s.jpg",
				param.getCaptureTime(), 
				param.getDeviceInfoType(), 
				param.getDeviceInfoId(), 
				param.getCamaraSerial());
		param.setWholeFileName(fileName);
		//裁剪文件
		if(param.canCut()){
			fileName = folder + String.format("%s_%s_%s_%s_%s_%s_%s_%s.jpg",
					param.getCaptureTime(), 
					param.getDeviceInfoType(), 
					param.getDeviceInfoId(), 
					param.getCamaraSerial(),
					param.getX(),
					param.getY(),
					param.getW(),
					param.getH()
					);
			param.setPartialFileName(fileName);
		}
	}
	
	// 解析文件名得到各元数据
	void parseWholeFileName(CameraCaptureVo param){
		if(StringUtils.isBlank(param.getWholeFileName())){
			return;
		}
		String[] arrays = param.getWholeFileName().split("[_.]");//["yyMMddhhmmss","deviceType","deviceId","camaraSerial","jpg"]
		if(arrays.length < 5){
			throw MeterExceptionFactory.applicationException("文件名不合法，无法解析", null);
		}
		param.setCaptureTime(arrays[0]);
		param.setDeviceInfoType(Long.parseLong(arrays[1]));
		param.setDeviceInfoId(Long.parseLong(arrays[2]));
		param.setCamaraSerial(arrays[3]);
	}
	
	public File buildFile(String snapshotName) {
		return new File(cameraSnapshotFolder+snapshotName);
	}
}