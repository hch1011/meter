package com.jd.meter.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.jd.meter.entity.DeviceType;
import com.jd.meter.util.TimeUtils;

/**
  * 每分钟扫描一次设备，看设备在当前有没有要执行的情况；
  * 扫描时间hhmm，mm
  * 
  * @author hc
  */
@Component("monitorJobService")  
public class MonitorJobService {
	@Autowired
	private DeviceService deviceService;
	private Map<Long,JobTaskWrap> taskMap;

	private long currentTime;
	private String currentTimeHhmm;		//1010
	private String currentTimemm;		//10
	
	public void scanDevice(){
		currentTime = System.currentTimeMillis();
		currentTimeHhmm = TimeUtils.getDateString(new Date(currentTime), "HHmm");
		currentTimemm = TimeUtils.getDateString(new Date(currentTime), "mm");
		
		if(taskMap == null){
			reloadAllDeviceType();
		}

		for(JobTaskWrap task : taskMap.values()){
			if(task.canRun(currentTime, currentTimeHhmm, currentTimemm)){
					deviceService.monitor(task);
					task.setLastRunAt(currentTime);
			}
		}
	}
	
	public void reloadAllDeviceType(){
		 Collection<DeviceType> list = deviceService.queryAllDeviceType(true);
		 Map<Long,JobTaskWrap> newTaskMap = Maps.newHashMap();
		 for(DeviceType item : list){
			 List<String> times = TimeUtils.parseTimeExpression(item.getSnapTimes(), false);
			 if(times != null && times.size() > 0){
				 JobTaskWrap wrap = new JobTaskWrap();
				 wrap.addRunTimes(times);
				 wrap.setDeviceType(item.getType());
				 newTaskMap.put(wrap.getDeviceType(),wrap);
			 }
		 }
		 taskMap = newTaskMap;
	}
	
	public void reloadDeviceType(DeviceType deviceType){
		if (taskMap == null) {
			return;
		}
		List<String> times = TimeUtils.parseTimeExpression(deviceType.getSnapTimes(), false);
		if (times != null && times.size() > 0) {
			JobTaskWrap wrap = new JobTaskWrap();
			wrap.addRunTimes(times);
			wrap.setDeviceType(deviceType.getType());
			taskMap.put(wrap.getDeviceType(), wrap);
		}
	}
}
 
