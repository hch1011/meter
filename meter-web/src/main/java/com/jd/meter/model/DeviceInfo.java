package com.jd.meter.model;

import java.util.Date;

public class DeviceInfo {
	private Integer id;				//数据库ID
	private String code;			//设备编号： A-1,A-2,A-3...A-9,B-1,B-2
	private String path;			//路劲终端位置          1号主变A相， 1号主变B相， 1号主变C相， 2号主变A相...
	private String type;			//类别(汉字):避雷针; SF6密度
	private String name;			//名称               :避雷针1，避雷针2，SF6密度计1，SF6密度计2
	private String ip;				//终端IP

 
	private String preReadData;		//最新预读数据
	private Date preReadTime;		//最新预读时间
	private Date preReadInsertTime;	//最新预读数据保存数据库时间
	private String preReadStatus;	//最新预读状态；正常,预警,报警,失败
	private String warningReason;	//报警原因
	private Date createTime;		//
	private Date updateTime;		//
	private String description;		//说明
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public String getPreReadData() {
		return preReadData;
	}
	public void setPreReadData(String preReadData) {
		this.preReadData = preReadData;
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
	public String getWarningReason() {
		return warningReason;
	}
	public void setWarningReason(String warningReason) {
		this.warningReason = warningReason;
	}
	
	
}
