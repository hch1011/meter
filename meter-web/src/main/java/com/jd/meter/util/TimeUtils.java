package com.jd.meter.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.jd.meter.exception.MeterExceptionFactory;

public class TimeUtils { 
	
    // 一天毫秒数
    public static final long timeLengthOfDay = 24*3600*1000;
    public static final long timeLengthOfYear = 365*24*3600*1000;
    // 
    private final static String patternFormat = "[1-9]\\d{3}(-\\d{2}){2} \\d{2}(:\\d{2}){2}";
    private final static String patternShotFormat = "[1-9]\\d{3}(-\\d{2}){2}";
    private final static String patternYyyymmddFormat = "[1-9]\\d{3}(\\d{2}){2}";
    
    public final static String defaultFormat = "yyyy-MM-dd HH:mm:ss";
    public final static String defaultShotFormat = "yyyy-MM-dd";
    public final static String yyyymmddFormat = "yyyyMMdd";
    public final static String defaultUTCFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    
    public final static String dayNumberFormat = "yyyyMMdd";
    public final static String timeNumberFormat = "HHmmss";
    public final static String fullNumberFormat = "yyyyMMddHHmmss";
   
    /**
     * 
     * @return
     */
    public static String getDateString() {
        return getDateString(new Date(), defaultFormat);
    }
    /**
     * 返回获取日期格式的数值，如20151212010101
     * @return
     */
    public static Long getDateTimeNumber( ) {
        return Long.valueOf(getDateString(new Date(), fullNumberFormat));
    }
    /**
     * 将时间转换成字符串，格式yyyy-MM-dd HH:mm:ss(2011-07-20 16:43:43)
     * 如果传入的date=null，返回null
     * @param format
     * @return
     */
    public static String getDateString(Date date) {
        return getDateString(date, defaultFormat);
    }
    
    /**
     * 将毫秒转换成时间字符串，格式yyyy-MM-dd HH:mm:ss(2011-07-20 16:43:43)
     * 对参数未作验证
     * 
     * @param second
     * @return
     */
    public static String getDateString(long ms) {
        return getDateString(new Date(ms), defaultFormat);
    }
    /**
     * 获得当前时间，格式自定义
     * 
     * @param format
     * @return
     */
    public static String getDateString(Date date, String format) {
        if(date == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    
    /**
     * 日期字符串转对象，失败返回null
     * 
     * 只支持三种格式的日期类型字符串
     * yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd
     * yyyyMMdd
     * 
     * 不成功的转型会返回null
     * 
     * @param dataString
     * @return date | null
     */
    public static Date getDate(String dataString){
        if(dataString==null || dataString.length()==0 || "null".endsWith(dataString)){
            return null;
        }
        
        String pattern = getFormatPattern(dataString);
        if(pattern == null){
            return null;
        }
        return getDate(dataString,pattern);
    }
    
    /**
     * 日期字符串转对象,失败返回null
     * @param dataString
     * @return data | null
     */
    public static Date getDate(String dataString, String format){
        if(dataString == null || dataString.trim().length()==0){
            return null;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return  sdf.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 返回指定日期，时分秒为 00:00:00
    public static Date getDateMinTime(Date date){
    	String str = getDateString(date, defaultShotFormat);
    	return getDate(str, defaultShotFormat);
    }
    
    // 返回指定日期，时分秒为23:59:59
    public static Date getDateMaxTime(Date date){
    	String str = getDateString(date, defaultShotFormat) + " 23:59:59";
    	return getDate(str, defaultFormat);
    }
    
    private static Date forever;
    //返回一个足够远的将来时间3000-01-01 00:00:00
    public static Date getDateForever() {
    	if(forever == null){
    		forever =  getDate("2999-12-31 23:59:59");
    	}
        return forever;
    }
    
    /**
     * 根据输入的时间字符串，解析日期格式，支持3种格式
     * yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd
     * yyyyMMdd
     */
    public static String getFormatPattern(String dataString){
        if( dataString.matches(patternFormat)){
            return defaultFormat;
        }
        if( dataString.matches(patternShotFormat)){
            return defaultShotFormat;
        }
        if( dataString.matches(patternYyyymmddFormat)){
            return yyyymmddFormat;
        }
        return null;
    }
    
    /**
     * 对给定的时间加减指定的年月日
     * @param date
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date add(Date date, Integer yearInc, Integer monthInc, Integer dayInc){
    	//GregorianCalendar.add(5, -1); 表示天减1
    	//GregorianCalendar.add(3. -1); 表示周减1
    	//GregorianCalendar.add(2, -1); 表示月份减1
    	//GregorianCalendar.add(1, -1); 表示年份减1
    	GregorianCalendar gc = new GregorianCalendar();
    	gc.setTime(date);
    	if(yearInc != null){
    		gc.add(1, yearInc);
    	}
    	if(monthInc != null){
    		gc.add(2, monthInc);
    	}
    	if(dayInc != null){
    		gc.add(5, dayInc);
    	}
    	 
    	return gc.getTime();
    }
 
    /**
     * 给定日期返回当天的最后时间，即23点59分59秒
     * @param date
     * @return
     */
    public static Date maxOfDay(Date date){
    	GregorianCalendar gc = new GregorianCalendar();
    	gc.setTime(date);
    	gc.set(GregorianCalendar.HOUR, 59);
    	gc.set(GregorianCalendar.MINUTE, 59);
    	gc.set(GregorianCalendar.SECOND, 59);
    	return gc.getTime();
    }
 
    
    /**
     * 获取给定日期的前一天
     * @param dNow
     * @return
     */
    public static Date getBeforeDate(Date dNow){
    	return getBeforeDate(dNow, 1);
    }
    
    /**
     * 获取给定日期的前n天
     * @param dNow:基准时间
     * @param days:前几天的时间，如1表示比基准时间早1天
     * @return
     */
    public static Date getBeforeDate(Date dNow, int days){
    	if (days <=0 ) {
    		// make sure days is greater than zero
    		return null;
    	}
    	
    	Calendar calendar = Calendar.getInstance();//得到日历
    	calendar.setTime(dNow);//把当前时间赋给日历
    	calendar.add(Calendar.DAY_OF_MONTH, -days);//设置为前一天
    	return calendar.getTime();//得到前一天的时间
    }
    /**
     * 求两个时间之间间隔月数， 不到一个月按一个月算,1秒也算一个月，不返回负数
     * 
     *  2009-02-01 00:00:00 to 2009-02-01 00:00:00 = 0
     *  
     *  2009-02-01 00:00:00 to 2009-02-01 00:00:01 = 1
     *  2009-02-01 00:00:00 to 2009-03-01 00:00:00 = 1
     *  2009-02-01 00:00:00 to 2009-03-01 00:00:01 = 2
     *  2009-02-01 00:00:00 to 2009-03-31 23:59:59 = 2
     *  
     *  2009-03-31 00:00:00 to  2009-03-31 00:00:00 = 0
     *  2009-03-31 00:00:01 to  2009-03-31 00:00:01 = 0
     *  2009-03-31 00:00:00 to  2009-03-31 00:00:01 = 1
     *  2009-03-31 23:59:59 to  2009-04-01 00:00:00 = 1
     *  2009-03-31 23:59:57 to  2009-04-31 23:59:57 = 1
     *  2009-03-31 23:59:57 to  2009-04-31 23:59:58 = 2
     *  
     *  @return >0
     */
    public static int spaceForMonth(Date beginTime, Date endTime){
    	//date是值传递，这里不会修改外面的值
    	if(beginTime.getTime() > endTime.getTime()){
    		Date temp = beginTime; 
    		beginTime = endTime;
    		endTime = temp;
    	}
    	GregorianCalendar begin = new GregorianCalendar();
    	GregorianCalendar end = new GregorianCalendar();
    	begin.setTime(beginTime);
    	end.setTime(endTime);
    	 
    	int i = 0;
    	while(begin.before(end)){
    		i++;
    		//begin.add(Calendar.MONTH, 1);错误：不能顺序循环+1。比如本月31号，加1得不到31号后续就会出错
    		begin.setTime(beginTime);	
    		begin.add(Calendar.MONTH, i);
    	}
    	return i;
    }
    
    
    /**
     * 
     * 解析时间表达式，两种格式，可混合使用,分隔符逗号
     * 10:00,12:00,10,20
     * 
     * 10:00  表示指定时间运行
     * 10表示每小时的对应分钟运行
     * 
     * @param timeExpression
     * @param exceptionIfBadSubExpression  
     * @return
     */
    public static List<String> parseTimeExpression(String timeExpression, boolean exceptionIfBadSubExpression){
    	timeExpression = timeExpression.replace(":", "");
    	timeExpression = timeExpression.replace("：", "");
    	List<String> rtList = Lists.newArrayList();
    	String[] arrays = timeExpression.split("[^\\d]");
    	
    	String tmpStr;
    	
    	for(String item : arrays){ 
			if(StringUtils.isBlank(item)){
    			continue;
    		}
			switch (item.length()) {
			case 2:
    			for(int i=0; i<24; i++){
    				tmpStr = i < 10 ? "0"+i+item : i+item;
    				rtList.add(tmpStr);
    			}				
				break;
			case 4:
				rtList.add(item);
				break;
			default:
				if(exceptionIfBadSubExpression){
					throw MeterExceptionFactory.applicationException("时间格式错误:"+item, null);
				}
			}
    	}
    	
    	return rtList;
    } 

    
    public static void main(String[] args) {
    	System.out.println(parseTimeExpression("00:00,01:11 02:12，03；04    1", false));
	}
    
    static void testSubForMonth(){
    	Date data1 = getDate("2016-03-28 00:00:00");
    	Date data2 = getDate("2017-03-28 00:00:00");
    	System.out.println(data1);
    	System.out.println(data2);
    	System.out.println("0 = " + TimeUtils.spaceForMonth(data2, data1));
    	System.out.println(data1);
    	System.out.println(data2);
    	System.out.println("0 = " + TimeUtils.spaceForMonth(data1, data2));
    	System.out.println(data1);
    	System.out.println(data2);
    	
    	
    	System.out.println("0 = " + TimeUtils.spaceForMonth(getDate("2016-03-28 00:00:00"), getDate("2017-03-28 00:00:00")));
    	System.out.println("0 = " + TimeUtils.spaceForMonth(getDate("2016-03-29 00:00:00"), getDate("2017-03-29 00:00:00")));
    	System.out.println("0 = " + TimeUtils.spaceForMonth(getDate("2016-04-29 00:00:00"), getDate("2017-04-29 00:00:00")));
    	System.out.println("0 = " + TimeUtils.spaceForMonth(getDate("2016-05-29 00:00:00"), getDate("2017-05-29 00:00:00")));
    	System.out.println("0 = " + TimeUtils.spaceForMonth(getDate("2016-06-29 00:00:00"), getDate("2017-06-29 00:00:00")));
    	System.out.println("0 = " + TimeUtils.spaceForMonth(getDate("2016-07-29 00:00:00"), getDate("2017-07-29 00:00:00")));
    	System.out.println("0 = " + TimeUtils.spaceForMonth(getDate("2016-08-29 00:00:00"), getDate("2017-08-29 00:00:00")));
    	System.out.println("0 = " + TimeUtils.spaceForMonth(getDate("2016-09-29 00:00:00"), getDate("2017-09-29 00:00:00")));
    	System.out.println("0 = " + TimeUtils.spaceForMonth(getDate("2016-09-29 12:12:12"), getDate("2017-09-29 12:12:12")));
    	
    	System.out.println("0 = " + TimeUtils.spaceForMonth(getDate("2009-02-01 00:00:00"), getDate("2009-02-01 00:00:00")));
    	System.out.println("1 = " + TimeUtils.spaceForMonth(getDate("2009-02-01 00:00:00"), getDate("2009-02-01 00:00:01")));
    	System.out.println("1 = " + TimeUtils.spaceForMonth(getDate("2009-02-01 00:00:00"), getDate("2009-03-01 00:00:00")));
    	
    	System.out.println("2 = " + TimeUtils.spaceForMonth(getDate("2009-02-01 00:00:00"), getDate("2009-03-01 00:00:01")));
    	System.out.println("2 = " + TimeUtils.spaceForMonth(getDate("2009-02-01 00:00:00"), getDate("2009-03-31 23:59:59")));
    	 
    	System.out.println("0 = " + TimeUtils.spaceForMonth(getDate("2009-03-31 00:00:00"), getDate("2009-03-31 00:00:00")));
    	System.out.println("0 = " + TimeUtils.spaceForMonth(getDate("2009-03-31 00:00:01"), getDate("2009-03-31 00:00:01")));
    	System.out.println("1 = " + TimeUtils.spaceForMonth(getDate("2009-03-31 00:00:00"), getDate("2009-03-31 00:00:01")));
    	System.out.println("1 = " + TimeUtils.spaceForMonth(getDate("2009-03-31 23:59:59"), getDate("2009-04-01 00:00:00")));
    	System.out.println("1 = " + TimeUtils.spaceForMonth(getDate("2009-03-31 23:59:57"), getDate("2009-04-30 23:59:57")));
    	System.out.println("2 = " + TimeUtils.spaceForMonth(getDate("2009-03-31 23:59:57"), getDate("2009-04-30 23:59:58"))); 
    	

    	System.out.println("2 = " + TimeUtils.spaceForMonth(getDate("2009-02-01 00:00:00"), getDate("2009-03-31 23:59:59")));
    	System.out.println("14 = " + TimeUtils.spaceForMonth(getDate("2009-02-01 00:00:00"), getDate("2010-03-31 23:59:59")));
    	System.out.println("26 = " + TimeUtils.spaceForMonth(getDate("2009-02-01 00:00:00"), getDate("2011-03-31 23:59:59")));
    	System.out.println("38 = " + TimeUtils.spaceForMonth(getDate("2009-02-01 00:00:00"), getDate("2012-03-31 23:59:59")));
    	
    	System.out.println("2 = " + TimeUtils.spaceForMonth(getDate("2009-02-01"), getDate("2009-03-31")));
    	System.out.println("14 = " + TimeUtils.spaceForMonth(getDate("2009-02-01"), getDate("2010-03-31")));
    	System.out.println("26 = " + TimeUtils.spaceForMonth(getDate("2009-02-01"), getDate("2011-03-31")));
    	System.out.println("38 = " + TimeUtils.spaceForMonth(getDate("2009-02-01"), getDate("2012-03-31")));
    }
}
