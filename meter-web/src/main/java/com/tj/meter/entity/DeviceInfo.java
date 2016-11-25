package com.tj.meter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "device_info")
public class DeviceInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Transient
	private DeviceType deviceType;
	@Transient
	private CameraInfo cameraInfo;
	
    @Id
    @Column(name = "id")
	private Long id;
    
	@Column(name = "code")
	private String code;			//设备编号： A-1,A-2,A-3...A-9,B-1,B-2

	@Column(name = "input_num")
	private Integer inputNum;		//进线编号
	
	@Column(name = "path")
	private String path;			//路劲终端位置  	1号主变A相; 1号主变B相; 1号主变C相， 2号主变A相...

	@Column(name = "type")
	private Long type;				//类别:避雷针,SF6密度计

	@Column(name = "name")
	private String name;			//名称  :避雷针1，避雷针2，SF6密度计1，SF6密度计2
	
	@Column(name = "ip")
	private String ip;				//终端IP

	@Column(name = "snap_data_id")
	private Long snapDataId;		//最新预读数据ID
	

	@Column(name = "picture_local_path")
	private String pictureLocalPath;//本地保存的原始图片相对路径
	
	@Column(name = "snap_data")
	private Float snapData;			//最新预读数据
	
	@Column(name = "snap_status")
	private Integer snapStatus;		//最新预读状态:0失败;1正常,2预警,3报警
	
	@Column(name = "snap_time")
	@Temporal(TemporalType.TIMESTAMP)
    private Date snapTime;			//最新预读时间


	@Column(name = "change_rate")
	private Float changeRate;		//变化率
	
	@Column(name = "frequency")
	private Integer frequency;		//动作次数
	
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
	
	
	// 摄像头信息
	@Column(name = "camera_serial")
	private String cameraSerial; 	//摄像头序列号
	
	@Column(name = "camera_bind_time")
	private Date cameraBindTime;	
	@Column(name = "x")
	private Integer x; 				//图片切片坐标 X轴起点
	@Column(name = "y")
	private Integer y; 				//图片切片坐标 Y轴起点
	@Column(name = "w")
	private Integer w; 				//图片切片宽度
	@Column(name = "h")
	private Integer h; 				//图片切片高度

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getInputNum() {
		return inputNum;
	}

	public void setInputNum(Integer inputNum) {
		this.inputNum = inputNum;
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

	public Long getSnapDataId() {
		return snapDataId;
	}

	public void setSnapDataId(Long snapDataId) {
		this.snapDataId = snapDataId;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public String getCameraSerial() {
		return cameraSerial;
	}

	public void setCameraSerial(String cameraSerial) {
		this.cameraSerial = cameraSerial;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getW() {
		return w;
	}

	public void setW(Integer w) {
		this.w = w;
	}

	public Integer getH() {
		return h;
	}

	public void setH(Integer h) {
		this.h = h;
	}
	
	public CameraInfo getCameraInfo() {
		return cameraInfo;
	}

	public void setCameraInfo(CameraInfo cameraInfo) {
		this.cameraInfo = cameraInfo;
	}

	public Date getCameraBindTime() {
		return cameraBindTime;
	}

	public void setCameraBindTime(Date cameraBindTime) {
		this.cameraBindTime = cameraBindTime;
	}

	public String getPictureLocalPath() {
		return pictureLocalPath;
	}

	public void setPictureLocalPath(String pictureLocalPath) {
		this.pictureLocalPath = pictureLocalPath;
	}

	public String getSnapStatusEn() {
		//0失败;1正常,2预警,3报警
		if(snapStatus == null || snapStatus == 0){
			return "fail";
		}
		if(snapStatus == 1){
			return "success";
		}
		if(snapStatus == 2){
			return "warning";
		}
		if(snapStatus == 3){
			return "alarm";
		}
		return "unowStatus:"+snapStatus;
	}
	public String getSnapStatusCn() {
		//0失败;1正常,2预警,3报警
		if(snapStatus == null || snapStatus == 0){
			return "失败";
		}
		if(snapStatus == 1){
			return "正常";
		}
		if(snapStatus == 2){
			return "预警";
		}
		if(snapStatus == 3){
			return "报警";
		}
		return "位置状态:"+snapStatus;
	}
}
