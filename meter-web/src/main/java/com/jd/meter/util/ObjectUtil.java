package com.jd.meter.util;

import java.io.Closeable;

import org.apache.commons.lang.StringUtils;
import com.jd.meter.exception.MeterException;


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
                throw new MeterException("sleep exception", e);
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
                	throw new MeterException("close exception", e);
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
}
