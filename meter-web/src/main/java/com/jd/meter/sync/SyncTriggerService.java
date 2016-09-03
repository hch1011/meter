package com.jd.meter.sync;

import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.meter.entity.DeviceData;
import com.jd.meter.service.DeviceService;
import com.jd.meter.sync.client.BioClient;
import com.jd.meter.sync.client.BioClientUtils;
import com.jd.meter.sync.pkg.SyncResponsePackage;
import com.jd.meter.util.ObjectUtil;

/**
 * 
 * 数据同步触发器服务,目前主要同步DeviceData
 * 1 服务启动时扫描一次数据库
 * 2 每次有数据录入，同步当前数据，然后在扫描一次数据库
 * 
 */
@Service
public class SyncTriggerService {
	private static Logger LOGGER = LoggerFactory.getLogger(SyncTriggerService.class);
	@Autowired
	private DeviceService deviceService;
	private BioClient client;

	private BlockingQueue<DeviceData> queue = new LinkedBlockingQueue<DeviceData>(20);  
	SyncDataThread syncDataThread;
 	 
	/**
	 * 扫描数据库,
	 * 如果队列有数据休眠10秒
	 */
	public void scanDatabase(){
		int selectSize = 1;
		while(selectSize > 0){
			while(queue.size() > 0){
				ObjectUtil.sleep(10*1000, true);
			}
			List<DeviceData> list = deviceService.queryWaitingSync(100);
			selectSize = list.size();
			for (DeviceData item : list) {
				sendData(item);
			}
		}
	}
	// 异步发送数据
	public void sendDataSync(DeviceData data){
		boolean rt = queue.offer(data);
		LOGGER.info("sendDataSync()#queue.offer(data)="+rt+",queueSize="+queue.size());
		startSyncDataThread();
	}
	
	public void sendData(DeviceData data){
		LOGGER.info("sendData() start"+ data.getId()); 
		try {
			refrashBioClient();
			data.setSyncSuccessTime(new Date());
			SyncResponsePackage rt = client.write(data);
			LOGGER.info("syncData() response.code="+ rt.getCode()); 
			// 同步成功
			if(rt.getCode() == SyncResponsePackage.CODE_OK){
				deviceService.updateDeviceDataForSyncSuccess(data);
			}
		} catch (Exception e) {
			 data.incSincCount();
			 LOGGER.info("sendData() error and sendDataSync(),syncCount="+data.getSincCount());
			 client = null;
			 if(data.getSincCount() < 30){
				 ObjectUtil.sleep(1000, true);
				 sendDataSync(data);
			 }
			 
		}
	}

	public BioClient refrashBioClient(){
		if(client == null){
			client = BioClientUtils.getCenterServerClient();
		}
		return client ;
	}
	
	public void startSyncDataThread(){
		if(syncDataThread == null){
			syncDataThread = new SyncDataThread(this);
			syncDataThread.start();
			LOGGER.debug("syncDataThread.start()");
		}
	}
	
	class SyncDataThread extends Thread{
		BlockingQueue<DeviceData> _queue = null;
		private volatile boolean running = true;
		SyncTriggerService service;
		
		public SyncDataThread(SyncTriggerService service){
			this.service = service;
			this._queue = service.queue;
			this.setDaemon(true);
		}
		
		@Override
		public void run() {
			while(running){
				//移除并返问队列头部的元素    如果队列为空，则返回null
				try {
					DeviceData data = _queue.poll(20, TimeUnit.SECONDS);
					if(data == null){
						running = false;
						syncDataThread = null;
						return;
					}
					service.sendData(data);					
				} catch (InterruptedException e) {
					running = false;
					syncDataThread = null;
					LOGGER.error("SyncDataThread error:"+e.getMessage(), e);
				}
			}
			
			
		}
	}
	
}
