package com.jd.meter.ys.sdk;

import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.jd.meter.util.TimeUtils;

public class YsClientDeviceTest {

	public static String testDeviceSerial  = "630782726";
	@Test
	public void testDeviceAdd() {
		YsClientDevice.deviceAdd(testDeviceSerial, "MFDBNC");
		System.out.println("testDeviceAdd() end");
	}

	@Test
	public void testDeviceUpdate() {
		YsClientDevice.deviceUpdate(testDeviceSerial, "测试摄像头");
		System.out.println("testDeviceUpdate() end");
	}
	
	@Test
	public void deviceList() {
		List<DeviceInfo> list = YsClientDevice.deviceList();
		System.out.println(JSON.toJSONString(list, true));
		System.out.println("testDeviceUpdate() end");
	}

	@Test
	public void testCapture() {
		String url = YsClientDevice.capture(testDeviceSerial, "1");
		System.out.println("url="+url); 
		System.out.println("testCapture() end");
	}

	@Test
	public void testWriteFileFromUrl书房() {
		String url = YsClientDevice.capture("630782726", "1");
		String filePath = "c:\\meter_pic\\"+TimeUtils.getDateTimeNumber()+".png";
		YsClientDevice.writeFileFromUrl(url, filePath);
		System.out.println("url="+url);
		System.out.println("testWriteFileFromUrl() end");
	}
	@Test
	public void testWriteFileFromUrl办公室() {
		String url = YsClientDevice.capture("573604832", "1");
		String filePath = "c:\\meter_pic\\办公室"+TimeUtils.getDateTimeNumber()+".png";
		YsClientDevice.writeFileFromUrl(url, filePath);
		System.out.println("url="+url);
		System.out.println("testWriteFileFromUrl() end");
	}
}
