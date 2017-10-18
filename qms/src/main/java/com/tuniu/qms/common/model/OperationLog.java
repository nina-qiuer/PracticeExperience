package com.tuniu.qms.common.model;

public class OperationLog  extends BaseModel {

	/**
	 * 质检操作日志
	 */
	private static final long serialVersionUID = 1L;

	/** 质检单id */
	private Integer qcId;
	
	/** 处理人id */
	private Integer dealPeople;
	
	/** 处理人名称 */
	private String dealPeopleName;
	
	/** 事件 */
	private String flowName;
	
	/** 处理岗 */
	private String dealDepart;
	
	/** 操作信息*/
	private String content;

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

	public Integer getDealPeople() {
		return dealPeople;
	}

	public void setDealPeople(Integer dealPeople) {
		this.dealPeople = dealPeople;
	}

	public String getDealPeopleName() {
		return dealPeopleName;
	}

	public void setDealPeopleName(String dealPeopleName) {
		this.dealPeopleName = dealPeopleName;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public String getDealDepart() {
		return dealDepart;
	}

	public void setDealDepart(String dealDepart) {
		this.dealDepart = dealDepart;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
