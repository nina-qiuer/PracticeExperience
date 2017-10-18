package com.tuniu.qms.qs.model;

import com.tuniu.qms.common.model.BaseModel;

public class SubstdOrderAmt extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	/** 订单ID */
	private Integer ordId;
	
	/** 统计日期 */
	private java.sql.Date statisticDate;
	
	/** 签约日期 */
	private java.sql.Date signDate;
	
	/** 出游日期 */
	private java.sql.Date backDate;
	
	/** 实收总额 */
	private Double collectionAmount;
	
	/** 应收总额 */
	private Double validAmount;
	
	/** 未收总额 */
	private Double nonCollectionAmount;
	
	/** 合同总额 */
	private Double contractAmount;
	
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
	
	/**产品类型**/
	private String productType;
	
	/**目的地大类**/
	private String destName;
	
	/**产品单号**/
	private Integer prdId;
	
	/**线路编号*/
    private Long routeId;
    

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getDestName() {
		return destName;
	}

	public void setDestName(String destName) {
		this.destName = destName;
	}

	
	public Integer getPrdId() {
		return prdId;
	}

	public void setPrdId(Integer prdId) {
		this.prdId = prdId;
	}


	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public java.sql.Date getStatisticDate() {
		return statisticDate;
	}

	public void setStatisticDate(java.sql.Date statisticDate) {
		this.statisticDate = statisticDate;
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

	public Double getCollectionAmount() {
		return collectionAmount;
	}

	public void setCollectionAmount(Double collectionAmount) {
		this.collectionAmount = collectionAmount;
	}

	public Double getValidAmount() {
		return validAmount;
	}

	public void setValidAmount(Double validAmount) {
		this.validAmount = validAmount;
	}

	public Double getNonCollectionAmount() {
		return nonCollectionAmount;
	}

	public void setNonCollectionAmount(Double nonCollectionAmount) {
		this.nonCollectionAmount = nonCollectionAmount;
	}

	public Double getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
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
