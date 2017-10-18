package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;

public class QcSameGroupBill extends BaseModel {

	/**
	 * 同团投诉
	 */
	private static final long serialVersionUID = 1L;

	/** 订单号*/
	private Integer orderId;
	
	/** 投诉单号*/
	private Integer cmpId;
	
	/** 质检单号*/
	private Integer qcId;
	
	/** 投诉处理状态*/
	private String cmpStateName;
	
	/**质检状态*/
	private String qcStateName;
	
	/** 对客处理方案*/
	private String solution;
	
	/**质检员**/
	private String qcPerson;

	
	
	public String getQcPerson() {
		return qcPerson;
	}

	public void setQcPerson(String qcPerson) {
		this.qcPerson = qcPerson;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCmpId() {
		return cmpId;
	}

	public void setCmpId(Integer cmpId) {
		this.cmpId = cmpId;
	}

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

	public String getCmpStateName() {
		return cmpStateName;
	}

	public void setCmpStateName(String cmpStateName) {
		this.cmpStateName = cmpStateName;
	}

	public String getQcStateName() {
		return qcStateName;
	}

	public void setQcStateName(String qcStateName) {
		this.qcStateName = qcStateName;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	
}
