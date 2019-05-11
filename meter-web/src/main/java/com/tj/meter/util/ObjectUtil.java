package com.tj.meter.util;

import java.io.Closeable;

import org.apache.commons.lang.StringUtils;
import com.tj.meter.exception.MeterException;


public class ObjectUtil {
    
    /**
     * 线程休眠millis毫秒<br>
     * eatException=true  出错不抛异常<br>
     * eatException=false 出错抛出异常RuntimeException<br>
     * @param millis
     * @param eatException 
     * @throws MeterException 
     */
    public static void sleep(long millis, boolean eatException) throws MeterException{
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            if(eatException){
                throw new MeterException("sleep exception",null, e);
            }
        }
    }
    
    /**
     * 关闭需要关闭的资源<br>
     * eatException=true  出错不抛异常<br>
     * eatException=false 出错抛出异常RuntimeException<br>
     * @param obj
     * @throws MeterException 
     */
    public static void close(Closeable obj, boolean eatException) throws MeterException{
        if(obj!=null){
            try {
                obj.close();
            } catch (Exception e) {
                if(eatException){
                	throw new MeterException("close exception",null, e);
                }
            }
        }
    }
    
    public static String blankToNull(String str){
    	if(StringUtils.isBlank(str)){
    		return null;
    	}
    	return str;
    }
    
    /**
     * 检查对象不为空
     * @param info
     * @param exceptionIfNull
     * @param exceptionMsg
     * @return
     */
    public static boolean checkNotNull(Object obj, boolean exceptionIfNull, String exceptionMsg){
    	if(obj != null){
    		return true;
    	}
    	
    	if(exceptionIfNull){
    		if(exceptionMsg != null){
    			throw new MeterException(exceptionMsg);
    		}else{
    			throw new MeterException("数据不存在");
    		}
    	}
    	return false;
    }
}
