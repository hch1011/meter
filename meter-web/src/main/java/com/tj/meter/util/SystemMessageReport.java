package com.tj.meter.util;

import com.tj.meter.exception.MeterException;

/**
 * 当后台出错时，需要通知相应管理员
 * @author hc
 *
 */
public class SystemMessageReport {
     public static int errorCount;					//需要人员参与的系统错误次数，
     public static String globalMessage;			//最新的系统错误消息
     public static MeterException globalException;	
     
     
     
}
