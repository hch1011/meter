<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    

	<div class="device-data-body">
	 	<h3>查询操作</h3>
	 	<form id="deviceDateForm" class="form-horizontal col-xs-8" action="${basePath}/device/data/info" method="get" style="width:280px; margin-bottom:20px;"> 
	          
	          <div class="form-group">
	            <label class="col-sm-5 control-label">起始日期：</label>
	         	<div class="col-sm-7">
	         		<input id="input_beginDate" class="form-control beginDate" type="text" name="beginDate" placeholder="${beginDate}" value="${beginDate}">
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="col-sm-5 control-label">起始时间：</label>
	         	<div class="col-sm-7">
	         		<input id="input_beginTime"  class="form-control beginTime" type="text" name="beginTime" placeholder="${beginTime}" value="${beginTime}">
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="col-sm-5 control-label">终止日期：</label>
	         	<div class="col-sm-7">
	         		<input id="input_endDate"  class="form-control endDate" type="text" name="endDate" placeholder="${endDate}" value="${endDate}">
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="col-sm-5 control-label">终止时间：</label>
	         	<div class="col-sm-7">
	         		<input id="input_endTime"  class="form-control endTime" type="text" name="endTime" placeholder="${endTime}" value="${endTime}">
	            </div>
	          </div>
	          
	          <div class="form-group">
	            <label class="col-sm-5 control-label">选择设备：</label>
	         	<div class="col-sm-7">
	            	<select id="input_deviceId" name="deviceId" class="form-control">
	            		<!-- <option value="">----请选择设备----</option> -->
		            	<c:forEach items="${deviceList}" var="device">
		            		<option value="${device.id}">${device.name}/${device.code}</option>
		            	</c:forEach>			
					</select>
	            </div>
	          </div>
	          
	          <div class="form-group" style="margin-left: -22px;">
	         	  <label class="col-sm-1 control-label" style="margin-right:-20px;"></label>
	              <div class="col-sm-6">
	              	  <span><button type="button" class="btn btn-sm btn-primary" style="width:248px; margin-top:10px;font-size: 16px;">查询 <i class="fa fa-search"></i></button></span>
	                  <!-- 
	                  <span><button type="button" class="btn btn-sm btn-primary" style="width:248px; margin-top:5px; margin-bottom:10px;font-size: 16px;">动作次数查询 <i class="fa fa-search"></i></button></span>
                      -->
	              </div>
	          </div>
	          
	          
			</form>
			<div class="placeholder-container">
				<!-- <span>电流历史曲线图</span>  -->
				<span id="title"></span>
				<div id="placeholder" style="width:700px;height:350px;"></div>
			</div>
	</div>
	
	