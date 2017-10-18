package com.tuniu.qms.report.model;

import com.tuniu.qms.common.model.BaseModel;

public class DevQcWorkStat extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	/** 质检员id */
	private Integer qcPersonId;
	
	/** 质检员姓名 */
	private String qcPerson;
	
	/** 分配单数 */
	private Integer distribNum;
	
	/** 完成单数 */
	private Integer doneNum;
	
	/** 总完成单数 */
	private Integer doneTotalNum;
	
	/** 撤销单数 */
	private Integer cancelNum;
	
	/** 撤销率 */
	private Double cancelRate;
	
	/** 研发故障单总数 */
	private Integer problemTotalNum;
	
	/** 故障率 */
	private Double problemRate;
	
	/** 上班天数 */
	private Integer workDayNum;
	
	/** 日均完成单数 */
	private Double avgDailyDoneNum;
	
	/** 日均完成研发故障单数 */
	private Double avgDailyProblemNum;
	
	/** 研发故障S */
	private Integer sDevNum;
	
	/** 研发故障A */
	private Integer aDevNum;
	
	/**  研发故障B*/
	private Integer bDevNum;
	
	/** 研发故障C */
	private Integer cDevNum;
	
	/**非研发故障 */
	private Integer noDevNum;
	
	/** 及时完成单数 */
	private Integer timelyDoneNum;
	
	/** 到期单数 */
	private Integer expireNum;
	
	/** 及时率 */
	private Double timelyRate;
	
	/** 完成率 */
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

	public Integer getDistribNum() {
		return distribNum;
	}

	public void setDistribNum(Integer distribNum) {
		this.distribNum = distribNum;
	}

	public Integer getDoneNum() {
		return doneNum;
	}

	public void setDoneNum(Integer doneNum) {
		this.doneNum = doneNum;
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

	public Integer getsDevNum() {
		return sDevNum;
	}

	public void setsDevNum(Integer sDevNum) {
		this.sDevNum = sDevNum;
	}

	public Integer getaDevNum() {
		return aDevNum;
	}

	public void setaDevNum(Integer aDevNum) {
		this.aDevNum = aDevNum;
	}

	public Integer getbDevNum() {
		return bDevNum;
	}

	public void setbDevNum(Integer bDevNum) {
		this.bDevNum = bDevNum;
	}

	public Integer getcDevNum() {
		return cDevNum;
	}

	public void setcDevNum(Integer cDevNum) {
		this.cDevNum = cDevNum;
	}

	public Integer getNoDevNum() {
		return noDevNum;
	}

	public void setNoDevNum(Integer noDevNum) {
		this.noDevNum = noDevNum;
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

	public Double getDoneRate() {
		return doneRate;
	}

	public void setDoneRate(Double doneRate) {
		this.doneRate = doneRate;
	}
	
	

}
