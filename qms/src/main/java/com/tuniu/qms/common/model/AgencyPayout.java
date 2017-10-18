package com.tuniu.qms.common.model;

import java.io.Serializable;

public class AgencyPayout implements Serializable {

	/**
	 * 供应商赔付信息
	 */
	private static final long serialVersionUID = 1L;

	private Integer orderId;//订单号
	
	private String startCity;//出发城市
	
	private Integer routeId;//线路ID
	
	private String routeName;//线路
	
	private Integer agencyId ;//供应商编号
	
	private String agencyName;//供应商
	
	private Double localCurrencyAmount;//承担金额本币总额
	
	private Double foreignCurrencyAmount;//承担外币金额外币总额
	
	private String confirmState;//确认状态
	
	private String claimBasis; // 索赔理据
	
	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public Double getLocalCurrencyAmount() {
		return localCurrencyAmount;
	}

	public void setLocalCurrencyAmount(Double localCurrencyAmount) {
		this.localCurrencyAmount = localCurrencyAmount;
	}

	public Double getForeignCurrencyAmount() {
		return foreignCurrencyAmount;
	}

	public void setForeignCurrencyAmount(Double foreignCurrencyAmount) {
		this.foreignCurrencyAmount = foreignCurrencyAmount;
	}

	public String getConfirmState() {
		return confirmState;
	}

	public void setConfirmState(String confirmState) {
		this.confirmState = confirmState;
	}

	public String getClaimBasis() {
		return claimBasis;
	}

	public void setClaimBasis(String claimBasis) {
		this.claimBasis = claimBasis;
	}
	
}
