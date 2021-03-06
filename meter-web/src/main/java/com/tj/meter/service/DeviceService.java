package com.tj.meter.service;

import static com.tj.meter.exception.MeterExceptionFactory.exceptionIfBlank;
import static com.tj.meter.exception.MeterExceptionFactory.exceptionIfNotBlank;

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

@Service("deviceService")
public class DeviceService {
	private static Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);

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
	

	public DeviceInfo createDeviceInfo(DeviceInfo deviceInfo) {
		exceptionIfBlank(deviceInfo.getId(), "ID不能为空");
		exceptionIfBlank(deviceInfo.getInputNum(), "进线不能为空");
		exceptionIfBlank(deviceInfo.getPath(), "路径不能为空");
		exceptionIfBlank(deviceInfo.getName(), "名称不能为空");
		exceptionIfBlank(queryDeviceTypeByType(deviceInfo.getType(), true), "类型不对");
		exceptionIfNotBlank(deviceInfoDao.findOne(deviceInfo.getId()), "ID已存在");
		if(deviceInfo.getMonitorPageFlag() == null ){
			deviceInfo.setMonitorPageFlag(0);
		}
		if(deviceInfo.getMonitorPageSort() == null ){
			deviceInfo.setMonitorPageSort(deviceInfo.getId().floatValue()%100000);
		}
		deviceInfo.setCreateTime(new Date());
		deviceInfo.setUpdateTime(new Date());
		deviceInfoDao.save(deviceInfo);
		return deviceInfo;
	}
	

	public DeviceInfo updateDeviceInfo(DeviceInfo deviceInfo) {
		exceptionIfBlank(deviceInfo.getId(), "ID不能为空");
		DeviceInfo deviceInfoDb = deviceInfoDao.findOne(deviceInfo.getId());
		exceptionIfBlank(deviceInfoDb, "设备不存在");
		//只能更新的属性
		//deviceInfoDb.setId(deviceInfo.getId());
		deviceInfoDb.setCode(deviceInfo.getCode());
		deviceInfoDb.setInputNum(deviceInfo.getInputNum());
		deviceInfoDb.setPath(deviceInfo.getPath());
		deviceInfoDb.setName(deviceInfo.getName());
		deviceInfoDb.setMonitorPageFlag(deviceInfo.getMonitorPageFlag());
		deviceInfoDb.setMonitorPageSort(deviceInfo.getMonitorPageSort());
		deviceInfoDb.setDescription(deviceInfo.getDescription());
		deviceInfoDb.setUpdateTime(new Date());
		deviceInfoDao.save(deviceInfoDb);
		return deviceInfo;
	}
	

	public void deleteDeviceInfo(Long id) {
		deviceInfoDao.delete(id);
	}
	
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
		if(type == null){
			return null;
		}
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
			
			// checkData
			DeviceInfo deviceInfoDb = deviceInfoDao.findOne(deviceData.getDeviceId());
			if(deviceInfoDb == null){
				throw MeterExceptionFactory.systemException("仪表数据未找到", null);
			}
			deviceInfoDb.setUpdateTime(deviceData.getSnapTime());
			// 一直失败状态，只更新时间不跟新数据
			if(DeviceData.snapStatus_fail.equals(deviceData.getSnapStatus())){
				//第一次出错
				if(!DeviceData.snapStatus_fail.equals(deviceInfoDb.getSnapStatus())){
					deviceInfoDb.setSnapFailCount(1);
 				}else{
 					if(deviceInfoDb.getSnapFailCount() == null){
 						deviceInfoDb.setSnapFailCount(1);
 					}else{
 						deviceInfoDb.setSnapFailCount(deviceInfoDb.getSnapFailCount() + 1);
 					}
 				}

				if(deviceInfoDb.getSnapFailBeginTime() == null){
					deviceInfoDb.setSnapFailBeginTime(deviceData.getSnapTime());
				}
				deviceInfoDb.setSnapStatus(deviceData.getSnapStatus());
				if(deviceData.getPictureLocalPath() != null){
					deviceInfoDb.setPictureLocalPath(deviceData.getPictureLocalPath());	
				}
				deviceInfoDb.setWarningReason(deviceData.getWarningReason());
				deviceInfoDao.save(deviceInfoDb);
				return;
			}else{
				deviceInfoDb.setSnapFailBeginTime(null);
				deviceInfoDb.setSnapFailCount(0);
				deviceInfoDb.setWarningReason(null);
			}
			
			DeviceType deviceType = queryDeviceTypeByType(deviceInfoDb.getType(),true);
			ObjectUtil.checkNotNull(deviceType, true, "设备类型不存在，type="+deviceInfoDb.getType());
			
			//TODO 变化率计算
			deviceData.setChangeRate(0f);
			if(deviceInfoDb.getSnapData() != null  && deviceInfoDb.getSnapTime() != null && deviceData.getSnapData() != null){
				float deltaValue = deviceData.getSnapData() - deviceInfoDb.getSnapData();
				long deltaTime = deviceData.getSnapTime().getTime() - deviceInfoDb.getSnapTime().getTime();
				deviceInfoDb.setChangeRate(deltaValue * 3600000 / deltaTime);
			}
			
			//设置报警信息
			deviceType.resetDataWarningStatus(deviceData);
		
			//save Devicedata
			if(deviceData.getId() == null){
				deviceData.setId(SnowflakeIdGenerator.getInstance().nextId());
			}
			deviceDataDao.save(deviceData);
			
			//update DeviceInfo
			deviceInfoDb.setSnapDataId(deviceData.getId());
			deviceInfoDb.setSnapData(deviceData.getSnapData());
			deviceInfoDb.setSnapStatus(deviceData.getSnapStatus());
			deviceInfoDb.setSnapTime(deviceData.getSnapTime());
	 		deviceInfoDb.setChangeRate(deviceData.getChangeRate());
			deviceInfoDb.setFrequency(deviceData.getFrequency());
			deviceInfoDb.setWarningReason(deviceData.getWarningReason());
			deviceInfoDb.setPictureLocalPath(deviceData.getPictureLocalPath());
			deviceInfoDao.save(deviceInfoDb);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
	}
	
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
		
		DeviceInfo deviceInfoDb = deviceInfoDao.findOne(deviceInfoId);
		if(deviceInfoDb == null){
			throw MeterExceptionFactory.applicationException("为找到对应的仪表", null);
		}
		
		deviceInfoDb.setX(x);
		deviceInfoDb.setY(y);
		deviceInfoDb.setW(w);
		deviceInfoDb.setH(h);
		deviceInfoDb.setUpdateTime(new Date());
		deviceInfoDao.save(deviceInfoDb);
		cache.cache(deviceInfoDb);
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
				
		for(DeviceInfo infoDb : list){
			infoDb.setCameraSerial(null);
			deviceInfoDao.save(infoDb);
			cache.clean(infoDb);
		}
		//绑定现在的摄像头
		DeviceInfo infoDb = deviceInfoDao.findOne(deviceInfoId);
		cache.clean(infoDb);
		
		infoDb.setUpdateTime(new Date());
		infoDb.setCameraBindTime(infoDb.getUpdateTime());
		infoDb.setCameraSerial(cameraSerial);
		deviceInfoDao.save(infoDb);
		cache.cache(infoDb);
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
		
		if("success".equals(param.getResult())){
			deviceData.setSnapStatus(DeviceData.snapStatus_normal);
			deviceData.setSnapData(param.getValue());
		}else{
			deviceData.setSnapStatus(DeviceData.snapStatus_fail);
			deviceData.setSnapData(null);
			deviceData.setWarningReason(param.getScreenMessage());
		} 
 		return deviceData;
	}
}
