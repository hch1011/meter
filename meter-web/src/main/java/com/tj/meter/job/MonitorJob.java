package com.tj.meter.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tj.meter.exception.MeterException;
import com.tj.meter.service.DeviceService;
import com.tj.meter.service.MonitorJobService;

/**
 * 每分钟运行一次
 * 
 * @author hc
 */
@Component("monitorJob")
public class MonitorJob {
	private static Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
	
	@Autowired
	private MonitorJobService monitorJobService;
	@Value("${meter.monitorJob.running}")
	private boolean running;

	public void process() {
		LOGGER.info("MonitorJob.process() start");
		if(running){
			try {
				monitorJobService.scanDevice();
			} catch (MeterException e) {
				LOGGER.info(e.getScreenMessage() + "\n" + e.getDebugMessage());
			} catch (Exception e) {
				LOGGER.info(e.getMessage(), e);
			}
		}else{
			LOGGER.info("running=false, skip this job");
		}
		
	}
}
