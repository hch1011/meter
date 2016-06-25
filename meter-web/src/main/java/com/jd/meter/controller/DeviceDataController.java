package com.jd.meter.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jd.meter.dao.DeviceDataRepository;
import com.jd.meter.entity.DeviceData;

/**
 * Created by Darker on 2016/5/6.
 */
@Controller
@RequestMapping(value = "/device/data")
public class DeviceDataController {
	
	@Autowired
    private DeviceDataRepository deviceDataDao;

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Object preOrder(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	DeviceData deviceData) {

		deviceDataDao.save(deviceData);
			
		return deviceData;
    }

} 
