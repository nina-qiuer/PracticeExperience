package com.tuniu.gt.warning.entity;

import java.io.Serializable;

import com.tuniu.gt.frm.entity.EntityBase;

public class EwOrderAgencyEntity extends EntityBase implements Serializable{
	
	private static final long serialVersionUID = 1142530108878643356L;
	
	private int ewOrderId; // 预警订单ID
	
	private int agencyId; // 供应商ID
	
	private String agencyName; // 供应商Name

	public int getEwOrderId() {
		return ewOrderId;
	}

	public void setEwOrderId(int ewOrderId) {
		this.ewOrderId = ewOrderId;
	}

	public int getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

}
