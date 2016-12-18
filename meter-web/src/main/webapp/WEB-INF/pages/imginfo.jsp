<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
 
	<div class="img-info-body">
		<input class="device_id" type="hidden" value="${deviceId}" />
		<input id="rang_x" type="hidden" value="-1" />
		<input id="rang_y" type="hidden" value="-1" />
		<input id="rang_w" type="hidden" value="-1" />
		<input id="rang_h" type="hidden" value="-1" />
		
		<input class="has_img" type="hidden" value="false" /><!-- 是否正确加载快照 -->
	 	<div class="img">
	 		<img width="600px" src="${basePath}/resources/images/demo/shapshot_loading.jpg" />
	 	</div>
	 	<div class="img-op">
	 		<button class="btn btn-info btn-a" type="button">图像重新采集</button> 		
	 		<shiro:hasRole name="manager">
	 			<button class="btn btn-info btn-b" type="button">保存区域选取</button>
	 		</shiro:hasRole>
	 	</div>
	 	<table>
 			<tr>
 				<td>
 					<label class="label-a" >接地电流值(mA)：</label>
 				</td>
 				<td>
 					<input class="i-a" type="text" value="" readonly/>
 				</td>
 				<td>
 					<label>采集日期：</label>
 				</td>
 				<td>
 					<input class="i-b" type="text" value="" readonly/>
 				</td>
 			</tr>
 			<tr>
 				<td>
 					<label class="label-c" >电流变化率(mA/6h)：</label>
 				</td>
 				<td>
 					<input class="i-c" type="text" value="" readonly/>
 				</td>
 				<td>
 					<label>采集时间：</label>
 				</td>
 				<td>
 					<input class="i-d" type="text" value="" readonly/>
 				</td>
 			</tr>
 		</table>
	</div>