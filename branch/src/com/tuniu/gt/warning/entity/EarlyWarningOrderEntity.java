package com.tuniu.gt.warning.entity;

import java.io.Serializable;
import java.util.List;

import com.tuniu.gt.frm.entity.EntityBase;
import com.tuniu.gt.sms.entity.SmsSendRecordEntity;

public class EarlyWarningOrderEntity extends EntityBase implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int ewId; // 预警id
	private int orderId; // 订单id
	private int orderType; // 订单类型
	private int routeId; // 线路ID
	private String routeName; // 线路名称
	private int destCategoryId; // 目的地大类ID
	private String destCategoryName; // 目的地大类Name
	private String productType; // 产品类型
	private java.sql.Date startDate; // 出游日期
	private java.sql.Date backDate; // 归来日期
	private String startCity; // 出发地
	private String backCity; // 目的地
	private Integer contactId; // 联系人ID
	private String contactName; // 联系人姓名
	private String contactPhone; // 联系人电话
	private int childCnt; // 儿童数
	private int adultCnt; // 成人数
	private String groupTermNum; // 团期号
	private String selfGroupNum; // 自组团号
	private int complaintId; // 投诉单id，0代表未发起投诉
	private int delFlag; // 解除标志位，0：未解除，1：已解除
	
	private List<EwOrderFlightEntity> flightList; // 关联航班
	
	private List<EwOrderAgencyEntity> agencyList; // 关联供应商
	
	private List<SmsSendRecordEntity> smsList; // 短信发送记录

	public String getGroupTermNum() {
		return groupTermNum;
	}

	public void setGroupTermNum(String groupTermNum) {
		this.groupTermNum = groupTermNum;
	}

	public String getSelfGroupNum() {
		return selfGroupNum;
	}

	public void setSelfGroupNum(String selfGroupNum) {
		this.selfGroupNum = selfGroupNum;
	}

	public int getEwId() {
		return ewId;
	}

	public void setEwId(int ewId) {
		this.ewId = ewId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public int getDestCategoryId() {
		return destCategoryId;
	}

	public void setDestCategoryId(int destCategoryId) {
		this.destCategoryId = destCategoryId;
	}

	public String getDestCategoryName() {
		return destCategoryName;
	}

	public void setDestCategoryName(String destCategoryName) {
		this.destCategoryName = destCategoryName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public java.sql.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.sql.Date startDate) {
		this.startDate = startDate;
	}

	public java.sql.Date getBackDate() {
		return backDate;
	}

	public void setBackDate(java.sql.Date backDate) {
		this.backDate = backDate;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getBackCity() {
		return backCity;
	}

	public void setBackCity(String backCity) {
		this.backCity = backCity;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public int getChildCnt() {
		return childCnt;
	}

	public void setChildCnt(int childCnt) {
		this.childCnt = childCnt;
	}

	public int getAdultCnt() {
		return adultCnt;
	}

	public void setAdultCnt(int adultCnt) {
		this.adultCnt = adultCnt;
	}

	public int getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(int complaintId) {
		this.complaintId = complaintId;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public List<EwOrderFlightEntity> getFlightList() {
		return flightList;
	}

	public void setFlightList(List<EwOrderFlightEntity> flightList) {
		this.flightList = flightList;
	}

	public List<EwOrderAgencyEntity> getAgencyList() {
		return agencyList;
	}

	public void setAgencyList(List<EwOrderAgencyEntity> agencyList) {
		this.agencyList = agencyList;
	}

	public List<SmsSendRecordEntity> getSmsList() {
		return smsList;
	}

	public void setSmsList(List<SmsSendRecordEntity> smsList) {
		this.smsList = smsList;
	}
	
}
