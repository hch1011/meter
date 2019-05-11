<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    

	<div class="camera-body">
			<%--<table class="table table-bordered">
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
				<c:forEach items="${list}" var="item">
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
			</table>--%>

		<table class="table table-hover">
			<thead>
			<tr>
				<td colspan="3" align="center">摄像头</td>
				<td class="camera-line"></td>
				<td colspan="4" align="center">仪表设备</td>
			</tr>
			<tr class="success">
				<th>摄像头序列号</th>
				<th>摄像头名称</th>
				<th>状态</th>
				<th>仪表名称</th>
				<th>仪表类别</th>
				<th>仪表编号</th>
				<th>操作</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${list}" var="item">
				<tr>
					<td>${item.deviceSerial}</td>
					<td>${item.deviceName}/${item.channelName}</td>
					<td
					<c:if test="${fn:contains(item.statusText,'在线')==true}" >style="color: blue;" </c:if>
					<c:if test="${fn:contains(item.statusText,'未知状态')==true}" >style="color: red;" </c:if>
					>${item.statusText}</td>
					<td>${item.deviceInfo.name}</td>
					<td>${item.deviceInfo.type}</td>
					<td>${item.deviceInfo.code}</td>
					<td><a href="${basePath}/camera/setting?cameraSerial=${item.deviceSerial}" target="_blank" style="color: #3ede45;">详情</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>

	</div>