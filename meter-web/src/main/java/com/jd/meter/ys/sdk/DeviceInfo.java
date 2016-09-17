package com.jd.meter.ys.sdk;

import java.io.Serializable;

/**
 * 设备信息
 * 
 * @author hc
 *
 */
public class DeviceInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String deviceSerial;// 设备序列号:427734168
	private String deviceName; // 设备名称
	private String model;// 设备型号，如CS-C2S-21WPFR-WX
	private int status;// 设备在线状态：0不在线，1在线;  摄像头在线状态：1-在线，2-不在线
	private int defence;// A1设备布撤防状态：0睡眠，8在家，16外出，非A1设备布撤防状态：0撤防，1布防
	private int isEncrypt;// 是否加密：0不加密，1加密
	
	//摄像头信息
	private String channelNo;//通道号
	private String channelName; //通道名
	private String isShared; //分享状态：1分享所有者，0未分享，2分享接受者（表示此摄像头是别人分享给我的）
	private int picUrl;//图片地址（大图），若在萤石客户端设置封面则返回封面图片，未设置则返回默认图片
	private int videoLevel;//视频质量：0-流畅，1-均衡，2-高清，3-超清

	public String getDeviceSerial() {
		return deviceSerial;
	}

	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getIsEncrypt() {
		return isEncrypt;
	}

	public void setIsEncrypt(int isEncrypt) {
		this.isEncrypt = isEncrypt;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getIsShared() {
		return isShared;
	}

	public void setIsShared(String isShared) {
		this.isShared = isShared;
	}

	public int getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(int picUrl) {
		this.picUrl = picUrl;
	}

	public int getVideoLevel() {
		return videoLevel;
	}

	public void setVideoLevel(int videoLevel) {
		this.videoLevel = videoLevel;
	}

}
