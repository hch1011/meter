package com.tj.meter.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tj.meter.dao.DeviceDataDao;
import com.tj.meter.entity.DeviceData;
import com.tj.meter.service.DeviceService;

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
