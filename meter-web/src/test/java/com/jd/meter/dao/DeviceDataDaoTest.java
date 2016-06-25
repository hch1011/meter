package com.jd.meter.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.alibaba.fastjson.JSON;

import com.jd.meter.entity.DeviceData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/context/application-context.xml"})
public class DeviceDataDaoTest {
    @Autowired
    private DeviceDataRepository dao;
    
    @Test
    public void testGetAllDeviceData() {
    	Iterable<DeviceData> iter = dao.findAll();
    	if(iter != null){
    		for (DeviceData data : iter) {
            	System.out.println(JSON.toJSONString(data, true));
            }
    	}
    	
    }

}
