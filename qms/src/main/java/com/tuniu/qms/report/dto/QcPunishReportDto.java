package com.tuniu.qms.report.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.report.model.QcPunishReport;

public class QcPunishReportDto extends BaseDto<QcPunishReport>{

	private Integer qcId;
	//关联部门 三级
    private String firstDepName;
		
    private String twoDepName;
		
	private String threeDepName;
	
	private Integer firstDepId;
		
	private Integer twoDepId;
		
	private Integer threeDepId;
	//质检类型 三级
	private String firstQcTypeName;
	
	private String secondQcTypeName;
	
	private String thirdQcTypeName;
	
	private Integer firstQcTypeId;
	
	private Integer secondQcTypeId;
	
	private Integer thirdQcTypeId;
	//质检组 三级
    private String firstQcGroupName;
		
    private String twoQcGroupName;
		
	private String threeQcGroupName;
	
	private Integer firstQcGroupId;
		
	private Integer twoQcGroupId;
		
	private Integer threeQcGroupId;
	//关联部门	
	private String depName;
	
	private Integer depType;
	//关联岗位
	private Integer jobId;
	
	private String jobName;
	//质检类型
	private String qcName;
	
	private Integer qcType;
	//被处罚人工号 
	private String pubPersonId;
	//被处罚人Name 
	private String punishPersonName;
	//质检人id
	private String qcPersonId;
	//质检人名称
	private String qcPerson;
	//质检组
	private String qcGroupName;
	
	private String qcGroupId;
	//时间粒度
	private Integer timeType;
	
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
	//维度
	private Integer viewFlag=1; 

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
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

	public String getFirstQcTypeName() {
		return firstQcTypeName;
	}

	public void setFirstQcTypeName(String firstQcTypeName) {
		this.firstQcTypeName = firstQcTypeName;
	}

	public String getSecondQcTypeName() {
		return secondQcTypeName;
	}

	public void setSecondQcTypeName(String secondQcTypeName) {
		this.secondQcTypeName = secondQcTypeName;
	}

	public String getThirdQcTypeName() {
		return thirdQcTypeName;
	}

	public void setThirdQcTypeName(String thirdQcTypeName) {
		this.thirdQcTypeName = thirdQcTypeName;
	}

	public Integer getFirstQcTypeId() {
		return firstQcTypeId;
	}

	public void setFirstQcTypeId(Integer firstQcTypeId) {
		this.firstQcTypeId = firstQcTypeId;
	}

	public Integer getSecondQcTypeId() {
		return secondQcTypeId;
	}

	public void setSecondQcTypeId(Integer secondQcTypeId) {
		this.secondQcTypeId = secondQcTypeId;
	}

	public Integer getThirdQcTypeId() {
		return thirdQcTypeId;
	}

	public void setThirdQcTypeId(Integer thirdQcTypeId) {
		this.thirdQcTypeId = thirdQcTypeId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public Integer getDepType() {
		return depType;
	}

	public void setDepType(Integer depType) {
		this.depType = depType;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getQcName() {
		return qcName;
	}

	public void setQcName(String qcName) {
		this.qcName = qcName;
	}

	public Integer getQcType() {
		return qcType;
	}

	public void setQcType(Integer qcType) {
		this.qcType = qcType;
	}

	public String getPubPersonId() {
		return pubPersonId;
	}

	public void setPubPersonId(String pubPersonId) {
		this.pubPersonId = pubPersonId;
	}

	public String getPunishPersonName() {
		return punishPersonName;
	}

	public void setPunishPersonName(String punishPersonName) {
		this.punishPersonName = punishPersonName;
	}

	public String getQcPersonId() {
		return qcPersonId;
	}

	public void setQcPersonId(String qcPersonId) {
		this.qcPersonId = qcPersonId;
	}

	public String getQcPerson() {
		return qcPerson;
	}

	public void setQcPerson(String qcPerson) {
		this.qcPerson = qcPerson;
	}

	public String getQcGroupName() {
		return qcGroupName;
	}

	public void setQcGroupName(String qcGroupName) {
		this.qcGroupName = qcGroupName;
	}

	public String getQcGroupId() {
		return qcGroupId;
	}

	public void setQcGroupId(String qcGroupId) {
		this.qcGroupId = qcGroupId;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
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

	public String getWeekStart() {
		return weekStart;
	}

	public void setWeekStart(String weekStart) {
		this.weekStart = weekStart;
	}

	public String getWeekFinish() {
		return weekFinish;
	}

	public void setWeekFinish(String weekFinish) {
		this.weekFinish = weekFinish;
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

	public int getNowYear() {
		return nowYear;
	}

	public void setNowYear(int nowYear) {
		this.nowYear = nowYear;
	}

	public Integer getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(Integer viewFlag) {
		this.viewFlag = viewFlag;
	}

	public String getFirstQcGroupName() {
		return firstQcGroupName;
	}

	public void setFirstQcGroupName(String firstQcGroupName) {
		this.firstQcGroupName = firstQcGroupName;
	}

	public String getTwoQcGroupName() {
		return twoQcGroupName;
	}

	public void setTwoQcGroupName(String twoQcGroupName) {
		this.twoQcGroupName = twoQcGroupName;
	}

	public String getThreeQcGroupName() {
		return threeQcGroupName;
	}

	public void setThreeQcGroupName(String threeQcGroupName) {
		this.threeQcGroupName = threeQcGroupName;
	}

	public Integer getFirstQcGroupId() {
		return firstQcGroupId;
	}

	public void setFirstQcGroupId(Integer firstQcGroupId) {
		this.firstQcGroupId = firstQcGroupId;
	}

	public Integer getTwoQcGroupId() {
		return twoQcGroupId;
	}

	public void setTwoQcGroupId(Integer twoQcGroupId) {
		this.twoQcGroupId = twoQcGroupId;
	}

	public Integer getThreeQcGroupId() {
		return threeQcGroupId;
	}

	public void setThreeQcGroupId(Integer threeQcGroupId) {
		this.threeQcGroupId = threeQcGroupId;
	}
	
}
