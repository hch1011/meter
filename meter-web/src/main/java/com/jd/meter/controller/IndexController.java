package com.jd.meter.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Darker on 2016/5/6.
 */
@Controller
public class IndexController {

	@RequestMapping(value = "/login")
    public String login(HttpServletRequest request, HttpServletResponse Response, Model model){
		return "login";
    }
	
	@RequestMapping(value = "/go")
    public String go(HttpServletRequest request, HttpServletResponse Response, Model model){
		return "index";
    }
	
    @RequestMapping(value = "/index")
    public String index(Model model){

        String result = "index";
        model.addAttribute(result, result);
        return result;
    }

} 
