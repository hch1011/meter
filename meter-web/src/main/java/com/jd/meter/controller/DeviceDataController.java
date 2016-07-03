package com.jd.meter.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jd.meter.entity.DeviceData;
import com.jd.meter.service.DeviceService;

/**
 * Created by Darker on 2016/5/6.
 */
@Controller
public class DeviceDataController {
	
	@Autowired
    private DeviceService  deviceService;

	/**
	 * 提交数据
	 * @param request
	 * @param response
	 * @param deviceData
	 * @return
	 */
	@RequestMapping(value = "/device/data", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Object postDeviceData(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	DeviceData deviceData) {
		deviceService.submitData(deviceData);
		return deviceData;
    }

	@RequestMapping(value = "/device/data/info", method = RequestMethod.GET)
	public Object getDeviceData(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	Model model) {
			
		return "devicedata";
    }
	 
	@RequestMapping(value = "/device/data/img/info", method = RequestMethod.GET)
	public String imgInfo(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	Long deviceId) {

		return "imginfo";
    }
	
	@RequestMapping(value = "/device/data/param", method = RequestMethod.GET)
	public String param(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	Long deviceId) {

		return "param";
    }
	
	@RequestMapping(value = "/device/data/error/report", method = RequestMethod.GET)
	public String errorInfo(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	Long deviceId) {

		return "errorreport";
    }

} 
