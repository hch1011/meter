package com.jd.meter.entity;

/**
 * 快照相关信息
 *
 */
public class CameraCaptureVo {
	// 拍照参数
    String camaraSerial;
    String camaraChannelNo;
    Long deviceId;
    Long deviceType;
    
    boolean opCapture;		//是否抓拍，如果不抓拍，图片可以从其他地方获取
    boolean opSave;			//是否保存到本地
    boolean opRecognition ;	//是否识别
    
    // 照相机返回值
    String uuid;
    String ysUrl;			//外网url,可直接访问
    String captureTime;		//拍照时间
    
    // 保存文件会返回下列值
    String fileName;		//文件名，相对路径: yyyy\MM\dd\time_deviceType_deviceId_camaraSerial.jpg
    String fileName2;		//文件名，用于识别的:yyyy\MM\dd\time_deviceType_deviceId_camaraSerial_x_y_w_h.jpg
    String url;	 			//本网站对于url
    // 识别范围
    int x,y,w,h;
    
    
    // 如果识别会返回下列值
	String recognitionProgramPath;	//识别程序完整路径
    boolean success;  			//识别状态码
    String code;  			//识别状态码
    Float value;			//识别值
    String msg;				//错误消息
    
	
	public CameraCaptureVo capture() {
		this.opCapture = true;
		return this;
	}
	public CameraCaptureVo save() {
		this.opSave = true;
		return this;
	}
	public CameraCaptureVo recognition() {
		this.opRecognition = true;
		return this;
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
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	
	public boolean isOpCapture() {
		return opCapture;
	}
	public void setOpCapture(boolean opCapture) {
		this.opCapture = opCapture;
	}
	public boolean isOpSave() {
		return opSave;
	}
	public void setOpSave(boolean opSave) {
		this.opSave = opSave;
	}
	public boolean isOpRecognition() {
		return opRecognition;
	}
	public void setOpRecognition(boolean opRecognition) {
		this.opRecognition = opRecognition;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Long getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Long deviceType) {
		this.deviceType = deviceType;
	}
	public String getFileName2() {
		return fileName2;
	}
	public void setFileName2(String fileName2) {
		this.fileName2 = fileName2;
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
	
	public boolean canCut(){
		return x>0 || y>0 || w>0 || h>0;
	}
	public String getRecognitionProgramPath() {
		return recognitionProgramPath;
	}
	public void setRecognitionProgramPath(String recognitionProgramPath) {
		this.recognitionProgramPath = recognitionProgramPath;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
