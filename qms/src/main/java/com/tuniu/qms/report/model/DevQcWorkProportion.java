package com.tuniu.qms.report.model;

import com.tuniu.qms.common.model.BaseModel;

/**
 * 研发质检工作量统计人均
 * @author chenhaitao
 *
 */
public class DevQcWorkProportion extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	
	/** 质检员id */
	private Integer qcPersonId;
	
	/** 质检员姓名 */
	private String qcPerson;
	
	/** 人均分配单数 */
	private Double distribNum;
	
	/** 人均完成单数 */
	private Double doneNum;
	
	/** 人均总完成单数 */
	private Double doneTotalNum;
	
	/**人均 撤销单数 */
	private Double cancelNum;
	
	/**人均 撤销率 */
	private Double cancelRate;
	
	/**人均 研发故障单总数 */
	private Double problemTotalNum;
	
	/**人均故障率 */
	private Double problemRate;
	
	/** 人均上班天数 */
	private Double workDayNum;
	
	/** 人均日均完成单数 */
	private Double avgDailyDoneNum;
	
	/** 人均日均完成研发故障单数 */
	private Double avgDailyProblemNum;
	
	/** 人均研发故障S */
	private Double sDevNum;
	
	/** 人均研发故障A */
	private Double aDevNum;
	
	/** 人均 研发故障B*/
	private Double bDevNum;
	
	/**人均 研发故障C */
	private Double cDevNum;
	
	/**人均非研发故障 */
	private Double noDevNum;
	
	/** 人均及时完成单数 */
	private Double timelyDoneNum;
	
	/** 人均到期单数 */
	private Double expireNum;
	
	/** 人均及时率 */
	private Double timelyRate;
	
	/**人均 完成率 */
	private Double doneRate;

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

	public Double getDistribNum() {
		return distribNum;
	}

	public void setDistribNum(Double distribNum) {
		this.distribNum = distribNum;
	}

	public Double getDoneNum() {
		return doneNum;
	}

	public void setDoneNum(Double doneNum) {
		this.doneNum = doneNum;
	}

	public Double getDoneTotalNum() {
		return doneTotalNum;
	}

	public void setDoneTotalNum(Double doneTotalNum) {
		this.doneTotalNum = doneTotalNum;
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

	public Double getsDevNum() {
		return sDevNum;
	}

	public void setsDevNum(Double sDevNum) {
		this.sDevNum = sDevNum;
	}

	public Double getaDevNum() {
		return aDevNum;
	}

	public void setaDevNum(Double aDevNum) {
		this.aDevNum = aDevNum;
	}

	public Double getbDevNum() {
		return bDevNum;
	}

	public void setbDevNum(Double bDevNum) {
		this.bDevNum = bDevNum;
	}

	
	public Double getcDevNum() {
		return cDevNum;
	}

	public void setcDevNum(Double cDevNum) {
		this.cDevNum = cDevNum;
	}

	public Double getNoDevNum() {
		return noDevNum;
	}

	public void setNoDevNum(Double noDevNum) {
		this.noDevNum = noDevNum;
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

	public Double getDoneRate() {
		return doneRate;
	}

	public void setDoneRate(Double doneRate) {
		this.doneRate = doneRate;
	}

}
