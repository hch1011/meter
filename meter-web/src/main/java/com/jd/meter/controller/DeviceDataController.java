package com.jd.meter.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jd.meter.dao.DeviceTypeDao;
import com.jd.meter.entity.DeviceData;
import com.jd.meter.entity.DeviceInfo;
import com.jd.meter.entity.DeviceType;
import com.jd.meter.service.DeviceService;

/**
 * Created by Darker on 2016/5/6.
 */
@Controller
public class DeviceDataController extends BaseController{

	@Autowired
	DeviceTypeDao deviceTypeDao;
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
	
	//参数设置	
	@RequestMapping(value = "/device/data/param", method = RequestMethod.GET)
	public String paramPage(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	@RequestParam(required=false) Long type,
	    	Model model
	    	) {
		List<DeviceType> list = deviceTypeDao.findAll();
		model.addAttribute("deviceTypeList", list);

		if(type != null){
			for(DeviceType item : list){
				if(type.equals(item.getType())){
					model.addAttribute("currentDeviceType", item);
				}
			}
		}else{
			model.addAttribute("currentDeviceType", list.get(0));
		}

		return "param";
    }
	
	//参数设置	
	@RequestMapping(value = "/device/data/param", method = RequestMethod.POST)
	public String paramUpdate(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	@RequestParam(required=false) Long type,
	    	DeviceType deviceType,
	    	Model model
	    	) {
		DeviceType deviceTypeDB = deviceTypeDao.findOne(deviceType.getType());
		//
		if(deviceType.getDataForWarning() != null){
			deviceTypeDB.setDataForWarning(deviceType.getDataForWarning());
		}
		if(deviceType.getDataForAlarm() != null){
			deviceTypeDB.setDataForAlarm(deviceType.getDataForAlarm());
		}
		//
		if(deviceType.getChangeRateForWarning()  != null){
			deviceTypeDB.setChangeRateForWarning(deviceType.getChangeRateForWarning());
		}		
		if(deviceType.getChangeRateForAlarm() != null){
			deviceTypeDB.setChangeRateForAlarm(deviceType.getChangeRateForAlarm());
		}
		
		if(deviceType.getFrequencyForWarning()  != null){
			deviceTypeDB.setFrequencyForWarning(deviceType.getFrequencyForWarning());
		}
		if(deviceType.getFrequencyForAlarm()  != null){
			deviceTypeDB.setFrequencyForAlarm(deviceType.getFrequencyForAlarm());
		}
		
		if(StringUtils.isNotBlank(deviceType.getSnapTimes())){
			deviceTypeDB.setSnapTimes(deviceType.getSnapTimes());
		}
		
		deviceTypeDao.save(deviceTypeDB);

		return paramPage( request, response, deviceType.getType(), model );
    }
	
	
	@RequestMapping(value = "/device/data/error/report", method = RequestMethod.GET)
	public String errorInfo(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	@RequestParam(required=false)  Integer[] status,
	    	Model model
	    	) {
		if(status == null || status.length == 0){
			status = new Integer[]{0,2,3};
		}
		List<DeviceInfo> list = deviceService.queryDeviceInfoBySnapStatus(status);
		model.addAttribute("list", list);
		return "errorreport";
    }
	
	@RequestMapping(value = "/device/data/error/reportExcel", method = RequestMethod.GET)
	public void errorExcel(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	@RequestParam(required=false)  Integer[] status
 	    	) {
		if(status == null || status.length == 0){
			status = new Integer[]{0,2,3};
		}
		
		
     } 
} 
