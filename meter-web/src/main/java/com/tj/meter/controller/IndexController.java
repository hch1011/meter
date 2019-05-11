package com.tj.meter.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tj.meter.entity.DeviceType;
import com.tj.meter.exception.MeterException;
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
		model.addAttribute("type", 1);//前端主页面选中
		
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
    
	 
	@RequestMapping("/login")
	public String login(
			HttpServletRequest request, 
			HttpServletResponse Response,
			@RequestParam(value="username", required = false) String username,
			@RequestParam(value="password", required = false) String password,
			@RequestParam(value="rememberMe", required = false, defaultValue="false") boolean rememberMe,
			Model model){
 
        if(SecurityUtils.getSubject().isAuthenticated()){
            return "redirect:index";
        } 
        
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			
			//model.addAttribute("screenMessage", "请输入正确的用户名或密码");
			return "login";
		}
		 UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		 token.setRememberMe(rememberMe);
 		 
		 Subject currentUser = SecurityUtils.getSubject();
		 
		 try {  
	            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查  
	            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应  
	            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法  
 	            currentUser.login(token);
	        }catch(UnknownAccountException uae){  
	            model.addAttribute("screenMessage", "用户名或密码错误");
	        }catch(IncorrectCredentialsException ice){  
	            model.addAttribute("screenMessage", "用户名或密码错误");  
	        }catch(LockedAccountException lae){  
	            model.addAttribute("screenMessage", "用户被锁定");  
	        }catch(ExcessiveAttemptsException eae){  
	            model.addAttribute("screenMessage", "ExcessiveAttemptsException");  
	        }catch(AuthenticationException ae){  
	        	if(ae.getCause() != null && ae.getCause() instanceof MeterException){
		            model.addAttribute("screenMessage", ((MeterException)ae.getCause()).getScreenMessage()); 
	        		
	        	}else{
		            //注意：这个必须放在后面，因为这个异常可以处理所有认证失败的情况
		            model.addAttribute("screenMessage", "authentication faild");  
	        	}
	        }  
	        //验证是否登录成功  
	        if(currentUser.isAuthenticated()){
	        	SavedRequest savedRequest = WebUtils.getSavedRequest(request);
	        	if(savedRequest != null){
		        	String url = savedRequest.getRequestUrl();
		        	if(StringUtils.isNotBlank(url)){
		        		url = url.substring(request.getContextPath().length());
		        		return "redirect:" + url;
		        	}
	        	}
	            return "redirect:index";
	        }
	        
	        token.clear(); 
	        return "login";
	}

	 
	@RequestMapping("/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:login";
	}

	
	@RequestMapping(value = "/go")
	@ResponseBody
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
