<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    

	<div class="content-body">
			<table width="500px">
				<tr>
					<td>摄像头序列号</td>
					<td>摄像头名称</td>
					<td>在线状态</td>
					<td>设备名称</td>
					<td>设备类别</td>
					<td>设备编号</td>
					<td></td>
				</tr>
				<c:forEach items="${page.content}" var="item">
				<tr>
					<td>${item.deviceSerial}</td>
					<td>${item.deviceName}/${item.channelName}</td>
					<td>${item.statusText}</td>
					<td>${item.deviceInfo.name}</td>
					<td>${item.deviceInfo.type}</td>
					<td>${item.deviceInfo.code}</td>
					<td><a href="${basePath}/camera/setting">详情</a></td>
				</tr>
				</c:forEach>
			</table>  
	</div>