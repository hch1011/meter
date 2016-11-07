package com.jd.meter.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 快照相关信息
 *
 */
public class CameraCaptureVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
    Date currentTime;				//当前时间
	
	// 如果识别会返回下列值
    boolean success;  				//识别状态
    String code;  					//returnCode,正常0
    Float value;					//识别值
    String screenMessage;			//用于显示的消息
    String debugMessage;			//用于调试的明细
    
    //指令
    boolean needCapture;			//是否抓拍，如果不抓拍，图片可以从其他地方获取
    boolean needSave;				//是否保存到本地
    boolean needRecognition ;		//是否识别
    boolean needSubmitResult;		//是否提交结果到业务流程
    
	// 拍照参数
    String camaraSerial;			
    String camaraChannelNo;
    Long deviceInfoId;
    Long deviceInfoType;
    
    // 照相机返回值
    String uuid;
    String ysUrl;			//外网url,可直接访问
    String captureTime;		//拍照时间
    
    // 保存文件
    String wholeFileName;	//完整图片文件名，相对路径: yyyy\MM\dd\time_deviceType_deviceId_camaraSerial.jpg
    String partialFileName;	//裁剪后的文件名，用于识别的:yyyy\MM\dd\time_deviceType_deviceId_camaraSerial_x_y_w_h.jpg
    
    // 识别范围
    int x,y,w,h;
	String recognitionProgramPath;	//识别程序完整路径
    

	public boolean canCut(){
		return x>=0 && y>=0 && w>=1 && h>1;
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Float getValue() {
		return value;
	}


	public void setValue(Float value) {
		this.value = value;
	}
 

	public boolean isNeedCapture() {
		return needCapture;
	}


	public void setNeedCapture(boolean needCapture) {
		this.needCapture = needCapture;
	}
	
	public String getScreenMessage() {
		return screenMessage;
	}


	public void setScreenMessage(String screenMessage) {
		this.screenMessage = screenMessage;
	}


	public String getDebugMessage() {
		return debugMessage;
	}


	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}


	public boolean isNeedSave() {
		return needSave;
	}


	public void setNeedSave(boolean needSave) {
		this.needSave = needSave;
	}


	public boolean isNeedRecognition() {
		return needRecognition;
	}


	public void setNeedRecognition(boolean needRecognition) {
		this.needRecognition = needRecognition;
	}
	
	public boolean isNeedSubmitResult() {
		return needSubmitResult;
	}


	public void setNeedSubmitResult(boolean needSubmitResult) {
		this.needSubmitResult = needSubmitResult;
	}


	public String getCamaraSerial() {
		return camaraSerial;
	}


	public void setCamaraSerial(String camaraSerial) {
		this.camaraSerial = camaraSerial;
	}


	public String getCamaraChannelNo() {
		return camaraChannelNo;
	}


	public void setCamaraChannelNo(String camaraChannelNo) {
		this.camaraChannelNo = camaraChannelNo;
	}
 

	public Long getDeviceInfoId() {
		return deviceInfoId;
	}


	public void setDeviceInfoId(Long deviceInfoId) {
		this.deviceInfoId = deviceInfoId;
	}


	public Long getDeviceInfoType() {
		return deviceInfoType;
	}


	public void setDeviceInfoType(Long deviceInfoType) {
		this.deviceInfoType = deviceInfoType;
	}


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public String getYsUrl() {
		return ysUrl;
	}


	public void setYsUrl(String ysUrl) {
		this.ysUrl = ysUrl;
	}


	public String getCaptureTime() {
		return captureTime;
	}


	public void setCaptureTime(String captureTime) {
		this.captureTime = captureTime;
	}


	public String getWholeFileName() {
		return wholeFileName;
	}


	public void setWholeFileName(String wholeFileName) {
		this.wholeFileName = wholeFileName;
	}


	public String getPartialFileName() {
		return partialFileName;
	}


	public void setPartialFileName(String partialFileName) {
		this.partialFileName = partialFileName;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getW() {
		return w;
	}


	public void setW(int w) {
		this.w = w;
	}


	public int getH() {
		return h;
	}


	public void setH(int h) {
		this.h = h;
	}


	public String getRecognitionProgramPath() {
		return recognitionProgramPath;
	}


	public void setRecognitionProgramPath(String recognitionProgramPath) {
		this.recognitionProgramPath = recognitionProgramPath;
	}


	public Date getCurrentTime() {
		return currentTime;
	}


	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	
}
