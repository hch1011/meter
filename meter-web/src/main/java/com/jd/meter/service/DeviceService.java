package com.jd.meter.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.jd.meter.dao.DeviceDataDao;
import com.jd.meter.dao.DeviceInfoDao;
import com.jd.meter.dao.DeviceTypeDao;
import com.jd.meter.entity.DeviceData;
import com.jd.meter.entity.DeviceInfo;
import com.jd.meter.entity.DeviceType;
import com.jd.meter.sync.SyncTriggerService;
import com.jd.meter.util.ObjectUtil;
import com.jd.meter.util.SnowflakeIdGenerator;

@Service("deviceService")
public class DeviceService {
	private static Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
	 
	@Value("${meter.centerServer.syncdata:false}")
	public boolean syncdata = false;
	@Autowired
	DeviceTypeDao deviceTypeDao;
	@Autowired
	DeviceInfoDao deviceInfoDao;
	@Autowired
	DeviceDataDao deviceDataDao;
	@Autowired
	SyncTriggerService syncTriggerService;
	
	List<DeviceInfo> allDeviceInfoList; 
	Map<Long,DeviceType> deviceTypeCache = new HashMap<Long, DeviceType>();
	
	
	CacheBuilder<Object, Object> deviceInfoCache = 
			 CacheBuilder.newBuilder()
			 .expireAfterWrite(120, TimeUnit.SECONDS);


	
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
		if(deviceData.getId() == null){
			deviceData.setId(SnowflakeIdGenerator.getInstance().nextId());
		}
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

		if(syncdata){
			if(deviceData.getSyncSuccessTime() == null){
				syncTriggerService.sendDataSync(deviceData);
			}
		}
	}
	/**
	 * 接受到同步过来的数据
	 * @param deviceData
	 */
	public void receiveSyncDeviceData(DeviceData deviceData) {
		LOGGER.info("receiveSyncDeviceData");
		DeviceData deviceDataDB = deviceDataDao.findOne(deviceData.getId());
		if(deviceDataDB != null){
			return;
		}
		deviceDataDao.save(deviceData);
		
		DeviceInfo deviceInfo = deviceInfoDao.findOne(deviceData.getDeviceId());
		if(deviceInfo != null && deviceInfo.getSnapTime().before(deviceData.getSnapTime())){
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

	public void updateDeviceDataForSyncSuccess(DeviceData deviceData) {
		deviceDataDao.save(deviceData);
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
	

	/**
	 * 获取待同步的数据
	 * @param length
	 * @return
	 */
	public List<DeviceData> queryWaitingSync(int length) {
		List<DeviceData> list = deviceDataDao.findWaitingSync(length);
		return list;
	}
 	
	public DeviceData queryDeviceDataById(Long deviceId) {
		return deviceDataDao.findOne(deviceId);
	}

	public void bindCamera(Long deviceInfoId, String cameraSerial, boolean force) {
		DeviceInfo deviceInfo = deviceInfoDao.findOne(deviceInfoId);
		deviceInfo.setCameraSerial(cameraSerial);
		deviceInfo.setUpdateTime(new Date());
		deviceInfo.setCameraBindTime(deviceInfo.getUpdateTime());
		deviceInfoDao.save(deviceInfo);
	}

	public void reSetCutRange(Long deviceInfoId, Integer x, Integer y, Integer w, Integer h) {
		DeviceInfo deviceInfo = deviceInfoDao.findOne(deviceInfoId);
		deviceInfo.setX(x);
		deviceInfo.setY(y);
		deviceInfo.setW(w);
		deviceInfo.setH(h);
		deviceInfo.setUpdateTime(new Date());
		deviceInfoDao.save(deviceInfo);
	}

//	public Boolean imageRecollect(Long deviceId) {
//		String message = "" + deviceId;
//		return sendMsg.send(message);
//	}

}
