package com.xinxing.o.boss.common.time;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeUtils {
	private static final Logger log = Logger
			.getLogger(TimeUtils.class);
	
	/** 
     * 时间戳转换成日期格式字符串 
     * @param seconds 精确到秒的字符串 
     * @param formatStr 
     * @return 
     */  
    public static String timeStamp2Date(String seconds,String format) {  
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
            return "";  
        }  
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";  
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(new Date(Long.valueOf(seconds+"000")));  
    }  
    /** 
     * 日期格式字符串转换成时间戳 
     * @param date 字符串日期 
     * @param format 如：yyyy-MM-dd HH:mm:ss 
     * @return 
     */  
    public static String date2TimeStamp(String date_str,String format){  
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat(format);  
            return String.valueOf(sdf.parse(date_str).getTime()/1000);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  
      
    /** 
     * 取得当前时间戳（精确到毫秒） 
     * @return 
     */  
    public static String timeStamp(){  
        long time = System.currentTimeMillis();  
        String t = String.valueOf(time);  
        return t;  
    }  

    /** 
     * 取得当前时间戳（精确到秒） 
     * @return 
     */  
    public static String timeStampSecond(){  
        long time = System.currentTimeMillis()/1000;  
        String t = String.valueOf(time);  
        return t;  
    }  

    
    /**
     * timestamp转化
     * @param timestamp
     * @return
     */
    public static String timeStamp2DateTimeStr(Timestamp timestamp){
    	String format = "yyyy-MM-dd HH:mm:ss";
    	SimpleDateFormat sdf = new SimpleDateFormat(format);  
    	return sdf.format(timestamp);
    }
    
    
    /**
     * Date转化
     * @param date
     * @return
     */
    public static String date2DateTimeStr(Date date){
    	String format = "yyyyMMddHHmmss";
    	SimpleDateFormat sdf = new SimpleDateFormat(format);  
    	return sdf.format(date);
    }
    
    /**
     * Date转化
     * @param date
     * @return
     */
    public static String date2DateTimeFormateStr(Date date){
    	String format = "yyyy-MM-dd HH:mm:ss";
    	SimpleDateFormat sdf = new SimpleDateFormat(format);  
    	return sdf.format(date);
    }
    
    /**
     * 获取现在时间的格式 yyyyMMddHHmmss
     * @return
     */
    public static String getNowTimeFormate(){
    	Date date = new Date();
    	String format = "yyyyMMddHHmmss";
    	SimpleDateFormat sdf = new SimpleDateFormat(format);  
    	return sdf.format(date);
    }
    /**
     * 获取现在时间的格式 yyyyMMddHHmmssSSS
     * @return
     */
    public static String getSSSNowTimeFormate(){
    	Date date = new Date();
    	String format = "yyyyMMddHHmmssSSS";
    	SimpleDateFormat sdf = new SimpleDateFormat(format);  
    	return sdf.format(date);
    }
    
    /**
     * 获取现在时间的格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getNowTime(){
    	Date date = new Date();
    	String format = "yyyy-MM-dd HH:mm:ss";
    	SimpleDateFormat sdf = new SimpleDateFormat(format);  
    	return sdf.format(date);
    }
    
    /**
     * 返回秒数
     * @param dateTimeStr
     * @return
     */
    public static int getSecondPast(String dateTimeStr) {
		DateTime time = new DateTime();
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime dateTime = DateTime.parse(dateTimeStr, format);    
		long times =time.getMillis() - dateTime.getMillis();
		return (int)times/1000;
	}
    
    /**
     * 为时间添加小时数
     * @param date
     * @param i
     * @return
     */
    public static Date addHourForNow(Date date,int i){
    	Calendar c=Calendar.getInstance();
    	c.clear();
    	c.setTime(date);
    	c.add(Calendar.HOUR_OF_DAY, i);
    	return c.getTime();
    }
    
    /**
     * 为时间添加天数
     * @param date
     * @param i
     * @return
     */
    public static Date addDayForNow(Date date,int i){
    	Calendar c=Calendar.getInstance();
    	c.clear();
    	c.setTime(date);
    	c.add(Calendar.DAY_OF_MONTH, i);
    	return c.getTime();
    }
    
    /**
     * 为时间添加天数
     * @param date
     * @param i
     * @return
     */
    public static String addDayForNow(String date,int i){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date2=null;
    	Calendar c=null;
		try {
			date2 = sdf.parse(date);
	    	c=Calendar.getInstance();
	    	c.clear();
	    	c.setTime(date2);
	    	c.add(Calendar.DAY_OF_MONTH, i);
		} catch (ParseException e) {
			log.info("date:"+e.getMessage(),e);
		}
    	return sdf.format(c.getTime());
    }
    
    /**
     * 获取现在时间前几分钟的时间
     * @param minutes
     * @return
     */
    public static String getNowBeforeMinutes(int minutes){
    	DateTime time = new DateTime();
    	time = time.minusMinutes(minutes);
    	String timeStr = time.toString("yyyy-MM-dd HH:mm:ss");
    	return timeStr;
    }
    
    /**
     * 获取现在时间前几分钟的时间
     * @param minutes
     * @return
     */
    public static Date getNowBeforeMonth(int months){
    	Calendar c=Calendar.getInstance();
    	c.clear();
    	c.setTime(new Date());
    	c.add(Calendar.MONTH,months );
    	Date date = c.getTime();
    	return date;
    }
    
    /**
     * 获取现在时间前几秒的时间
     * @param minutes
     * @return
     */
    public static String getNowBeforeSeconds(int seconds){
    	Calendar c=Calendar.getInstance();
    	c.clear();
    	c.setTime(new Date());
    	c.add(Calendar.SECOND, -seconds);
    	Date date = c.getTime();
    	
    	
    	String format = "yyyy-MM-dd HH:mm:ss";
    	SimpleDateFormat sdf = new SimpleDateFormat(format);  
    	return sdf.format(date);
    }
    
    /**
     * 返回时间戳
     * @return yyyyMMddHHmmssSSS
     */
    public static String getNowTimeStamp(){
    	DateTime time = new DateTime();
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMddHHmmssSSS");
		return time.toString(format);
    }
    
    /**
     * 获取今天的 yyyy-MM-dd
     * @return
     */
    public static String getTodayDateStr(){
    	DateTime time = new DateTime();
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
		return time.toString(format);
    }
    
    /**
     * 获取当月第几天
     * @return
     */
    public static int getMonthDay(){
    	DateTime time = new DateTime();
    	return time.getDayOfMonth();
    }
    
    /**
     * 获取当前时间与传过来的时间相差秒数，正数则以超过该时间
     * @return
     */
    public static int getTwoDate(Date startDate){
    	Date endDate=new Date();
    	return (int) ((endDate.getTime()-startDate.getTime())/1000);
    }
   
    /**
     * 根据格式获取时间
     * @param pattem  yyMMddHHmmss  yyyy-MM-dd HH:mm:ss之类的格式
     * @return
     */
    public static String getDate(String pattem){
    	DateTime time = new DateTime();
    	DateTimeFormatter format = DateTimeFormat.forPattern(pattem);
    	return time.toString(format);
    }
    
   
    public static void main(String[] args) {
		
    	/*String date = getDate("yyyyMMddHHmmss");
    	System.out.println(date);*/
    	Date date = new Date(1473756173000L);
    	String str = date2DateTimeFormateStr(date);
    	System.out.println(str);
    	
    	int i = getTwoDate(date);
    	System.out.println(i);
    	
	}
	/**
	 * 传入yyyy-MM-dd HH:mm:ss
	 * @param time
	 * @return 日期
	 * @throws ParseException
	 */
	public static Date getDateByStr(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today=null;
		try {
			today = sdf.parse(time);
		} catch (ParseException e) {
			log.error("日期转换yyyy-MM-dd HH:mm:ss错误,time:"+time);
		}
		return today;
	}
}
