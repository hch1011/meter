package com.jd.meter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.meter.dao.DeviceDataDao;
import com.jd.meter.dao.DeviceInfoDao;
import com.jd.meter.dao.DeviceTypeDao;
import com.jd.meter.entity.DeviceInfo;
import com.jd.meter.entity.DeviceType;

@Service("deviceService")
public class DeviceService {
	@Autowired
	DeviceTypeDao deviceTypeDao;
	@Autowired
	DeviceInfoDao deviceInfoDao;
	@Autowired
	DeviceDataDao deviceDataDao;
	
	
	/**
	 * 查询主界面要展示的数据
	 */
	public List<DeviceType>  queryDataForMonitorPage(){
		List<DeviceType> typeObjList = deviceTypeDao.findByMonitorPageFlagOrderByMonitorPageSort(1);
		if(typeObjList == null || typeObjList.size() == 0){
			return typeObjList;
		}
		
		Map<Long, DeviceType> map = new HashMap<Long, DeviceType>();
		for(DeviceType type : typeObjList){
			map.put(type.getType(), type);
		}
		
		
		List<DeviceInfo> list = deviceInfoDao.findMonitorInfo();
		DeviceType temp;
		for(DeviceInfo info : list){
			temp = map.get(info.getType());
			if(temp != null){
				temp.addDeviceInfo(info);
			}
		}
		
		return typeObjList;
	}
}
