package com.tuniu.qms.qs.model;

import com.tuniu.qms.common.model.BaseModel;

public class SubstdPurchaseAmt extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	/** 采购单ID */
	private Integer purchaseId;
	
	/** 添加日期 */
	private java.sql.Date addDate;
	
	/** 金额 */
	private Double price;
	
	/** 类型 */
	private String type;
	
	/** 订单ID */
	private Integer ordId;
	
	/** 签约日期 */
	private java.sql.Date signDate;
	
	/** 出游归来日期 */
	private java.sql.Date backDate;
	
	/** 是否现付，0：否，1：是 */
	private Integer payFlag;
	
	/** 当月添加采购单总额 */
	private Double monthTotalAmount;
	
	/** 采购单总额 */
	private Double totalAmount;
	
	/** 合同总额 */
	private Double contractAmount;
	
	/** 是否超时，0：否，1：是 */
	private Integer timeOutFlag;
	
	/** 是否跨月，0：否，1：是 */
	private Integer diffMonthFlag;
	
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
	
	/**出游日期*/
	private java.sql.Date groupDate;
	
	/**线路编号*/
	private Long routeId;
	

	public java.sql.Date getGroupDate() {
		return groupDate;
	}

	public void setGroupDate(java.sql.Date groupDate) {
		this.groupDate = groupDate;
	}


	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public Integer getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public java.sql.Date getSignDate() {
		return signDate;
	}

	public void setSignDate(java.sql.Date signDate) {
		this.signDate = signDate;
	}

	public java.sql.Date getBackDate() {
		return backDate;
	}

	public void setBackDate(java.sql.Date backDate) {
		this.backDate = backDate;
	}

	public Integer getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(Integer payFlag) {
		this.payFlag = payFlag;
	}

	public Double getMonthTotalAmount() {
		return monthTotalAmount;
	}

	public void setMonthTotalAmount(Double monthTotalAmount) {
		this.monthTotalAmount = monthTotalAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

	public Integer getTimeOutFlag() {
		return timeOutFlag;
	}

	public void setTimeOutFlag(Integer timeOutFlag) {
		this.timeOutFlag = timeOutFlag;
	}

	public Integer getDiffMonthFlag() {
		return diffMonthFlag;
	}

	public void setDiffMonthFlag(Integer diffMonthFlag) {
		this.diffMonthFlag = diffMonthFlag;
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
	
}
