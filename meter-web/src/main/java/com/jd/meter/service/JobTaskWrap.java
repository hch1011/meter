package com.jd.meter.service;

import java.util.*;

import com.google.common.collect.Sets;
import com.jd.meter.entity.DeviceInfo;

 /**
  * 执行时间表示:
  * 
  * 每半分钟扫描一次，但实际任务的执行时间最小间隔分钟，只要在当前分钟没有执行过的任务就可以执行
  * 
  * 1020,1350   指定时间执行 
  * 00,10,30    表示每小时的相应分钟都执行
  * 
  * @author hc
  */
public class JobTaskWrap {
	Set<String> runTimes; 	//可以运行的时间
	long lastRunAt = 0;		//最近一次运行时间，一分钟最多只能运行一次

	Long deviceType = null;
	List<DeviceInfo> DeviceInfoList;	//最近一次运行时间，一分钟最多只能运行一次
 
	/**
	 * 
	 * @param timeExpressions  两种格式:小时分钟1030或分钟格式10
	 */
	public void addRunTimes(Collection<String> timeExpressions){
		if(runTimes == null){
			runTimes = Sets.newHashSet();
		}
		runTimes.addAll(timeExpressions);
	}
	
	
	public boolean canRun(long currentTimeLong, String currentTimeExpressions1, String currentTimeExpressions2 ){
		if(currentTimeLong - lastRunAt < 60000){
			return false;
		}
 		return runTimes.contains(currentTimeExpressions1) || runTimes.contains(currentTimeExpressions2);
	}


	public Set<String> getRunTimes() {
		return runTimes;
	}


	public void setRunTimes(Set<String> runTimes) {
		this.runTimes = runTimes;
	}


	public long getLastRunAt() {
		return lastRunAt;
	}


	public void setLastRunAt(long lastRunAt) {
		this.lastRunAt = lastRunAt;
	}


	public Long getDeviceType() {
		return deviceType;
	}


	public void setDeviceType(Long deviceType) {
		this.deviceType = deviceType;
	}


	public List<DeviceInfo> getDeviceInfoList() {
		return DeviceInfoList;
	}


	public void setDeviceInfoList(List<DeviceInfo> deviceInfoList) {
		DeviceInfoList = deviceInfoList;
	}
}
 
 