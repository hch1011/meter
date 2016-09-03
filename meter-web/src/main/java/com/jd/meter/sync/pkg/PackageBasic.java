package com.jd.meter.sync.pkg;

import java.nio.ByteBuffer;

/**
 * 数据包类型
 * @author hc
 *
 */
public class PackageBasic {
	public static int maxPackageLength = 1024*1024;  	// 数据包最大长度，用于验证length合法性；1M
	public static int minPackageLength = 10;  		 	// 数据包最小长度，用于验证length合法性；header
	public static byte delimiter = (byte)0xff;			// 分隔符
 	// Header 
	public int length = 0;				// 4 + 4 + 4 + data.length
	public int packageType = 0;		// 数据包类型
	// body
	public byte[] body;					// 数据内容

	private byte[] pkg;   				//网络写的数据

	public void setLength(int length) {
		this.length = length;
	}

	public void setPackageType(int packageType) {
		this.packageType = packageType;
	}

	public void setBody(byte[] body) {
		this.body = body;
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
	
	
	public byte[] buildByteArray(){ 
		if(pkg == null){
			ByteBuffer out = ByteBuffer.allocate(resetLength()+minPackageLength);
			out.put(PackageBasic.delimiter);
	        out.putInt(this.resetLength());
	        out.putInt(this.getPackageType());
	        out.put((byte)0);
	        if(body != null) {
	            out.put(this.getBody());
	        }
	        pkg = out.array();
		}
		return pkg;
	}
	
	public static void main(String[] args) {
		PackageBasic pang = new PackagePang();
		System.out.println(pang.buildByteArray());
	}
}
