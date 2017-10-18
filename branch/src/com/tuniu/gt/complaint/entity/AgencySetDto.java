package com.tuniu.gt.complaint.entity;

import java.io.Serializable;

/**
 * 订单相关供应商
 */
public class AgencySetDto implements Serializable {

	private static final long serialVersionUID = 1143547666362877620L;

	/** 订单ID */
	private Integer ordId;

	/** 供应商ID */
	private Integer agencyId;

	/** 供应商名称 */
	private String agencyName;

	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
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
	
	@Override  
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof AgencySetDto) {
			AgencySetDto agencySetDto=(AgencySetDto)obj;
			result = agencyId.equals(agencySetDto.agencyId);
        }
		return result;
	}
	
	@Override  
	public int hashCode() {
		String hash = agencyId + agencyName;
		return hash.hashCode();  
	}
}
