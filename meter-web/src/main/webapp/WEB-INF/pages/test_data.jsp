<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    

	<div class="content-body">
	<h2>生产测试数据
	 <c:forEach items="${deviceList}" var="item">
	 <a href="${basePath}/ys/capture?deviceSerial=${item.deviceSerial}" target="_blank">${item.deviceName}</a> 
	 </c:forEach>
	  </h2>
	 

	<!-- 
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
	 -->
	  <form action="${basePath}/test/data" method="post">
	  	<table class="table .table-bordered">
	  		<tr>
	  			<td>选择设备</td>
	  			<td>
					<select name="deviceId"  style="border:1px solid #333333; width:80;height:20;">  
						<c:forEach items="${deviceInfoList}" var="item">
							<option value="${item.id}">${item.name} | ${item.code}</option>
						</c:forEach>
					</select>
				</td>
	  		</tr>
	  		<tr>
	  			<td>预读数据</td>
	  			<td>
	  				<input class="form-control time-day" type="text" name="snapData"  value="1.0">
				</td>
	  		</tr>
	  		<tr>
	  			<td>变化率</td>
	  			<td>
	  				<input class="form-control time-day" type="text" name="changeRate"  value="1.0">
				</td>
	  		</tr>
	  		<tr>
	  			<td>动作次数</td>
	  			<td>
	  				<input class="form-control time-day" type="text" name="frequency"  value="2">
				</td>
	  		</tr>
	  		<tr>
	  			<td>图片路径</td>
	  			<td>
	  				<input class="form-control time-day" type="text" name="pictureUrl"  value="/resources/images/imginfo.png">
				</td>
	  		</tr>
	  		<tr>
	  			<td> </td>
	  			<td>
	  				<input class="btn btn-default" type="submit" value="提交">
				</td>
	  		</tr>
	  	</table>
	   
	  </form>
	  
	</div>