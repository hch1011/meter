<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    

	<div class="report-body">
	 	<table class="table table-hover">
              <thead>
                <tr class="success">
                  <th>No</th>
                  <th>类别</th>
                  <th>名称</th>
                  <th>读数</th>
                  <th>状态</th>
                  <th>故障类型</th>
                </tr>
              </thead>
              <tbody>
              <c:forEach items="${list}" var="item" varStatus="status">
                <tr>
                  <td>${status.index+1}</td>
                  <td>${item.deviceType.typeName}</td>
                  <td>${item.name}</td>
                  <td>${item.snapDataScreen}</td>
                  <td  class="status_${item.snapStatus}">${item.snapStatusCn}</td>
                  <td>${item.warningReason}</td>
                </tr>
              </c:forEach> 
                <!-- <tr class="success">
                  <td style="color:rgb(201 225 255)">生成报表</td>
                </tr> -->
              </tbody>
        </table>
        <a class="report" href="${basePath}/device/data/error/reportExcel">生成报表</a>
	</div>
