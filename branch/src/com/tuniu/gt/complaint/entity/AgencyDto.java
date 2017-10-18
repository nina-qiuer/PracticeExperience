package com.tuniu.gt.complaint.entity;


import java.io.Serializable;

/**
 * 订单相关供应商
 * @author chenhaitao
 */
public class AgencyDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	/** 订单ID */
	private Integer ordId;
	
	/** 供应商ID */
	private Integer agencyId;
	
	/** 供应商名称 */
	private String agencyName;
	
	private Integer delFlag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
}
