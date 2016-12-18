<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
	<div class="camera-body">
		<table class="table table-hover">
			<thead>
			<tr>
				<td align="center">登录名</td>
				<td align="center">昵称</td>
				<td align="center">真实姓名</td>
				<td align="center">角色</td>
				<td align="center">状态</td>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${accountList}" var="account">
				<tr>
					<td>${account.loginName}</td>
					<td>${account.nickname}</td>
					<td>${account.realName}</td>
					<td>${account.roleText}</td>
					<td>${account.statusText}</td>
					<td>修改用户,删除用户</td>
				</tr>
				<tr>
					<td> </td>
					<td> </td>
					<td> </td>
					<td> </td>
					<td> </td>
					<td>添加用户</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>