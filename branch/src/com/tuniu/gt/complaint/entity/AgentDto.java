package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
/**
 * 
 * @author chenhaitao
 *
 */
public class AgentDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	
	private Integer agentId	;//人员ID	
	
	private String agentName ;//人员名	
	
	private Integer agentManagerId;//经理ID

	private String   agentManagerName;//经理姓名
	

	private Integer agentType	;//Integer	1：客服专员，2：运营专员；...：以后可扩展	 
	
	private Integer orderId;//订单号

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Integer getAgentType() {
		return agentType;
	}

	public void setAgentType(Integer agentType) {
		this.agentType = agentType;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getAgentManagerId() {
		return agentManagerId;
	}

	public void setAgentManagerId(Integer agentManagerId) {
		this.agentManagerId = agentManagerId;
	}

	public String getAgentManagerName() {
		return agentManagerName;
	}

	public void setAgentManagerName(String agentManagerName) {
		this.agentManagerName = agentManagerName;
	}
	
	
}
