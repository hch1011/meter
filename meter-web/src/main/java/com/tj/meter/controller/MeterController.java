package com.tj.meter.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tj.meter.entity.CameraInfo;
import com.tj.meter.entity.DeviceData;
import com.tj.meter.entity.DeviceInfo;
import com.tj.meter.entity.DeviceType;
import com.tj.meter.entity.SystemAccount;
import com.tj.meter.exception.MeterExceptionFactory;
import com.tj.meter.util.ExcelTemplate;
import com.tj.meter.util.ImageUtils;
import com.tj.meter.util.MultiValue;
import com.tj.meter.util.TimeUtils;
import com.tj.meter.ys.sdk.YsClientProxy;
import com.google.common.collect.Lists;
import com.tj.meter.dao.DeviceTypeDao;
import com.tj.meter.service.Cache;
import com.tj.meter.service.DeviceService;
import com.tj.meter.service.MonitorJobService;

/**
 * Created by Darker on 2016/5/6.
 */
@Controller
public class MeterController extends BaseController{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	public final static String defaultDateFormat = "yyyy-MM-dd";
	public final static String defaultTimeFormat = "HH:mm:ss";

	@Autowired
	DeviceTypeDao deviceTypeDao;
	@Autowired
    DeviceService  deviceService;
	@Autowired
	MonitorJobService monitorJobService;
	@Autowired
	YsClientProxy ysClientProxy;
	@Autowired
	Cache cache;
	
	
	@RequestMapping(value = "/admin/createDevice", method = RequestMethod.POST)
	@ResponseBody
	public Object createDeviceInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			DeviceInfo deviceInfo,
			Model model
	) {
		try {
			deviceInfo = deviceService.createDeviceInfo(deviceInfo);
			return success(deviceInfo);
		} catch (Exception e) {
			return fail(e);
		}
	}

	@RequestMapping(value = "/admin/updateDevice", method = RequestMethod.POST)
	@ResponseBody
	public Object updateDeviceInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			DeviceInfo deviceInfo,
			Model model
	) {
		try {
			deviceInfo = deviceService.updateDeviceInfo(deviceInfo);
			return success(deviceInfo);
		} catch (Exception e) {
			return fail(e);
		}
	}

	@RequestMapping(value = "/admin/deleteDevice", method = RequestMethod.DELETE)
	@ResponseBody
	public Object updateDeviceInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			Long id,
			Model model
	) {
		try {
			deviceService.deleteDeviceInfo(id);
			return success();
		} catch (Exception e) {
			return fail(e);
		}
	}
	
	@RequestMapping(value = "/admin/device/list", method = RequestMethod.GET)
 	public Object meterList(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	@RequestParam(value="deviceType", required = false) Long deviceType,
	    	Model model
	    	) {
		Collection<DeviceType> deviceTypeList = deviceService.queryAllDeviceType(true);
		if(deviceType == null){
			deviceType = 1L;
		}
		List<DeviceInfo> deviceInfoList = deviceService.queryDeviceInfoByType(deviceType);
		long maxId = 0;
		for(DeviceInfo item : deviceInfoList){
			if(item.getId() > maxId){
				maxId = item.getId();
			}
		}
		
		model.addAttribute("deviceTypeList", deviceTypeList);
		model.addAttribute("deviceInfoList", deviceInfoList);
		model.addAttribute("currentDeviceType", deviceType);
		model.addAttribute("maxId", ++maxId);
		return "device/list";
    }
	
	
	
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
		deviceData.setDataType(DeviceData.dataType_manualEdit);
		deviceService.submitData(deviceData);
		
		return deviceData;
    }

	@RequestMapping(value = "/device/data/info", method = RequestMethod.GET)
	public Object getDeviceDataPage(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	@RequestParam(value="beginDate", required = false) String beginDate,
	    	@RequestParam(value="beginTime", required = false) String beginTime, 
	    	@RequestParam(value="endTime", required = false) String endDate,
	    	@RequestParam(value="toTime", required = false) String endTime,
	    	@RequestParam(value="deviceId", required = false) String deviceId,
	    	Model model) {
		Date currentDate = new Date();
		if(StringUtils.isBlank(endDate)){
			endDate = TimeUtils.getDateString(currentDate, "yyyy-MM-dd");
		}
		if(StringUtils.isBlank(endTime)){
			//endTime = TimeUtils.getDateString(currentDate, "HH:mm:ss");
			endTime = "23:59:59";
		}
		Date toDateTime = TimeUtils.getDate(endDate + " " + endTime, "yyyy-MM-dd HH:mm:ss");

		if(StringUtils.isBlank(beginDate)){
			beginDate = TimeUtils.getDateString(TimeUtils.add(toDateTime, null, null, -14), "yyyy-MM-dd");
		}
		if(StringUtils.isBlank(beginTime)){
			beginTime = "00:00:00";
		} 

		List<DeviceInfo>  deviceList = deviceService.queryDeviceInfoAllOrderByInputNum();
		
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endDate", endDate);
		model.addAttribute("endTime", endTime);
		model.addAttribute("deviceId", deviceId);
		model.addAttribute("deviceList", deviceList);

		model.addAttribute("type", 3);//前端数据查询选中
		return "devicedata";
    }
	
	@RequestMapping(value = "/device/data/info/json", method = RequestMethod.GET)
	@ResponseBody
	public Object getDeviceDataJson(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	@RequestParam(value="beginDate", required = false) String beginDate,
	    	@RequestParam(value="beginTime", required = false) String beginTime, 
	    	@RequestParam(value="endDate", required = false) String endDate,
	    	@RequestParam(value="endTime", required = false) String endTime,
	    	@RequestParam(value="deviceId", required = false) Long deviceId,
	    	Model model) {
		Date currentDate = new Date();
		if(StringUtils.isBlank(endDate)){
			endDate = TimeUtils.getDateString(currentDate, "yyyy-MM-dd");
		}
		if(StringUtils.isBlank(endTime)){
			endTime = TimeUtils.getDateString(currentDate, "HH:mm:ss");
		}
		Date toDateTime = TimeUtils.getDate(endDate + " " + endTime, "yyyy-MM-dd HH:mm:ss");

		if(StringUtils.isBlank(beginDate)){
			beginDate = TimeUtils.getDateString(TimeUtils.add(toDateTime, null, -1, 0), "yyyy-MM-dd");
		}
		if(StringUtils.isBlank(beginTime)){
			beginTime = "00:00:00";
		}
		Date fromDateTime = TimeUtils.getDate(beginDate + " " + beginTime, "yyyy-MM-dd HH:mm:ss");
		 
		if(deviceId == null){
			return success();
		}
		
		List<DeviceData> list = deviceService.queryDeviceDataListByRange(deviceId, fromDateTime, toDateTime, null);
		Map<String, Object> rt = success();
		List<MultiValue> rtList = Lists.newArrayList(); 
 		for(DeviceData data : list){
			rtList.add(
					new MultiValue()
					.setO1(data.getId())
					.setO2(TimeUtils.getDateString(data.getSnapTime()))
					.setO3(data.getSnapData() == null? 0 : data.getSnapData())
					); 
		}
 		DeviceInfo device = deviceService.queryDeviceInfoById(deviceId, true);
 		if(device != null){
 			DeviceType type = deviceService.queryDeviceTypeByType(device.getType(), true);
 			if(type!=null){
 				rt.put("deviceType",type);
 			}
 		}
		rt.put("list", rtList);
		rt.put("deviceId", deviceId);
		model.addAttribute("type", 3);//前端数据查询选中
		return rt;
    }
	 
	@RequestMapping(value = "/device/data/img/info", method = RequestMethod.GET)
	public String imgInfo(
			HttpServletRequest request,
	    	HttpServletResponse response,
			Model model,
	    	@RequestParam(value="deviceId", required = false)Long deviceId) {

		try{
			if(deviceId != null) {
				model.addAttribute("deviceInfo", deviceService.queryDeviceInfoById(deviceId, true));
			}
			List<Map<Integer,List<DeviceInfo>>> resultList = deviceService.queryDeviceInfoGroupByInputNum();
			model.addAttribute("deviceInfoList", resultList);
			model.addAttribute("deviceId", deviceId);
			model.addAttribute("type", 2);//前端图像采集选中
		}catch (Exception e) {
			logger.info("获取图像采集信息出错", e);
		}
		return "imginfo";
    }

	@RequestMapping(value = "/device/data/img/info/one", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Object getImgInfoById(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="deviceId", required = true)Long deviceId) {
		DeviceInfo deviceInfo = deviceService.queryDeviceInfoById(deviceId, true);
		Map<String, Object> map = new HashMap<String, Object>();
		String date = TimeUtils.getDateString(deviceInfo.getSnapTime(), defaultDateFormat);
		String time = TimeUtils.getDateString(deviceInfo.getSnapTime(), defaultTimeFormat);
		DeviceType deviceType = deviceService.queryDeviceTypeByType(deviceInfo.getType(), true);
		if(StringUtils.isBlank(deviceInfo.getPictureLocalPath())){
			try {
				CameraInfo camera = deviceService.queryCameraById(deviceInfo.getCameraSerial(), true);
				String url = ysClientProxy.capture(camera.getDeviceSerial(), camera.getChannelNo());
				camera.setLastSnapshotUrl(url);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		map.put("deviceInfo", deviceInfo);
		map.put("deviceType", deviceType);
		map.put("date", date);
		map.put("time", time);
		//map.put("deviceData", deviceData);
		return map;
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
		model.addAttribute("type", 4);//前端参数配置选中
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

		//监控中心更新
		monitorJobService.reloadDeviceType(deviceTypeDB);
		return paramPage( request, response, deviceType.getType(), model );
    }
	
	
	@RequestMapping(value = "/device/data/error/report", method = RequestMethod.GET)
	public String report(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	@RequestParam(required=false)  Integer[] status,
	    	Model model
	    	) {
		if(status == null || status.length == 0){
			status = new Integer[]{0,1,2,3};
		}
		List<DeviceInfo> list = deviceService.queryDeviceInfoBySnapStatus(status);
		model.addAttribute("list", list);
		model.addAttribute("type", 5);//前端错误报告选中
		return "errorreport";
    }
	
	@RequestMapping(value = "/device/data/error/reportExcel", method = RequestMethod.GET)
	public String reportExcel(
			HttpServletRequest request,
	    	HttpServletResponse response
	    	) throws IOException {
		response.setHeader("Content-Disposition", "attachment; filename=\"report_" + 
	    	TimeUtils.getDateString(new Date(),"yyyyMMddHHmmss") + ".xlsx\"");
		
		OutputStream out = response.getOutputStream();

		ExcelTemplate template = new ExcelTemplate();
		template.readTemplateByClasspath("/excelTemplate/exportingTemplate.xlsx");
		 
		List<DeviceInfo> list = deviceService.queryDeviceInfoBySnapStatus(new Integer[]{0,1,2,3});
		
 		int orderNum = 1;			//订单序号
 		DeviceType type;
		for (DeviceInfo item :  list) {
			template.createNewRow();
			template.createCell(orderNum++);//序号
			 type = deviceService.queryDeviceTypeByType(item.getType(),true);
			 if(type!=null){
				 template.createCell(type.getTypeName());// 类别
			 }else{
				 template.createCell(item.getType());// 类别
			 }
			
			template.createCell(item.getName());// 名称
			template.createCell(item.getPath());// 位置
			template.createCell(TimeUtils.getDateString(item.getSnapTime()));//时间
			template.createCell(item.getSnapDataScreen());// 值
			template.createCell(item.getSnapStatusCn());// 状态
			template.setCellStyle(null, "status_"+item.getSnapStatus()+"_Style");
			template.createCell(item.getWarningReason());// 故障类型
			template.setCellStyle(null, "status_"+item.getSnapStatus()+"_Style");
			
			template.createCell(" ");// 结束空白符
			template.setCellStyle(null, "end_cell_Style");
		}
		template.wirteToStream(out);
	    out.flush();
		return null;
    }

	@RequestMapping(value = "/device/data/img/recognition", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Object imgRecognition(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="imagePath", required = true)String imagePath,
			@RequestParam(value="x", required = true)int x,
			@RequestParam(value="y", required = true)int y,
			@RequestParam(value="w", required = true)int w,
			@RequestParam(value="h", required = true)int h
	) {
		Map<String, Object> map = new HashMap<>();
		try{
			ImageUtils.cutImage(imagePath, imagePath, x, y, w, h);
			map.put("success", true);
		} catch (IOException e) {
			logger.error("image recognition failed");
			map.put("success", false);
		} catch (Exception e) {
			logger.error("image recognition failed");
			map.put("success", false);
		}

		return map;
	}

	
	/**
	 * 清空缓存
	 * @param request
	 * @param Response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cc", method = RequestMethod.GET)
	@ResponseBody
	public Object clearCacle(
			HttpServletRequest request,
			HttpServletResponse response,
			@PageableDefault(page=0, size=100) Pageable pageable,
			Model model
	) {
		cache.cleanAll();
		return success();
	}
	
} 
