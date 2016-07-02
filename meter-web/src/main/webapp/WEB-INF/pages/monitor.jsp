<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    

	<div class="content-body">
	<c:forEach items="${list}" var="item">
		<h2>${item.typeName} 监测部分:</h2>
		<c:forEach items="${item.deviceInfoList}" var="info">
	 	<div class="block" data-id="${info.id}">
	 		${info.name}
	 		<div class="block-image"><img src="/meter/resources/images/light${info.snapStatus}.png"/></div>
	 	</div>
		</c:forEach>
		<div style="clear:both;"></div>		
	</c:forEach>
	 
	 	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">详细监测信息</h4>
					</div>
					<div class="modal-body body-detail">
						<div class="tl" data-id="${id}">
				 			密度计#1
				 			<div class="tl-image"><img src="resources/images/light1.png"/></div>
				 			<button class="btn btn-info" type="button">图像信息管理</button>
				 		</div>
				 		<table>
				 			<tr>
				 				<td>
				 					<label>接地电流值(mA)：</label>
				 				</td>
				 				<td>
				 					<input type="text" value="4.2"/>
				 				</td>
				 				<td>
				 					<label>采集日期：</label>
				 				</td>
				 				<td>
				 					<input type="text" value="2016.06.25"/>
				 				</td>
				 			</tr>
				 			<tr>
				 				<td>
				 					<label>电流变化率(mA/6h)：</label>
				 				</td>
				 				<td>
				 					<input type="text" value="2.1"/>
				 				</td>
				 				<td>
				 					<label>采集时间：</label>
				 				</td>
				 				<td>
				 					<input type="text" value="08:00:00"/>
				 				</td>
				 			</tr>
				 		</table>
				 		
					</div>
					<div class="modal-footer">
						<!-- <button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button> -->
						<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
	 	 
	 </div>