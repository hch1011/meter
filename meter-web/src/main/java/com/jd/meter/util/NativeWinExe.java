package com.jd.meter.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class NativeWinExe {
	/**
	 * 
	 * @param exeFullname 需要执行的操作系统exe命令全路径名
	 * @param args 参数列表
	 */
	 public static void call(String exeFullname, String... args){
		try {
			exeFullname = exeFullname.replace("\\", "/");
			int lastSplit = exeFullname.lastIndexOf("/");
			String dir = exeFullname.substring(0, lastSplit);
			
			List<String> commands = new ArrayList<>();
			commands.add(exeFullname);
 			for(String arg : args){
 				commands.add(arg);
 			} 
			ProcessBuilder pb = new ProcessBuilder();
			pb.directory(new File(dir));
			pb.command(commands); 
			Process p = pb.start();
			InputStream f = p.getInputStream();
			System.out.println("result=" + getString(f));
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return;
	 }
	
	public static void main(String[] args) {
		System.out.println("main() start");
		String cmdStr = "C:\\data\\meter\\halcondot\\f32.exe " ;
		String fileIn = "C:\\data\\meter\\image\\f000.JPG";
		//String fileOut = "C:\\data\\meter\\image\\f000.txt";
		call(cmdStr,  fileIn);
		
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
