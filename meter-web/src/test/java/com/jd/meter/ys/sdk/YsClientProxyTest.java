package com.jd.meter.ys.sdk;

import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.tj.meter.entity.CameraInfo;
import com.tj.meter.util.TimeUtils;
import com.tj.meter.ys.sdk.YsClientProxy;

public class YsClientProxyTest {
	YsClientProxy ysClientProxy = new YsClientProxy();

	public static String testDeviceSerial  = "573604832";
	@Test
	public void testDeviceAdd() {
		ysClientProxy.deviceAdd(testDeviceSerial, "MFDBNC");
		System.out.println("testDeviceAdd() end");
	}

	@Test
	public void testDeviceUpdate() {
		ysClientProxy.deviceUpdate(testDeviceSerial, "测试摄像头");
		System.out.println("testDeviceUpdate() end");
	}
	
	@Test
	public void deviceList() {
		List<CameraInfo> list = ysClientProxy.deviceList(0,50);
		System.out.println(JSON.toJSONString(list, true));
		System.out.println("testDeviceUpdate() end");
	}

	@Test
	public void testCapture() {
		String url = ysClientProxy.capture(testDeviceSerial, "1");
		System.out.println("url="+url); 
		System.out.println("testCapture() end");
	}

	@Test
	public void testWriteFileFromUrl书房() {
		String url = ysClientProxy.capture("630782726", "1");
		String filePath = "c:\\meter_pic\\"+TimeUtils.getDateTimeNumber()+".png";
		ysClientProxy.writeFileFromUrl(url, filePath);
		System.out.println("url="+url);
		System.out.println("testWriteFileFromUrl() end");
	}
	@Test
	public void testWriteFileFromUrl办公室() {
		String url = ysClientProxy.capture("573604832", "1");
		String filePath = "c:\\meter_pic\\办公室"+TimeUtils.getDateTimeNumber()+".png";
		ysClientProxy.writeFileFromUrl(url, filePath);
		System.out.println("url="+url);
		System.out.println("testWriteFileFromUrl() end");
	}
}
