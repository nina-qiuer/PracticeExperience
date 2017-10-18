package com.tuniu.qms.qc.model;

import java.util.Date;
import java.util.List;

import com.tuniu.qms.common.model.BaseModel;

public class InnerPunishBill extends BaseModel {

	private static final long serialVersionUID = 1L;

	/** 质检单id */
	private Integer qcId;

	/** 被处罚人ID */
	private Integer punishPersonId;

	/** 被处罚人工号 */
	private String pubPersonId;

	/** 被处罚人Name */
	private String punishPersonName;

	/** 连带责任标识位，0：否，1：是 */
	private Integer relatedFlag;

	/** 关联部门ID */
	private Integer depId;

	/** 关联岗位ID */
	private Integer jobId;

	/** 关联部门名称 */
	private String depName;

	/** 关联岗位名称 */
	private String jobName;

	/** 处罚事由 */
	private String punishReason;

	/** 记分处罚（分） */
	private Integer scorePunish;

	/** 经济处罚（元） */
	private Double economicPunish;

	/** 订单号 */
	private Integer ordId;

	/** 投诉单号 */
	private Integer cmpId;

	/** 产品单号 */
	private Integer prdId;

	/** JIRA单号 */
	private Integer jiraNum;

	private Integer qcTypeId;

	/** 质检类型 */
	private String qcTypeName;

	/** 质检状态 */
	private Integer qcState;

	private Integer oldId;

	private String qcPerson;

	private Date qcFinishTime;

	private Integer week;

	private String businessUnit;

	private String dep;

	private String team;

	private String qcTypeNature;

	private Integer groupFlag;

	private Integer useFlag;

	/** 责任单Id */
	private Integer respId;
	/** 责任单类型 1：内部责任单，2：外部责任单 */
	private Integer respType;
	
	/** 责任经理*/
	private String respManagerName;
	
	/** 责任总监*/
	private String respGeneralName;

	public Integer getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(Integer groupFlag) {
		this.groupFlag = groupFlag;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getQcTypeNature() {
		return qcTypeNature;
	}

	public void setQcTypeNature(String qcTypeNature) {
		this.qcTypeNature = qcTypeNature;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Date getQcFinishTime() {
		return qcFinishTime;
	}

	public void setQcFinishTime(Date qcFinishTime) {
		this.qcFinishTime = qcFinishTime;
	}

	public String getQcPerson() {
		return qcPerson;
	}

	public void setQcPerson(String qcPerson) {
		this.qcPerson = qcPerson;
	}

	public Integer getOldId() {
		return oldId;
	}

	public void setOldId(Integer oldId) {
		this.oldId = oldId;
	}

	public Integer getQcState() {
		return qcState;
	}

	public void setQcState(Integer qcState) {
		this.qcState = qcState;
	}

	/** 处罚依据 */
	private List<InnerPunishBasis> ipbList;

	public String getPubPersonId() {
		return pubPersonId;
	}

	public void setPubPersonId(String pubPersonId) {
		this.pubPersonId = pubPersonId;
	}

	public Integer getQcTypeId() {
		return qcTypeId;
	}

	public void setQcTypeId(Integer qcTypeId) {
		this.qcTypeId = qcTypeId;
	}

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

	public Integer getPunishPersonId() {
		return punishPersonId;
	}

	public void setPunishPersonId(Integer punishPersonId) {
		this.punishPersonId = punishPersonId;
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

	public String getPunishReason() {
		return punishReason;
	}

	public void setPunishReason(String punishReason) {
		this.punishReason = punishReason;
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

	public List<InnerPunishBasis> getIpbList() {
		return ipbList;
	}

	public void setIpbList(List<InnerPunishBasis> ipbList) {
		this.ipbList = ipbList;
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

	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public Integer getCmpId() {
		return cmpId;
	}

	public void setCmpId(Integer cmpId) {
		this.cmpId = cmpId;
	}

	public Integer getPrdId() {
		return prdId;
	}

	public void setPrdId(Integer prdId) {
		this.prdId = prdId;
	}

	public Integer getJiraNum() {
		return jiraNum;
	}

	public void setJiraNum(Integer jiraNum) {
		this.jiraNum = jiraNum;
	}

	public String getQcTypeName() {
		return qcTypeName;
	}

	public void setQcTypeName(String qcTypeName) {
		this.qcTypeName = qcTypeName;
	}

	public Integer getRelatedFlag() {
		return relatedFlag;
	}

	public void setRelatedFlag(Integer relatedFlag) {
		this.relatedFlag = relatedFlag;
	}

	public Integer getRespId() {
		return respId;
	}

	public void setRespId(Integer respId) {
		this.respId = respId;
	}

	public Integer getRespType() {
		return respType;
	}

	public void setRespType(Integer respType) {
		this.respType = respType;
	}

	public Integer getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}

	public String getRespManagerName() {
		return respManagerName;
	}

	public void setRespManagerName(String respManagerName) {
		this.respManagerName = respManagerName;
	}

	public String getRespGeneralName() {
		return respGeneralName;
	}

	public void setRespGeneralName(String respGeneralName) {
		this.respGeneralName = respGeneralName;
	}

}
