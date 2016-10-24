package com.jd.meter.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import com.jd.meter.entity.CameraInfo;
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
	@RequestMapping(value = "/camera/list", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Object camereList(
			HttpServletRequest request,
			HttpServletResponse response,
			@PageableDefault(page=0, size=100) Pageable pageable
	) {
		Page<CameraInfo> page = ysClientProxy.cameraList(pageable);

		return page;
	}

} 
