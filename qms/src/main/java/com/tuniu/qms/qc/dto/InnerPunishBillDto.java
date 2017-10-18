package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.InnerPunishBill;

public class InnerPunishBillDto extends  BaseDto<InnerPunishBill> {


	private Integer qcId;
	
	 private Integer state=-1 ;//质检状态
	
	/**被处罚人*/
	private String punishPersonName;
	
	/**被处罚工号*/
	private Integer punishPersonId;
	
	/**被处罚工号*/
	private String pubPersonId;
	
	/**部门ID*/
	private Integer depId;
	
	/**岗位ID*/
	private Integer jobId;
	
	/**部门名称*/
	private String depName;
	
	/**岗位名称*/
	private String jobName;
	
	/**记分处罚区间*/
	private Integer scorePunishBgn;
	
	/**记分处罚区间*/
	private Integer scorePunishEnd;
	
	/**经济处罚区间*/
	private Double economicPunishBgn;
	
	/**经济处罚区间*/
	private Double economicPunishEnd;
	
	/**投诉单号*/
	private Integer cmpId;
	
	/**订单号*/
	private Integer ordId;
	
	/**产品ID*/
	private Integer prdId;
	
	/**使用方（组别），1：投诉质检组，2：运营质检组，3：研发质检组 4 内部质检*/
	private Integer groupFlag=-1;
	
	/**jira号*/
	private Integer jiraNum ;
	
	/**质检类型*/
	private Integer qcTypeId;
	
	/**质检类型名称*/
	private String qcTypeName;
	
	/** 添加开始时间 */
	private String addPunishTimeBgn;
	
	/** 添加结束时间 */
	private String addPunishTimeEnd;
	
	/** 质检完成开始时间 */
	private String finishTimeBgn;
	
	/** 质检完成结束时间 */
	private String finishTimeEnd;
	
	/** 连带责任标识位，0：否，1：是 */
	private Integer relatedFlag;
	
	/** 责任经理*/
	private Integer respManagerId;
	private String respManagerName;
	
	/** 责任总监*/
	private Integer respGeneralId;
	private String respGeneralName;
	
	/** 被处罚时责任岗位类型  0：全部  1：责任经理   2：责任总监*/
	private Integer punishJobType;
	
	private Integer totalScores = 0;
	
	public String getFinishTimeBgn() {
		return finishTimeBgn;
	}

	public void setFinishTimeBgn(String finishTimeBgn) {
		this.finishTimeBgn = finishTimeBgn;
	}

	public String getFinishTimeEnd() {
		return finishTimeEnd;
	}

	public void setFinishTimeEnd(String finishTimeEnd) {
		this.finishTimeEnd = finishTimeEnd;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getPunishPersonId() {
		return punishPersonId;
	}

	public void setPunishPersonId(Integer punishPersonId) {
		this.punishPersonId = punishPersonId;
	}

	public String getPubPersonId() {
		return pubPersonId;
	}

	public void setPubPersonId(String pubPersonId) {
		this.pubPersonId = pubPersonId;
	}

	public String getAddPunishTimeBgn() {
		return addPunishTimeBgn;
	}

	public void setAddPunishTimeBgn(String addPunishTimeBgn) {
		this.addPunishTimeBgn = addPunishTimeBgn;
	}

	public String getAddPunishTimeEnd() {
		return addPunishTimeEnd;
	}

	public void setAddPunishTimeEnd(String addPunishTimeEnd) {
		this.addPunishTimeEnd = addPunishTimeEnd;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getQcTypeName() {
		return qcTypeName;
	}

	public void setQcTypeName(String qcTypeName) {
		this.qcTypeName = qcTypeName;
	}

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

	public Integer getQcTypeId() {
		return qcTypeId;
	}

	public void setQcTypeId(Integer qcTypeId) {
		this.qcTypeId = qcTypeId;
	}

	public String getPunishPersonName() {
		return punishPersonName;
	}

	public void setPunishPersonName(String punishPersonName) {
		this.punishPersonName = punishPersonName;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public Integer getScorePunishBgn() {
		return scorePunishBgn;
	}

	public void setScorePunishBgn(Integer scorePunishBgn) {
		this.scorePunishBgn = scorePunishBgn;
	}

	public Integer getScorePunishEnd() {
		return scorePunishEnd;
	}

	public void setScorePunishEnd(Integer scorePunishEnd) {
		this.scorePunishEnd = scorePunishEnd;
	}

	public Double getEconomicPunishBgn() {
		return economicPunishBgn;
	}

	public void setEconomicPunishBgn(Double economicPunishBgn) {
		this.economicPunishBgn = economicPunishBgn;
	}

	public Double getEconomicPunishEnd() {
		return economicPunishEnd;
	}

	public void setEconomicPunishEnd(Double economicPunishEnd) {
		this.economicPunishEnd = economicPunishEnd;
	}

	public Integer getCmpId() {
		return cmpId;
	}

	public void setCmpId(Integer cmpId) {
		this.cmpId = cmpId;
	}

	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public Integer getPrdId() {
		return prdId;
	}

	public void setPrdId(Integer prdId) {
		this.prdId = prdId;
	}

	public Integer getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(Integer groupFlag) {
		this.groupFlag = groupFlag;
	}

	public Integer getJiraNum() {
		return jiraNum;
	}

	public void setJiraNum(Integer jiraNum) {
		this.jiraNum = jiraNum;
	}

	public Integer getRelatedFlag() {
		return relatedFlag;
	}

	public void setRelatedFlag(Integer relatedFlag) {
		this.relatedFlag = relatedFlag;
	}

	public Integer getRespManagerId() {
		return respManagerId;
	}

	public void setRespManagerId(Integer respManagerId) {
		this.respManagerId = respManagerId;
	}

	public String getRespManagerName() {
		return respManagerName;
	}

	public void setRespManagerName(String respManagerName) {
		this.respManagerName = respManagerName;
	}

	public Integer getRespGeneralId() {
		return respGeneralId;
	}

	public Integer getPunishJobType() {
		return punishJobType;
	}

	public void setPunishJobType(Integer punishJobType) {
		this.punishJobType = punishJobType;
	}

	public void setRespGeneralId(Integer respGeneralId) {
		this.respGeneralId = respGeneralId;
	}

	public String getRespGeneralName() {
		return respGeneralName;
	}

	public void setRespGeneralName(String respGeneralName) {
		this.respGeneralName = respGeneralName;
	}

	public Integer getTotalScores() {
		return totalScores;
	}

	public void setTotalScores(Integer totalScores) {
		this.totalScores = totalScores;
	}
	
}
