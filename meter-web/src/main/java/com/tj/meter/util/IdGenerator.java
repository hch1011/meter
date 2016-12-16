package com.tj.meter.util;

import org.apache.commons.lang.math.RandomUtils;

public class IdGenerator {
	public static String MM = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
	public static int len = MM.length();
	/**
	 * 随机字符串
	 * @return
	 */
	public static  String randomStr(int length){
		if(length < 1){
			return "";
		}
		if(length > 1000){
			throw new RuntimeException("large length");
		}
		StringBuilder sb = new StringBuilder();
		
		while(length-- > 0){
			sb.append(MM.charAt(RandomUtils.nextInt()%len));
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(IdGenerator.randomStr(10));
		System.out.println(IdGenerator.randomStr(10));
		System.out.println(IdGenerator.randomStr(10));
		System.out.println(IdGenerator.randomStr(10));
	}
	 
}
