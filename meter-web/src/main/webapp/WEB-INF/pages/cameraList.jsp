<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    

	<div class="content-body">
			<table class="table table-bordered">
				<thead>
				<tr>
					<td colspan="3" align="center">摄像头</td>
					<td colspan="4" align="center">仪表设备</td>
				</tr>
				<tr>
					<td>摄像头序列号</td>
					<td>摄像头名称</td>
					<td>状态</td>
					<td>仪表名称</td>
					<td>仪表类别</td>
					<td>仪表编号</td>
					<td></td>
				</tr>
				</thead>
				<c:forEach items="${page.content}" var="item">
				<tr>
					<td>${item.deviceSerial}</td>
					<td>${item.deviceName}/${item.channelName}</td>
					<td>${item.statusText}</td>
					<td>${item.deviceInfo.name}</td>
					<td>${item.deviceInfo.type}</td>
					<td>${item.deviceInfo.code}</td>
					<td><a href="${basePath}/camera/setting?cameraSerial=${item.deviceSerial}" target="_blank">详情</a></td>
				</tr>
				</c:forEach>
			</table>  
	</div>