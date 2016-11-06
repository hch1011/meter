<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    

	<div class="content-body">
		<table>
			<tr>
				<td style="font-size: 26px">
				摄像头信息
				</td>
				<td>
				序列号:${camera.deviceSerial}, 通道:${camera.channelName}, 名称:${camera.deviceName}
				</td>
			</tr>
			<tr>
				<td style="font-size: 26px">
				仪表信息
				</td>
				<td>
					<c:if test="${empty camera.deviceInfo}">
					未关联仪表
					</c:if>
					<c:if test="${not empty camera.deviceInfo}">
						仪表编号:${camera.deviceInfo.code},
						仪表名称:${camera.deviceInfo.name},
						仪表类别:${camera.deviceInfo.type},
						仪表进线:${camera.deviceInfo.inputNum}
					</c:if>
				</td>
			</tr>
		</table>
		<hr> 
		<input type="hidden" id="basePath"  value="${basePath}">
		<input type="hidden" id="cameraSerial" name="cameraSerial" value="${camera.deviceSerial}">
		
		<button type="button" class="btn btn-default" onclick="preview()">预览图片</button>
		<select id="deviceInfoId"  name="deviceInfoId" style="border: 1px">
			<option value=''>---请选择仪表---</option>
			<c:forEach items="${deviceInfoList}" var="device">		
				<option value="${device.id}" <c:if test="${camera.deviceInfo.id == device.id}">selected</c:if>>
				${device.inputNum}/${device.name}/${device.name}/${device.code}
				</option>		 
			</c:forEach>
		</select>
		<button type="button" class="btn btn-default" onclick="bindCameraAndDevice()">保存关联</button>	
		识别范围：
		<input id="rang_x" name="x" value="${camera.deviceInfo.x}" size="3">
		<input id="rang_y" name="y" value="${camera.deviceInfo.y}" size="3">
		<input id="rang_w" name="w" value="${camera.deviceInfo.w}" size="3">
		<input id="rang_h" name="h" value="${camera.deviceInfo.h}" size="3">
		<button type="button" class="btn btn-default" onclick="saveRang()">保存范围</button>
		
		<button type="button" class="btn btn-default" onclick="recognition()">识别结果</button><span id="divResult">fdfd</span>
	
		<div id="divPreView"><img id="img_preview" width="800px" src="http://pic.ys7.com:99/image/3/3/2a21227c84cf4e549a8b15a2199291b7/2/24/6086/705315165/48144"></div> 
	 </div>