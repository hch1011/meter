<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    

	<div class="content-body">
	 	<%-- <c:if test="${type == 1}"> --%>
	 		<h2>SF6密度监测部分:</h2>
	 		<div class="block" data-id="${id}">
	 			密度计#1
	 			<div class="block-image"><img src="resources/images/light1.png"/></div>
	 		</div>
	 		<div class="block">
	 			密度计#2
	 			<div class="block-image"><img src="resources/images/light2.png"/></div>
	 		</div>
	 		<div class="block">
	 			密度计#3
	 			<div class="block-image"><img src="resources/images/light3.png"/></div>
	 		</div>
	 		<div class="block">
	 			密度计#4
	 			<div class="block-image"><img src="resources/images/light4.png"/></div>
	 		</div>
	 		<div style="clear:both;"></div>
	 	<%-- </c:if> --%>
	 	
	 	
	 	<%-- <c:if test="${type == 1}"> --%>
	 		<h2>氧化锌避雷器监测部分:</h2>
	 		<div class="block">
	 			避雷器#1
	 			<div class="block-image"><img src="resources/images/light1.png"/></div>
	 			
	 		</div>
	 		<div class="block">
	 			避雷器#2
	 			<div class="block-image"><img src="resources/images/light2.png"/></div>
	 		</div>
	 		<div class="block">
	 			避雷器#3
	 			<div class="block-image"><img src="resources/images/light3.png"/></div>
	 		</div>
	 		<div class="block">
	 			避雷器#4
	 			<div class="block-image"><img src="resources/images/light4.png"/></div>
	 		</div>
	 	<%-- </c:if> --%>
	 	
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
				 		
						<div class="detail">
							<label>接地电流值(mA)：</label>
							<input type="text" value="4.2"/>
						</div>
						<div class="detail">
							<label>采集日期：</label>
							<input type="text" value="2016.06.25"/>
						</div>
						<div class="detail">
							<label>电流变化率(mA/6h)：</label>
							<input type="text" value="2.1"/>
						</div>
						<div class="detail">
							<label>采集时间：</label>
							<input type="text" value="08:00:00"/>
						</div>
						<div class="detail">
							<label>避雷动作次数：</label>
							<input type="text" value="2" />
						</div>
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