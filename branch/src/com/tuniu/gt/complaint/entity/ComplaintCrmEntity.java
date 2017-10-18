package com.tuniu.gt.complaint.entity;

import java.util.Date;

public class ComplaintCrmEntity {
	private String compDate;//投诉日期
	
	private Integer orderId;//订单编号
	
	private String orderType;//订单状态
	
	private Integer complaintId;//投诉单号
	
	private String productName;//产品名称
	
	private Integer userId;//会员id
	
	private String compHandlerName;//投诉处理人
	
	private String statusName;//投诉状态
	
	private Integer productId;//产品编号

	public String getCompDate() {
		return compDate;
	}

	public void setCompDate(String compDate) {
		this.compDate = compDate;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Integer getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCompHandlerName() {
		return compHandlerName;
	}

	public void setCompHandlerName(String compHandlerName) {
		this.compHandlerName = compHandlerName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}
