package com.jd.meter.sync.pkg;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jd.meter.entity.DeviceData;
import com.jd.meter.exception.MeterExceptionFactory;
import com.jd.meter.util.ByteUtil;

/**
 * 数据包类型
 * @author hc
 *
 */
public class PackageUtil {
	 public static SyncResponsePackage buildSyncResponsePackage(int code,String data,String message ){
		 SyncResponsePackage response = new SyncResponsePackage(code, data,message);
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("code", code);
		 if(data != null){
			 map.put("data", data);
		 }

		 if(message != null){
			 map.put("message", message);
		 }
		 response.setBody(JSON.toJSONString(map).getBytes());
		 return response;
	}
	
	 public static PackageBasic encodePackage(DeviceData deviceData){		 
		PackageBasic pkg= new PackageBasic();
		pkg.setPackageType(PackageType.data_entity_json);
		pkg.setBody(JSON.toJSONString(deviceData).getBytes(Charset.forName("utf-8")));
		return pkg;
	 }
	 public static DeviceData decodePackage(PackageBasic data){
		String str = new String(data.getBody(),Charset.forName("utf-8"));
		DeviceData obj =  JSON.parseObject(str, DeviceData.class);
		return obj;
	 }
	
	
	 public static PackageBasic readPackage(InputStream in) throws IOException{
		 // header
		byte[] header = new byte[PackageBasic.HEADER_LENGTH];
		int readLength = in.read(header);
		if(readLength != PackageBasic.HEADER_LENGTH){
			throw MeterExceptionFactory.readSocketError("readLength="+readLength);
		}
		int bodyLength = ByteUtil.readInt(header, 1, true);
		int packageType = ByteUtil.readInt(header, 5, true);
		
		if(bodyLength == 0){
			PackageBasic pkg = new PackageBasic();
			pkg.setPackageType(packageType);
			return pkg;
		}
		
		
		// body
		byte[] body = new byte[bodyLength];
		readLength = in.read(body);
		if(readLength != bodyLength){
			throw MeterExceptionFactory.readSocketError("bad readLength="+readLength);
		}
		
		PackageBasic pkg = new PackageBasic();
		pkg.setPackageType(packageType);
		pkg.setBody(body);
		return pkg;
	 }
	 
	 public static SyncResponsePackage readSyncResponse(InputStream in) throws IOException{
		 PackageBasic pkg = readPackage(in);
		 String bodyStr = new String(pkg.getBody(),Charset.forName("utf-8"));
		 JSONObject json = JSON.parseObject(bodyStr);

		 SyncResponsePackage response = new SyncResponsePackage();
		 response.setCode(json.getIntValue("code"));
		 response.setData(json.getString("data"));
		 response.setMessage(json.getString("message"));
		 return response;
	 }
	 
	 public static void writePackage(OutputStream out, PackageBasic pkg){
		 try {
			out.write(pkg.toByteArray());
		} catch (IOException e) {
			throw MeterExceptionFactory.readSocketError("writePackage error");
		}
	 }	
}
