package com.tuniu.qms.test;

import java.text.ParseException;
import java.util.Date;

import com.tuniu.qms.common.util.DateUtil;

public class Test {

	public static void main(String[] args) {
		try {
			Date d1 = DateUtil.getSqlYesterday();
			Date d2 = DateUtil.parseDate("2015-07-31", "yyyy-MM-dd");
			System.out.println(DateUtil.getDaysBetween(d1, d2));
			
			System.out.println(Integer.MAX_VALUE);
			System.out.println(Long.MAX_VALUE);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
