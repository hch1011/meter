<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="menu" style="background:#f0f9fd;">

	<div class="lefttop"><span></span>设备列表</div>
    
    <dl class="leftmenu">
		<c:forEach var="deviceInfoMap" items="${deviceInfoList}" varStatus="status">
			<c:forEach items="${deviceInfoMap}" var="deviceInfoMap">
			<dd>
			<div class="title" data-title="${deviceInfoMap.key}">
				<span><img src="/meter/resources/images/leftico01.png" /></span>进线${deviceInfoMap.key}
			</div>
			<ul class="menuson">
				<c:forEach var="deviceInfo" items="${deviceInfoMap.value}" varStatus="status">
					<c:if test="${fn:contains(deviceInfo.path,'A')==true}" >
						<li><cite></cite><a data-deviceId="${deviceInfo.id}">A相：${deviceInfo.name}</a><i></i></li>
					</c:if>
					<c:if test="${fn:contains(deviceInfo.path,'B')==true}" >
						<li><cite></cite><a data-deviceId="${deviceInfo.id}">B相：${deviceInfo.name}</a><i></i></li>
					</c:if>
					<c:if test="${fn:contains(deviceInfo.path,'C')==true}" >
						<li><cite></cite><a data-deviceId="${deviceInfo.id}">C相：${deviceInfo.name}</a><i></i></li>
					</c:if>
				</c:forEach>
			</ul>
			</dd>
			</c:forEach>
		</c:forEach>
    </dl>
</div>
