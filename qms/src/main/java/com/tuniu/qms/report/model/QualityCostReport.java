package com.tuniu.qms.report.model;

import com.tuniu.qms.common.model.BaseModel;

/**
 * 质量成本台账
 * @author chenhaitao
 *
 */
public class QualityCostReport  extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
  
	/** 内部责任单号*/
	private Integer ispId;
	
	/** 质检单号*/
	private Integer qcId;
	
	/** 投诉单号*/
	private Integer cmpId;
	
	/** 产品ID */
	private Integer prdId;
	
	/** 一级成本科目*/
	private String firstCostAccount;
	
	/** 二级成本科目*/
	private String twoCostAccount;
	
	/** 三级成本科目*/
	private String threeCostAccount;
	
	/** 四级成本科目*/
	private String fourCostAccount;
	
	/** 一级责任部门ID*/
	private Integer firstDepId;
	
	/** 一级责任部门*/
	private String firstDepName;
	
	/** 二级责任部门ID*/
	private Integer twoDepId;
	
	/** 二级责任部门*/
	private String twoDepName;
	
	/** 三级责任部门ID*/
	private Integer threeDepId;
	
	/** 三级责任部门*/
	private String threeDepName;
	
	/**责任岗位ID*/
	private Integer jobId;
	
	/** 责任岗位Name*/
	private String jobName;
	
	/** 责任人ID*/
	private Integer respPersonId;
	
	/** 责任人Name*/
	private String respPersonName;
	
	/**责任占比*/
	private Double respRate;
	
	/** 质量成本*/
	private Double qualityCost;
	
	/** 处理人ID*/
	private Integer dealPersonId;
	
	/** 处理人姓名*/
	private String dealPersonName;
	
	private Integer year;
	
	private Integer yearQuarter;
	
	private Integer yearMonth;
	
	private Integer yearWeek;

	public Integer getIspId() {
		return ispId;
	}

	public void setIspId(Integer ispId) {
		this.ispId = ispId;
	}

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

	public Integer getCmpId() {
		return cmpId;
	}

	public void setCmpId(Integer cmpId) {
		this.cmpId = cmpId;
	}

	public String getFirstCostAccount() {
		return firstCostAccount;
	}

	public void setFirstCostAccount(String firstCostAccount) {
		this.firstCostAccount = firstCostAccount;
	}

	public String getTwoCostAccount() {
		return twoCostAccount;
	}

	public void setTwoCostAccount(String twoCostAccount) {
		this.twoCostAccount = twoCostAccount;
	}

	public String getThreeCostAccount() {
		return threeCostAccount;
	}

	public void setThreeCostAccount(String threeCostAccount) {
		this.threeCostAccount = threeCostAccount;
	}

	public String getFourCostAccount() {
		return fourCostAccount;
	}

	public void setFourCostAccount(String fourCostAccount) {
		this.fourCostAccount = fourCostAccount;
	}

	public Integer getFirstDepId() {
		return firstDepId;
	}

	public void setFirstDepId(Integer firstDepId) {
		this.firstDepId = firstDepId;
	}

	public String getFirstDepName() {
		return firstDepName;
	}

	public void setFirstDepName(String firstDepName) {
		this.firstDepName = firstDepName;
	}

	public Integer getTwoDepId() {
		return twoDepId;
	}

	public void setTwoDepId(Integer twoDepId) {
		this.twoDepId = twoDepId;
	}

	public String getTwoDepName() {
		return twoDepName;
	}

	public void setTwoDepName(String twoDepName) {
		this.twoDepName = twoDepName;
	}

	public Integer getThreeDepId() {
		return threeDepId;
	}

	public void setThreeDepId(Integer threeDepId) {
		this.threeDepId = threeDepId;
	}

	public String getThreeDepName() {
		return threeDepName;
	}

	public void setThreeDepName(String threeDepName) {
		this.threeDepName = threeDepName;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Integer getRespPersonId() {
		return respPersonId;
	}

	public void setRespPersonId(Integer respPersonId) {
		this.respPersonId = respPersonId;
	}

	public String getRespPersonName() {
		return respPersonName;
	}

	public void setRespPersonName(String respPersonName) {
		this.respPersonName = respPersonName;
	}

	public Double getRespRate() {
		return respRate;
	}

	public void setRespRate(Double respRate) {
		this.respRate = respRate;
	}

	public Double getQualityCost() {
		return qualityCost;
	}

	public void setQualityCost(Double qualityCost) {
		this.qualityCost = qualityCost;
	}

	public Integer getDealPersonId() {
		return dealPersonId;
	}

	public void setDealPersonId(Integer dealPersonId) {
		this.dealPersonId = dealPersonId;
	}

	public String getDealPersonName() {
		return dealPersonName;
	}

	public void setDealPersonName(String dealPersonName) {
		this.dealPersonName = dealPersonName;
	}

	public Integer getPrdId() {
		return prdId;
	}

	public void setPrdId(Integer prdId) {
		this.prdId = prdId;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getYearQuarter() {
		return yearQuarter;
	}

	public void setYearQuarter(Integer yearQuarter) {
		this.yearQuarter = yearQuarter;
	}

	public Integer getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Integer yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Integer getYearWeek() {
		return yearWeek;
	}

	public void setYearWeek(Integer yearWeek) {
		this.yearWeek = yearWeek;
	}
	
}
