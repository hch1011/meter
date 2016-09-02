package com.jd.meter.sync.pkg;
/**
 * 数据包类型
 * @author hc
 *
 */
public class PackageType {
	public int ping = 1;				// 这类数据没有body
	public int pong = 2;				//

	public int cmd = 3;					// 指令类型的package，指令在body里面

	public int connect_w = 10;			// 询问是否能与目标机器通讯,不管直接通讯还是间接通讯
	public int connect_direct_w = 11;	// 询问能直接与目标机器通讯
	public int connect_direct_y = 12;	// 回答能直接与目标机器通讯
	public int connect_direct_n = 13;	// 回答能直接与目标机器通讯
	
	public int concate_indirect_w = 14;	// 询问是否能直接与目标机器通讯
	public int concate_indirect_y = 15;	// 回答能直接与目标机器通讯
	public int concate_indirect_n = 16;	// 回答能直接与目标机器通讯
	
	
	public int data_db = 51;			// 数据库数据
	public int data_pic = 52;			// 图片数据
	
			
}
