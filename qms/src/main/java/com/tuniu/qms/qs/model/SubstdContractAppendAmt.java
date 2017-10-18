package com.tuniu.qms.qs.model;

import com.tuniu.qms.common.model.BaseModel;

public class SubstdContractAppendAmt extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	/** 合同增补单ID */
	private Integer caId;
	
	/** 订单签约日期 */
	private java.sql.Date signDate;
	
	/** 合同增补单添加日期 */
	private java.sql.Date addDate;
	
	/** 合同增补单金额 */
	private Double price;
	
	/** 当月添加合同增补单总额 */
	private Double monthTotalAmount;
	
	/** 是否跨月，0：否，1：是 */
	private Integer diffMonthFlag;
	
	/** 订单ID */
	private Integer ordId;
	
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

	public Integer getCaId() {
		return caId;
	}

	public void setCaId(Integer caId) {
		this.caId = caId;
	}

	public java.sql.Date getSignDate() {
		return signDate;
	}

	public void setSignDate(java.sql.Date signDate) {
		this.signDate = signDate;
	}

	public java.sql.Date getAddDate() {
		return addDate;
	}

	public void setAddDate(java.sql.Date addDate) {
		this.addDate = addDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getMonthTotalAmount() {
		return monthTotalAmount;
	}

	public void setMonthTotalAmount(Double monthTotalAmount) {
		this.monthTotalAmount = monthTotalAmount;
	}

	public Integer getDiffMonthFlag() {
		return diffMonthFlag;
	}

	public void setDiffMonthFlag(Integer diffMonthFlag) {
		this.diffMonthFlag = diffMonthFlag;
	}

	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
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
