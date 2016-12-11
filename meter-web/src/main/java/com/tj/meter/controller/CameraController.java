package com.tj.meter.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tj.meter.entity.CameraCaptureVo;
import com.tj.meter.entity.CameraInfo;
import com.tj.meter.entity.DeviceInfo;
import com.tj.meter.exception.MeterExceptionFactory;
import com.tj.meter.service.CameraService;
import com.tj.meter.service.DeviceService;
import com.tj.meter.ys.sdk.YsClientProxy;

/**
 * 摄像头管理,图片管理
 * 裁剪图片会将原图缩放为600px宽，高度等比例缩放
 */
@Controller
public class CameraController extends BaseController{

	private static Logger LOGGER = LoggerFactory.getLogger(CameraController.class);
	public final static String defaultDateFormat = "yyyy-MM-dd";
	public final static String defaultTimeFormat = "HH:mm:ss";

	@Autowired
	YsClientProxy ysClientProxy;
	@Autowired
	DeviceService deviceService;
	@Autowired
	CameraService cameraService;

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
		
		List<CameraInfo> list = deviceService.queryAllCameraList(true);
		model.addAttribute("list", list);
		model.addAttribute("type", 6);
		return "camera/list";
	}

	/**
	 * 摄像头设置
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

		if(StringUtils.isBlank(camera.getLastSnapshotUrl())){
			try {
				String url = ysClientProxy.capture(camera.getDeviceSerial(), camera.getChannelNo());
				camera.setLastSnapshotUrl(url);
			} catch (Exception e) {
				//camera.setPicUrl(url);//应该设置一张获取图片失败的
	 		}
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
		model.addAttribute("type", 6);
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
			deviceService.bindCameraAndDeviceInfo(cameraSerial, deviceInfoId, force);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return fail(e.getMessage());
		}

		return success();
	}
	
	/**
	 * 预览图片，只查看不保存
	 * 
	 * 参数,device
	 * DeviceInfo.
	 */
	@RequestMapping(value = "/camera/preview", method = RequestMethod.GET)
	@ResponseBody
	public Object camerePreview(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="deviceInfoId",required=false)  Long deviceInfoId,
			@RequestParam(value="cameraSerial",required=false)  String cameraSerial
	) { 
		
		try {
			if(StringUtils.isBlank(cameraSerial)){
				DeviceInfo info = deviceService.queryDeviceInfoById(deviceInfoId, true);
				if(info == null || info.getCameraSerial() == null){
					throw MeterExceptionFactory.applicationException("仪表没关联摄像头", null);
				}
				cameraSerial = info.getCameraSerial();
			} 
			CameraInfo camera = deviceService.queryCameraById(cameraSerial, true);
			if(camera == null){
				throw MeterExceptionFactory.applicationException("摄像头未找到:"+cameraSerial, null);
			}
			String url = ysClientProxy.capture(camera.getDeviceSerial(), camera.getChannelNo());
			camera.setLastSnapshotUrl(url);
			return success(url);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return fail(e.getMessage());
		}
	}
	
	/**
	 * 保存图片切割范围
	 * 参数,device
	 * DeviceInfo.
	 */
	@RequestMapping(value = "/camera/range", method = RequestMethod.PUT)
	@ResponseBody
	public Object camereRange(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="deviceInfoId",required=false)  Long deviceInfoId,
			@RequestParam(value="x",required=false)  Integer x,
			@RequestParam(value="y",required=false)  Integer y,
			@RequestParam(value="w",required=false)  Integer w,
			@RequestParam(value="h",required=false)  Integer h
	) { 
		try {
			deviceService. updateCutRange(deviceInfoId,x,y,w,h);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return fail(e.getMessage());
		}
		return success();
	}

	/**
	 * 图片拍照识别综合操作，可完成拍照，保存，识别
	 * @return
	 */
	@RequestMapping(value = "/camera/captureSuite", method = RequestMethod.POST)
	@ResponseBody
	public Object captureStream(@RequestBody CameraCaptureVo param){
		try {
			cameraService.captureHandle(param);
			return success(param);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return fail(e.getMessage());
		}
	}
	
	/**
	 * 读取本地存盘快照文件
	 * ** = 用本地文件夹下的文件相对路径
	 * @param path
	 * @return
	 */
	@RequestMapping(value = "/deviceSnapshot/**")
    public void deviceSnapshot(HttpServletRequest request,HttpServletResponse response){
		String requestURI = request.getRequestURI();
		int index = requestURI.indexOf("/", requestURI.indexOf("deviceSnapshot"));
		String snapshotName = requestURI.substring(index);
		if(snapshotName.indexOf("..") > 0){
			throw MeterExceptionFactory.applicationException("路径不合法", null);
		}
		
		File file = cameraService.buildFile(snapshotName);
		try {
			FileUtils.copyFile(file, response.getOutputStream());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
    }
} 
