package com.tuniu.qms.report.model;

import com.tuniu.qms.common.model.BaseModel;

public class CmpQcWorkStat extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	/** 质检员id */
	private Integer qcPersonId;
	
	/** 质检员姓名 */
	private String qcPerson;
	
	/** 分配单数 */
	private Integer distribNum;
	
	/** 总完成单数 */
	private Integer doneTotalNum;
	
	/** 投诉未完成质检完成单数 */
	private Integer cmpUnDoneQcDoneNum;
	
	/** 撤销单数 */
	private Integer cancelNum;
	
	/** 撤销率 */
	private Double cancelRate;
	
	/** 总产出问题点数 */
	private Integer problemTotalNum;
	
	/** 问题率 */
	private Double problemRate;
	
	/** 上班天数 */
	private Integer workDayNum;
	
	/** 日均完成单数 */
	private Double avgDailyDoneNum;
	
	/** 日均产出问题点数 */
	private Double avgDailyProblemNum;
	
	/** 执行问题个数 */
	private Integer zxNum;
	
	/** 流程系统问题个数 */
	private Integer lcxtNum;
	
	/** 供应商问题个数 */
	private Integer gysNum;
	
	/** 低满意度问题个数 */
	private Integer dmyNum;
	
	/** 及时完成单数 */
	private Integer timelyDoneNum;
	
	/** 到期单数 */
	private Integer expireNum;
	
	/** 及时率 */
	private Double timelyRate;
	
	/** 质检期开始已完成单数 */
	private Integer qcPeriodBgnDoneNum;
	
	/** 质检期开始总单数 */
	private Integer qcPeriodBgnNum;
	
	/** 完成率 */
	private Double doneRate;
	
	/** 部门名称 */
	private String departmentName;
	
	public Integer getQcPersonId() {
		return qcPersonId;
	}

	public void setQcPersonId(Integer qcPersonId) {
		this.qcPersonId = qcPersonId;
	}

	public String getQcPerson() {
		return qcPerson;
	}

	public void setQcPerson(String qcPerson) {
		this.qcPerson = qcPerson;
	}

	public Integer getDistribNum() {
		return distribNum;
	}

	public void setDistribNum(Integer distribNum) {
		this.distribNum = distribNum;
	}

	public Integer getDoneTotalNum() {
		return doneTotalNum;
	}

	public void setDoneTotalNum(Integer doneTotalNum) {
		this.doneTotalNum = doneTotalNum;
	}

	public Integer getCancelNum() {
		return cancelNum;
	}

	public void setCancelNum(Integer cancelNum) {
		this.cancelNum = cancelNum;
	}

	public Double getCancelRate() {
		return cancelRate;
	}

	public void setCancelRate(Double cancelRate) {
		this.cancelRate = cancelRate;
	}

	public Integer getProblemTotalNum() {
		return problemTotalNum;
	}

	public void setProblemTotalNum(Integer problemTotalNum) {
		this.problemTotalNum = problemTotalNum;
	}

	public Double getProblemRate() {
		return problemRate;
	}

	public void setProblemRate(Double problemRate) {
		this.problemRate = problemRate;
	}

	public Integer getWorkDayNum() {
		return workDayNum;
	}

	public void setWorkDayNum(Integer workDayNum) {
		this.workDayNum = workDayNum;
	}

	public Double getAvgDailyDoneNum() {
		return avgDailyDoneNum;
	}

	public void setAvgDailyDoneNum(Double avgDailyDoneNum) {
		this.avgDailyDoneNum = avgDailyDoneNum;
	}

	public Double getAvgDailyProblemNum() {
		return avgDailyProblemNum;
	}

	public void setAvgDailyProblemNum(Double avgDailyProblemNum) {
		this.avgDailyProblemNum = avgDailyProblemNum;
	}

	public Integer getZxNum() {
		return zxNum;
	}

	public void setZxNum(Integer zxNum) {
		this.zxNum = zxNum;
	}

	public Integer getLcxtNum() {
		return lcxtNum;
	}

	public void setLcxtNum(Integer lcxtNum) {
		this.lcxtNum = lcxtNum;
	}

	public Integer getGysNum() {
		return gysNum;
	}

	public void setGysNum(Integer gysNum) {
		this.gysNum = gysNum;
	}

	public Integer getDmyNum() {
		return dmyNum;
	}

	public void setDmyNum(Integer dmyNum) {
		this.dmyNum = dmyNum;
	}

	public Integer getTimelyDoneNum() {
		return timelyDoneNum;
	}

	public void setTimelyDoneNum(Integer timelyDoneNum) {
		this.timelyDoneNum = timelyDoneNum;
	}

	public Integer getExpireNum() {
		return expireNum;
	}

	public void setExpireNum(Integer expireNum) {
		this.expireNum = expireNum;
	}

	public Double getTimelyRate() {
		return timelyRate;
	}

	public void setTimelyRate(Double timelyRate) {
		this.timelyRate = timelyRate;
	}

	public Integer getQcPeriodBgnDoneNum() {
		return qcPeriodBgnDoneNum;
	}

	public void setQcPeriodBgnDoneNum(Integer qcPeriodBgnDoneNum) {
		this.qcPeriodBgnDoneNum = qcPeriodBgnDoneNum;
	}

	public Integer getQcPeriodBgnNum() {
		return qcPeriodBgnNum;
	}

	public void setQcPeriodBgnNum(Integer qcPeriodBgnNum) {
		this.qcPeriodBgnNum = qcPeriodBgnNum;
	}

	public Double getDoneRate() {
		return doneRate;
	}

	public void setDoneRate(Double doneRate) {
		this.doneRate = doneRate;
	}

	public Integer getCmpUnDoneQcDoneNum() {
		return cmpUnDoneQcDoneNum;
	}

	public void setCmpUnDoneQcDoneNum(Integer cmpUnDoneQcDoneNum) {
		this.cmpUnDoneQcDoneNum = cmpUnDoneQcDoneNum;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
