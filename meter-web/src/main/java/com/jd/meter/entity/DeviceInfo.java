package com.jd.meter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "device_info")
public class DeviceInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
    @Id
    @Column(name = "id")
	private Long Id;
    
	@Column(name = "code")
	private String code;			//设备编号： A-1,A-2,A-3...A-9,B-1,B-2
	
	@Column(name = "path")
	private String path;			//路劲终端位置  	1号主变A相; 1号主变B相; 1号主变C相， 2号主变A相...

	@Column(name = "type")
	private Long type;				//类别

	@Column(name = "name")
	private String name;			//名称               :避雷针1，避雷针2，SF6密度计1，SF6密度计2
	
	@Column(name = "ip")
	private String ip;				//终端IP

	@Column(name = "snap_data")
	private Float snapData;			//最新预读数据
	
	@Column(name = "snap_status")
	private Integer snapStatus;		//最新预读状态:0失败;1正常,2预警,3报警
	
	@Column(name = "snap_time")
	@Temporal(TemporalType.TIMESTAMP)
    private Date snapTime;			//最新预读时间

	@Column(name = "warning_reason")
	private String warningReason;	//报警原因
	
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
    private Date createTime;		//创建时间
	
	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;		//更新时间

	@Column(name = "monitor_page_flag")
	private Integer monitorPageFlag;//1出现在监控首页,其它不出现

	@Column(name = "monitor_page_sort")
	private Float monitorPageSort;	//出现在监控首页顺序,在同一类别内排序,默认按编号
	
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

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
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

	public Float getSnapData() {
		return snapData;
	}

	public void setSnapData(Float snapData) {
		this.snapData = snapData;
	}

	public Integer getSnapStatus() {
		return snapStatus;
	}

	public void setSnapStatus(Integer snapStatus) {
		this.snapStatus = snapStatus;
	}

	public Date getSnapTime() {
		return snapTime;
	}

	public void setSnapTime(Date snapTime) {
		this.snapTime = snapTime;
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

	public Integer getMonitorPageFlag() {
		return monitorPageFlag;
	}

	public void setMonitorPageFlag(Integer monitorPageFlag) {
		this.monitorPageFlag = monitorPageFlag;
	}

	public Float getMonitorPageSort() {
		return monitorPageSort;
	}

	public void setMonitorPageSort(Float monitorPageSort) {
		this.monitorPageSort = monitorPageSort;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}
}
