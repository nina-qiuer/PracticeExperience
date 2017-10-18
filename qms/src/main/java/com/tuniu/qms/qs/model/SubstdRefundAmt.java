package com.tuniu.qms.qs.model;

import com.tuniu.qms.common.model.BaseModel;

public class SubstdRefundAmt extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	/** 退款单ID */
	private Integer refundId;
	
	/** 退款单编号 */
	private String refundNum;
	
	/** 退款单添加日期 */
	private java.sql.Date addDate;
	
	/** 退款类型，1：现金，2：旅游券 */
	private Integer refundType;
	
	/** 退款金额 */
	private Double refundAmount;
	
	/** 订单ID */
	private Integer ordId;
	
	/** 现金退款总额 */
	private Double cashRefundAmount;
	
	/** 现金收款总额 */
	private Double cashCollectionAmount;
	
	/** 现金超退总额 */
	private Double cashBeyondAmount;
	
	/** 订单退款总额 */
	private Double orderRefundAmount;
	
	/** 订单收款总额 */
	private Double orderCollectionAmount;
	
	/** 订单超退总额 */
	private Double orderBeyondAmount;
	
	/** 事业部 */
	private String businessUnit;
	
	/** 产品部 */
	private String prdDep;
	
	/** 产品组 */
	private String prdTeam;
	
	/** 产品经理ID */
	private Integer prdManagerId;
	
	/** 产品经理 */
	private String prdManager;
	
	/** 产品专员ID */
	private Integer producterId;
	
	/** 产品专员 */
	private String producter;
	
	/** 客服经理ID */
	private Integer customerManagerId;
	
	/** 客服经理 */
	private String customerManager;
	
	/** 客服专员ID */
	private Integer customerId;
	
	/** 客服专员 */
	private String customer;

	public Integer getRefundId() {
		return refundId;
	}

	public void setRefundId(Integer refundId) {
		this.refundId = refundId;
	}

	public String getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(String refundNum) {
		this.refundNum = refundNum;
	}

	public java.sql.Date getAddDate() {
		return addDate;
	}

	public void setAddDate(java.sql.Date addDate) {
		this.addDate = addDate;
	}

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public Double getCashRefundAmount() {
		return cashRefundAmount;
	}

	public void setCashRefundAmount(Double cashRefundAmount) {
		this.cashRefundAmount = cashRefundAmount;
	}

	public Double getCashCollectionAmount() {
		return cashCollectionAmount;
	}

	public void setCashCollectionAmount(Double cashCollectionAmount) {
		this.cashCollectionAmount = cashCollectionAmount;
	}

	public Double getCashBeyondAmount() {
		return cashBeyondAmount;
	}

	public void setCashBeyondAmount(Double cashBeyondAmount) {
		this.cashBeyondAmount = cashBeyondAmount;
	}

	public Double getOrderRefundAmount() {
		return orderRefundAmount;
	}

	public void setOrderRefundAmount(Double orderRefundAmount) {
		this.orderRefundAmount = orderRefundAmount;
	}

	public Double getOrderCollectionAmount() {
		return orderCollectionAmount;
	}

	public void setOrderCollectionAmount(Double orderCollectionAmount) {
		this.orderCollectionAmount = orderCollectionAmount;
	}

	public Double getOrderBeyondAmount() {
		return orderBeyondAmount;
	}

	public void setOrderBeyondAmount(Double orderBeyondAmount) {
		this.orderBeyondAmount = orderBeyondAmount;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getPrdDep() {
		return prdDep;
	}

	public void setPrdDep(String prdDep) {
		this.prdDep = prdDep;
	}

	public String getPrdTeam() {
		return prdTeam;
	}

	public void setPrdTeam(String prdTeam) {
		this.prdTeam = prdTeam;
	}

	public Integer getPrdManagerId() {
		return prdManagerId;
	}

	public void setPrdManagerId(Integer prdManagerId) {
		this.prdManagerId = prdManagerId;
	}

	public String getPrdManager() {
		return prdManager;
	}

	public void setPrdManager(String prdManager) {
		this.prdManager = prdManager;
	}

	public Integer getProducterId() {
		return producterId;
	}

	public void setProducterId(Integer producterId) {
		this.producterId = producterId;
	}

	public String getProducter() {
		return producter;
	}

	public void setProducter(String producter) {
		this.producter = producter;
	}

	public Integer getCustomerManagerId() {
		return customerManagerId;
	}

	public void setCustomerManagerId(Integer customerManagerId) {
		this.customerManagerId = customerManagerId;
	}

	public String getCustomerManager() {
		return customerManager;
	}

	public void setCustomerManager(String customerManager) {
		this.customerManager = customerManager;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
}
