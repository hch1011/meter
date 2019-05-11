package com.tj.meter.ys.sdk;

public enum YsErrorCodeEnum {
	OK(200,"操作成功"),
	e10001(10001,"参数错误"),
	e10002(10002,"accessToken异常或过期"),
	e10005(10005,"appKey异常,appKey被冻结"),
	e10007(10007,"调用次数达到上限"),
	e10029(10029,"调用频率超过限制"),
	e20002(20002,"设备不存在"),
	e20007(20007,"设备不在线"),
	e20010(20010,"accessToken异常或过期"),
	e20011(20011,"设备验证码错误"),
	e20013(20013,"设备已被别人添加"),
	e20014(20014,"deviceSerial不合法"),
	e20018(20018,"该用户不拥有该设备"),
	e20017(20017,"设备已被自己添加"),
	e20032(20032,"该用户下该通道不存在"),
	e60017(60017,"设备抓图失败,操作过于频繁，稍后再试"),
	e60020(60020,"不支持该命令,确认设备是否支持抓图"),
	e49999(49999,"数据异常"), 
	//e10002(10002,"accessToken")
	;
	public static String getMsgByCode(int code){
		for(YsErrorCodeEnum item : YsErrorCodeEnum.values()){
			if(item.code == code){
				return item.msg;
			}
		}
		return "位置异常，code="+code;
	}

	int code = 0;
	String msg;
	private YsErrorCodeEnum(int code,String msg){
		this.code=code;
		this.msg=msg;
	}
}
