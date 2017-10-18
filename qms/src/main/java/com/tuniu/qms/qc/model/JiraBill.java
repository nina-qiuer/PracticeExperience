package com.tuniu.qms.qc.model;

import java.util.Date;

import com.tuniu.qms.common.model.BaseModel;
/**
 * jira单
 * @author chenhaitao
 *
 */
public class JiraBill extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** jira ID*/
	private String  jiraId;
	
	/**系统编号*/
	private String  project;
	
	/**研发质检单号*/
	private Integer qcId;

	/**jira单号*/
	private String jiraName;
	
	/** jira状态编号*/
	private String statusNum;
	
	/** jira状态名称*/
	private String statusName;
	
	/** jira单类型*/
	private String typeNum;

	/** jiraa类型名称*/
	private String typeName;

	/** 标题*/
	private String summary;
	
	/**描述*/
	private String description;
	
	/** 创建日期*/
	private Date created;
	
	/** 更新日期*/
	private Date updated;
	
	/** 问题主要原因*/
	private String mianReason;
	
	/** 问题原因明细*/
	private String reasonDetail;
	
	/** 解决方案*/
	private String solution;
	
	/** 问题等级*/
	private String  eventClass;
	
	/** 研发处理人*/
	private String devProPeople;
	
	/** 提出人*/
	private String  applicationPeople;

	/** 关联标识 --未关联，1：已关联，2：已关闭*/
	private Integer flag ;
	
	
	
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getJiraId() {
		return jiraId;
	}

	public void setJiraId(String jiraId) {
		this.jiraId = jiraId;
	}

	public String getJiraName() {
		return jiraName;
	}

	public void setJiraName(String jiraName) {
		this.jiraName = jiraName;
	}

	public String getStatusNum() {
		return statusNum;
	}

	public void setStatusNum(String statusNum) {
		this.statusNum = statusNum;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getTypeNum() {
		return typeNum;
	}

	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getMianReason() {
		return mianReason;
	}

	public void setMianReason(String mianReason) {
		this.mianReason = mianReason;
	}

	public String getReasonDetail() {
		return reasonDetail;
	}

	public void setReasonDetail(String reasonDetail) {
		this.reasonDetail = reasonDetail;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getEventClass() {
		return eventClass;
	}

	public void setEventClass(String eventClass) {
		this.eventClass = eventClass;
	}

	public String getDevProPeople() {
		return devProPeople;
	}

	public void setDevProPeople(String devProPeople) {
		this.devProPeople = devProPeople;
	}

	public String getApplicationPeople() {
		return applicationPeople;
	}

	public void setApplicationPeople(String applicationPeople) {
		this.applicationPeople = applicationPeople;
	}
	
	
}
