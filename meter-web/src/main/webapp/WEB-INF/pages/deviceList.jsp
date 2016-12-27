<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
   
   
    <div class="btn-group">
      <c:forEach items="${deviceTypeList}" var="type">
        <c:if test="${currentDeviceType == type.type}">
            <a type="button" class="btn-type btn btn-primary" href="${basePath}//admin/device/list?deviceType=${type.type}">${type.typeName}</a> 
        </c:if>
        <c:if test="${currentDeviceType != type.type}">
            <a type="button" class="btn-type btn btn-default" href="${basePath}//admin/device/list?deviceType=${type.type}">${type.typeName}</a> 
        </c:if>
      </c:forEach>
	</div>
	
	<div class="device-body">
		<table class="table table-hover">
			<thead>
			<tr>
                <td>ID</td>
				<td>设备编码</td>
				<td>进线</td>
				<td>路径</td>
				<td>设备名</td>
				<td>监控页面展示</td>
				<td>监控首页排序</td>
                <td>备注</td>
                <td></td>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${deviceInfoList}" var="deviceInfo">
				<form id="form_${deviceInfo.id}">
				<tr>
                    <td><input name="id" value="${deviceInfo.id}" size="10" readonly></td>
					<td><input name="code" value="${deviceInfo.code}" size="10"> </td>
                    <td><input name="inputNum" value="${deviceInfo.inputNum}" size="4" > </td>
					<td><input name="path" value="${deviceInfo.path}" size="16"></td>
					<td><input name="name" value="${deviceInfo.name}" size="16"></td>
                    <td>
                        <select name="monitorPageFlag" >
                            <option value='1' <c:if test="${deviceInfo.monitorPageFlag == '1' }">selected</c:if>>是</option>
                            <option value='0' <c:if test="${deviceInfo.monitorPageFlag == '0' }">selected</c:if>>否</option>
                        </select>
                    </td>
                    <td>
                        <input name="monitorPageSort" value="${deviceInfo.monitorPageSort}" size="4"></td> 
					</td>
					<td>
                        <input name="description" value="${deviceInfo.description}"  size="20"></td> 
					</td>
					<td>
					   <button type="button" class="htn-form btn btn-primary btn-xs" data-method='update' data-id="${deviceInfo.id}">修改</button>
					   <button type="button" class="htn-form btn btn-primary btn-xs" data-method='delete' data-id="${deviceInfo.id}">删除</button>
					</td>
				</tr>
				</form>
			</c:forEach>
			 <form id="form_create">
				<tr>
                    <td>
                        <input type="hidden" name="type" value="${currentDeviceType}" >
                        <input name="id" value="${maxId}" size="10">
                    </td>
					<td>
					   <input name="code" value="" size="10">
					</td>
					<td><input name="inputNum" value="" size="4"></td>
					<td><input name="path" value="" size="16"></td>
					<td><input name="name" value="" size="16"></td>
					<td>
						<select name="monitorPageFlag">
							<option></option>
							<option value='1'> 是 </option>
							<option value='0'> 否 </option>
						</select>
					</td>
                    <td><input name="monitorPageSort" value=""  size="4"></td>
					<td><input name="description" value="" size="20"></td>
					<td>
					<button type="button" class="htn-form btn btn-primary btn-xs" data-method='create'>新增</button>
					</td>
				</tr>
				</form>
			</tbody>
		</table>
	</div>