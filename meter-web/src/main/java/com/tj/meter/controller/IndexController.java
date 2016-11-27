package com.tj.meter.controller;

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

import com.tj.meter.entity.DeviceType;
import com.tj.meter.service.DeviceService;

/**
 * Created by Darker on 2016/5/6.
 */
@Controller
public class IndexController {
	Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Autowired
	DeviceService deviceService;
	
	@RequestMapping(value = {"", "/index"})
	public String index(HttpServletRequest request, Model model) {
		if(request.getSession().getServletContext().getAttribute("bastPath") == null){
			request.getSession().getServletContext().setAttribute("bastPath", request.getContextPath());
		}
		
		return monitorPage(model);
	}
	
	/**
	 * 返回监控主页
	 */
    @RequestMapping(value = "/monitor")
    public String monitorPage(Model model){
    	List<DeviceType> list = deviceService.queryDataForMonitorPage();
        model.addAttribute("list", list);
		model.addAttribute("type", 1);//前端主页面选中
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
	
	@RequestMapping(value = "/{path}/html")
    public String common(
    		@PathVariable("path") String path
    		){
		return path+"_html";
    }

} 