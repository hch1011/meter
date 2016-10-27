package com.jd.meter.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jd.meter.util.Constant;

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
	private String dataWarningStrategy;		//四种报警/预警策略(<,<=,>,>=);其他符号表示不做判断,做正常处理
	
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
	private Integer frequencyForWarning;	//动作次数预警阈值

	@Column(name= "frequency_for_alarm")
	private Integer frequencyForAlarm;		//动作次数报警阈值
	
	@Column(name = "frequency_warning_strategy")
	private String frequencyWarningStrategy;//报警策略，四种策略（<,<=,>,>=） ；其他符号表示不做判断
	
	@Column(name = "snap_times")
    private String snapTimes;				//最新预读时间列表

	@Column(name = "monitor_page_flag")
	private Integer monitorPageFlag;		//是否需要出现在监控首页
	
	@Column(name = "monitor_page_sort")
	private Float monitorPageSort;			//出现在监控首页顺序,在同一类别内排序,默认按编号
	

	@Column(name = "recognition_program_path")
	private String recognitionProgramPath;	//识别程序完整路径
	
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

	public Integer getFrequencyForWarning() {
		return frequencyForWarning;
	}

	public void setFrequencyForWarning(Integer frequencyForWarning) {
		this.frequencyForWarning = frequencyForWarning;
	}

	public Integer getFrequencyForAlarm() {
		return frequencyForAlarm;
	}

	public void setFrequencyForAlarm(Integer frequencyForAlarm) {
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
	
	public String getRecognitionProgramPath() {
		return recognitionProgramPath;
	}

	public void setRecognitionProgramPath(String recognitionProgramPath) {
		this.recognitionProgramPath = recognitionProgramPath;
	}

	/**
	 * 检查数据状态,返回状态
	 * 如果有报警，状态为报警
	 * 
	 * 
	 * @return
	 */
	public Integer resetDataWarningStatus(DeviceData data){
		if(data == null){
			return null;
		}
		if(data.getSnapData() == null){
			data.setSnapStatus(Constant.status_invalid);
			return data.getSnapStatus();
		}
		
		data.setSnapStatus(null);
		
		//*********报警判断
		// 数据报警
		if(checkMatch(data.getSnapData(), dataForAlarm, dataWarningStrategy)){
			data.setSnapStatus(Constant.status_alarm);
			data.appendWarningReason(dataName + dataWarningStrategy + "报警值" + dataForAlarm.floatValue());
			//return data.getSnapStatus();
		}
		 
		// 变化率报警
		if(checkMatch(data.getChangeRate(), changeRateForAlarm, changeRateWarningStrategy)){
			data.setSnapStatus(Constant.status_alarm);
			data.appendWarningReason(dataName + "变化率" + dataWarningStrategy + "报警值" + changeRateForAlarm.floatValue());
			//return data.getSnapStatus();
		}
		// 动作次数报警
		if(checkMatch(data.getFrequency(), frequencyForAlarm, frequencyWarningStrategy)){
			data.setSnapStatus(Constant.status_alarm);
			data.appendWarningReason(dataName + "动作次数" + dataWarningStrategy + "报警值" + frequencyForAlarm.floatValue());
			//return data.getSnapStatus();
		}
		
		if(data.getSnapStatus() != null){
			return data.getSnapStatus();
		}
		
		//*********预警判断
		// 数据预警
		if(checkMatch(data.getSnapData(), dataForWarning, dataWarningStrategy)){
			if(data.getSnapData() == null){
				data.setSnapStatus(Constant.status_warning);
			}
			data.appendWarningReason(dataName + dataWarningStrategy + "预警值" + dataForWarning.floatValue());
			//return data.getSnapStatus();
		}
		// 变化率报警
		if(checkMatch(data.getChangeRate(), changeRateForWarning, changeRateWarningStrategy)){
			if(data.getSnapData() == null){
				data.setSnapStatus(Constant.status_warning);
			}
			data.appendWarningReason(dataName + "变化率" + dataWarningStrategy + "预警值" + changeRateForWarning.floatValue());
			//return data.getSnapStatus();
		}
		// 动作次数报警
		if(checkMatch(data.getFrequency(), frequencyForWarning, frequencyWarningStrategy)){
			if(data.getSnapData() == null){
				data.setSnapStatus(Constant.status_warning);
			}
			data.appendWarningReason(dataName + "动作次数" + dataWarningStrategy + "预警值" + frequencyForWarning.floatValue());
			//return data.getSnapStatus();
		}
		
		if(data.getSnapStatus() == null){
			data.setSnapStatus(Constant.status_ok);
		}
		return 1;
	}
	
	/**
	 * 判断data1，dat2是否匹配指定符号
	 * 如果匹配，返回true,
	 * @param data1
	 * @param data2
	 * @param symbol	符号： >,<,>=,<=
	 * @return
	 */
	private boolean checkMatch(Float data1, Float data2, String symbol){
		if(data1 == null || data2 == null){
			return false;
		}
		
		if(">".equals(symbol)){
			return data1.floatValue() > data2.floatValue();
		}else if("<".equals(symbol)){
			return data1.floatValue() < data2.floatValue();
		}else if(">=".equals(symbol)){
			return data1.floatValue() >= data2.floatValue();
		}else if("<=".equals(symbol)){
			return data1.floatValue() <= data2.floatValue();
		}
		return false;
	}
	//如果匹配，返回true
	private boolean checkMatch(Integer data1, Integer data2, String symbol){
		if(data1 == null || data2 == null){
			return false;
		}
		
		if(">".equals(symbol)){
			return data1.intValue() > data2.intValue();
		}else if("<".equals(symbol)){
			return data1.intValue() < data2.intValue();
		}else if(">=".equals(symbol)){
			return data1.intValue() >= data2.intValue();
		}else if("<=".equals(symbol)){
			return data1.intValue() <= data2.intValue();
		}
		return false;
	}
}