<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String basePath = request.getContextPath();
	application.setAttribute("basePath", basePath);
%>

<title>登录</title>
<link href="resources/css/page/login.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="resources/js/commonjs/jquery-1.12.4.min.js"></script>
<script src="resources/js/commonjs/cloud.js" type="text/javascript"></script>

<script language="javascript">
	$(function(){
	    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
		$(window).resize(function(){  
	    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
	    })  
	});  
</script>
</head>
<body style="background-color:#1c77ac; background-image:url(resources/images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">
    <div id="mainBody">
      <div id="cloud1" class="cloud"></div>
      <div id="cloud2" class="cloud"></div>
    </div>  
	<div class="logintop">    
	    <span>欢迎登录后台管理界面平台</span>    
	    <ul>
		    <li><a href="#">回首页</a></li>
		    <li><a href="#">帮助</a></li>
		    <li><a href="#">关于</a></li>
	    </ul>    
	</div>
    <div class="loginbody">
	    <span class="systemlogo"></span> 
	    <div class="loginbox">
	    <form id="loginForm" action="${basePath}/login" method="post">
		    <ul><!--  onclick="JavaScript:this.value=''" -->
			    <li><input name="username" type="text" class="loginuser" value="admin" /></li>
			    <li><input name="password" type="password" class="loginpwd" value="admin123"/></li>
			    <li><input type="submit" class="loginbtn" value="登录 " />
			    <!-- 
			    <label><input name="rememberMe" type="checkbox" value="true" checked="checked" />记住密码</label>			    
			    <label><a href="#">忘记密码？</a></label>
			     -->
			     <c:if test="${screenMessage != null}"><label>${screenMessage}</label></c:if>
			    </li>
			    <li> <label>普通用户user/user123,管理员admin/admin123</label></li>
		    </ul>
		</form>
	    </div>
    </div>
    <div class="loginbm">版权所有  2016-06-03</div>
</body>
</html>