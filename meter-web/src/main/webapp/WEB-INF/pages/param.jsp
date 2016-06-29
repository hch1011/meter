<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    

	<div class="param-body">
	 	<h3>参数设置</h3>
	 	<form id="param" class="form-horizontal col-xs-12" action="list" method="post"> 
	          <table>
	          	<tbody>
	          	<tr>
	          		<td>
	          		<div class="form-group">
		            	<label class="col-sm-6 control-label">电流报警阈值：</label>
		         		<div class="col-sm-6">
		         			<div class="del">-</div>
	         				<input class="form-control beginDate" type="text" name="beginDate" placeholder="2016-01-01" value="">
			            	<div class="plus">+</div>
			            </div>
			        </div>
	          		</td>
	          		<td class="odd">
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">电流预警阈值：</label>
				         	<div class="col-sm-6">
				         		<div class="del">-</div>
				         		<input class="form-control beginTime" type="text" name="beginTime" placeholder="08:00:00" value="">
				            	<div class="plus">+</div>
				            </div>
				        </div>
	          		</td>
	          	</tr>
	          	<tr>
	          		<td>
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">电流变化频率报警阈值：</label>
				         	<div class="col-sm-6">
				         		<div class="del">-</div>
				         		<input class="form-control endDate" type="text" name="endDate" placeholder="2016-12-30" value="">
				            	<div class="plus">+</div>
				            </div>
				          </div>
	          		</td>
	          		<td class="odd">
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">电流变化频率预警阈值：</label>
				         	<div class="col-sm-6">
				         		<div class="del">-</div>
				         		<input class="form-control endTime" type="text" name="endTime" placeholder="23:59:59" value="">
					            <div class="plus">+</div>
				            </div>
				          </div>
	          		</td>
	          	</tr>
	          	<tr>
	          		<td>
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">动作次数报警阈值：</label>
				         	<div class="col-sm-6">
				         		<div class="del">-</div>
				         		<input class="form-control endDate" type="text" name="endDate" placeholder="2016-12-30" value="">
				            	<div class="plus">+</div>
				            </div>
				          </div>
	          		</td>
	          		<td class="odd">
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">动作次数预警阈值：</label>
				         	<div class="col-sm-6">
				         		<div class="del">-</div>
				         		<input class="form-control endTime" type="text" name="endTime" placeholder="23:59:59" value="">
					            <div class="plus">+</div>
				            </div>
				          </div>
	          		</td>
	          	</tr>
	          	<tr>
	          		<td>
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">每日巡检时间：</label>
				         	<div class="col-sm-6">
				            	<div class="del">-</div>
				            	<input class="form-control endTime" type="text" name="endTime" placeholder="23:59:59" value="">
				            	<div class="plus">+</div>
				            </div>
	          			</div>
	          		</td>
	          		<td class="odd">
	          			<div class="form-group">
				         	  <label class="col-sm-1 control-label"></label>
				              <div class="col-sm-6">
				                  <span><button type="submit" class="btn btn-sm btn-primary" style="font-size: 16px;">保存参数 <i class="fa fa-search"></i></button></span>
				              </div>
				          </div>
	          		</td>
	          	</tr>
	          	</tbody>
	          </table>
			</form>
	</div>
