package com.tuniu.qms.common.model;

import java.math.BigDecimal;

public class SyncyTask extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer dataId;
	/** 任务类型标识  1：供应商责任赔款查漏监视提醒任务*/
	private int taskType;
	
	private int failTimes;
	
	private BigDecimal SupplierAmount;
	
	private Integer dealPersonId;
	
	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public int getFailTimes() {
		return failTimes;
	}

	public void setFailTimes(int failTimes) {
		this.failTimes = failTimes;
	}

	public BigDecimal getSupplierAmount() {
		return SupplierAmount;
	}

	public void setSupplierAmount(BigDecimal supplierAmount) {
		SupplierAmount = supplierAmount;
	}

	public Integer getDealPersonId() {
		return dealPersonId;
	}

	public void setDealPersonId(Integer dealPersonId) {
		this.dealPersonId = dealPersonId;
	}
	
}
