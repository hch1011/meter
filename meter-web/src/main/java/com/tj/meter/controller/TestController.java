package com.tj.meter.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.tj.meter.dao.DeviceInfoDao;
import com.tj.meter.dao.DeviceTypeDao;
import com.tj.meter.entity.CameraInfo;
import com.tj.meter.entity.DeviceData;
import com.tj.meter.entity.DeviceInfo;
import com.tj.meter.service.DeviceService;
import com.tj.meter.ys.sdk.YsClientProxy;
 
@Controller
public class TestController extends BaseController{
	Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Autowired
	DeviceService deviceService;
	@Autowired
	DeviceTypeDao deviceTypeDao;
	@Autowired
	DeviceInfoDao deviceInfoDao;;
	@Autowired
	YsClientProxy ysClientProxy;
	
	
	@RequestMapping(value = "/test/data", method=RequestMethod.GET)
	public String testdataPage(
			@RequestParam(name="type", required = false) Long type,
			Model model
			) {
		if(type != null){
			List<DeviceInfo> deviceInfoList = deviceInfoDao.findByType(type);
			model.addAttribute("deviceInfoList", deviceInfoList);
		}else{
			List<DeviceInfo> deviceInfoList = deviceInfoDao.findAll();
			model.addAttribute("deviceInfoList", deviceInfoList);
		}
		model.addAttribute("type", 7);//前端测试数据选中
		
		List<CameraInfo> deviceList = ysClientProxy.deviceList(0,50);
		model.addAttribute("deviceList", deviceList);//前端测试数据选中
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
	
	@RequestMapping(value = "/ys/capture", method=RequestMethod.GET)
	public Object capture(
 			@RequestParam(name="deviceSerial", required = false) String deviceSerial
			) {
		String url = ysClientProxy.capture(deviceSerial, "1");
		return "redirect:"+url;
	}
} 
