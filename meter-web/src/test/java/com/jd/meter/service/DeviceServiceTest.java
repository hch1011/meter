package com.jd.meter.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.jd.meter.entity.DeviceType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/context/application-context.xml"})
public class DeviceServiceTest {
	@Autowired
	DeviceService deviceService;
	@Test
	public void testQueryDataForMonitorPage() {
		List<DeviceType> list = deviceService.queryDataForMonitorPage();
		System.out.println(JSON.toJSONString(list, true));
	}

}
