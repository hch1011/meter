package com.tj.meter.dao;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.tj.meter.entity.DeviceType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/context/application-context.xml"})
public class SystemPropertyDaoTest {
    @Autowired
    private DeviceTypeDao deviceTypeDao;
  
    @Test
    public void findByMonitorPageFlagOrderByMonitorPageSort() {
    	System.out.println("findByMonitorPageFlagOrderByMonitorPageSort() start");
    	List<DeviceType> list = deviceTypeDao.findByMonitorPageFlagOrderByMonitorPageSort(1);
    	System.out.println(JSON.toJSONString(list, true));
    	System.out.println("findByMonitorPageFlagOrderByMonitorPageSort() end");
    	
    }
    
    
    @Test
    public void testGetAllDeviceData() {
    	System.out.println("testGetAllDeviceData() start");
    	Iterable<DeviceType> iter = deviceTypeDao.findAll();
    	System.out.println(JSON.toJSONString(iter, true));
    	System.out.println("testGetAllDeviceData() end");
    	
    }

}
