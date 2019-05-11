package com.tj.meter.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tj.meter.dao.DeviceDataDao;
import com.tj.meter.dao.DeviceInfoDao;
import com.tj.meter.dao.DeviceTypeDao;
import com.tj.meter.entity.CameraCaptureVo;
import com.tj.meter.entity.CameraInfo;
import com.tj.meter.entity.DeviceData;
import com.tj.meter.entity.DeviceInfo;
import com.tj.meter.entity.DeviceType;
import com.tj.meter.exception.MeterException;
import com.tj.meter.exception.MeterExceptionFactory;
import com.tj.meter.util.ObjectUtil;
import com.tj.meter.util.SnowflakeIdGenerator;
import com.tj.meter.ys.sdk.YsClientProxy;

@Service("systemPropertyService")
public class SystemPropertyService {
	private static Logger LOGGER = LoggerFactory.getLogger(SystemPropertyService.class);

	@Autowired
	Cache cache;
	
	@Value("${meter.centerServer.syncdata:false}")
	private boolean syncdata = false;
	@Autowired
	private DeviceTypeDao deviceTypeDao;
	@Autowired
	private DeviceInfoDao deviceInfoDao;
	@Autowired
	private DeviceDataDao deviceDataDao;
	@Autowired
	private CameraService cameraService;
	@Autowired
	private YsClientProxy ysClientProxy;
	
	
	//CacheBuilder<Object, Object> deviceInfoCache = CacheBuilder.newBuilder().expireAfterWrite(120, TimeUnit.SECONDS);
	
	public Collection<DeviceType>  queryAllDeviceType(boolean fromCache) {
		if(fromCache && cache.deviceTypeCache.size() > 0){
			return cache.deviceTypeCache.values();
		}
		List<DeviceType> list = deviceTypeDao.findAll();
		for(DeviceType item : list){
			cache.deviceTypeCache.put(item.getType(), item);
		}
		return list;
	}

	public DeviceType  queryDeviceTypeByType(Long type, boolean fromCache) {
		DeviceType obj;
		if(fromCache){
			obj = cache.deviceTypeCache.get(type);
			if(obj != null){
				return obj;
			}
		}
		
		obj = deviceTypeDao.findOne(type);
		if(obj != null){
			cache.deviceTypeCache.put(type, obj);
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
		try {
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
			// 一直失败状态，只更新时间
			if(DeviceData.snapStatus_fail.equals(deviceData.getSnapStatus())
				&& DeviceData.snapStatus_fail.equals(deviceInfo.getSnapStatus())){
				DeviceInfo deviceInfoUpdate = new DeviceInfo();
				deviceInfoUpdate.setId(deviceInfo.getId());
				deviceInfoUpdate.setSnapData(deviceData.getSnapData());
				deviceInfoUpdate.setUpdateTime(new Date());
				deviceInfoUpdate.setSnapTime(new Date());
				deviceInfoUpdate.setPictureLocalPath(deviceData.getPictureLocalPath());
				deviceInfoUpdate.setWarningReason(deviceData.getWarningReason());
				deviceInfoDao.save(deviceInfoUpdate);
				return;
			}
			
			ObjectUtil.checkNotNull(deviceInfo, true, "仪表不存在，deviceId="+ deviceData.getDeviceId());
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
			deviceInfo.setSnapDataId(deviceData.getId());
			deviceInfo.setSnapData(deviceData.getSnapData());
			deviceInfo.setSnapStatus(deviceData.getSnapStatus());
			deviceInfo.setSnapTime(deviceData.getSnapTime());
	 		deviceInfo.setChangeRate(deviceData.getChangeRate());
			deviceInfo.setFrequency(deviceData.getFrequency());
			deviceInfo.setWarningReason(deviceData.getWarningReason());
			deviceInfo.setPictureLocalPath(deviceData.getPictureLocalPath());
			deviceInfoDao.save(deviceInfo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
	}
//	/**
//	 * 接受到同步过来的数据
//	 * @param deviceData
//	 */
//	public void receiveSyncDeviceData(DeviceData deviceData) {
//		LOGGER.info("receiveSyncDeviceData");
//		DeviceData deviceDataDB = deviceDataDao.findOne(deviceData.getId());
//		if(deviceDataDB != null){
//			return;
//		}
//		deviceDataDao.save(deviceData);
//		
//		DeviceInfo deviceInfo = deviceInfoDao.findOne(deviceData.getDeviceId());
//		if(deviceInfo != null && deviceInfo.getSnapTime().before(deviceData.getSnapTime())){
//	 		deviceInfo.setChangeRate(deviceData.getChangeRate());
//			deviceInfo.setFrequency(deviceData.getFrequency());
//			deviceInfo.setSnapData(deviceData.getSnapData());
//			deviceInfo.setSnapStatus(deviceData.getSnapStatus());
//			deviceInfo.setSnapTime(deviceData.getSnapTime());
//			deviceInfo.setWarningReason(deviceData.getWarningReason());
//			deviceInfo.setSnapDataId(deviceData.getId());
//			deviceInfoDao.save(deviceInfo);
//		}
//	}
 
	public List<DeviceInfo> queryDeviceInfoAllOrderByInputNum(){
		if(System.currentTimeMillis() > cache.queryDeviceInfoAllOrderByInputNumCacheExpireTime){
			cache.queryDeviceInfoAllOrderByInputNumCache = null;
		}
		if(cache.queryDeviceInfoAllOrderByInputNumCache == null){
			cache.queryDeviceInfoAllOrderByInputNumCache = deviceInfoDao.findAllDeviceInfoOrderByInputNum();
			cache.queryDeviceInfoAllOrderByInputNumCacheExpireTime = System.currentTimeMillis() + 600 * 1000;
		}
		
		 return cache.queryDeviceInfoAllOrderByInputNumCache;
	}
	

	public List<Map<Integer,List<DeviceInfo>>> queryDeviceInfoGroupByInputNum() {
		List<Map<Integer,List<DeviceInfo>>> resultList = new ArrayList<Map<Integer,List<DeviceInfo>>>();
		List<DeviceInfo> deviceInfoList = queryDeviceInfoAllOrderByInputNum();

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
	
	
	public DeviceInfo queryDeviceInfoById(Long deviceInfoId, boolean useCache) {
		if(deviceInfoId == null){
			return null;
		}
		DeviceInfo info = cache.deviceInfoCache.get(deviceInfoId);
		if(useCache && info != null){
			return info;
		} 
		info = deviceInfoDao.findOne(deviceInfoId);
 		return cache.cache(info);
	}

	public List<DeviceInfo> queryDeviceInfoBySnapStatus(Integer[] snapStatus) {
		List<DeviceInfo> list = deviceInfoDao.findBySnapStatusIn(snapStatus);
		for (DeviceInfo deviceInfo : list) {
			deviceInfo.setDeviceType(this.queryDeviceTypeByType(deviceInfo.getType(), true));
		}
		
		return list;
	}
	
	private DeviceInfo queryDeviceInfoByCameraSerial(String deviceSerial) {
		DeviceInfo info = queryDeviceInfoById(cache.cameraDeviceInfoCache.get(deviceSerial), true);
		if(info == null){
			List<DeviceInfo> list = deviceInfoDao.findByCameraSerial(deviceSerial);
			if(list.size() >= 1){
				info = list.get(0);
				cache.cache(info);
			}
		}
		return info;
	}
	
//	/**
//	 * 获取待同步的数据
//	 * @param length
//	 * @return
//	 */
//	public List<DeviceData> queryWaitingSync(int length) {
//		List<DeviceData> list = deviceDataDao.findWaitingSync(length);
//		return list;
//	}
 	
	public List<DeviceData> queryDeviceDataListByRange(Long deviceId, Date fromDate, Date toDate,Pageable pageable) {
		if(pageable == null){
			pageable = new PageRequest(0, 500, Direction.ASC, "id"); 
		} 
		return deviceDataDao.findByRange(deviceId, fromDate, toDate);
	}

	public DeviceData queryDeviceDataById(Long deviceId) {
		return deviceDataDao.findOne(deviceId);
	}

	
	/**
	 * 保存仪表识别范围
	 * 
	 * @param deviceInfoId
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public void updateCutRange(Long deviceInfoId, Integer x, Integer y, Integer w, Integer h) {
		if(deviceInfoId == null){
			throw MeterExceptionFactory.applicationException("为指定仪表，不能保存范围", null);
		}
		
		if(x<1 || y<1  || w<10 || h<10){
			throw MeterExceptionFactory.applicationException("识别范围不正确", null);
		}
		
		DeviceInfo deviceInfo = deviceInfoDao.findOne(deviceInfoId);
		if(deviceInfo == null){
			throw MeterExceptionFactory.applicationException("为找到对应的仪表", null);
		}
		
		deviceInfo.setX(x);
		deviceInfo.setY(y);
		deviceInfo.setW(w);
		deviceInfo.setH(h);
		deviceInfo.setUpdateTime(new Date());
		deviceInfoDao.save(deviceInfo);
		cache.cache(deviceInfo);
	}
	
	public CameraInfo queryCameraById(String deviceSerial, boolean withDeviceInfo) {
		CameraInfo info = cache.cameraCache.get(deviceSerial);
		if(info == null){
			loadAllCameraListFromSDK();
			info = cache.cameraCache.get(deviceSerial);
		}

		if(info == null){
			throw MeterExceptionFactory.applicationException("未找到摄像头", null);
		}
		if(withDeviceInfo){
			info.setDeviceInfo(this.queryDeviceInfoByCameraSerial(deviceSerial));
		}
 		return info;
	}

	public List<CameraInfo> queryAllCameraList(boolean withDeviceInfo){
		long currentTime =  System.currentTimeMillis();
		if(cache.cameraCacheExpireTime < currentTime ){
			try {
				// 重新加载摄像头列表
				loadAllCameraListFromSDK();
			} catch (Exception e) {
				// 如果获取列表失败，原数据再可用5分钟
				cache.cameraCacheExpireTime = System.currentTimeMillis() + 5 * 60 *1000L;
			}
		}
		ArrayList<CameraInfo> list = Lists.newArrayList(cache.cameraCache.values());
		 
		if(withDeviceInfo){
			for(CameraInfo item : list){
				item.setDeviceInfo(queryDeviceInfoByCameraSerial(item.getDeviceSerial()));
			}
		}
		return list;
	}
	
	/**
	 * 从SDK查询所有摄像头
	 * @return
	 */
	private Map<String,CameraInfo> loadAllCameraListFromSDK(){
		//摄像头列表缓存,key是序列号
		Map<String,CameraInfo> tmpMap = Maps.newHashMap();
		int page = 0;//当前第几页
		int pageSize = 10000;//每页大小，实际每页最大大小，还得开放平台确定
		PageRequest pageable = new PageRequest(page, pageSize);
		Page<CameraInfo> dataPage = ysClientProxy.cameraList(pageable);
		while(dataPage.getContent().size() > 0){
			for(CameraInfo item : dataPage.getContent()){
				tmpMap.put(item.getDeviceSerial(), item);
			}
			pageable = new PageRequest(++page, dataPage.getSize());
			dataPage = ysClientProxy.cameraList(pageable);
		}
		
		cache.cameraCache = tmpMap;
		cache.cameraCacheExpireTime = System.currentTimeMillis() + 3600 * 1000L;
		return tmpMap;
	}


//	public void bindCamera(Long deviceInfoId, String cameraSerialId, boolean force) {
//		DeviceInfo deviceInfo = deviceInfoDao.findOne(deviceInfoId);
//		deviceInfo.setCameraSerial(cameraSerialId);
//		deviceInfo.setUpdateTime(new Date());
//		deviceInfo.setCameraBindTime(deviceInfo.getUpdateTime());
//		deviceInfoDao.save(deviceInfo);
//		
//		clean(deviceInfo);
// 	}

	
	public void bindCameraAndDeviceInfo(String cameraSerial, Long deviceInfoId, boolean force){
		
		// 解绑之前的摄像头
		List<DeviceInfo> list = deviceInfoDao.findByCameraSerial(cameraSerial);
				
		for(DeviceInfo info : list){
			info.setCameraSerial(null);
			deviceInfoDao.save(info);
			cache.clean(info);
		}
		//绑定现在的摄像头
		DeviceInfo info = deviceInfoDao.findOne(deviceInfoId);
		cache.clean(info);
		
		info.setUpdateTime(new Date());
		info.setCameraBindTime(info.getUpdateTime());
		info.setCameraSerial(cameraSerial);
		deviceInfoDao.save(info);
		cache.cache(info);
	}
	 

	
	public void monitor(JobTaskWrap task) {
		if(task.getDeviceInfoList() == null){
			task.setDeviceInfoList(queryDeviceInfoByType(task.getDeviceType())); 
		}
		for(DeviceInfo device : task.getDeviceInfoList()){
			monitor(device);
		}
	}
	
	public void monitor(DeviceInfo device) {
		CameraCaptureVo param = new CameraCaptureVo();
		param.setDeviceInfoId(device.getId());
		param.setNeedRecognition(true);
		//param.setNeedSubmitResult(true);
		
		 try {
			cameraService.captureHandle(param);
			DeviceData deviceData = buildDeviceData(param, null);
			submitData(deviceData);
		} catch (MeterException  e) {
			DeviceData deviceData = buildDeviceData(param, null);
			deviceData.setWarningReason(e.getScreenMessage());
			submitData(deviceData);
			LOGGER.error(e.getMessage());
		} catch (Exception  e) {
			DeviceData deviceData = buildDeviceData(param, null);
			deviceData.setSnapStatus(DeviceData.snapStatus_fail);
			deviceData.setWarningReason("系统错误");
			submitData(deviceData);
			LOGGER.error(e.getMessage(),e);
		}
	}
	
	DeviceData buildDeviceData(CameraCaptureVo param, String dataType){
		if(dataType == null){
			dataType = DeviceData.dataType_scheduleLaunch;
		}
		
		DeviceData deviceData = new DeviceData();
		deviceData.setDeviceId(param.getDeviceInfoId());
		deviceData.setSnapTime(param.getCurrentTime());
		deviceData.setDataType(dataType);
		deviceData.setCreateTime(param.getCurrentTime());
		deviceData.setUpdateTime(param.getCurrentTime());
		deviceData.setPictureUrl(param.getYsUrl());
		deviceData.setPictureLocalPath(param.getWholeFileName());
		
		if("200".equals(param.getCode()) || "0".equals(param.getCode())){
			deviceData.setSnapStatus(DeviceData.snapStatus_normal);
			deviceData.setSnapData(param.getValue());
		}else{
			deviceData.setSnapStatus(DeviceData.snapStatus_fail);
			deviceData.setSnapData(null);
			deviceData.setWarningReason(param.getScreenMessage());
		} 
 		return deviceData;
	}
	
//	int i = 100;
//	float testDate(){
//		i++;
//		if(i>150){
//			i=100;
//		}
//		return (float)i;
//	}
}
