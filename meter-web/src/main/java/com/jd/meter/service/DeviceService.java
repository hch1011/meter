package com.jd.meter.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.meter.dao.DeviceDataDao;
import com.jd.meter.dao.DeviceInfoDao;
import com.jd.meter.dao.DeviceTypeDao;
import com.jd.meter.entity.DeviceData;
import com.jd.meter.entity.DeviceInfo;
import com.jd.meter.entity.DeviceType;
import com.jd.meter.util.ObjectUtil;
import com.jd.meter.util.SnowflakeIdGenerator;

@Service("deviceService")
public class DeviceService {
	@Autowired
	DeviceTypeDao deviceTypeDao;
	@Autowired
	DeviceInfoDao deviceInfoDao;
	@Autowired
	DeviceDataDao deviceDataDao;
	
	List<DeviceInfo> allDeviceInfoList; 
	
	public List<DeviceInfo>  queryDeviceInfoByType(Long type) {
		List<DeviceInfo>  list = deviceInfoDao.findByType(type);
		return list;
	}
	
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
 
	/**
	 * 提交数据，
	 * @param deviceData
	 */
	public void submitData(DeviceData deviceData) {
		// init
		if(deviceData.getCreateTime() == null){
			deviceData.setCreateTime(new Date());
		}
		deviceData.setUpdateTime(deviceData.getCreateTime());
		if(deviceData.getSnapData() == null){
			deviceData.setSnapData(0f);
		}
		
		// checkData
		DeviceInfo deviceInfo = deviceInfoDao.findOne(deviceData.getDeviceId());
		ObjectUtil.checkNotNull(deviceInfo, true, "仪表不存在，deviceId="+deviceData.getDeviceId());
		DeviceType deviceType = deviceTypeDao.findOne(deviceInfo.getType());
		ObjectUtil.checkNotNull(deviceType, true, "设备类型不存在，type="+deviceInfo.getType());
		
		//TODO 变化率计算
		if(deviceInfo.getChangeRate() == null){
			deviceInfo.setChangeRate(deviceData.getSnapData() - deviceInfo.getSnapData());
		}
		
		
		//设置报警信息
		deviceType.resetDataWarningStatus(deviceData);
	
		//save Devicedata
		deviceData.setId(SnowflakeIdGenerator.getInstance().nextId());
		deviceData.setDeviceCode(deviceInfo.getCode());
		deviceDataDao.save(deviceData);
		
		//update DeviceInfo
 		deviceInfo.setChangeRate(deviceData.getChangeRate());
		deviceInfo.setFrequency(deviceData.getFrequency());
		deviceInfo.setSnapData(deviceData.getSnapData());
		deviceInfo.setSnapStatus(deviceData.getSnapStatus());
		deviceInfo.setSnapTime(deviceData.getSnapTime());
		deviceInfo.setWarningReason(deviceData.getWarningReason());
		deviceInfo.setSnapDataId(deviceData.getId());
		deviceInfoDao.save(deviceInfo);
	}

}
