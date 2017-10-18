package com.tuniu.qms.report.model;

import com.tuniu.qms.common.model.BaseModel;

public class QcPunishReport extends BaseModel {
	
private static final long serialVersionUID = 1L;
	
	//质检单id
	private Integer qcId;
	//内部处罚单id
	private Integer innerPunishId;
	//记分处罚（分） 
	private Integer scorePunish;
	// 经济处罚（元） 
	private Double economicPunish;
	//质检类型id 名称   一级  二级  三级
	private Integer firstQcTypeId;
	
	private String firstQcTypeName;
	
	private Integer secondQcTypeId;
	
	private String secondQcTypeName;
	
	private Integer thirdQcTypeId;
	
	private String thirdQcTypeName;
	// 关联部门id 名称    一级  二级  三级
    private Integer firstDepId;
	
	private String firstDepName;
	
	private Integer twoDepId;
	
	private String twoDepName;
	
	private Integer threeDepId;
	
	private String threeDepName;
	//质检组 三级
    private String firstQcGroupName;
		
    private String twoQcGroupName;
		
	private String threeQcGroupName;
	
	private Integer firstQcGroupId;
		
	private Integer twoQcGroupId;
		
	private Integer threeQcGroupId;
	//关联岗位id
	private Integer jobId;
	//关联岗位名称
	private String jobName;
	//被处罚人id
	private Integer punPersonId;
	// 被处罚人Name 
	private String punishPersonName;
	//质检人id
	private Integer qcPersonId;
	// 质检人Name 
	private String qcPersonName;
    //质检完成时间
    private String qcFinishTime;
    //质检完成时间粒度  年  季 月 周
	private Integer year;
	
	private Integer yearQuarter;
	
	private Integer yearMonth;
	
	private Integer yearWeek;

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

	public Integer getInnerPunishId() {
		return innerPunishId;
	}

	public void setInnerPunishId(Integer innerPunishId) {
		this.innerPunishId = innerPunishId;
	}

	public Integer getScorePunish() {
		return scorePunish;
	}

	public void setScorePunish(Integer scorePunish) {
		this.scorePunish = scorePunish;
	}

	public Double getEconomicPunish() {
		return economicPunish;
	}

	public void setEconomicPunish(Double economicPunish) {
		this.economicPunish = economicPunish;
	}

	public Integer getFirstQcTypeId() {
		return firstQcTypeId;
	}

	public void setFirstQcTypeId(Integer firstQcTypeId) {
		this.firstQcTypeId = firstQcTypeId;
	}

	public String getFirstQcTypeName() {
		return firstQcTypeName;
	}

	public void setFirstQcTypeName(String firstQcTypeName) {
		this.firstQcTypeName = firstQcTypeName;
	}

	public Integer getSecondQcTypeId() {
		return secondQcTypeId;
	}

	public void setSecondQcTypeId(Integer secondQcTypeId) {
		this.secondQcTypeId = secondQcTypeId;
	}

	public String getSecondQcTypeName() {
		return secondQcTypeName;
	}

	public void setSecondQcTypeName(String secondQcTypeName) {
		this.secondQcTypeName = secondQcTypeName;
	}

	public Integer getThirdQcTypeId() {
		return thirdQcTypeId;
	}

	public void setThirdQcTypeId(Integer thirdQcTypeId) {
		this.thirdQcTypeId = thirdQcTypeId;
	}

	public String getThirdQcTypeName() {
		return thirdQcTypeName;
	}

	public void setThirdQcTypeName(String thirdQcTypeName) {
		this.thirdQcTypeName = thirdQcTypeName;
	}

	public Integer getFirstDepId() {
		return firstDepId;
	}

	public void setFirstDepId(Integer firstDepId) {
		this.firstDepId = firstDepId;
	}

	public String getFirstDepName() {
		return firstDepName;
	}

	public void setFirstDepName(String firstDepName) {
		this.firstDepName = firstDepName;
	}

	public Integer getTwoDepId() {
		return twoDepId;
	}

	public void setTwoDepId(Integer twoDepId) {
		this.twoDepId = twoDepId;
	}

	public String getTwoDepName() {
		return twoDepName;
	}

	public void setTwoDepName(String twoDepName) {
		this.twoDepName = twoDepName;
	}

	public Integer getThreeDepId() {
		return threeDepId;
	}

	public void setThreeDepId(Integer threeDepId) {
		this.threeDepId = threeDepId;
	}

	public String getThreeDepName() {
		return threeDepName;
	}

	public void setThreeDepName(String threeDepName) {
		this.threeDepName = threeDepName;
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

	public Integer getPunPersonId() {
		return punPersonId;
	}

	public void setPunPersonId(Integer punPersonId) {
		this.punPersonId = punPersonId;
	}

	public String getPunishPersonName() {
		return punishPersonName;
	}

	public void setPunishPersonName(String punishPersonName) {
		this.punishPersonName = punishPersonName;
	}

	public String getQcFinishTime() {
		return qcFinishTime;
	}

	public void setQcFinishTime(String qcFinishTime) {
		this.qcFinishTime = qcFinishTime;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getYearQuarter() {
		return yearQuarter;
	}

	public void setYearQuarter(Integer yearQuarter) {
		this.yearQuarter = yearQuarter;
	}

	public Integer getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Integer yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Integer getYearWeek() {
		return yearWeek;
	}

	public void setYearWeek(Integer yearWeek) {
		this.yearWeek = yearWeek;
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

	public Integer getQcPersonId() {
		return qcPersonId;
	}

	public void setQcPersonId(Integer qcPersonId) {
		this.qcPersonId = qcPersonId;
	}

	public String getQcPersonName() {
		return qcPersonName;
	}

	public void setQcPersonName(String qcPersonName) {
		this.qcPersonName = qcPersonName;
	}
	
}
