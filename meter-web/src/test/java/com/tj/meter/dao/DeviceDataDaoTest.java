package com.tj.meter.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.alibaba.fastjson.JSON;

import com.tj.meter.entity.DeviceData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/context/application-context.xml"})
public class DeviceDataDaoTest {
    @Autowired
    private DeviceDataDao deviceDataDao;
    
    @Test
    public void testCrud() {
    	System.out.println("testCrud() start");
    	DeviceData data = new DeviceData();
    	data.setId(1L);
    	data.setCreateTime(new Date());
    	data.setDataType(1);
    	data.setDeviceCode("deviceCode");
    	data.setDeviceId(1L);
    	data.setMetaData("metaData");
    	data.setPictureUrl("pictureUrl");
    	data.setSnapData(1.2F);
    	//data.setSnapStatus(1);
    	data.setSnapTime(data.getCreateTime());
    	data.setWarningReason("warningReason");
    	

    	System.out.println(JSON.toJSONString(data));
		deviceDataDao.save(data);
    	System.out.println(JSON.toJSONString(data));
    	DeviceData db = deviceDataDao.findOne(1L);
    	System.out.println("db:"+JSON.toJSONString(db));
    	System.out.println("testCrud() end");
    }
    
    @Test
    public void testGetAllDeviceData() {
    	Iterable<DeviceData> iter = deviceDataDao.findAll();
    	if(iter != null){
    		for (DeviceData data : iter) {
            	System.out.println(JSON.toJSONString(data, true));
            }
    	}
    	
    }

}
