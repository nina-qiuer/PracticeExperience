package com.tuniu.qms.access.server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;


@Service("timeTest")
public class TimeTest {

	public void  testJob() {
		  Calendar cal = Calendar.getInstance();
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date date = cal.getTime();
		  String nowDate =sdf.format(date);
		//long justNow =System.currentTimeMillis();
		  
		  
		   String dateString = "2012-12-06 ";  
		   SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd ");  
		   try {
			   
				Date date1 = sdf1.parse(dateString);
				System.out.println(date1);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}  
		
	    System.out.println(nowDate);
		System.out.println("开始执行 任务"+Math.random()*10);
		
	}


}
