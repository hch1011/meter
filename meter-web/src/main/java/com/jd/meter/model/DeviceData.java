package com.jd.meter.model;

import java.util.Date;

public class DeviceData {
	private Long id;				//数据库ID
	private Integer deviceId;		//设备ID	
	private String deviceCode;		//设备编号： A-1,A-2,A-3...A-9,B-1,B-2 
	//data
	private String preReadData;	//最新预读数据
	private String preReadStatus;	//最新预读状态；正常,预警,报警,失败
	private Date preReadTime;		//最新预读时间
	private Date preReadInsertTime;	//最新预读数据保存数据库时间
	private String warningReason;	//报警原因
	private Integer	dataType;		//数值类型：1预读，2重新识别，3人工修正 
	private String route;			//路由
	private String pictureUrl;		//图片路径
	private String metaData;		//元数据，如果有，json格式
	
	
	
	
	private Date createTime;		//
	private Date updateTime;		//
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getPreReadStatus() {
		return preReadStatus;
	}
	public void setPreReadStatus(String preReadStatus) {
		this.preReadStatus = preReadStatus;
	}
	public Date getPreReadTime() {
		return preReadTime;
	}
	public void setPreReadTime(Date preReadTime) {
		this.preReadTime = preReadTime;
	}
	public Date getPreReadInsertTime() {
		return preReadInsertTime;
	}
	public void setPreReadInsertTime(Date preReadInsertTime) {
		this.preReadInsertTime = preReadInsertTime;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getMetaData() {
		return metaData;
	}
	public void setMetaData(String metaData) {
		this.metaData = metaData;
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
	public String getPreReadData() {
		return preReadData;
	}
	public void setPreReadData(String preReadData) {
		this.preReadData = preReadData;
	}
	public String getWarningReason() {
		return warningReason;
	}
	public void setWarningReason(String warningReason) {
		this.warningReason = warningReason;
	}
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
}
