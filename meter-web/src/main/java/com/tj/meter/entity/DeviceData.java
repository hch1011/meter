package com.tj.meter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.persistence.TemporalType;



@Entity()
@Table(name = "device_data")
public class DeviceData  implements Serializable{
	//schedule.launch正常监控(任务触发), manual.launch人工 触发监控, manual.edit人工修改 	
	public static final String dataType_scheduleLaunch = "schedule.launch";
	public static final String dataType_manualLaunch = "manual.launch";
	public static final String dataType_manualEdit = "manual.edit";
	public static final Integer  snapStatus_fail = 0;//失败
	public static final Integer  snapStatus_normal = 0;//正常
	public static final Integer  snapStatus_warning = 0;//预警
	public static final Integer  snapStatus_alarm = 0;//报警
	
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @Column(name = "id")
	private Long id;
	
	@Column(name = "device_id")
	private Long deviceId;			//设备ID
	
	@Column(name = "device_code")
	private String deviceCode;		//设备编号： A-1,A-2,A-3...A-9,B-1,B-2 
	
	@Column(name = "snap_data")
	private Float snapData;			//最新预读数据
	
	@Column(name = "snap_status")
	private Integer snapStatus;		//最新预读状态；0失败;1正常,2预警,3报警
	
	@Column(name = "snap_time")
	@Temporal(TemporalType.TIMESTAMP)
    private Date snapTime;			//最新预读时间
	
	@Column(name = "change_rate")
	private Float changeRate;		//变化率
	
	@Column(name = "frequency")
	private Integer frequency;		//动作次数
	
	@Column(name = "data_type")
	private String	dataType;		//数值类型：schedule.launch正常监控(任务触发), manual.launch人工 触发监控, manual.edit人工修改 
	
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	@Column(name = "picture_local_path")
	private String pictureLocalPath;//本地保存的原始图片相对路径
	
	@Column(name = "picture_url")
	private String pictureUrl;		//该属性不一定有效。图片url,可以是外部平台url；也可以是自己的url(尽量使用pictureLocalPath拼接一个)
	
	@Column(name = "warning_reason")
	private String warningReason;	//报警原因

	@Column(name = "meta_data")
	private String metaData;		//元数据，如果有，json格式
	
	@Column(name = "sync_success_time")
	private Date syncSuccessTime;	//同步成功时间，服务端时间使用client带过去的时间
	 
	@Transient
	private int sincCount;		//同步次数
	@Transient
	private String test1;		//元数据，如果有，json格式

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

	

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
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

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getWarningReason() {
		return warningReason;
	}

	public void setWarningReason(String warningReason) {
		this.warningReason = warningReason;
	}
	//追加报警 (预警)原因
	public void appendWarningReason(String warningReason) {
		if(this.warningReason == null){
			this.warningReason = warningReason;
		}else{
			this.warningReason = this.warningReason + ";" + warningReason;
		}
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	public String getTest1() {
		return test1;
	}

	public void setTest1(String test1) {
		this.test1 = test1;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getChangeRate() {
		return changeRate;
	}

	public void setChangeRate(Float changeRate) {
		this.changeRate = changeRate;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Date getSyncSuccessTime() {
		return syncSuccessTime;
	}

	public void setSyncSuccessTime(Date syncSuccessTime) {
		this.syncSuccessTime = syncSuccessTime;
	}

	public int getSincCount() {
		return sincCount;
	}

	public void setSincCount(int sincCount) {
		this.sincCount = sincCount;
	}
	public void incSincCount() {
		sincCount++;
	}

	public String getPictureLocalPath() {
		return pictureLocalPath;
	}

	public void setPictureLocalPath(String pictureLocalPath) {
		this.pictureLocalPath = pictureLocalPath;
	}
	
}
