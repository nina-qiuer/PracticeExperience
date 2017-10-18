package com.tuniu.bi.dto;


public class ProductExtDto {
	
	/** 产品ID */
	private Integer id;
	
	/** 产品名称 */
	private String prdName;
	
	/** 目的地大类 */
	private String destCate;
	
	/** 独立成团标识位，0：否，1：是 */
	private Integer aloneGroupFlag;
	
	/** 当前剩余团期数量 */
	private Integer surplusGroupNum;
	
	/** 最远团期 */
	private java.sql.Date furthestGroupDate;
	
	/** 销售期长（天） */
	private Integer salesPeriodLength;
	
	/** 标准销售期长（天） */
	private Integer stdSalesPeriodLength;
	
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getAloneGroupFlag() {
		return aloneGroupFlag;
	}

	public void setAloneGroupFlag(Integer aloneGroupFlag) {
		this.aloneGroupFlag = aloneGroupFlag;
	}

	public Integer getSurplusGroupNum() {
		return surplusGroupNum;
	}

	public void setSurplusGroupNum(Integer surplusGroupNum) {
		this.surplusGroupNum = surplusGroupNum;
	}

	public java.sql.Date getFurthestGroupDate() {
		return furthestGroupDate;
	}

	public void setFurthestGroupDate(java.sql.Date furthestGroupDate) {
		this.furthestGroupDate = furthestGroupDate;
	}

	public Integer getSalesPeriodLength() {
		return salesPeriodLength;
	}

	public void setSalesPeriodLength(Integer salesPeriodLength) {
		this.salesPeriodLength = salesPeriodLength;
	}

	public Integer getStdSalesPeriodLength() {
		return stdSalesPeriodLength;
	}

	public void setStdSalesPeriodLength(Integer stdSalesPeriodLength) {
		this.stdSalesPeriodLength = stdSalesPeriodLength;
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
