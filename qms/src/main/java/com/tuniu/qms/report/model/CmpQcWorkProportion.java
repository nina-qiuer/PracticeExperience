package com.tuniu.qms.report.model;

import com.tuniu.qms.common.model.BaseModel;

/**
 * 工作量统计人均
 * @author chenhaitao
 *
 */
public class CmpQcWorkProportion extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	
	/** 质检员姓名 */
	private String qcPerson;
	
	/** 人均分配单数 */
	private Double distribNum;
	
	/** 人均总完成单数 */
	private Double doneTotalNum;
	
	/** 人均投诉未完成质检完成单数 */
	private Double cmpUnDoneQcDoneNum;
	
	/** 人均撤销单数 */
	private Double cancelNum;
	
	/** 人均撤销率 */
	private Double cancelRate;
	
	/** 人均总产出问题点数 */
	private Double problemTotalNum;
	
	/** 人均问题率 */
	private Double problemRate;
	
	/** 人均上班天数 */
	private Double workDayNum;
	
	/**人均 日均完成单数 */
	private Double avgDailyDoneNum;
	
	/**人均 日均产出问题点数 */
	private Double avgDailyProblemNum;
	
	/** 人均执行问题个数 */
	private Double zxNum;
	
	/** 人均流程系统问题个数 */
	private Double lcxtNum;
	
	/** 人均供应商问题个数 */
	private Double gysNum;
	
	/** 人均低满意度问题个数 */
	private Double dmyNum;
	
	/**人均 及时完成单数 */
	private Double timelyDoneNum;
	
	/**人均 到期单数 */
	private Double expireNum;
	
	/**人均 及时率 */
	private Double timelyRate;
	
	/** 人均质检期开始已完成单数 */
	private Double qcPeriodBgnDoneNum;
	
	/** 人均质检期开始总单数 */
	private Double qcPeriodBgnNum;
	
	/** 人均完成率 */
	private Double doneRate;

	public String getQcPerson() {
		return qcPerson;
	}

	public void setQcPerson(String qcPerson) {
		this.qcPerson = qcPerson;
	}

	public Double getDistribNum() {
		return distribNum;
	}

	public void setDistribNum(Double distribNum) {
		this.distribNum = distribNum;
	}

	public Double getDoneTotalNum() {
		return doneTotalNum;
	}

	public void setDoneTotalNum(Double doneTotalNum) {
		this.doneTotalNum = doneTotalNum;
	}

	public Double getCmpUnDoneQcDoneNum() {
		return cmpUnDoneQcDoneNum;
	}

	public void setCmpUnDoneQcDoneNum(Double cmpUnDoneQcDoneNum) {
		this.cmpUnDoneQcDoneNum = cmpUnDoneQcDoneNum;
	}

	public Double getCancelNum() {
		return cancelNum;
	}

	public void setCancelNum(Double cancelNum) {
		this.cancelNum = cancelNum;
	}

	public Double getCancelRate() {
		return cancelRate;
	}

	public void setCancelRate(Double cancelRate) {
		this.cancelRate = cancelRate;
	}

	public Double getProblemTotalNum() {
		return problemTotalNum;
	}

	public void setProblemTotalNum(Double problemTotalNum) {
		this.problemTotalNum = problemTotalNum;
	}

	public Double getProblemRate() {
		return problemRate;
	}

	public void setProblemRate(Double problemRate) {
		this.problemRate = problemRate;
	}

	public Double getWorkDayNum() {
		return workDayNum;
	}

	public void setWorkDayNum(Double workDayNum) {
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

	public Double getZxNum() {
		return zxNum;
	}

	public void setZxNum(Double zxNum) {
		this.zxNum = zxNum;
	}

	public Double getLcxtNum() {
		return lcxtNum;
	}

	public void setLcxtNum(Double lcxtNum) {
		this.lcxtNum = lcxtNum;
	}

	public Double getGysNum() {
		return gysNum;
	}

	public void setGysNum(Double gysNum) {
		this.gysNum = gysNum;
	}

	public Double getDmyNum() {
		return dmyNum;
	}

	public void setDmyNum(Double dmyNum) {
		this.dmyNum = dmyNum;
	}

	public Double getTimelyDoneNum() {
		return timelyDoneNum;
	}

	public void setTimelyDoneNum(Double timelyDoneNum) {
		this.timelyDoneNum = timelyDoneNum;
	}

	public Double getExpireNum() {
		return expireNum;
	}

	public void setExpireNum(Double expireNum) {
		this.expireNum = expireNum;
	}

	public Double getTimelyRate() {
		return timelyRate;
	}

	public void setTimelyRate(Double timelyRate) {
		this.timelyRate = timelyRate;
	}

	public Double getQcPeriodBgnDoneNum() {
		return qcPeriodBgnDoneNum;
	}

	public void setQcPeriodBgnDoneNum(Double qcPeriodBgnDoneNum) {
		this.qcPeriodBgnDoneNum = qcPeriodBgnDoneNum;
	}

	public Double getQcPeriodBgnNum() {
		return qcPeriodBgnNum;
	}

	public void setQcPeriodBgnNum(Double qcPeriodBgnNum) {
		this.qcPeriodBgnNum = qcPeriodBgnNum;
	}

	public Double getDoneRate() {
		return doneRate;
	}

	public void setDoneRate(Double doneRate) {
		this.doneRate = doneRate;
	}
	
	

}
