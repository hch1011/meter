<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    

	<div class="content-body">
	<h1>摄像头信息</h1>
	<p>序列号:${camera.deviceSerial}, 通道:${camera.channelName}, 名称:${camera.deviceName}
	</p>
	<h1>仪表信息</h1>
	<p>
		<c:if test="${empty camera.deviceInfo}">
		未绑定仪表
		</c:if>
		<c:if test="${not empty camera.deviceInfo}">
			仪表编号:${camera.deviceInfo.code},
			仪表名称:${camera.deviceInfo.name},
			仪表类别:${camera.deviceInfo.type},
			仪表进线:${camera.deviceInfo.inputNum}
		</c:if>
	</p>
	<hr> 
	<input type="hidden" id="basePath"  value="${basePath}">
	<input type="hidden" id="cameraSerial" name="cameraSerial" value="${camera.deviceSerial}">
	
	<select id="deviceInfoId"  name="deviceInfoId" style="border: 1px">
		<option value=''>---请选择设备---</option>
		<c:forEach items="${deviceInfoList}" var="device">		
			<option value="${device.id}" <c:if test="${camera.deviceInfo.id == device.id}">selected</c:if>>
			${device.inputNum}/${device.name}/${device.name}/${device.code}
			</option>		 
		</c:forEach>
	</select>
	<button type="button" class="btn btn-default" onclick="bind()">绑定设备</button>	
	<button type="button" class="btn btn-default" onclick="preview()">预览图片</button>
	<button type="button" class="btn btn-default" onclick="saveRang()">保存范围</button>
	
		<div id="preView" style="width: 600px;height:500px;overflow-y:auto;overflow-x:auto;">
			
		</div>
	 </div>