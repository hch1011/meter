package com.jd.meter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "device_info")
public class DeviceInfo extends EntityId implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "code")
	private String code;			//设备编号： A-1,A-2,A-3...A-9,B-1,B-2
	
	@Column(name = "path")
	private String path;			//路劲终端位置          1号主变A相， 1号主变B相， 1号主变C相， 2号主变A相...
	
	@Column(name = "type")
	private String type;			//类别(汉字):避雷针; SF6密度
	
	@Column(name = "name")
	private String name;			//名称               :避雷针1，避雷针2，SF6密度计1，SF6密度计2
	
	@Column(name = "ip")
	private String ip;				//终端IP

 
	@Column(name = "pre_read_data")
	private Float preReadData;		//最新预读数据
	
	@Column(name = "pre_read_time")
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private Date preReadTime;		//最新预读时间
	
	@Column(name = "pre_tead_insert_time")
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private Date preReadInsertTime;	//最新预读数据保存数据库时间
	
	@Column(name = "pre_read_status")
	private Integer preReadStatus;	//最新预读状态；正常,预警,报警,失败
	
	@Column(name = "warning_reason")
	private String warningReason;	//报警原因
	
	@Column(name = "create_time")
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private Date createTime;		//
	
	@Column(name = "update_time")
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private Date updateTime;		//
	
	@Column(name = "description")
	private String description;		//说明

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Float getPreReadData() {
		return preReadData;
	}

	public void setPreReadData(Float preReadData) {
		this.preReadData = preReadData;
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

	public Integer getPreReadStatus() {
		return preReadStatus;
	}

	public void setPreReadStatus(Integer preReadStatus) {
		this.preReadStatus = preReadStatus;
	}

	public String getWarningReason() {
		return warningReason;
	}

	public void setWarningReason(String warningReason) {
		this.warningReason = warningReason;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
