package com.tuniu.qms.qs.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderUncollectionMonitor implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 订单客服名称*/
	private String salerName;
	/** 欠款订单*/
	private List<OrderUncollection> orderList;
	/** 欠款订单数*/
	private int orderAmount;
	/** 订单欠款总额*/
	private BigDecimal uncollectionMoneys;

	public String getSalerName() {
		return salerName;
	}

	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}

	public List<OrderUncollection> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderUncollection> orderList) {
		this.orderList = orderList;
	}

	public int getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getUncollectionMoneys() {
		return uncollectionMoneys;
	}

	public void setUncollectionMoneys(BigDecimal uncollectionMoneys) {
		this.uncollectionMoneys = uncollectionMoneys;
	}
	
}
