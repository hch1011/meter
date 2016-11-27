<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    

<div class="camera-setting-body">
	<table class="table table-bordered">
		<tbody>
		<tr>
			<td rowspan="2" class="size">摄像头信息</td>
			<td class="warning">序列号</td>
			<td class="warning">通道</td>
			<td class="warning" colspan="2">名称</td>
		</tr>
		<tr>
			<td>${camera.deviceSerial}</td>
			<td>${camera.channelName}</td>
			<td colspan="2">${camera.deviceName}</td>
		</tr>
		<tr>
			<td rowspan="2"　class="size">仪表信息</td>
			<td class="warning">仪表编号</td>
			<td class="warning">仪表名称</td>
			<td class="warning">仪表类别</td>
			<td class="warning">仪表进线</td>
		</tr>
		<tr>
			<td>${camera.deviceInfo.code}</td>
			<td>${camera.deviceInfo.name}</td>
			<td>${camera.deviceInfo.deviceType.typeName}　</td>
			<td>${camera.deviceInfo.inputNum}</td>
		</tr>
		</tbody>
	</table>

	<input type="hidden" id="basePath"  value="${basePath}">
	<input type="hidden" id="cameraSerial" name="cameraSerial" value="${camera.deviceSerial}">
	<div class="container">
	<div class="left">
		<button type="button" class="btn btn-primary bu" onclick="preview()">预览图片</button>
		<div class="interval"></div>
		<em class="word">关联仪表：</em>
		<select id="deviceInfoId"  name="deviceInfoId">
			<option value=''>---请选择仪表---</option>
			<c:forEach items="${deviceInfoList}" var="device">
				<option value="${device.id}" <c:if test="${camera.deviceInfo.id == device.id}">selected</c:if>>
						${device.inputNum}/${device.name}/${device.name}/${device.code}
				</option>
			</c:forEach>
		</select>
		<button type="button" class="btn btn-success" onclick="bindCameraAndDevice()">保存关联</button>
		<div class="interval"></div>
		<em class="word">识别范围：</em>
		x:<input id="rang_x" readonly name="x" value="${camera.deviceInfo.x}" size="3">
		y:<input id="rang_y" readonly name="y" value="${camera.deviceInfo.y}" size="3">
		w:<input id="rang_w" readonly name="w" value="${camera.deviceInfo.w}" size="3">
		h:<input id="rang_h" readonly name="h" value="${camera.deviceInfo.h}" size="3">
		
		<button type="button" class="btn btn-success" onclick="saveRang()">保存范围</button>
		<br/>
		<div class="interval"></div>
		<button type="button" class="btn btn-primary bu" onclick="recognition()">识别图片</button>
		<div class="interval"></div>
		<em class="word-a">识别结果：<em id="divResult"></em></em> 
	</div>


		<div id="divPreView" class="right" >
			
			<!--  <img id="img_preview" src=" " alt="摄像头快照" /> -->
		</div>
	</div>
</div>