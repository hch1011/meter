<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>

<div class="top" id="top" style="background:url(resources/images/topbg.gif) repeat-x;">
	<div class="topleft">
		<a href="main.html" target="_parent"><img src="resources/images/logo.png"	title="系统首页" /></a>
	</div>

	<ul class="nav">
		<li>
			<!-- class="selected" -->
			<a href="index"  data-num="0"><img
					src="resources/images/icon01.png" title="主页面" />
				<h2>主页面</h2>
			</a>
		</li>
		<li>
		<a href="go" data-num="1"><img
				src="resources/images/icon02.png" title="图像采集" />
				<h2>图像采集</h2>
			</a>
		</li>
		<li>
			<a href="javascript:void(0)" data-num="2"><img
				src="resources/images/icon03.png" title="数据查询" />
			<h2>数据查询</h2>
			</a>
		</li>
		<li>
			<a href="javascript:void(0)" data-num="3"><img
				src="resources/images/icon04.png" title="参数配置" />
			<h2>参数配置</h2>
			</a>
		</li>
		<li>
			<a href="javascript:void(0)" data-num="4"><img
				src="resources/images/icon05.png" title="诊断报告" />
			<h2>诊断报告</h2>
			</a>
			</li>
		<!-- <li>
			<a href=""><img
				src="resources/images/icon06.png" title="系统设置" />
			<h2>系统设置</h2>
			</a>
		</li> -->
	</ul>

	<div class="topright">
		<ul>
			<li><span><img src="resources/images/help.png" title="帮助"
					class="helpimg" /></span><a href="#">帮助</a></li>
			<li><a href="#">关于</a></li>
			<li><a href="">退出</a></li>
		</ul>

		<div class="user">
			<span>admin</span> <i>消息</i> <b>5</b>
		</div>
	</div>
</div>
