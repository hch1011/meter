<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    

	<div class="content-body">
	<h2>生产测试数据</h2>
	<div>
		选择设备类型
		<select id="select_deviceType" name="type">
			<c:forEach items="${deviceTypeList}" var="item"><option value="${item.type}">${item.typeName}</option></c:forEach>
		</select>
		选择设备
		<select id="select_deviceInfo" name="deviceId">
			<c:forEach items="${deviceTypeList}" var="item"><option value="${item.type}">${item.typeName}</option></c:forEach>
		</select>
	 </div>
	  
	</div>