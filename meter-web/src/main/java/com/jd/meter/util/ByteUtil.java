package com.jd.meter.util;

import com.jd.meter.exception.MeterExceptionFactory;

/**
 * byte工具包
 * @author cheng.huang
 *
 */
public final class ByteUtil {	
	/**
	 * 从src的index位置，读取16位，返回short
	 * 
	 * @param src			源byte[]
	 * @param index			数据开始位置
	 * @param isBigEdian	byte[]是否isBigEdian
	 * @return short
	 */
    public static final short readShort(byte[] src, int index, boolean isBigEdian) {
    	if(isBigEdian){
            return (short)(((src[index] & 0xFF) << 8) | (src[++index] & 0xFF));
    	}else{
            return (short)((src[index] & 0xFF) | ((src[++index] & 0xFF) << 8));
    	}
    }

    
	/**
	 * 从src的index位置，读取32位，返回int
	 * 
	 * 
	 * @param src			源byte[]
	 * @param index			数据开始位置
	 * @param isBigEdian	byte[]是否isBigEdian
	 * @return int
	 */
    public static final int readInt(byte[] src, int index, boolean isBigEdian) {
    	if(isBigEdian){
    		return    ((src[index] & 0xFF) << 24) 
    				| ((src[++index] & 0xFF) << 16) 
    				| ((src[++index] & 0xFF) << 8) 
    				| (src[++index] & 0xFF);
    	}else{
    		return     (src[index] & 0xFF) 
    				| ((src[++index] & 0xFF) << 8) 
    				| ((src[++index] & 0xFF) << 16) 
    				| ((src[++index] & 0xFF) << 24);
    	}
    }

    
	/**
	 * 从src的index位置，读取64位，返回long
	 * 
	 * 
	 * @param src			源byte[]
	 * @param index			数据开始位置
	 * @param isBigEdian	byte[]是否isBigEdian
	 * @return long
	 */
    public static final long readLong(byte[] src, int index, boolean isBigEdian) {    	
    	if(isBigEdian){
            return    ((src[  index] & 0xFFL) << 56) 
            		| ((src[++index] & 0xFFL) << 48) 
            		| ((src[++index] & 0xFFL) << 40) 
            		| ((src[++index] & 0xFFL) << 32) 
            		| ((src[++index] & 0xFFL) << 24) 
            		| ((src[++index] & 0xFFL) << 16) 
            		| ((src[++index] & 0xFFL) << 8) 
            		|  (src[++index] & 0xFFL);
        }else{
            return     (src[  index] & 0xFFL)  
            		| ((src[++index] & 0xFFL) << 8) 
            		| ((src[++index] & 0xFFL) << 16) 
            		| ((src[++index] & 0xFFL) << 24) 
            		| ((src[++index] & 0xFFL) << 32) 
            		| ((src[++index] & 0xFFL) << 40) 
            		| ((src[++index] & 0xFFL) << 48) 
            		| ((src[++index] & 0xFFL) << 56);
        }
    }

    
  	/**
  	 * 从src的index位置，读取length字节，返回int
  	 * 
  	 * 
  	 * @param src			源byte[]
  	 * @param index			数据开始位置
  	 * @param isBigEdian	byte[]是否isBigEdian
  	 * @return int
  	 */
      public static final byte[] readBytes(byte[] src, int index, int length) { 
    	  byte[] dest = new byte[length];
    	  System.arraycopy(src, index, dest, 0, length);
    	  return dest;
      }
      
    /**
     * 将一个short 放到byte[] dst的index位置
     * 字节序列BigEndian
     * 
     * @param value		
     * @param dst
     * @param index
     * @param isBigEdian
     * @return
     * @throws Exception 
     */
    public static final void writeShort(short value, byte[] dst, int index, boolean isBigEdian){
    	if(isBigEdian){
    		 dst[index] = (byte) ((value >> 8) & 0xFF);
    	     dst[++index] = (byte) (value & 0xFF);
    	}else{
    		throw MeterExceptionFactory.applicationException("ByteUtil.writeShort() 未实现的分支",null);
    	}
    }

    /**
     * int to 32bit
     */
    public static final void writeInt(int value, byte[] dst, int index, boolean isBigEdian) {
     
		if (isBigEdian) {
	        dst[index] = (byte) ((value >> 24) & 0xFF);
	        dst[++index] = (byte) ((value >> 16) & 0xFF);
	        dst[++index] = (byte) ((value >> 8) & 0xFF);
	        dst[++index] = (byte) (value & 0xFF);
		} else {
			throw MeterExceptionFactory.applicationException("ByteUtil.writeShort() 未实现的分支",null);
		}
    }

    /**
     * long to 64bit
     */
    public static final void writeLong(long value, byte[] dst, int index, boolean isBigEdian) {
		if (isBigEdian) {
	        dst[index] = (byte) ((value >> 56) & 0xFF);
	        dst[++index] = (byte) ((value >> 48) & 0xFF);
	        dst[++index] = (byte) ((value >> 40) & 0xFF);
	        dst[++index] = (byte) ((value >> 32) & 0xFF);
	        dst[++index] = (byte) ((value >> 24) & 0xFF);
	        dst[++index] = (byte) ((value >> 16) & 0xFF);
	        dst[++index] = (byte) ((value >> 8) & 0xFF);
	        dst[++index] = (byte) (value & 0xFF);
		} else {
			throw MeterExceptionFactory.applicationException("ByteUtil.writeShort() 未实现的分支",null);
		}
    }
    
    /**
     * 
     * @param attachment
     * @param buffer
     * @param index
     * @param attachmentLength
     */
	public static void writeBytes(byte[] src, byte[] dest, int index, int length) {
		System.arraycopy(src, 0, dest, index, length);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] bs = new byte[2];
		bs[1] = 1;
		bs[0] = 127;
		bs[0] = ++bs[0];
		
		System.out.println(Integer.toBinaryString(bs[0]));
		System.out.println(Integer.toBinaryString(bs[1]));
		
		short s = readShort(bs, 0, false);
		System.out.println(s);
		
		System.out.println("------------");		
		
		s++;
		writeShort(s, bs, 0, true);
		s = readShort(bs, 0, true);		
		System.out.println(Integer.toBinaryString(bs[0]));
		System.out.println(Integer.toBinaryString(bs[1]));
		System.out.println(s);
	}

}
