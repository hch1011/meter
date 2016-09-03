package com.jd.meter.sync.pkg;
 
/**
 * 数据包类型
 * @author hc
 *
 */
public class PackageBasic {
	public static int maxPackageLength = 1024*1024;  	// 数据包最大长度，用于验证length合法性；1M
	public static int minPackageLength = 12;  		 	// 数据包最小长度，用于验证length合法性；header
	public static int delimiter = 0xFFFFFFFF;			// 分隔符
 	// Header 
	public int length = 0;				// 4 + 4 + 4 + data.length
	public int packageType = 1;			// 数据包类型
	// body
	public byte[] body;					// 数据内容
	
	
}
