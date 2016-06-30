package com.jd.meter.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.alibaba.fastjson.JSON;

import com.jd.meter.entity.DeviceData;
import com.jd.meter.entity.DeviceInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/context/application-context.xml"})
public class DeviceInfoDaoTest {
    @Autowired
    private DeviceInfoDao deviceInfoDao;
    
    

    @Test
    public void testCrud() {
    	System.out.println("testCrud() start");
    	//clean
    	
    	DeviceInfo data = new DeviceInfo();
    	data.setId(1L);
    	data.setCode("code");
    	data.setName("name");
    	data.setCreateTime(new Date());
    	data.setUpdateTime(data.getCreateTime());
    	data.setSnapData(1.2F);
    	data.setSnapTime(data.getCreateTime());
    	data.setWarningReason("warningReason");
    	

    	System.out.println(JSON.toJSONString(data));
    	deviceInfoDao.save(data);
    	System.out.println(JSON.toJSONString(data));
    	System.out.println("testCrud() end");

    	//deviceInfoDao.delete(data);
    }
    
    @Test
    public void testGetAllDeviceData() {
    	Iterable<DeviceInfo> iter = deviceInfoDao.findAll();
    	if(iter != null){
    		for (DeviceInfo data : iter) {
            	System.out.println(JSON.toJSONString(data, true));
            }
    	}
    	
    }

}
