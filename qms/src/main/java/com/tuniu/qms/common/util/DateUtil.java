package com.tuniu.qms.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

import com.tuniu.qms.access.client.OaClient;

public class DateUtil extends DateUtils {
	
	public static Calendar ca;
	/** 获取当日剩余时间 */
	public static Date getTodaySurplusTime() {
		Long curMills = System.currentTimeMillis();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date(curMills));
		calendar.add(Calendar.DATE, 1); // 把日期往后增加一天.(整数往后推,负数往前移动)
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date surplusTime = new Date(calendar.getTimeInMillis() - curMills);
		return surplusTime;
	}
	
	/** 默认格式化日期字符串 */
	public static String formatAsDefaultDate(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}
	
	/** 默认格式化日期时间字符串 */
	public static String formatAsDefaultDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/** 日期格式化 */
	public static String formatDate(Date date, String pattern) {
		String result = "";
		if (null != date) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			result = sdf.format(date);
		}
		return result;
	}
	
	public static java.sql.Date getSqlYesterday() {
		return addSqlDates(new Date(), -1);
	}
	
	public static java.sql.Date addSqlDates(Date date, int num) {
		return new java.sql.Date(addDays(date, num).getTime());
	}
	
	public static java.sql.Date addSqlDates(java.sql.Date date, int num) {
		return new java.sql.Date(addDays(date, num).getTime());
	}
	
	/** 计算两个日期之间间隔的天数 */
	public static int getDaysBetween(Date dateBgn, Date dateEnd) {
		Long milliseconds = dateEnd.getTime() - dateBgn.getTime();
		Long msOneDay = (long) (1000 * 3600 * 24);
		return (int) MathUtil.div(milliseconds, msOneDay, 0);
	}
	
	/** 计算两个日期之间间隔的天数 */
	public static int getDaysBetween(java.sql.Date dateBgn, java.sql.Date dateEnd) {
		return getDaysBetween(new Date(dateBgn.getTime()), new Date(dateEnd.getTime()));
	}
	
	public static Date parseUtilDate(String str) {
		try {
			return parseDate(str, "yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date parseDateTime(String str) {
		try {
			return parseDate(str, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static java.sql.Date parseSqlDate(String str) {
		return new java.sql.Date(parseUtilDate(str).getTime());
	}

	/**
	 * 获取当月第一天
	 * @return
	 */
	public static String getMonthFirstDay(){
		
		Calendar cal = Calendar.getInstance();
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		Date beginTime = cal.getTime();
		String billStartTime = formatAsDefaultDate(beginTime) ;
	//	Date beginTimes = parseDateTime(billStartTime);
		return billStartTime;
		
	}
	
	/**
	 * 获取当月最后一天
	 * @return
	 */
	public static String getMonthEndDay(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		Date endTime = cal.getTime();
		String billEndTime = formatAsDefaultDate(endTime) ;
		//Date endTimes = parseDateTime(billEndTime);
		return billEndTime;
	}
	
	/**
	 * 获取当前日期上一天
	 * @return
	 */
	public static String getBeforeDay(){
		
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return formatAsDefaultDate(cal.getTime());
		
	}

	
	public static String getTodayTime(){
		
		String todayTime = formatAsDefaultDate(new Date());
		return todayTime;
	}

	/**
	 * 获取当前时间前12小时
	 */
	public static String getBeforeDayTime(){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - 12);
		return 	DateUtil.formatAsDefaultDateTime(cal.getTime());
		
	}
	
	/** 获取日期 */
	public static int getDay(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}
	
	public static int getDay(java.sql.Date date) {
		return getDay(new Date(date.getTime()));
	}
	
	/** 获取年份 */
	public static int getYear(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	
	public static int getYear(java.sql.Date date) {
		return getYear(new Date(date.getTime()));
	}
	
	/**
	 * 获取上一年
	 * @param date
	 * @return
	 */
	 public static int getBeforeYear(Date date){
		 
		   Calendar ca = Calendar.getInstance();
		   ca.setTime(date); 
		   ca.add(Calendar.YEAR, -1); //年份减1 
		   Date beforeDate = ca.getTime();
		   return DateUtil.getYear(beforeDate);
		 
	 }
	
	/** 获取月份 */
	public static int getMonth(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	/** 获取年月 */
	public static int getYearAndMonth(Date date){
		
		int year = getYear(date);
		int month = getMonth(date);
		String yearMonth = "";
		if(month<10){
			yearMonth = "0"+String.valueOf(month);
		}else{
			yearMonth = String.valueOf(month);
		}
		return Integer.parseInt(String.valueOf(year)+yearMonth);
		
	}
	
	/** 获取季度 */
	public static int getQuarter(Date date) {
	
		int quarter =0;
		int month = getMonth(date);
		if(month == 1 || month ==2 || month ==3){
			quarter = 1;
		}
		else if(month == 4 || month ==5 || month ==6){
			quarter = 2;
		}
		else if(month == 7 || month ==8 || month ==9){
			
			quarter = 3;
		}
		else if(month == 10 || month ==11 || month ==12){
			
			quarter = 4;
		}
		return quarter;
	}
	
	/** 获取年季 */
	public static int getYearAndQuarter(Date date){
		
		int year = getYear(date);
		int quarter = getQuarter(date);
		String yearQuarter ="0"+String.valueOf(quarter);
		return Integer.parseInt(String.valueOf(year)+yearQuarter);
		
	}
	
	/**
	 * 获取日期所在周
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date){
		
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setMinimalDaysInFirstWeek(1);
		calendar.setTime(date);
		int c =calendar.get(Calendar.WEEK_OF_YEAR);
		return c;
		
	}
	
	public static String getNowWeek(Date date){
		String nowWeek ="";
		int week = getWeek(date);
		if(week<10){
			nowWeek = "0"+String.valueOf(week);
		}else{
			nowWeek = String.valueOf(week);
		}
		return nowWeek;
	}
	
	/** 获取年周*/
	public static int getYearAndWeek(Date date){
		int realWeek=0;
		int year = getYear(date);
		int week = getWeek(date);
		int month = getMonth(date);
		String yearWeek ="";
		if(week<10){
			yearWeek = "0"+String.valueOf(week);
		}else{
			yearWeek = String.valueOf(week);
		}
		
		realWeek=Integer.parseInt(String.valueOf(year)+yearWeek);
		if(month==12){
				
			if(week<3){
					
				int nextYear = year+1;
				realWeek = Integer.parseInt(String.valueOf(nextYear)+ yearWeek);
			}
		}
		return realWeek;
		
	}
	
	
	public static int getMonth(java.sql.Date date) {
		return getMonth(new Date(date.getTime()));
	}
	
	public static java.sql.Date getLongTime(long time){
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date date = new java.sql.Date(time);
	    String longdate =  sdf.format(date);
	    return parseSqlDate(longdate);
	}
	
//	public static Date getSomeDay(Date date,int days){
//	
//		Calendar calendar = Calendar.getInstance();//日历对象
//    	calendar.setTime(date);
//	    calendar.add(Calendar.DAY_OF_MONTH, days);//天数相加
//    	return calendar.getTime();
//	}
	
	/**
	 * 获取几个工作日之后的结束时间
	 * @param beginTime
	 * @param days
	 * @return
	 */
	public static Date  getFinishDate(Date beginTime,int days){
		 
		 Date endTime = new Date();
		 for (int i=0; i<=days; i++) {
				endTime = DateUtil.addSqlDates(beginTime, i);
	            int flag = OaClient.getDayIsWork(endTime);
				if(flag == 9){//接口失败
	            	return null;
	            }else if (flag !=0) { // 如果是休息日，则往后推一天
	            	days++;
	            }
			}
		 return endTime;
	 }
	
	public static String getLastYearOrDay(String dateStr,int addYear, int addMonth, int addDate)  {  
		
	    String dateTmp ="";
        try {  
        	
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	        Date sourceDate = sdf.parse(dateStr);  
	        Calendar cal = Calendar.getInstance();  
	        cal.setTime(sourceDate);  
	        cal.add(Calendar.YEAR,addYear);  
	        cal.add(Calendar.MONTH, addMonth);  
	        cal.add(Calendar.DATE, addDate);  
	        SimpleDateFormat returnSdf = new SimpleDateFormat("yyyy-MM-dd");  
	        dateTmp = returnSdf.format(cal.getTime());  
	       
        } catch (Exception e) {  
        	
        	e.printStackTrace();  
        }  
        return dateTmp;  
	}
	

	/**
	 * 获取当前日期的各字段
	 * @param field
	 * @return
	 */
	public static int getField(int field){
		return getField(new Date(),field);
	}
	
	/**
	 * 获取指定日期的各字段
	 * @param date
	 * @param field
	 * @return
	 */
	public static int getField(Date date, int field){
		Calendar c =  Calendar.getInstance();
		c.setTime(date);
		int fieldValue=c.get(field);
		if(field==Calendar.MONTH){
			fieldValue++;
		}
		return fieldValue;
	}
	
	public static Map<String, Object> getYearAndWeek(){
		
		Map<String, Object> map =new HashMap<String, Object>();
		int weekBgn = 0;
		int weekEnd = 0;
		int wYearBgn = 0;
		int wYearEnd = 0;
		int beforeYear = DateUtil.getBeforeYear(new Date());
	    weekEnd = DateUtil.getWeek(new Date());
	    if(weekEnd>6){
		   
		    weekBgn = weekEnd -6;
		    wYearBgn = DateUtil.getYear(new Date());
		    wYearEnd =  DateUtil.getYear(new Date());
	   }else{
		   
		     Calendar c = Calendar.getInstance(); 
			 c.set(beforeYear, Calendar.DECEMBER, 31, 23, 59, 59);  
		     int week =DateUtil.getWeek(c.getTime());
		     if(week ==1){
		    	 
		    	 weekBgn = 52 - (6-weekEnd);
		     }else{
		    	 
		    	 weekBgn = 53 - (6-weekEnd);
		     }
		     wYearBgn =  beforeYear;
			 wYearEnd =  DateUtil.getYear(new Date());
	   }
	    
	    if(weekBgn<10){
	    	
	         map.put("weekBgn", "0"+String.valueOf(weekBgn));
	         
		}else{
			
			 map.put("weekBgn", weekBgn); 
		}
	    if(weekEnd<10){
	    	
	    	map.put("weekEnd", "0"+String.valueOf(weekEnd)); 
	    	
		}else{
			
			map.put("weekEnd", weekEnd); 
		}
	    map.put("wYearBgn", wYearBgn);
	    map.put("wYearEnd", wYearEnd);
		return map;
	}
	
	public static List<String> monthList(){
		
		List<String> list =new ArrayList<String>();
		for(int i=1 ;i<=12;i++){
			
			if(i<10){
				
				list.add("0"+String.valueOf(i));
			}else{
				
				list.add(String.valueOf(i));
			}
		}
		return list;
		
	}
	
	public static List<String> weekList(){
		
		List<String> list =new ArrayList<String>();
		
		for(int i=1 ;i<=53;i++){
			if(i<10){
				
				list.add("0"+String.valueOf(i));
			}else{
				
				list.add(String.valueOf(i));
			}
			
		}
		
		return list;
		
	}
	
	public static List<Integer> newWeekList(){
		
		List<Integer> list =new ArrayList<Integer>();
		
		for(int i=1 ;i<=53;i++){
				
			list.add(i);
			
		}
		
		return list;
		
	}
	
	/**
	 * 取得指定年指定周的第一天
	 * 
	 * @param year
	 *            week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
        Calendar calendar = getCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week);

        return getFirstDayOfWeek(calendar.getTime());
	}
	
	/**
	 * 取得当前日期所在周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar calendar = getCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Monday
		return calendar.getTime();
	}
	
	
	/**
	 * 取得指定年指定周的最后一天
	 * 
	 * @param year
	 *            week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
	        Calendar calendar = getCalendar();
	        calendar.set(Calendar.YEAR, year);
	        calendar.set(Calendar.WEEK_OF_YEAR, week);
	        return getLastDayOfWeek(calendar.getTime());
	}
	
	/**
	 * 取得当前日期所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar calendar = getCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Sunday
		return calendar.getTime();
	}

	private static Calendar getCalendar(){
	    if(ca==null){
	        ca = Calendar.getInstance();
	        ca.setFirstDayOfWeek(Calendar.SUNDAY);
	        ca.setMinimalDaysInFirstWeek(1);
	    }
	    
	    return ca;
	}
	public static void main(String[] args) {
		String str = "2016-12-31";
		Date date = parseUtilDate(str);
		int d = getMonth(date);
		if(d==12){
				
			int week =  DateUtil.getWeek(date);
				if(week<3){
					
					int year =DateUtil.getYear(date)+1;
					System.out.println(year);
				}
			}
			
		System.out.println(d);
		System.out.println(getYear(getSqlYesterday()));
	}
	
}
