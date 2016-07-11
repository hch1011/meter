<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    

	<div class="param-body">
	 	<h3>参数设置:</h3> 
	 	<c:forEach items="${deviceTypeList}" var="item">
	 		<c:if test="${item.type eq currentDeviceType.type}">
	 		<a class="btn btn-primary" href="${basePath}/device/data/param?type=${item.type}" role="button">${item.typeName}</a>
	 		</c:if>
	 		<c:if test="${item.type ne currentDeviceType.type}">
	 		<a class="btn btn-default" href="${basePath}/device/data/param?type=${item.type}" role="button">${item.typeName}</a>
	 		</c:if>
	 	</c:forEach>
	 	<hr>
	 	
	 	
	 	<form id="param" class="form-horizontal col-xs-12" action="${basePath}/device/data/param" method="post" > 
	 		<input type="hidden" name="type" value="${currentDeviceType.type}">
	          <table>
	          	<tbody>
	          	<tr>
	          		<td>
	          		<div class="form-group">
		            	<label class="col-sm-6 control-label">${currentDeviceType.dataName}报警阈值：</label>
		         		<div class="col-sm-6">
		         			<div class="del current-del">-</div>
	         				<input class="form-control current-error" type="text" name="dataForAlarm" value="${currentDeviceType.dataForAlarm}">
			            	<div class="plus current-plus">+</div>
			            </div>
			        </div>
	          		</td>
	          		<td class="odd">
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">${currentDeviceType.dataName}预警阈值：</label>
				         	<div class="col-sm-6">
				         		<div class="del current-del">-</div>
				         		<input class="form-control current-warn" type="text" name="dataForWarning" value="${currentDeviceType.dataForWarning}">
				            	<div class="plus current-plus">+</div>
				            </div>
				        </div>
	          		</td>
	          	</tr>
	          	<tr>
	          		<td>
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">${currentDeviceType.dataName}变化频率报警阈值：</label>
				         	<div class="col-sm-6">
				         		<div class="del current-del">-</div>
				         		<input class="form-control current-change-error" type="text" name="changeRateForAlarm" value="${currentDeviceType.changeRateForAlarm}">
				            	<div class="plus current-plus">+</div>
				            </div>
				          </div>
	          		</td>
	          		<td class="odd">
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">${currentDeviceType.dataName}变化频率预警阈值：</label>
				         	<div class="col-sm-6">
				         		<div class="del current-del">-</div>
				         		<input class="form-control current-change-warn" type="text" name="changeRateForWarning" value="${currentDeviceType.changeRateForWarning}">
					            <div class="plus current-plus">+</div>
				            </div>
				          </div>
	          		</td>
	          	</tr>
	          	<tr>
	          		<td>
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">动作次数报警阈值：</label>
				         	<div class="col-sm-6">
				         		<div class="del times-del">-</div>
				         		<input class="form-control times-error" type="text" name="frequencyForAlarm" value="${currentDeviceType.frequencyForAlarm}">
				            	<div class="plus times-plus">+</div>
				            </div>
				          </div>
	          		</td>
	          		<td class="odd">
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">动作次数预警阈值：</label>
				         	<div class="col-sm-6">
				         		<div class="del times-del">-</div>
				         		<input class="form-control times-warn" type="text" name="frequencyForWarning" value="${currentDeviceType.frequencyForWarning}">
					            <div class="plus times-plus">+</div>
				            </div>
				          </div>
	          		</td>
	          	</tr>
	          	<tr>
	          		<td>
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">每日巡检时间：</label>
				         	<div class="col-sm-6">
				            	<!-- <div class="del time-del">-</div> -->
				            	<input class="form-control time-day" type="text" name="snapTimes"  value="${currentDeviceType.snapTimes}">
				            	<!-- <div class="plus time-plus">+</div> -->
				            </div>
	          			</div>
	          		</td>
	          		<td class="odd">
	          			<div class="form-group">
				         	  <label class="col-sm-1 control-label"></label>
				              <div class="col-sm-6">
				                  <span><button type="submit" class="btn btn-sm btn-primary" style="font-size: 16px;">保存参数 <i class="fa fa-search"></i></button></span>
				              </div>
				          </div>
	          		</td>
	          	</tr>
	          	</tbody>
	          </table>
			</form>
	</div>
