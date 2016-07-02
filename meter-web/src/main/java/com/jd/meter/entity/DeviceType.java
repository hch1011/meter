package com.jd.meter.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "device_type")
public class DeviceType implements Serializable{
	private static final long serialVersionUID = 1L;

	@Transient
	List<DeviceInfo> deviceInfoList;
	
	@Id
	@Column(name = "type")
	private Long type;						//类型编号,人工维护
	
	@Column(name = "type_name")
	private String typeName;				//类别名:避雷针; SF6密度
 

	@Column(name = "data_name")
	private String dataName;				//设备数据名称如:电流,SF6密度;用于组合页面label显示
	
	@Column(name = "data_for_warning")
	private Float dataForWarning;			//预警阈值(mA)

	@Column(name = "data_for_alarm")
	private Float dataForAlarm;				//报警阈值(mA)
	
	@Column(name = "data_warning_strategy")
	private String dataWarningStrategy;		//报警策略<,<=,>,>=
	
	@Column(name = "data_unit")
	private String dataUnit;				//单位

	@Column(name = "change_rate_for_warning")
	private Float changeRateForWarning;		//变化率频预警阈值

	@Column(name = "change_rate_for_alarm")
	private Float changeRateForAlarm;		//变化频率报警阈值
	
	@Column(name = "change_rate_warning_strategy")
	private String changeRateWarningStrategy;//报警策略<,<=,>,>=

	@Column(name = "change_rate_unit")
	private String changeRateUnit;			//单位

	@Column(name = "frequency_for_warning")
	private Float frequencyForWarning;		//动作次数预警阈值

	@Column(name= "frequency_for_alarm")
	private Float frequencyForAlarm;		//动作次数报警阈值
	
	@Column(name = "frequency_warning_strategy")
	private String frequencyWarningStrategy;//报警策略<,<=,>,>=
	
	@Column(name = "snap_times")
    private String snapTimes;				//最新预读时间列表

	@Column(name = "monitor_page_flag")
	private Integer monitorPageFlag;			//是否需要出现在监控首页
	@Column(name = "monitor_page_sort")
	private Float monitorPageSort;			//出现在监控首页顺序,在同一类别内排序,默认按编号
	
	@Column(name = "description")
	private String description;				//说明
	

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public Float getDataForWarning() {
		return dataForWarning;
	}

	public void setDataForWarning(Float dataForWarning) {
		this.dataForWarning = dataForWarning;
	}

	public Float getDataForAlarm() {
		return dataForAlarm;
	}

	public void setDataForAlarm(Float dataForAlarm) {
		this.dataForAlarm = dataForAlarm;
	}

	public String getDataWarningStrategy() {
		return dataWarningStrategy;
	}

	public void setDataWarningStrategy(String dataWarningStrategy) {
		this.dataWarningStrategy = dataWarningStrategy;
	}

	public String getDataUnit() {
		return dataUnit;
	}

	public void setDataUnit(String dataUnit) {
		this.dataUnit = dataUnit;
	}

	public Float getChangeRateForWarning() {
		return changeRateForWarning;
	}

	public void setChangeRateForWarning(Float changeRateForWarning) {
		this.changeRateForWarning = changeRateForWarning;
	}

	public Float getChangeRateForAlarm() {
		return changeRateForAlarm;
	}

	public void setChangeRateForAlarm(Float changeRateForAlarm) {
		this.changeRateForAlarm = changeRateForAlarm;
	}

	public String getChangeRateWarningStrategy() {
		return changeRateWarningStrategy;
	}

	public void setChangeRateWarningStrategy(String changeRateWarningStrategy) {
		this.changeRateWarningStrategy = changeRateWarningStrategy;
	}

	public String getChangeRateUnit() {
		return changeRateUnit;
	}

	public void setChangeRateUnit(String changeRateUnit) {
		this.changeRateUnit = changeRateUnit;
	}

	public Float getFrequencyForWarning() {
		return frequencyForWarning;
	}

	public void setFrequencyForWarning(Float frequencyForWarning) {
		this.frequencyForWarning = frequencyForWarning;
	}

	public Float getFrequencyForAlarm() {
		return frequencyForAlarm;
	}

	public void setFrequencyForAlarm(Float frequencyForAlarm) {
		this.frequencyForAlarm = frequencyForAlarm;
	}

	public String getFrequencyWarningStrategy() {
		return frequencyWarningStrategy;
	}

	public void setFrequencyWarningStrategy(String frequencyWarningStrategy) {
		this.frequencyWarningStrategy = frequencyWarningStrategy;
	}

	public String getSnapTimes() {
		return snapTimes;
	}

	public void setSnapTimes(String snapTimes) {
		this.snapTimes = snapTimes;
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

	public List<DeviceInfo> getDeviceInfoList() {
		return deviceInfoList;
	}

	public void setDeviceInfoList(List<DeviceInfo> deviceInfoList) {
		this.deviceInfoList = deviceInfoList;
	}
	
	public void addDeviceInfo(DeviceInfo deviceInfo) {
		if(this.deviceInfoList == null){
			this.deviceInfoList = new ArrayList<DeviceInfo>();
		}
		this.deviceInfoList.add(deviceInfo);
	}

	public Integer getMonitorPageFlag() {
		return monitorPageFlag;
	}

	public void setMonitorPageFlag(Integer monitorPageFlag) {
		this.monitorPageFlag = monitorPageFlag;
	}
}
