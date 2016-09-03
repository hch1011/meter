package com.jd.meter.sync.pkg;

import java.nio.ByteBuffer;

/**
 * 数据包类型
 * 1byte分隔符|4byte长度|4byte类型|1byte保留|body
 * 
 * @author hc
 *
 */
public class PackageBasic {
	public static byte DELIMITER = (byte)0xff;		// 分隔符
	public static int MAX_LENGTH = 1024*1024;  		// 数据包最大长度，用于验证length合法性；1M
	public static int HEADER_LENGTH = 10;  		 	// 数据包最小长度，用于验证length合法性；header
	
 	// Header 
	protected int length = 0;							// data.length
	protected int packageType = 0;					// 数据包类型
	
	// body
	private byte[] body;							// 数据内容
	private byte[] pkgData;   						// 整个数据包，用于网络的byte[]:header+body

	public void setLength(int length) {
		this.length = length;
	}

	public void setPackageType(int packageType) {
		this.packageType = packageType;
	}

	public void setBody(byte[] body) {
		if(body != null && body.length > 0){
			this.body = body;
			this.length = body.length;
		}
	}

	public int getLength() {
		return length;
	}

	public int getPackageType() {
		return packageType;
	}

	public byte[] getBody() {
		return body;
	}
	public int resetLength() {
		if(body == null) {
			length = 0;
		} else {
			length = body.length;
		}
		return length;
	}
	
	
	public byte[] toByteArray(){ 
		if(pkgData == null){
			resetLength();
			ByteBuffer out = ByteBuffer.allocate(length + HEADER_LENGTH);
			out.put(PackageBasic.DELIMITER);
	        out.putInt(length);
	        out.putInt(packageType);
	        out.put((byte)0);
	        if(body != null) {
	            out.put(body);
	        }
	        pkgData = out.array();
		}
		return pkgData;
	}
	
	public static void main(String[] args) {
		PackageBasic pang = new PangPackage();
		System.out.println(pang.toByteArray());
	}
}
