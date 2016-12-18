<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
	<div class="account-body">
		<table class="table table-hover">
			<thead>
			<tr>
				<td >登录名</td>
				<td >密码</td>
				<td >昵称</td>
				<td >真实姓名</td>
				<td >&nbsp;角色</td>
				<td >&nbsp;状态</td>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${accountList}" var="account">
				<form id="form_${account.id}">
				<tr>
					<td><input id="loginName_${account.id}" name="loginName" value="${account.loginName}" readonly> </td>
					<td><input id="password_${account.id}" name="password" value="" placeholder="*****************"></td>
					<td><input id="nickname_${account.id}" name="nickname" value="${account.nickname}"></td>
					<td><input id="realName_${account.id}" name="realName" value="${account.realName}"></td>
					<td>
						<select id="role_${account.id}"  name="role">
							<option value='user' <c:if test="${account.role == 'user' }">selected</c:if>>普通用户</option>
							<option value='manager' <c:if test="${account.role == 'manager' }">selected</c:if>>管理员</option>
							<option value='admin' <c:if test="${account.role == 'systemAdmin' }">selected</c:if>>超级管理员</option>
						</select>
					</td>
					<td>
						<select id="status_${account.id}" name="status">
							<!-- <option value='0' <c:if test="${account.status == '0' }">selected</c:if>>不可用</option> -->
							<option value='9' <c:if test="${account.status == '9' }">selected</c:if>>锁定&nbsp;&nbsp;</option>
							<option value='1' <c:if test="${account.status == '1' }">selected</c:if>>正常&nbsp;&nbsp;</option>
						</select>
					</td>
					<td>
					<button type="button" class="btn btn-primary btn-xs" data-method='update' data-id="${account.id}">修改</button>
					<button type="button" class="btn btn-primary btn-xs" data-method='delete' data-id="${account.id}">删除</button>
					</td>
				</tr>
				</form>
			</c:forEach>
			 <form id="form_create">
				<tr>
					<td><input name="loginName" value=""></td>
					<td><input name="password" value=""></td>
					<td><input name="nickname" value=""></td>
					<td><input name="realName" value=""></td>
					<td>
						<select name="role">
							<option></option>
							<option value='user'>普通用户</option>
							<option value='manager'>管理员</option>
							<option value='admin'>超级管理员</option>
						</select>
					</td>
					<td>
						<select name="status">
							<option></option>
							<option value='1' >正常&nbsp;&nbsp;</option>
							<option value='9' >锁定&nbsp;&nbsp;</option>
						</select>
					</td>
					<td>
					<button type="button" class="btn btn-primary btn-xs" data-method='create' data-id="${account.id}">添加用户</button>
					</td>
				</tr>
				</form>
			</tbody>
		</table>
	</div>