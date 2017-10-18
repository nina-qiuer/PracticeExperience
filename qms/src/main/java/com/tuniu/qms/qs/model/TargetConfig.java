package com.tuniu.qms.qs.model;

import com.tuniu.qms.common.model.BaseModel;

public class TargetConfig extends BaseModel {

	/**
	 * 目标值配置表
	 */
	private static final long serialVersionUID = 1L;

	/**组织架构**/
	private String depName;
	
	/**事业部**/
	private String businessUnit;
	
	/**产品部**/
	private String prdDep;
	
	/**产品组**/
	private String prdTeam;
	
	/**第一季度目标值**/
	private Double oneTargetValue;

	/**第二季度目标值**/
	private Double twoTargetValue;

	/**第三季度目标值**/
	private Double threeTargetValue;

	/**第四季度目标值**/
	private Double fourTargetValue;

	/**年**/
	private Integer year;

	private Integer flag ;
	
	
	
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Double getOneTargetValue() {
		return oneTargetValue;
	}

	public void setOneTargetValue(Double oneTargetValue) {
		this.oneTargetValue = oneTargetValue;
	}

	public Double getTwoTargetValue() {
		return twoTargetValue;
	}

	public void setTwoTargetValue(Double twoTargetValue) {
		this.twoTargetValue = twoTargetValue;
	}

	public Double getThreeTargetValue() {
		return threeTargetValue;
	}

	public void setThreeTargetValue(Double threeTargetValue) {
		this.threeTargetValue = threeTargetValue;
	}

	public Double getFourTargetValue() {
		return fourTargetValue;
	}

	public void setFourTargetValue(Double fourTargetValue) {
		this.fourTargetValue = fourTargetValue;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
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

	
	
}
