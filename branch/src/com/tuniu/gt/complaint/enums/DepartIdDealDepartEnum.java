package com.tuniu.gt.complaint.enums;

import com.tuniu.gt.complaint.Constans;

public enum DepartIdDealDepartEnum {
	// 预订中心
	PREDETERMINE_DEPART(2582, Constans.PREDETERMINE_DEPART),
	// 会员事业部
	CUST_DEPART(2580, Constans.VIP_DEPART),
	// 客户事业部
	CUSTOMER_DEPART(5495, Constans.VIP_DEPART),
	// 途致事业部
	STAR_CUST_DEPART(5496, Constans.VIP_DEPART);

	private Integer depId;

	private String dealDepart;

	private DepartIdDealDepartEnum(Integer depId, String dealDepart) {
		this.depId = depId;
		this.dealDepart = dealDepart;
	}

	private Integer getDepId() {
		return depId;
	}

	private String getDealDepart() {
		return dealDepart;
	}

	public static String getValue(Integer depId) {
		for (DepartIdDealDepartEnum departIdDealDepart : DepartIdDealDepartEnum.values()) {
			if (departIdDealDepart.getDepId().equals(depId)) {
				return departIdDealDepart.getDealDepart();
			}
		}
		return null;
	}
}
