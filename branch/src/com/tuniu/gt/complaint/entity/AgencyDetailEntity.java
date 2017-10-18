package com.tuniu.gt.complaint.entity;

import java.io.Serializable;

/**
 * 供NB接口调用类
 * @author chenhaitao
 *
 */
public class AgencyDetailEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String complaintId;
	
	private String orderId;
	
	private String guestNum;
	
	private String route;
	
	private String routeId;
	
	private String startTime;
	
	private String agencyId;
	
	private String agencyName;
	
	private String productLeader; 
	
	private String startCity; 
	
	private String statusName; 
	
	private String status; 
	
	private String typeName; 
	
	private String type; 
	
	private String supplierTotal; 
	
	private String addTime; 
	
	private String auditFlag; 
	
	private String audit_Flag_Name;

	private String salerName;//真实名字
	
	private String salerId;//唯一标识
	
	private String tel;//电话
	
	private String mobile;//手机
	
	private String extension;//分机号
	
	private String confirmState;//供应商确认状态
	

	public String getConfirmState() {
		return confirmState;
	}

	public void setConfirmState(String confirmState) {
		this.confirmState = confirmState;
	}

	public String getSalerName() {
		return salerName;
	}

	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}

	public String getSalerId() {
		return salerId;
	}

	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getGuestNum() {
		return guestNum;
	}

	public void setGuestNum(String guestNum) {
		this.guestNum = guestNum;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getProductLeader() {
		return productLeader;
	}

	public void setProductLeader(String productLeader) {
		this.productLeader = productLeader;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSupplierTotal() {
		return supplierTotal;
	}

	public void setSupplierTotal(String supplierTotal) {
		this.supplierTotal = supplierTotal;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public String getAudit_Flag_Name() {
		return audit_Flag_Name;
	}

	public void setAudit_Flag_Name(String audit_Flag_Name) {
		this.audit_Flag_Name = audit_Flag_Name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	} 
	
	
}
