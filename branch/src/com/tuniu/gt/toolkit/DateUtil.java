package com.tuniu.gt.toolkit;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil{
	
	private static Log LOG = LogFactory.getLog(DateUtil.class);
	
	public static final String DATE_TIME_PAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATE_PAT = "yyyy-MM-dd";
	
	public static Calendar ca;
	
	private static Calendar getCalendar(){
	    if(ca==null){
	        ca = Calendar.getInstance();
	        ca.setFirstDayOfWeek(Calendar.SUNDAY);
	        ca.setMinimalDaysInFirstWeek(1);
	    }
	    
	    return ca;
	}
	
	/**
	 * Parses a string representing a date by trying a variety of different parser.
	 * @param str
	 * @param parsePattern
	 * @return
	 */
	public static Date parseDate(String str,String parsePattern){
		try {
			return DateUtils.parseDate(str, new String[]{parsePattern});
		} catch (ParseException e) {
			LOG.error("ParseDate error:str = "+str+";parsePattern="+parsePattern,e);
		}
		return null;
	}
	
	public static Date parseDate(String str){
	    if("".equals(str)|| "null".endsWith(str)){
	        return null;
	    }
	    
		return parseDate(str, "yyyy-MM-dd");
	}
	
	public static int getDayOfWeek(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		int day=cal.get(Calendar.DAY_OF_WEEK);
		if (day==1){
			return 7;
		}else {
			return day-1;
		}
	}
	
	public static int getDayOfWeek(java.sql.Date date) {
		return getDayOfWeek(new Date(date.getTime()));
	}
	
	public static Date parseDateByYear(String str) {
		return parseDate(str, "yyyy");
	}
	
	public static Date parseDateTime(String str) {
		return parseDate(str, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static java.sql.Date parseSqlDate(String str, String pattern) {
		Date utilDate = parseDate(str, pattern);
		return new java.sql.Date(utilDate.getTime());
	}
	
	/**
	 * format data to my style
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format){
		String result = "";
		if (null != date) {
			SimpleDateFormat myFmt = new SimpleDateFormat(format);   
			myFmt.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			result = myFmt.format(date);
		}
		return result;
	}
	
	public static String formatDate(Date date){
		return formatDate(date,"yyyy-MM-dd");
	}
	
	public static String formatDateTime(Date date){
		return formatDate(date,"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 不需要考虑8小时时差
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate2(Date date,String format){
		SimpleDateFormat myFmt=new SimpleDateFormat(format);   
		return myFmt.format(date);
	}
	public static String formatDate2(Date date){
		return formatDate2(date,"yyyy-MM-dd");
	}
	
	public static String formatDateTime2(Date date){
		return formatDate2(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 获取距离date+days天的日期
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getSomeDay(Date date,int days){
		return DateUtils.addDays(date, days);
	}
	
	@SuppressWarnings("static-access")
	public static Date setHour(Date date,int hour){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(c.HOUR_OF_DAY, hour);
		return c.getTime();
	}
	
	public  static Date addMinute (Date date,int time){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, time);
		return cal.getTime();
	}
	
	/**
	 * 获取java.sql.Date类型的今天日期
	 * @return
	 */
	public static java.sql.Date getSqlToday() {
		return new java.sql.Date(new Date().getTime());
	}
	
	public static boolean isRestDay(List<java.sql.Date> fesList, List<java.sql.Date> offList, Date date) {
		return isRestDay(fesList, offList, new java.sql.Date(date.getTime()));
	}
	
	/**
	 * 判断某一天是否是节假日
	 * @param fesList，节假日List
	 * @param offList，补休日List
	 * @param sqlDate，某天
	 * @return
	 */
	public static boolean isRestDay(List<java.sql.Date> fesList, List<java.sql.Date> offList, java.sql.Date sqlDate) {
		boolean flag = false;
		
		if (dateListIsContains(fesList, sqlDate)) { // 如果是节假日，肯定是休息日
			flag = true;
		} else if (!dateListIsContains(offList, sqlDate) && 
				(6 == getDayOfWeek(sqlDate) || 7 == getDayOfWeek(sqlDate))) { // 如果非补休日且是周六日，肯定是休息日
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * 获取明天早上9点日期时间点
	 * @return
	 */
	public static Date getNexDateNine() {
		return getNexDateNine(new Date());
	}
	
	/**
	 * 获取第二天早上9点日期时间点
	 * @return
	 */
	public static Date getNexDateNine(Date dateTime) {
		String nextDateNine = formatDate(getSomeDay(dateTime, 1)) + " 09:00:00";
		return parseDate(nextDateNine, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 获取第二天0点
	 * @return
	 */
	public static String getNexDateTime(Date dateTime) {
		String nextDateNine = formatDate(getSomeDay(dateTime, 1)) + " 00:00:00";
		return nextDateNine;
	}
	
	
	public static String getYesterdayStr() {
		Date yesterday = getSomeDay(new Date(), -1);
		return formatDate(yesterday, "yyyy-MM-dd");
	}
	
	public static String getTomorrowStr() {
		Date yesterday = getSomeDay(new Date(), 1);
		return formatDate(yesterday, "yyyy-MM-dd");
	}
	
	/**
	 * 判断sqlDateList是否包含sqlDate
	 * @param dateList
	 * @param sqlDate
	 * @return
	 */
	public static boolean dateListIsContains(List<java.sql.Date> dateList, java.sql.Date sqlDate) {
		boolean flag = false;
		for (java.sql.Date date : dateList) {
			if (date.toString().equals(sqlDate.toString())) {
				flag = true;
			}
		}
		return flag;
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
	 * 获取当前日期上周的第一天
	 * @return
	 */
	public static Date getLastWeekFirstDay(Date date){
		
		Calendar calendar = getCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -7);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		return calendar.getTime();
	}
	
	
	/**
	 * 获取当前日期上周的的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastWeekEndDay(Date date) {
		Calendar calendar = getCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -7);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);
		return calendar.getTime();
	}
	
	
	/**
	 * 获取日期所在周
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date){
		
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		int c =calendar.get(Calendar.WEEK_OF_YEAR);
		return c;
		
	}
	/**
	 * 获取时间差
	 * @param assignTime
	 * @param statisticDate
	 * @return
	 */
	public  static long getbetweenTime(Date assignTime,Date statisticDate){
		
		  long diff = statisticDate.getTime() - assignTime.getTime();
		//  long minute = diff / (1000 * 60 );
		  return diff;
	}
	
	
	public  static String addTime (Date date,int time){
		
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
		 cal.set(Calendar.MINUTE, time);
	     String addDate = formatDateTime(cal.getTime());
		 return addDate;
		
	}
	/**
	 * 获取百分比
	 * @param num
	 * @param allNum
	 * @return
	 */
	public static String getPercent(int num ,int allNum){
		
		double percent = (double) num/(double)allNum;
		double rate=Math.round(percent*10000)/10000.0000 ;
		NumberFormat fmt = NumberFormat.getPercentInstance(); 
		fmt.setMinimumFractionDigits(2);//设置保留2位小数
	    String percentNum = fmt.format(rate); 
		return percentNum;
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
		Calendar c = getCalendar();
		c.setTime(date);
		int fieldValue=c.get(field);
		if(field==Calendar.MONTH){
			fieldValue++;
		}
		return fieldValue;
	}
	
	/**
	 * 获取指定日期的上个月开始短日期
	 * @param date
	 * @return
	 */
	public static String getPreMonthFirstShortDay(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c=DateUtils.truncate(c, Calendar.DAY_OF_MONTH);
		return formatDate(c.getTime());
	}
	
	public static String getPreMonthLastShortDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		c=DateUtils.truncate(c, Calendar.DAY_OF_MONTH);
		return formatDate(c.getTime());
	}
	
	public static String getMonthFirstShortDay(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c=DateUtils.truncate(c, Calendar.DAY_OF_MONTH);
		return formatDate(c.getTime());
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
		return formatDate(cal.getTime());
		
	}
	
	public static String getNextDayStr(String baseDate){
	    Date tempDate = parseDate(baseDate);
	    Calendar c = getCalendar();
	    c.setTime(tempDate);
	    c.add(Calendar.DAY_OF_MONTH, 1);
	    return formatDate(c.getTime());
	}
	
	/**
	 * @param date1
	 * @param date2
	 * @return  0:相等   -1：date1小   1：date1大
	 */
	public static int compare(String date1,String date2){
	    
	    return compare(parseDate(date1),parseDate(date2));
	}
	/**
     * @param parseDate
     * @param parseDate2
     * @return
     */
    private static int compare(Date parseDate, Date parseDate2) {
        int result = 0;
        if(parseDate.equals(parseDate2)){
            return 0;
        }
        
        boolean before = parseDate.before(parseDate2);
        return before?-1:1;
    }

    public static void main(String[] args) {
//		System.out.println(getField(Calendar.WEEK_OF_YEAR));
		/*System.out.println(getField(parseDate("2016-01-04"),Calendar.WEEK_OF_YEAR));
		System.out.println(getField(getFirstDayOfWeek(2016, 0),Calendar.WEEK_OF_YEAR));
		System.out.println(getFirstDayOfWeek(2016, 0));
		System.out.println(getLastDayOfWeek(2016, 1));*/
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    
//		System.out.println(getNextDayStr("2014-12-31"));
		System.out.println(compare("2014-12-31","2014-12-30"));
//		System.out.println(DateUtil.getField(new Date(),Calendar.HOUR_OF_DAY));
	}
    
    /** 获取月份 */
	public static int getMonth(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
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
}
