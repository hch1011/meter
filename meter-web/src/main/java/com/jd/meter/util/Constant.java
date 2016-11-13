package com.jd.meter.util;

public class Constant {

	public static String result_success = "success";
	public static String result_fail = "fail";


	//报警状态:0数据无效,读取数据失败;1数据正常,2数据预警,3数据报警
	public static Integer status_invalid = 0;	//数据无效,读取数据失败
	public static Integer status_ok = 1;		//正常
	public static Integer status_warning = 2;	//预警
	public static Integer status_alarm = 3;		//报警
	
	
}
