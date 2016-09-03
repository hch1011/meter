package com.jd.meter.sync.pkg;
/**
 * 数据包类型
 * @author hc
 *
 */
public class PackageType {
	public static int ping = 1;				// 这类数据没有body
	public static int pong = 2;				//

	public static int cmd = 3;					// 指令类型的package，指令在body里面

	public static int connect_w = 10;			// 询问是否能与目标机器通讯,不管直接通讯还是间接通讯
	public static int connect_direct_w = 11;	// 询问能直接与目标机器通讯
	public static int connect_direct_y = 12;	// 回答能直接与目标机器通讯
	public static int connect_direct_n = 13;	// 回答能直接与目标机器通讯
	
	public static int concate_indirect_w = 14;	// 询问是否能直接与目标机器通讯
	public static int concate_indirect_y = 15;	// 回答能直接与目标机器通讯
	public static int concate_indirect_n = 16;	// 回答能直接与目标机器通讯
	
	//实体数据
	public static int data_source_pic = 100;	// 摄像头上来的图像数据
	public static int data_db_json = 200;		// 数据库数据，java对象json
	public static int data_db_pic = 201;		// 本地存储的图片文件
			
}
