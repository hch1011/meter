package com.jd.meter.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RequestParam;

import com.jd.meter.dao.DeviceDataDao;
import com.jd.meter.entity.DeviceData;
import com.jd.meter.entity.DeviceInfo;
import com.jd.meter.service.DeviceService;

/**
 * Created by Darker on 2016/5/6.
 */
@Controller
public class JsonController extends BaseController{
	
	@Autowired
    private DeviceService  deviceService;
	@Autowired
    private DeviceDataDao  deviceDataDao;

	/**
	 * 提交数据
	 * @param request
	 * @param response
	 * @param deviceData
	 * @return
	 */
	@RequestMapping(value = "/json/deviceData", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Object postDeviceData(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	DeviceData deviceData) {
		deviceService.submitData(deviceData);
		return deviceData;
    }
	/**
	 * 根据id查询deviceData
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/json/deviceData/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Object deviceInfoJson(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	@PathVariable(value="id") Long id
	    	) { 
		return success(deviceDataDao.findOne(id));
    } 
 
	//查询指定type下的deviceInfo列表
	@RequestMapping(value = "/json/type/deviceInfo", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Object deviceInfoListJson(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	@RequestParam(value="type",required=false) Long type
	    	) {
		if(type == null){
			return fail("设备类型为空");
		}
		return success(deviceService.queryDeviceInfoByType(type));
    }
} 
