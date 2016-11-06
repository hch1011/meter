package com.jd.meter.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jd.meter.dao.DeviceDataDao;
import com.jd.meter.dao.DeviceInfoDao;
import com.jd.meter.dao.DeviceTypeDao;
import com.jd.meter.entity.CameraInfo;
import com.jd.meter.entity.DeviceData;
import com.jd.meter.entity.DeviceInfo;
import com.jd.meter.entity.DeviceType;
import com.jd.meter.exception.MeterExceptionFactory;
import com.jd.meter.util.ObjectUtil;
import com.jd.meter.util.SnowflakeIdGenerator;
import com.jd.meter.ys.sdk.YsClientProxy;

@Service("deviceService")
public class DeviceService {
	private static Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
	
	@Value("${meter.centerServer.syncdata:false}")
	private boolean syncdata = false;
	@Autowired
	private DeviceTypeDao deviceTypeDao;
	@Autowired
	private DeviceInfoDao deviceInfoDao;
	@Autowired
	private DeviceDataDao deviceDataDao;
	@Autowired
	private YsClientProxy ysClientProxy;
	 
	private List<DeviceInfo>  queryDeviceInfoAllOrderByInputNumCache;
	private long  queryDeviceInfoAllOrderByInputNumCacheExpireTime=0;//findAllDeviceInfoOrderByInputNumCache的实效时间10分钟
	private Map<Long,DeviceType> deviceTypeCache = Maps.newHashMap();
	private Map<Long,DeviceInfo> deviceInfoCache = Maps.newHashMap();
	
	private Map<String,CameraInfo> cameraCache = Maps.newHashMap();		//摄像头列表缓存,key是序列号
	private long                   cameraCacheExpireTime = 0; 			//摄像头列表缓存过期时间，存1小时
	
	private Map<String,Long> cameraDeviceInfoCache = Maps.newHashMap();	//摄像头与仪表绑定关系
	
	
	//CacheBuilder<Object, Object> deviceInfoCache = CacheBuilder.newBuilder().expireAfterWrite(120, TimeUnit.SECONDS);
	
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
 
	public List<DeviceInfo> queryDeviceInfoAllOrderByInputNum(){
		if(System.currentTimeMillis() > queryDeviceInfoAllOrderByInputNumCacheExpireTime){
			queryDeviceInfoAllOrderByInputNumCache = null;
		}
		if(queryDeviceInfoAllOrderByInputNumCache == null){
			queryDeviceInfoAllOrderByInputNumCache = deviceInfoDao.findAllDeviceInfoOrderByInputNum();
			queryDeviceInfoAllOrderByInputNumCacheExpireTime = System.currentTimeMillis() + 600 * 1000;
		}
		
		 return queryDeviceInfoAllOrderByInputNumCache;
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
		DeviceInfo info = deviceInfoCache.get(deviceInfoId);
		if(useCache && info != null){
			return info;
		} 
		info = deviceInfoDao.findOne(deviceInfoId);
 		return cache(info);
	}

	public List<DeviceInfo> queryDeviceInfoBySnapStatus(Integer[] snapStatus) {
		List<DeviceInfo> list = deviceInfoDao.findBySnapStatusIn(snapStatus);
		for (DeviceInfo deviceInfo : list) {
			deviceInfo.setDeviceType(this.queryDeviceTypeByType(deviceInfo.getType(), true));
		}
		
		return list;
	}
	
	private DeviceInfo queryDeviceInfoByCameraSerial(String deviceSerial) {
		DeviceInfo info = queryDeviceInfoById(cameraDeviceInfoCache.get(deviceSerial), true);
		if(info == null){
			List<DeviceInfo> list = deviceInfoDao.findByCameraSerial(deviceSerial);
			if(list.size() > 1){
				info = list.get(0);
				cache(info);
			}
		}
		return info;
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

	public void bindCamera(Long deviceInfoId, String cameraSerialId, boolean force) {
		DeviceInfo deviceInfo = deviceInfoDao.findOne(deviceInfoId);
		deviceInfo.setCameraSerial(cameraSerialId);
		deviceInfo.setUpdateTime(new Date());
		deviceInfo.setCameraBindTime(deviceInfo.getUpdateTime());
		deviceInfoDao.save(deviceInfo);
		
		clean(deviceInfo);
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
		cache(deviceInfo);
	}
	


	public CameraInfo queryCameraById(String deviceSerial, boolean withDeviceInfo) {
		CameraInfo info = cameraCache.get(deviceSerial);
		if(info == null){
			loadAllCameraListFromSDK();
			info = cameraCache.get(deviceSerial);
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
		if(cameraCacheExpireTime < currentTime ){
			try {
				// 重新加载摄像头列表
				loadAllCameraListFromSDK();
			} catch (Exception e) {
				// 如果获取列表失败，原数据再可用5分钟
				cameraCacheExpireTime = System.currentTimeMillis() + 5 * 60 *1000L;
			}
		}
		ArrayList<CameraInfo> list = Lists.newArrayList(cameraCache.values());
		 
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
		
		cameraCache = tmpMap;
		cameraCacheExpireTime = System.currentTimeMillis() + 3600 * 1000L;
		return tmpMap;
	}

	public void bindCameraToDeviceInfo(String cameraSerial, Long deviceInfoId){
		queryDeviceInfoByCameraSerial(cameraSerial);
		
		// 解绑之前的摄像头
		List<DeviceInfo> list = deviceInfoDao.findByCameraSerial(cameraSerial);
		for(DeviceInfo info : list){
			info.setCameraSerial(null);
			deviceInfoDao.save(info);
			clean(info);
		}
		//绑定现在的摄像头
		DeviceInfo info = deviceInfoDao.findOne(deviceInfoId);
		clean(info);
		
		info.setUpdateTime(new Date());
		info.setCameraBindTime(info.getUpdateTime());
		info.setCameraSerial(cameraSerial);
		deviceInfoDao.save(info);
		cache(info);
	}
	
	private DeviceInfo cache(DeviceInfo info) {
		if(info == null){
			return null;
		}
		deviceInfoCache.put(info.getId(), info);
		cameraDeviceInfoCache.put(info.getCameraSerial(), info.getId());
 		return info;
	}
	
	private DeviceInfo clean(DeviceInfo info) {
		if(info == null){
			return null;
		}
		deviceInfoCache.remove(info.getId());
		cameraDeviceInfoCache.remove(info.getCameraSerial());
 		return info;
	}
	
	public void cleanAll(){
		deviceTypeCache = Maps.newHashMap();
		deviceInfoCache = Maps.newHashMap();
		cameraCache = Maps.newHashMap();
		cameraDeviceInfoCache = Maps.newHashMap();
		queryDeviceInfoAllOrderByInputNumCacheExpireTime = 0;
	}
}
