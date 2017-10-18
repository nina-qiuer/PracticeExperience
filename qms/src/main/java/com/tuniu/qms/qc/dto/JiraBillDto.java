package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.JiraBill;

public class JiraBillDto extends BaseDto<JiraBill> {
	
	/**质检人ID*/
	private Integer qcPersonId;
	
	/**质检ID*/
	private Integer qcId;
	
	/**jira单号*/
	private String jiraName ;
	
	/**jira状态*/
	private String statusName;
	
	/**需求提出人*/
	private String applicationPeople;
	
	/**关键字*/
	private String keyWord ;
	
	/**创建开始时间*/
	private  String createTimeBgn;
	
	/**创建结束时间*/
	private  String createTimeEnd;
	
	/**添加时间上边界*/
	private String addTimeFrom;

	/** 添加时间下边界*/
	private String addTimeTo;
	
	/** 质检处理状态 */
	private Integer flag = 0; // 待处理
	
	/** 问题主要原因*/
	private String mianReason;
	
	/** 问题等级*/
	private String eventClass;
	
	public String getCreateTimeBgn() {
		return createTimeBgn;
	}
	public void setCreateTimeBgn(String createTimeBgn) {
		this.createTimeBgn = createTimeBgn;
	}
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public String getAddTimeFrom() {
		return addTimeFrom;
	}
	public void setAddTimeFrom(String addTimeFrom) {
		this.addTimeFrom = addTimeFrom;
	}
	public String getAddTimeTo() {
		return addTimeTo;
	}
	public void setAddTimeTo(String addTimeTo) {
		this.addTimeTo = addTimeTo;
	}
	public Integer getQcId() {
		return qcId;
	}
	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}
	public Integer getQcPersonId() {
		return qcPersonId;
	}
	public void setQcPersonId(Integer qcPersonId) {
		this.qcPersonId = qcPersonId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getJiraName() {
		return jiraName;
	}
	public void setJiraName(String jiraName) {
		this.jiraName = jiraName;
	}

	public String getApplicationPeople() {
		return applicationPeople;
	}
	public void setApplicationPeople(String applicationPeople) {
		this.applicationPeople = applicationPeople;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getMianReason() {
		return mianReason;
	}
	public void setMianReason(String mianReason) {
		this.mianReason = mianReason;
	}
	public String getEventClass() {
		return eventClass;
	}
	public void setEventClass(String eventClass) {
		this.eventClass = eventClass;
	}

}
