package com.tuniu.qms.report.model;

import com.tuniu.qms.common.model.BaseModel;

public class QualityProblemReport extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	private Integer qcId;
	
	/**1 内部责任单 2 外部责任单 */
	private Integer qpFlag;
	
	/** 触红标识位，0：未触红，1：触红 */
	private Integer touchRedFlag; 
	
	private Integer firstQpTypeId;
	
	private String firstQpTypeName;
	
	private Integer secondQpTypeId;
	
	private String secondQpTypeName;
	
	private Integer thirdQpTypeId;
	
	private String thirdQpTypeName;
	
    private Integer firstDepId;
	
	private String firstDepName;
	
	private Integer twoDepId;
	
	private String twoDepName;
	
	private Integer threeDepId;
	
	private String threeDepName;
	
	private Integer jobId;
	
	private String jobName;
	
	private Integer  respPersonId;
	
	private String respPersonName;
	
	private Integer	agencyId;
	
	private String	agencyName;
	
	private Integer year;
	
	private Integer yearQuarter;
	
	private Integer yearMonth;
	
	private Integer yearWeek;

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}


	public Integer getQpFlag() {
		return qpFlag;
	}

	public void setQpFlag(Integer qpFlag) {
		this.qpFlag = qpFlag;
	}

	public Integer getFirstQpTypeId() {
		return firstQpTypeId;
	}

	public void setFirstQpTypeId(Integer firstQpTypeId) {
		this.firstQpTypeId = firstQpTypeId;
	}

	public String getFirstQpTypeName() {
		return firstQpTypeName;
	}

	public void setFirstQpTypeName(String firstQpTypeName) {
		this.firstQpTypeName = firstQpTypeName;
	}

	public Integer getSecondQpTypeId() {
		return secondQpTypeId;
	}

	public void setSecondQpTypeId(Integer secondQpTypeId) {
		this.secondQpTypeId = secondQpTypeId;
	}

	public String getSecondQpTypeName() {
		return secondQpTypeName;
	}

	public void setSecondQpTypeName(String secondQpTypeName) {
		this.secondQpTypeName = secondQpTypeName;
	}

	public Integer getThirdQpTypeId() {
		return thirdQpTypeId;
	}

	public void setThirdQpTypeId(Integer thirdQpTypeId) {
		this.thirdQpTypeId = thirdQpTypeId;
	}

	public String getThirdQpTypeName() {
		return thirdQpTypeName;
	}

	public void setThirdQpTypeName(String thirdQpTypeName) {
		this.thirdQpTypeName = thirdQpTypeName;
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

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
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

	public Integer getTouchRedFlag() {
		return touchRedFlag;
	}

	public void setTouchRedFlag(Integer touchRedFlag) {
		this.touchRedFlag = touchRedFlag;
	}

	
	
}
