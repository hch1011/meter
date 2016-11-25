<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    

	<div class="img-info-body">
		<input class="device_id" type="hidden" value="${deviceId}" />
	 	<div class="img">
	 		<img src="/meter/resources/images/imginfo.png" />
	 	</div>
	 	<div class="img-op">
	 		<button class="btn btn-info btn-a" type="button">图像重新采集</button>
	 		<button class="btn btn-info btn-b" type="button">识别区域选取</button>
	 	</div>
	 	<table>
 			<tr>
 				<td>
 					<label class="label-a" >接地电流值(mA)：</label>
 				</td>
 				<td>
 					<input class="i-a" type="text" value="4.2"/>
 				</td>
 				<td>
 					<label>采集日期：</label>
 				</td>
 				<td>
 					<input class="i-b" type="text" value=""/>
 				</td>
 			</tr>
 			<tr>
 				<td>
 					<label class="label-c" >电流变化率(mA/6h)：</label>
 				</td>
 				<td>
 					<input class="i-c" type="text" value=""/>
 				</td>
 				<td>
 					<label>采集时间：</label>
 				</td>
 				<td>
 					<input class="i-d" type="text" value=""/>
 				</td>
 			</tr>
 		</table>
	</div>