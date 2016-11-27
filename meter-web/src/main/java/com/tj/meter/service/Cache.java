package com.tj.meter.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.tj.meter.entity.CameraInfo;
import com.tj.meter.entity.DeviceInfo;
import com.tj.meter.entity.DeviceType;

@Service("cache")
public class Cache {
	//@Autowired
	//private MonitorJobService monitorJobService;
	
	 public List<DeviceInfo>  queryDeviceInfoAllOrderByInputNumCache;
	 public long  queryDeviceInfoAllOrderByInputNumCacheExpireTime=0;//findAllDeviceInfoOrderByInputNumCache的实效时间10分钟
	 public Map<Long,DeviceType> deviceTypeCache = Maps.newHashMap();
	 public Map<Long,DeviceInfo> deviceInfoCache = Maps.newHashMap();
	
	 public Map<String,CameraInfo> cameraCache = Maps.newHashMap();		//摄像头列表缓存,key是序列号
	 public long                   cameraCacheExpireTime = 0; 			//摄像头列表缓存过期时间，存1小时
	
	 public Map<String,Long> cameraDeviceInfoCache = Maps.newHashMap();	//摄像头与仪表绑定关系
	 


		public DeviceInfo cache(DeviceInfo info) {
			deviceInfoCache.put(info.getId(), info);
			return info;
		}
		
		public DeviceInfo clean(DeviceInfo info) {
			if(info == null){
				return null;
			}
			deviceInfoCache.remove(info.getId());
			cameraDeviceInfoCache.remove(info.getCameraSerial());
	 		return info;
		}
		
		public void cleanAll(){
			
			queryDeviceInfoAllOrderByInputNumCache = null;;
			queryDeviceInfoAllOrderByInputNumCacheExpireTime=0;//findAllDeviceInfoOrderByInputNumCache的实效时间10分钟
			
			deviceTypeCache = Maps.newHashMap();
			deviceInfoCache = Maps.newHashMap();
			
			cameraCache = Maps.newHashMap();		//摄像头列表缓存,key是序列号
			cameraCacheExpireTime = 0; 			//摄像头列表缓存过期时间，存1小时
			
			cameraDeviceInfoCache = Maps.newHashMap();	//摄像头与仪表绑定关系
			
			//monitorJobService.reloadAllDeviceType();
		}

}
