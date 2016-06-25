package com.jd.meter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "device_data")
public class DeviceData extends EntityId implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name = "device_id")
	private Long deviceId;			//设备ID
	
	@Column(name = "device_code")
	private String deviceCode;		//设备编号： A-1,A-2,A-3...A-9,B-1,B-2 
	
	//data
	@Column(name = "pre_read_data")
	private Float preReadData;		//最新预读数据
	
	@Column(name = "pre_read_status")
	private Integer preReadStatus;	//最新预读状态；正常,预警,报警,失败
	
	@Column(name = "pre_read_time")
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private Date preReadTime;		//最新预读时间
	
	@Column(name = "pre_read_insert_time")
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private Date preReadInsertTime;	//最新预读数据保存数据库时间
	
	@Column(name = "warning_reason")
	private String warningReason;	//报警原因
	
	@Column(name = "data_type")
	private Integer	dataType;		//数值类型：1预读，2重新识别，3人工修正 
	
	//@Column(name = "route")
	//private String route;			//路由
	
	@Column(name = "picture_url")
	private String pictureUrl;		//图片路径
	
	@Column(name = "meta_data")
	private String metaData;		//元数据，如果有，json格式
	
	@Column(name = "create_time")
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private Date createTime;		
	
	@Column(name = "update_time")
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private Date updateTime;

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public Float getPreReadData() {
		return preReadData;
	}

	public void setPreReadData(Float preReadData) {
		this.preReadData = preReadData;
	}

	public Integer getPreReadStatus() {
		return preReadStatus;
	}

	public void setPreReadStatus(Integer preReadStatus) {
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
	
}
