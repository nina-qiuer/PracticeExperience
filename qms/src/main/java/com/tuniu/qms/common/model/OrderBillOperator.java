package com.tuniu.qms.common.model;

import java.io.Serializable;

/**
 * 订单相关运营人员
 * @author wangmingfang
 */
public class OrderBillOperator implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	/** 订单ID */
	private Integer ordId;
	
	/** 运营专员ID */
	private Integer operId;
	
	/** 运营专员姓名 */
	private String operName;
	
	/** 运营经理ID */
	private Integer managerId;
	
	/** 运营经理姓名 */
	private String managerName;
	
	private Integer delFlag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public Integer getOperId() {
		return operId;
	}

	public void setOperId(Integer operId) {
		this.operId = operId;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
}
