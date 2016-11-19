package com.tj.meter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 摄像头信息
 * 
 * @author hc
 *
 */
@Entity
@Table(name = "camera_info")
public class CameraInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	DeviceInfo deviceInfo;

    @Id
    @Column(name = "device_serial")
	private String deviceSerial;	// 设备序列号:427734168
	private String deviceName; 		// 设备名称
	private String model;			// 设备型号，如CS-C2S-21WPFR-WX
	private String status;			// 设备在线状态：0不在线，1在线;  摄像头在线状态：1-在线，2-不在线
	private String defence;			// A1设备布撤防状态：0睡眠，8在家，16外出，非A1设备布撤防状态：0撤防，1布防
	private String isEncrypt;		// 是否加密：0不加密，1加密
	
	//摄像头信息
	private String channelNo;		//通道号
	private String channelName; 	//通道名
	private String isShared; 		//分享状态：1分享所有者，0未分享，2分享接受者（表示此摄像头是别人分享给我的）
	private String picUrl;			//图片地址（大图），若在萤石客户端设置封面则返回封面图片，未设置则返回默认图片
	private String videoLevel;		//视频质量：0-流畅，1-均衡，2-高清，3-超清
	
	// 关联属性
	private Date createTime;
	private Date updateTime;
	
	
	
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

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getStatus() {
		return status;
	}
	
	public String getStatusText() {
		//摄像头在线状态：1-在线，2-不在线
		if("1".equals(status)){
			return "在线";
		}
		if("2".equals(status)){
			return "不在线";
		}
		//数据库有，但在线查询不到
		if("-1".equals(status)){
			return "遗失";
		}
		return "未知状态:"+status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDefence() {
		return defence;
	}

	public void setDefence(String defence) {
		this.defence = defence;
	}

	public String getIsEncrypt() {
		return isEncrypt;
	}

	public void setIsEncrypt(String isEncrypt) {
		this.isEncrypt = isEncrypt;
	}

	public String getVideoLevel() {
		return videoLevel;
	}

	public void setVideoLevel(String videoLevel) {
		this.videoLevel = videoLevel;
	}
}
