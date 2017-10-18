package com.tuniu.qms.qs.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qs.model.QualityCostLedger;

/**
 * 质量成本台账Dto
 * @author chenhaitao
 *
 */
public class QualityCostLedgerDto extends BaseDto<QualityCostLedger> {

	
	private Integer ispId;
	
	private Integer qcId;
	
	private Integer ordId;
	
	private String qptName;
	
	private String firstCostAccount;
	
	private String twoCostAccount;
	
	private String threeCostAccount;
	
	private String fourCostAccount;
	
    private Integer firstDepId;
	
	private Integer twoDepId;
	
	private Integer threeDepId;
	
	private String depName;
	
	private Integer jobId;
	
	private String jobName;
	
	private String respPersonName;
	
	private String dealPersonName;

	/**排序字段*/
    private String orderField = "id";
    
    /**排序方向  0：不排序  1：升序  2：降序*/
    private Integer orderDirect = 0;
    
    


	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public Integer getOrderDirect() {
		return orderDirect;
	}

	public void setOrderDirect(Integer orderDirect) {
		this.orderDirect = orderDirect;
	}

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
	

	public String getQptName() {
		return qptName;
	}

	public void setQptName(String qptName) {
		this.qptName = qptName;
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

	public Integer getTwoDepId() {
		return twoDepId;
	}

	public void setTwoDepId(Integer twoDepId) {
		this.twoDepId = twoDepId;
	}

	public Integer getThreeDepId() {
		return threeDepId;
	}

	public void setThreeDepId(Integer threeDepId) {
		this.threeDepId = threeDepId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
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

	public String getRespPersonName() {
		return respPersonName;
	}

	public void setRespPersonName(String respPersonName) {
		this.respPersonName = respPersonName;
	}

	public String getDealPersonName() {
		return dealPersonName;
	}

	public void setDealPersonName(String dealPersonName) {
		this.dealPersonName = dealPersonName;
	}
	
	
}
