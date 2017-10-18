package com.tuniu.bi.dto;


public class OrderExtDto {
	
	/** 订单ID */
	private Integer id;
	
	/** 产品ID */
	private Integer prdId;
	
	/** 产品名称 */
	private String prdName;
	
	/** 目的地大类 */
	private String destCate;
	
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
	
	/** 出游日期 */
	private java.sql.Date departDate;
	
	/** 出团通知发送日期 */
	private java.sql.Date noticeSendDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPrdId() {
		return prdId;
	}

	public void setPrdId(Integer prdId) {
		this.prdId = prdId;
	}

	public String getPrdName() {
		return prdName;
	}

	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}

	public String getDestCate() {
		return destCate;
	}

	public void setDestCate(String destCate) {
		this.destCate = destCate;
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

	public java.sql.Date getDepartDate() {
		return departDate;
	}

	public void setDepartDate(java.sql.Date departDate) {
		this.departDate = departDate;
	}

	public java.sql.Date getNoticeSendDate() {
		return noticeSendDate;
	}

	public void setNoticeSendDate(java.sql.Date noticeSendDate) {
		this.noticeSendDate = noticeSendDate;
	}
	
}
