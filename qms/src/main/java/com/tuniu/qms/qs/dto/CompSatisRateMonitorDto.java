package com.tuniu.qms.qs.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qs.model.CompSatisRateMonitor;

public class CompSatisRateMonitorDto extends BaseDto<CompSatisRateMonitor> {
	

	private Integer timeType;//时间粒度
	
	private String qYear;
	
	private String quarter;
	
	private String quarterBgn;
	
	private String quarterEnd;
	
	private String monthBgn;
	
	private String monthEnd;
	
	private String monthStart;
	
	private String monthFinish;
	
	private String quarterStart;
	
	private String quarterFinish;
	
	private String mYear;
	
	private String month;
	
	private String searchMonth;
	
	private String searchQuarter;
	
	private int nowYear;
	
	private String businessUnit;
	
	private String prdDep;
	
	private String prdTeam;
	
    private Integer targetId;
   
    private String year;
    
    private String befYear;
    
    private String searchYear ;
    
	private String quarter1;
	
	private String quarter2;
	
	private String quarter3;
	
	private String quarter4;
	
	private String month1;
	
	private String month2;
	
	private String month3;
	
	private String month4;
	
	private String month5;
	
	private String month6;
	
	private String month7;
	
	private String month8;
	
	private String month9;
	
	private String month10;
	
	private String month11;
	
	private String month12;
	
    private String url ="";	
    
    //产品专员
    private String prdSpecialist;
    
    //产品经理
    private String prdManager;

    
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(String searchYear) {
		this.searchYear = searchYear;
	}

	public String getMonth1() {
		return month1;
	}

	public void setMonth1(String month1) {
		this.month1 = month1;
	}

	public String getMonth2() {
		return month2;
	}

	public void setMonth2(String month2) {
		this.month2 = month2;
	}

	public String getMonth3() {
		return month3;
	}

	public void setMonth3(String month3) {
		this.month3 = month3;
	}

	public String getMonth4() {
		return month4;
	}

	public void setMonth4(String month4) {
		this.month4 = month4;
	}

	public String getMonth5() {
		return month5;
	}

	public void setMonth5(String month5) {
		this.month5 = month5;
	}

	public String getMonth6() {
		return month6;
	}

	public void setMonth6(String month6) {
		this.month6 = month6;
	}

	public String getMonth7() {
		return month7;
	}

	public void setMonth7(String month7) {
		this.month7 = month7;
	}

	public String getMonth8() {
		return month8;
	}

	public void setMonth8(String month8) {
		this.month8 = month8;
	}

	public String getMonth9() {
		return month9;
	}

	public void setMonth9(String month9) {
		this.month9 = month9;
	}

	public String getMonth10() {
		return month10;
	}

	public void setMonth10(String month10) {
		this.month10 = month10;
	}

	public String getMonth11() {
		return month11;
	}

	public void setMonth11(String month11) {
		this.month11 = month11;
	}

	public String getMonth12() {
		return month12;
	}

	public void setMonth12(String month12) {
		this.month12 = month12;
	}

	public String getQuarter1() {
		return quarter1;
	}

	public void setQuarter1(String quarter1) {
		this.quarter1 = quarter1;
	}

	public String getQuarter2() {
		return quarter2;
	}

	public void setQuarter2(String quarter2) {
		this.quarter2 = quarter2;
	}

	public String getQuarter3() {
		return quarter3;
	}

	public void setQuarter3(String quarter3) {
		this.quarter3 = quarter3;
	}

	public String getQuarter4() {
		return quarter4;
	}

	public void setQuarter4(String quarter4) {
		this.quarter4 = quarter4;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getBefYear() {
		return befYear;
	}

	public void setBefYear(String befYear) {
		this.befYear = befYear;
	}

	public String getMonthStart() {
		return monthStart;
	}

	public void setMonthStart(String monthStart) {
		this.monthStart = monthStart;
	}

	public String getMonthFinish() {
		return monthFinish;
	}

	public void setMonthFinish(String monthFinish) {
		this.monthFinish = monthFinish;
	}

	public String getQuarterStart() {
		return quarterStart;
	}

	public void setQuarterStart(String quarterStart) {
		this.quarterStart = quarterStart;
	}

	public String getQuarterFinish() {
		return quarterFinish;
	}

	public void setQuarterFinish(String quarterFinish) {
		this.quarterFinish = quarterFinish;
	}

	public String getQuarterBgn() {
		return quarterBgn;
	}

	public void setQuarterBgn(String quarterBgn) {
		this.quarterBgn = quarterBgn;
	}

	public String getQuarterEnd() {
		return quarterEnd;
	}

	public void setQuarterEnd(String quarterEnd) {
		this.quarterEnd = quarterEnd;
	}

	public String getMonthBgn() {
		return monthBgn;
	}

	public void setMonthBgn(String monthBgn) {
		this.monthBgn = monthBgn;
	}

	public String getMonthEnd() {
		return monthEnd;
	}

	public void setMonthEnd(String monthEnd) {
		this.monthEnd = monthEnd;
	}

	public Integer getTargetId() {
	return targetId;
}

public void setTargetId(Integer targetId) {
	this.targetId = targetId;
}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getPrdDep() {
		return prdDep;
	}

	public void setPrdDep(String prdDep) {
		this.prdDep = prdDep;
	}

	public String getPrdTeam() {
		return prdTeam;
	}

	public void setPrdTeam(String prdTeam) {
		this.prdTeam = prdTeam;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public String getqYear() {
		return qYear;
	}

	public void setqYear(String qYear) {
		this.qYear = qYear;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public String getmYear() {
		return mYear;
	}

	public void setmYear(String mYear) {
		this.mYear = mYear;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getSearchMonth() {
		return searchMonth;
	}

	public void setSearchMonth(String searchMonth) {
		this.searchMonth = searchMonth;
	}

	public String getSearchQuarter() {
		return searchQuarter;
	}

	public void setSearchQuarter(String searchQuarter) {
		this.searchQuarter = searchQuarter;
	}

	public int getNowYear() {
		return nowYear;
	}

	public void setNowYear(int nowYear) {
		this.nowYear = nowYear;
	}

	public String getPrdSpecialist() {
		return prdSpecialist;
	}

	public void setPrdSpecialist(String prdSpecialist) {
		this.prdSpecialist = prdSpecialist;
	}

	public String getPrdManager() {
		return prdManager;
	}

	public void setPrdManager(String prdManager) {
		this.prdManager = prdManager;
	}
	
}
