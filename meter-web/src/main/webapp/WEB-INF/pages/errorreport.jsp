<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    

	<div class="report-body">
	 	<table class="table table-hover">
              <thead>
                <tr class="success">
                  <th>/</th>
                  <th>类别</th>
                  <th>名称</th>
                  <th>位置</th>
                  <th>编号</th>
                  <th>故障类型</th>
                </tr>
              </thead>
              <tbody>
                <tr class="error">
                  <th>1</th>
                  <td>避雷器</td>
                  <td>避雷器#3</td>
                  <td>1号主变C相</td>
                  <td>A-3</td>
                  <td>泄露电流值恒超过报警值</td>
                </tr>
                <tr class="warning">
                  <th>2</th>
                  <td>避雷器</td>
                  <td>避雷器#5</td>
                  <td>1号主变B相</td>
                  <td>A-5</td>
                  <td>动作次数超过预警值</td>
                </tr>
                <tr class="error">
                  <th>3</th>
                  <td>SF6密度</td>
                  <td>密度计#3</td>
                  <td>1号GIS-C相</td>
                  <td>B-3</td>
                  <td>SF6密度低于报警值</td>
                </tr>
                <tr class="warning">
                  <th>4</th>
                  <td>SF6密度</td>
                  <td>密度计#5</td>
                  <td>2号GIS-B相</td>
                  <td>B-5</td>
                  <td>SF6密度低于预警值</td>
                </tr>
                <tr class="info">
                  <th>5</th>
                  <td>SF6密度</td>
                  <td>密度计#6</td>
                  <td>2号GIS-C相</td>
                  <td>B-6</td>
                  <td>图像识别失败</td>
                </tr>
                <!-- <tr class="success">
                  <td style="color:rgb(201 225 255)">生成报表</td>
                </tr> -->
              </tbody>
        </table>
        <a class="report" href="#">生成报表</a>
	</div>
