package com.tuniu.qms.qs.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qs.model.TargetConfig;

public class TargetConfigDto extends BaseDto<TargetConfig> {
	
	/**组织名称**/
	private String depName;
	
	/**事业部**/
	private String businessUnit;
	
	
	/**产品部**/
	private String prdDep;
	
	
	/**产品组**/
	private String prdTeam;
	
	/**年**/
	private Integer nowYear ;
	
	/**年**/
	private Integer year ;
	
	

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getNowYear() {
		return nowYear;
	}

	public void setNowYear(Integer nowYear) {
		this.nowYear = nowYear;
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

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}
	
	

}
