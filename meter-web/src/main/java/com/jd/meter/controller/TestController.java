package com.jd.meter.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jd.meter.dao.DeviceInfoDao;
import com.jd.meter.dao.DeviceTypeDao;
import com.jd.meter.entity.DeviceData;
import com.jd.meter.entity.DeviceInfo;
import com.jd.meter.entity.DeviceType;
import com.jd.meter.service.DeviceService;
 
@Controller
public class TestController {
	Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Autowired
	DeviceService deviceService;
	@Autowired
	DeviceTypeDao deviceTypeDao;
	@Autowired
	DeviceInfoDao deviceInfoDao;
	
	@RequestMapping(value = "/test/data", method=RequestMethod.GET)
	public String testdataPage(
			@RequestParam(name="type", required = false) Long type,
			Model model
			) {
//		List<DeviceType> deviceTypeList = deviceTypeDao.findAll();
//		model.addAttribute("deviceTypeList", deviceTypeList);
		if(type != null){
			List<DeviceInfo> deviceInfoList = deviceInfoDao.findByType(type);
			model.addAttribute("deviceInfoList", deviceInfoList);
		}else{
			List<DeviceInfo> deviceInfoList = deviceInfoDao.findAll();
			model.addAttribute("deviceInfoList", deviceInfoList);
		}
		
		return "test/data";
	}
	

	@RequestMapping(value = "/test/data", method=RequestMethod.POST)	
	public String testdataCreate(
			DeviceData deviceData,
			Model model
			) {
		deviceData.setDataType(1);
		deviceData.setSnapTime(new Date());
		deviceData.setCreateTime(deviceData.getSnapTime());
		deviceData.setUpdateTime(deviceData.getSnapTime());
		
		deviceService.submitData(deviceData);
		return testdataPage(null,model);
	}
} 
