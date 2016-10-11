package com.jd.meter.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * cmd /c dir 是执行完dir命令后关闭命令窗口。
 * cmd /k dir 是执行完dir命令后不关闭命令窗口。 
 * cmd /c start dir 会打开一个新窗口后执行dir指令，原窗口会关闭。 
 * cmd /k start dir 会打开一个新窗口后执行dir指令，原窗口不会关闭。 
 * @author hc
 *
 */
public class NativeWinExe {
	/**
	 * 需要执行的命令
	 * @param cmdStr
	 */
	 public static void exe(String cmdStr, String fileIn, String fileOut){
		try {
			Process p = Runtime.getRuntime().exec("cmd /c " + cmdStr);
			InputStream f = p.getInputStream();
			System.out.println(getString(f));
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return;
	 }
	
	public static void main(String[] args) {
		System.out.println("main() start");
		String cmdStr = "dir > D:\\data\\meter\\dir.log" ;
		//exe(cmdStr);
		
		System.out.println("main() end");
	}
	
	public static String getString(InputStream in){
		try {
			byte[] b = new byte[64];
			int count = in.read(b);
			if(count == -1 || count == 0){
				  return null;
			}
			
			if(count <= 64){
				  return new String(b, 0, count);
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append(b);
			
			while((count = in.read(b)) < 64 ){
				sb.append(new String(b, 0, count));
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
