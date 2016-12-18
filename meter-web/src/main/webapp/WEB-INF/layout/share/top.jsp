<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  

<div class="top" id="top" style="background:url(/meter/resources/images/topbg.gif) repeat-x;">
	<input class="hide-type" type="hidden" value="${type}">
	<div class="topleft">
		<a href="main.html" target="_parent">
		 <img src="/meter/resources/images/logo.png"	title="系统首页" />
		</a>
	</div>

	<ul class="nav">
		<li>
			<!-- class="selected" -->
			<a href="/meter/index"  data-num="1"><img
					src="/meter/resources/images/icon01.png" title="主页面" />
				<h2>主页面</h2>
			</a>
		</li>
		<li>
			<a href="/meter/device/data/img/info" data-num="2"><img
				src="/meter/resources/images/icon02.png" title="图像采集" />
				<h2>图像采集</h2>
			</a>
		</li>
		<li>
			<a href="/meter/device/data/info" data-num="3"><img
				src="/meter/resources/images/top/icon_sjcx.gif" title="数据查询" />
			<h2>数据查询</h2>
			</a>
		</li>
		
		<shiro:hasRole name="manager">
		<li>
			<a href="/meter/device/data/param" data-num="4"><img
				src="/meter/resources/images/icon03.png" title="参数配置" />
			<h2>参数配置</h2>
			</a>
		</li>
		</shiro:hasRole>
		
		<li>
			<a href="/meter/device/data/error/report" data-num="5"><img
				src="/meter/resources/images/icon05.png" title="统计报表" />
			<h2>数据报表</h2>
			</a>
		</li>
		
		<%--<shiro:hasRole name="manager">
		<li>
			<a href="/meter/camera/list" data-num="6">
			<img src="/meter/resources/images/icon06.png" title="摄像头管理" />
			<h2 style="width: 70px;">摄像头管理</h2>
			</a>
		</li> 
		</shiro:hasRole>--%>
		
		<!-- 这里要做成下拉框包括：清空缓存，仪表管理，摄像头管理，用户管理 -->
		<li>
			<%--<a href="/meter/admin/account/list" data-num="6">&lt;%&ndash;href="/meter/admin/account/list"&ndash;%&gt;
			<img src="/meter/resources/images/icon06.png" title="系统管理" />
			<h2 style="width: 70px;">系统管理</h2>--%>
			</a>
			<ul class="ce">
				<li>
					<a href="#">
						<img style="margin-top: 10px;" src="/meter/resources/images/icon06.png" title="系统管理" />
						<span><h2>系统管理<img class="more" src="/meter/resources/images/top/more.png"/></h2></span>

					</a>
					<ul class="er">
						<li><a  href="/meter/camera/list" data-num="6">摄像头管理</a></li>
						<li><a  href="##">二级目录B</a></li>
						<li><a  href="##">二级目录C</a></li>
					</ul>
				</li>
				<div class="clear"></div>
			</ul>
		</li> 
		
		<li>
			<a  id="tbCleanCache" href="#" data-num="8">
			<img src="/meter/resources/images/icon01.png" title="清空缓存" />
			<h2>清空缓存</h2>
			</a>
		</li>
		
		<li>
			<a  id="tbLogout" href="${basePath}/logout">
			<img src="/meter/resources/images/top/icon_logout.png" title="退出" />
			<h2>退出</h2>
			</a>
		</li>
	</ul>
	<!-- 
	<div class="topright">
		<ul>
			<li><span><img src="/meter/resources/images/help.png" title="帮助"
					class="helpimg" /></span><a href="#">帮助</a></li>
			<li><a href="#">关于</a></li>
			<li><a href="">退出</a></li>
		</ul>

		<div class="user">
			<span>admin</span> <i>消息</i> <b>5</b>
		</div>
	</div>
	 -->
</div>
