package com.tuniu.qms.report.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.report.model.QualityCostReport;

public class QualityCostReportDto extends BaseDto<QualityCostReport> {
	
	
	
    private String firstDepName;
		
    private String twoDepName;
		
	private String threeDepName;
	
	private Integer firstDepId;
		
	private Integer twoDepId;
		
	private Integer threeDepId;
	
	private String firstCostAccount;
	
	private String twoCostAccount;
	
	private String threeCostAccount;
	
	private String depName;
	
	private Integer depType;
	
	private Integer jobId;
	
	private String jobName;
	
	private String qptName;
	
	private Integer qptType;//质量问题类型
	
	private String respPersonName;

	private Integer timeType;//时间粒度
	
	private String yearBgn;
	
	private String yearEnd;
	
	private String qYearBgn;
	
	private String qYearEnd;
	
	private String quarterBgn;
	
	private String quarterEnd;
	
	private String mYearBgn;
	
	private String mYearEnd;
	
	private String monthBgn;
	
	private String monthEnd;
	
	private String wYearBgn;
	
	private String wYearEnd;
	
	private String weekBgn;
	
	private String weekEnd;
	
	private String weekStart;
	
	private String weekFinish;
	
	private String monthStart;
	
	private String monthFinish;
	
	private String quarterStart;
	
	private String quarterFinish;
	
	private int nowYear;
	
	private Integer viewFlag=1; //维度
	
	


	public String getFirstCostAccount() {
		return firstCostAccount;
	}

	public void setFirstCostAccount(String firstCostAccount) {
		this.firstCostAccount = firstCostAccount;
	}

	public String getTwoCostAccount() {
		return twoCostAccount;
	}

	public void setTwoCostAccount(String twoCostAccount) {
		this.twoCostAccount = twoCostAccount;
	}

	public String getThreeCostAccount() {
		return threeCostAccount;
	}

	public void setThreeCostAccount(String threeCostAccount) {
		this.threeCostAccount = threeCostAccount;
	}

	public String getWeekFinish() {
		return weekFinish;
	}

	public void setWeekFinish(String weekFinish) {
		this.weekFinish = weekFinish;
	}

	public String getMonthFinish() {
		return monthFinish;
	}

	public void setMonthFinish(String monthFinish) {
		this.monthFinish = monthFinish;
	}

	public String getQuarterFinish() {
		return quarterFinish;
	}

	public void setQuarterFinish(String quarterFinish) {
		this.quarterFinish = quarterFinish;
	}

	public String getMonthStart() {
		return monthStart;
	}

	public void setMonthStart(String monthStart) {
		this.monthStart = monthStart;
	}


	public String getQuarterStart() {
		return quarterStart;
	}

	public void setQuarterStart(String quarterStart) {
		this.quarterStart = quarterStart;
	}


	public Integer getFirstDepId() {
		return firstDepId;
	}

	public void setFirstDepId(Integer firstDepId) {
		this.firstDepId = firstDepId;
	}

	public Integer getTwoDepId() {
		return twoDepId;
	}

	public void setTwoDepId(Integer twoDepId) {
		this.twoDepId = twoDepId;
	}

	public Integer getThreeDepId() {
		return threeDepId;
	}

	public void setThreeDepId(Integer threeDepId) {
		this.threeDepId = threeDepId;
	}

	public String getWeekStart() {
		return weekStart;
	}

	public void setWeekStart(String weekStart) {
		this.weekStart = weekStart;
	}

	public Integer getDepType() {
		return depType;
	}

	public void setDepType(Integer depType) {
		this.depType = depType;
	}

	public String getwYearBgn() {
		return wYearBgn;
	}

	public void setwYearBgn(String wYearBgn) {
		this.wYearBgn = wYearBgn;
	}

	public String getwYearEnd() {
		return wYearEnd;
	}

	public void setwYearEnd(String wYearEnd) {
		this.wYearEnd = wYearEnd;
	}

	public String getmYearBgn() {
		return mYearBgn;
	}

	public void setmYearBgn(String mYearBgn) {
		this.mYearBgn = mYearBgn;
	}

	public String getmYearEnd() {
		return mYearEnd;
	}

	public void setmYearEnd(String mYearEnd) {
		this.mYearEnd = mYearEnd;
	}

	public String getqYearBgn() {
		return qYearBgn;
	}

	public void setqYearBgn(String qYearBgn) {
		this.qYearBgn = qYearBgn;
	}

	public String getqYearEnd() {
		return qYearEnd;
	}

	public void setqYearEnd(String qYearEnd) {
		this.qYearEnd = qYearEnd;
	}

	public int getNowYear() {
		return nowYear;
	}

	public void setNowYear(int nowYear) {
		this.nowYear = nowYear;
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

	public String getWeekBgn() {
		return weekBgn;
	}

	public void setWeekBgn(String weekBgn) {
		this.weekBgn = weekBgn;
	}

	public String getWeekEnd() {
		return weekEnd;
	}

	public void setWeekEnd(String weekEnd) {
		this.weekEnd = weekEnd;
	}

    
	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public Integer getQptType() {
		return qptType;
	}

	public void setQptType(Integer qptType) {
		this.qptType = qptType;
	}

	public Integer getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(Integer viewFlag) {
		this.viewFlag = viewFlag;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}


	public String getYearBgn() {
		return yearBgn;
	}

	public void setYearBgn(String yearBgn) {
		this.yearBgn = yearBgn;
	}

	public String getYearEnd() {
		return yearEnd;
	}

	public void setYearEnd(String yearEnd) {
		this.yearEnd = yearEnd;
	}


	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}


	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}


	public String getQptName() {
		return qptName;
	}

	public void setQptName(String qptName) {
		this.qptName = qptName;
	}

	public String getRespPersonName() {
		return respPersonName;
	}

	public void setRespPersonName(String respPersonName) {
		this.respPersonName = respPersonName;
	}


	
	public String getFirstDepName() {
		return firstDepName;
	}

	public void setFirstDepName(String firstDepName) {
		this.firstDepName = firstDepName;
	}

	public String getTwoDepName() {
		return twoDepName;
	}

	public void setTwoDepName(String twoDepName) {
		this.twoDepName = twoDepName;
	}

	public String getThreeDepName() {
		return threeDepName;
	}

	public void setThreeDepName(String threeDepName) {
		this.threeDepName = threeDepName;
	}

	
	
}
