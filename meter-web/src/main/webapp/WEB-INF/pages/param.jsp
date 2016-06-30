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
		         			<div class="del current-del">-</div>
	         				<input class="form-control current-error" type="text" name="currentError" placeholder="4.2" value="">
			            	<div class="plus current-plus">+</div>
			            </div>
			        </div>
	          		</td>
	          		<td class="odd">
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">电流预警阈值：</label>
				         	<div class="col-sm-6">
				         		<div class="del current-del">-</div>
				         		<input class="form-control current-warn" type="text" name="currentWarn" placeholder="2.4" value="">
				            	<div class="plus current-plus">+</div>
				            </div>
				        </div>
	          		</td>
	          	</tr>
	          	<tr>
	          		<td>
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">电流变化频率报警阈值：</label>
				         	<div class="col-sm-6">
				         		<div class="del current-del">-</div>
				         		<input class="form-control current-change-error" type="text" name="currentChangeError" placeholder="0.8" value="">
				            	<div class="plus current-plus">+</div>
				            </div>
				          </div>
	          		</td>
	          		<td class="odd">
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">电流变化频率预警阈值：</label>
				         	<div class="col-sm-6">
				         		<div class="del current-del">-</div>
				         		<input class="form-control current-change-warn" type="text" name="currentChangeWarn" placeholder="0.4" value="">
					            <div class="plus current-plus">+</div>
				            </div>
				          </div>
	          		</td>
	          	</tr>
	          	<tr>
	          		<td>
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">动作次数报警阈值：</label>
				         	<div class="col-sm-6">
				         		<div class="del times-del">-</div>
				         		<input class="form-control times-error" type="text" name="timesError" placeholder="5" value="">
				            	<div class="plus times-plus">+</div>
				            </div>
				          </div>
	          		</td>
	          		<td class="odd">
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">动作次数预警阈值：</label>
				         	<div class="col-sm-6">
				         		<div class="del times-del">-</div>
				         		<input class="form-control times-warn" type="text" name="timesWarn" placeholder="3" value="">
					            <div class="plus times-plus">+</div>
				            </div>
				          </div>
	          		</td>
	          	</tr>
	          	<tr>
	          		<td>
	          			<div class="form-group">
				            <label class="col-sm-6 control-label">每日巡检时间：</label>
				         	<div class="col-sm-6">
				            	<div class="del time-del">-</div>
				            	<input class="form-control time-day" type="text" name="timeDay" placeholder="08:00:00" value="">
				            	<div class="plus time-plus">+</div>
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
