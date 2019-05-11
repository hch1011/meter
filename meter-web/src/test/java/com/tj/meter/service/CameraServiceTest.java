package com.tj.meter.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tj.meter.entity.CameraCaptureVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/context/application-context.xml"})
public class CameraServiceTest {
	@Autowired
	CameraService cameraService;
	
	@Test
	public void testQueryDataForMonitorPage() {
		CameraCaptureVo param = new CameraCaptureVo();
		cameraService.captureHandle(param);
		 
	}

}
