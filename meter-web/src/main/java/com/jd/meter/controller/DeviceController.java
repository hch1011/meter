package com.jd.meter.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jd.meter.service.ImageCutService;

import org.apache.commons.lang.StringUtils;
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

import com.jd.meter.dao.DeviceTypeDao;
import com.jd.meter.entity.DeviceData;
import com.jd.meter.entity.DeviceInfo;
import com.jd.meter.entity.DeviceType;
import com.jd.meter.service.DeviceService;
import com.jd.meter.util.ExcelTemplate;
import com.jd.meter.util.TimeUtils;

/**
 * Created by Darker on 2016/5/6.
 */
@Controller
public class DeviceController extends BaseController{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	public final static String defaultDateFormat = "yyyy-MM-dd";
	public final static String defaultTimeFormat = "HH:mm:ss";

	@Autowired
	DeviceTypeDao deviceTypeDao;
	@Autowired
    private DeviceService  deviceService;
	@Autowired
	private ImageCutService imageCutService;

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

		model.addAttribute("type", 3);//前端数据查询选中
		return "devicedata";
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
		map.put("deviceInfo", deviceInfo);
		Date snapTime = deviceInfo.getSnapTime();
		String date = TimeUtils.getDateString(snapTime, defaultDateFormat);
		String time = TimeUtils.getDateString(snapTime, defaultTimeFormat);
		map.put("date", date);
		map.put("time", time);
		DeviceData deviceData = deviceService.queryDeviceDataById(deviceInfo.getSnapDataId());
		map.put("deviceData", deviceData);
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
		 
		List<DeviceInfo> list = deviceService.queryDeviceInfoBySnapStatus(new Integer[]{0,2,3});
		
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
			template.createCell(item.getPath());// 商位置
			template.createCell(item.getCode());// 编号
			template.createCell(TimeUtils.getDateString(item.getSnapTime()));//时间
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
			imageCutService.imageRecognition(imagePath, x, y, w, h);
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


	@RequestMapping(value = "/cc", method = RequestMethod.GET)
	@ResponseBody
	public Object clearCacle(
			HttpServletRequest request,
			HttpServletResponse response,
			@PageableDefault(page=0, size=100) Pageable pageable,
			Model model
	) {
		deviceService.cleanAll();
		return success();
	}
	
} 
