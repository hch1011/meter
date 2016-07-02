package com.jd.meter.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jd.meter.entity.DeviceType;
import com.jd.meter.service.DeviceService;

/**
 * Created by Darker on 2016/5/6.
 */
@Controller
public class IndexController {
	Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Autowired
	DeviceService deviceService;
	
	@RequestMapping(value = {"", "/index","/monitor"})
	public String index1(Model model) {
		return monitorPage(model);
	}
	
	/**
	 * 返回监控主页
	 */
    @RequestMapping(value = "/monitor")
    public String monitorPage(Model model){
    	List<DeviceType> list = deviceService.queryDataForMonitorPage();
        model.addAttribute("list", list);
        return "monitor";
    }
     
    
    
    @RequestMapping(value = "/monitor/json")
    public String monitorJson(Model model){

        String result = "home";
        model.addAttribute(result, result);
        return result;
    }
    
	@RequestMapping(value = "/login")
    public String login(HttpServletRequest request, HttpServletResponse Response, Model model){
		return "login";
    }
	
	@RequestMapping(value = "/go")
    public String go(HttpServletRequest request, HttpServletResponse Response, Model model){
		return "home";
    }
	
	@RequestMapping(value = "/html/{path}")
    public String common(
    		@PathVariable("path") String path
    		){
		return path+"_html";
    }
    
} 
