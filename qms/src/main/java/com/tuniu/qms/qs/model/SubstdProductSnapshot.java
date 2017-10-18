package com.tuniu.qms.qs.model;

import com.tuniu.qms.common.model.BaseModel;

public class SubstdProductSnapshot extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
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
	
	/** 统计日期 */
	private java.sql.Date statisticDate;
	
	/** 最远团期 */
	private java.sql.Date furthestGroupDate;
	
	/** 销售期长（天） */
	private Integer salesPeriodLength;
	
	/** 标准销售期长（天） */
	private Integer stdSalesPeriodLength;
	
	/** 销售期长差值（天） */
	private Integer splDifValue;
	
	/** 当前剩余团期数量 */
	private Integer surplusGroupNum;
	
	/** 团期丰富度 */
	private Double groupRichness;
	
	/** 标准团期丰富度 */
	private Double stdGroupRichness;
	
	/** 团期丰富度差值 */
	private Double grDifValue;
	
	/** 独立成团标识位，0：否，1：是 */
	private Integer aloneGroupFlag;

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

	public java.sql.Date getStatisticDate() {
		return statisticDate;
	}

	public void setStatisticDate(java.sql.Date statisticDate) {
		this.statisticDate = statisticDate;
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

	public Integer getSplDifValue() {
		return splDifValue;
	}

	public void setSplDifValue(Integer splDifValue) {
		this.splDifValue = splDifValue;
	}

	public Integer getSurplusGroupNum() {
		return surplusGroupNum;
	}

	public void setSurplusGroupNum(Integer surplusGroupNum) {
		this.surplusGroupNum = surplusGroupNum;
	}

	public Double getGroupRichness() {
		return groupRichness;
	}

	public void setGroupRichness(Double groupRichness) {
		this.groupRichness = groupRichness;
	}

	public Double getStdGroupRichness() {
		return stdGroupRichness;
	}

	public void setStdGroupRichness(Double stdGroupRichness) {
		this.stdGroupRichness = stdGroupRichness;
	}

	public Double getGrDifValue() {
		return grDifValue;
	}

	public void setGrDifValue(Double grDifValue) {
		this.grDifValue = grDifValue;
	}

	public Integer getAloneGroupFlag() {
		return aloneGroupFlag;
	}

	public void setAloneGroupFlag(Integer aloneGroupFlag) {
		this.aloneGroupFlag = aloneGroupFlag;
	}
	
}
