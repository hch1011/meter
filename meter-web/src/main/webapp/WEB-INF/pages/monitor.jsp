<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    

	<div class="content-body">
	<c:forEach items="${list}" var="type">
		<h2>${type.typeName}:</h2>
		<c:forEach items="${type.deviceInfoList}" var="info" varStatus="status">
	 	<div class="block" 
	 	    data-id="${info.id}" 
	 	    data-snapstatus="${info.snapStatus}"
	 		data-dataname="${type.dataName}" data-dataunit="${type.dataUnit}" data-snapdata="${info.snapData}"
	 		data-rateunit="${type.changeRateUnit}" data-rate="${info.changeRate}" 
	 		data-frequency="${info.frequency}" data-typename="${type.typeName}"
	 		data-infoname="${info.name}" data-type="${type.type}"
	 		data-date="<fmt:formatDate value="${info.snapTime}" pattern="yyyy-MM-dd"/>" data-time="<fmt:formatDate value="${info.snapTime}" pattern="hh:mm:ss"/>">
	 		${info.name}
	 		<div class="block-image"><img src="/meter/resources/images/light${info.snapStatus}.png"/></div>
	 	</div><c:if test="${status.index%4 == 3}"><br/></c:if>
		</c:forEach>
		
		<div style="clear:both;"></div>		
	</c:forEach>
	 
	 	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close btn-close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">详细监测信息</h4>
					</div>
					<div class="modal-body body-detail">
						<div class="tl" data-id="${id}">
				 			<span class="info-name">密度计#1</span>
				 			<div class="tl-image"><img src="resources/images/light1.png"/></div>
				 			<a href="/meter/device/data/img/info"><button class="btn btn-info" type="button">图像信息管理</button></a>
				 		</div>
				 		<table>
				 			<tr>
				 				<td>
				 					<label><span class="data-name">接地电流</span>(<span class="data-unit">mA</span>)：</label>
				 				</td>
				 				<td class="snap-data">
				 					<input type="text" value="4.2"/>
				 				</td>
				 				<td>
				 					<label>采集日期：</label>
				 				</td>
				 				<td class="create-data">
				 					<input type="text" value="2016.06.25"/>
				 				</td>
				 			</tr>
				 			<tr>
				 				<td>
				 					<label><span class="data-name">电流</span>变化率(<span class="change-rate-unit">mA/6h</span>)：</label>
				 				</td>
				 				<td class="change-rate">
				 					<input type="text" value="2.1"/>
				 				</td>
				 				<td>
				 					<label>采集时间：</label>
				 				</td>
				 				<td class="create-time">
				 					<input type="text" value="08:00:00"/>
				 				</td>
				 			</tr>
				 			<tr class="hid">
				 				<td>
				 					<label><span class="type-name">避雷器</span>动作次数：</label>
				 				</td>
				 				<td class="frequency">
				 					<input type="text" value="2.1"/>
				 				</td>
				 			</tr>
				 		</table>
				 		
					</div>
					<div class="modal-footer">
						<!-- <button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button> -->
						<button type="button" class="btn btn-primary btn-close" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
	 	 
	 </div>