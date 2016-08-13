package com.jd.meter.service;

import java.util.*;

import com.jd.meter.communication.SendMsg;
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
	@Autowired
	SendMsg sendMsg;
	
	List<DeviceInfo> allDeviceInfoList; 
	Map<Long,DeviceType> deviceTypeCache = new HashMap<Long, DeviceType>();
	
	
	public DeviceType  queryDeviceTypeByType(Long type, boolean fromCache) {
		DeviceType obj;
		if(fromCache){
			obj = deviceTypeCache.get(type);
			if(obj != null){
				return obj;
			}
		}
		
		
		obj = deviceTypeDao.findOne(type);
		if(obj != null){
			deviceTypeCache.put(type, obj);
		}
		 
		return obj;
	}
	
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

	public List<Map<Integer,List<DeviceInfo>>> queryDeviceInfo() {
		List<Map<Integer,List<DeviceInfo>>> resultList = new ArrayList<Map<Integer,List<DeviceInfo>>>();
		List<DeviceInfo> deviceInfoList = deviceInfoDao.findAllDeviceInfo();
		Integer inputNum = 0;
		Map<Integer,List<DeviceInfo>> map = null;
		List<DeviceInfo> list = null;
		for(DeviceInfo deviceInfo : deviceInfoList) {

			if(deviceInfo.getInputNum() != null && !deviceInfo.getInputNum().equals(inputNum)) {
				//进线不同
				map = new HashMap<Integer,List<DeviceInfo>>();
				list = new ArrayList<DeviceInfo>();
				inputNum = deviceInfo.getInputNum();
				list.add(deviceInfo);
				map.put(inputNum, list);
				resultList.add(map);
			} else {//进线相同
				if(map.get(deviceInfo.getInputNum()) != null) {
					map.get(deviceInfo.getInputNum()).add(deviceInfo);
				} else {
					list = new ArrayList<DeviceInfo>();
					list.add(deviceInfo);
					map.put(deviceInfo.getInputNum(), list);
				}
			}
		}
		return resultList;
	}

	public DeviceInfo queryDeviceInfoById(Long deviceId) {
		return deviceInfoDao.findOne(deviceId);
	}


	public List<DeviceInfo> queryDeviceInfoBySnapStatus(Integer[] snapStatus) {
		List<DeviceInfo> list = deviceInfoDao.findBySnapStatusIn(snapStatus);
		for (DeviceInfo deviceInfo : list) {
			deviceInfo.setDeviceType(this.queryDeviceTypeByType(deviceInfo.getType(), true));
		}
		
		return list;
	}

	public DeviceData queryDeviceDataByDeviceId(Long deviceId) {
		return deviceDataDao.findByDeviceId(deviceId);
	}

	public Boolean imageRecollect(Long deviceId) {
		String message = "" + deviceId;
		return sendMsg.send(message);
	}

}
