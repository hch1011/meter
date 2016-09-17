package com.jd.meter.ys.sdk;

import org.junit.Test; 
import com.jd.meter.util.TimeUtils;

public class YsClientDeviceTest {

	public static String testDeviceSerial  = "630782726";
//	@Test
//	public void testDeviceAdd() {
//		YsClientDevice.deviceAdd(testDeviceSerial, "MFDBNC");
//		System.out.println("testDeviceAdd() end");
//	}
//
//	@Test
//	public void testDeviceUpdate() {
//		YsClientDevice.deviceUpdate(testDeviceSerial, "测试摄像头");
//		System.out.println("testDeviceUpdate() end");
//	}
//	
//	@Test
//	public void deviceList() {
//		List<DeviceInfo> list = YsClientDevice.deviceList();
//		System.out.println(JSON.toJSONString(list, true));
//		System.out.println("testDeviceUpdate() end");
//	}
//
//	@Test
//	public void testCapture() {
//		String url = YsClientDevice.capture(testDeviceSerial, "1");
//		System.out.println("url="+url); 
//		System.out.println("testCapture() end");
//	}

	@Test
	public void testWriteFileFromUrl() {
		String url = YsClientDevice.capture(testDeviceSerial, "1");
		String filePath = "c:\\meter_pic\\"+TimeUtils.getDateTimeNumber()+".png";
		YsClientDevice.writeFileFromUrl(url, filePath);
		System.out.println("url="+url);
		System.out.println("testWriteFileFromUrl() end");
	}

}
