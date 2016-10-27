package com.jd.meter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jd.meter.entity.CameraInfo;
import com.jd.meter.entity.DeviceInfo;
import com.jd.meter.exception.MeterExceptionFactory;
import com.jd.meter.service.DeviceService;
import com.jd.meter.ys.sdk.YsClientProxy;

/**
 * 摄像头管理
 */
@Controller
public class CameraController extends BaseController{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	public final static String defaultDateFormat = "yyyy-MM-dd";
	public final static String defaultTimeFormat = "HH:mm:ss";

	@Autowired
	YsClientProxy ysClientProxy;
	@Autowired
	DeviceService deviceService;

	/**
	 * 所有摄像头
	 */
	@RequestMapping(value = "/camera/list", method = RequestMethod.GET)
	public String camereList(
			HttpServletRequest request,
			HttpServletResponse response,
			@PageableDefault(page=0, size=100) Pageable pageable,
			Model model
	) {
		deviceService.queryCameraList(pageable, true);
		Page<CameraInfo> page = ysClientProxy.cameraList(pageable);
		model.addAttribute("page", page);
		return "camera/list";
	}

	/**
	 * 所有摄像头
	 */
	@RequestMapping(value = "/camera/setting", method = RequestMethod.GET)
	public String camereSetting(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="cameraSerial", required = false) String cameraSerial,
			Model model
	) {
		CameraInfo camera = deviceService.queryCameraById(cameraSerial, true);
		if(camera == null){
			return "camera/setting";
		}
		
		List<Map<Integer,List<DeviceInfo> >> deviceInfoListByInputNum = deviceService.queryDeviceInfoGroupByInputNum();
		List<DeviceInfo> list = new ArrayList<>();
		for(Map<Integer,List<DeviceInfo> > item : deviceInfoListByInputNum){
			for(Integer key:item.keySet()){
				list.addAll(item.get(key));
			}
		}
		
		model.addAttribute("deviceInfoList", list);
		model.addAttribute("camera", camera);
		return "camera/setting";
	}


	/**
	 * 摄像头绑定到设备
	 * DeviceInfo.
	 */
	@RequestMapping(value = "/camera/bind", method = RequestMethod.POST)
	@ResponseBody
	public Object camereBind(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="deviceInfoId",required=true)  Long deviceInfoId,
			@RequestParam(value="cameraSerial",required=true)  String cameraSerial,
			@RequestParam(value="force",required=false, defaultValue="false") boolean force
	) { 
		try {
			deviceService.bindCamera(deviceInfoId, cameraSerial,force);
		} catch (Exception e) {
			return fail(e.getMessage());
		}

		return success();
	}
	
	/**
	 * 更新图片切割范围
	 * 参数,device
	 * DeviceInfo.
	 */
	@RequestMapping(value = "/camera/preview", method = RequestMethod.GET)
	@ResponseBody
	public Object camerePreview(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="cameraSerial",required=true)  String cameraSerial
	) { 
		//测试数据
		if(cameraSerial.startsWith("virtual_")){
			int i=1;
			try {
				i = Integer.parseInt(cameraSerial.substring(8))%2+1;
			} catch (Exception e) {
				// TODO: handle exception
			}
			return success("/meter/resources/images/demo/"+i+".jpg"); 
		}
		
		try {
			CameraInfo camera = deviceService.queryCameraById(cameraSerial, true);
			if(camera == null){
				throw MeterExceptionFactory.applicationException("摄像头未找到:"+cameraSerial, null);
			}

			String url = ysClientProxy.capture(camera.getDeviceSerial(), camera.getChannelNo());
			return success(url);
		} catch (Exception e) {
			return fail(e.getMessage());
		}
	}
	
	/**
	 * 更新图片切割范围
	 * 参数,device
	 * DeviceInfo.
	 */
	@RequestMapping(value = "/camera/range", method = RequestMethod.POST)
	@ResponseBody
	public Object camereReSetRange(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="deviceInfoId",required=true)  Long deviceInfoId,
			@RequestParam(value="x",required=true)  Integer x,
			@RequestParam(value="y",required=true)  Integer y,
			@RequestParam(value="w",required=true)  Integer w,
			@RequestParam(value="h",required=true)  Integer h
	) { 
		try {
			deviceService.reSetCutRange(deviceInfoId,x,y,w,h);
		} catch (Exception e) {
			return fail(e.getMessage());
		}
		return success();
	}
} 
