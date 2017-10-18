package com.tuniu.qms.qc.model;

import java.util.List;

import com.tuniu.qms.common.model.ComplaintSolution;

public class QcSameGroup {

	private Integer orderId;
	
	private List<ComplaintSolution> list;
	
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public List<ComplaintSolution> getList() {
		return list;
	}
	public void setList(List<ComplaintSolution> list) {
		this.list = list;
	}
	
	
	
}
