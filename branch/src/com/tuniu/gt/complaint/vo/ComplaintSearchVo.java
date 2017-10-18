package com.tuniu.gt.complaint.vo;

import java.util.HashMap;
import java.util.Map;

public class ComplaintSearchVo {
	private String orderId;
	private String id;
	private String complaintId;
	private String routeId;
	private String comeFrom;
	private String startCity;
	private String backCity;
	private String productLeader;
	private String state;
	private String orderStateTemp;
	private String level;
	private String notInTimeDeal;
	private String notInTimeDealDate;
	private String startTimeBegin;
	private String startTimeEnd;
	private String customerLeader;
	private String agencyId;
	private String agencyName;
	private String startBuildDate;
	private String endBuildDate;
	private String startFinishDate;
	private String endFinishDate;
	private String dealNameText;
	private String dealDepart;
	private String tab_flag;
	private String ownerName;
	private String deal;
	private String startAssignTime;
	private String endAssignTime;
	private String dealName;
	private String managerName;
	private String route;
	private String contactPhone;
	private String assignee;
	private String backTimeStart;
	private String backTimeEnd;
	private String complaintStartTime;
	private String complaintEndTime;
	private String destination;
	private String leader;
	private String preSales;
	private String productManager;
	private String producter;
	private String qcPersonName;
	private String reason;
	private String salesLeader;
	private String source;
	private String closedStartTime;
	private String closedEndTime;
	private String stateWork;
	private String ewTimeBegin;
	private String ewTimeEnd;
	private String warningLv;
	private String warningType;
	private String releaseName;
	private String compCity;
	private String compTimeThBgn;
	private String compTimeThEnd;
	private String chgFlightFlag;
	private String signCity;
	private String bdpName;
	private String priority;
    private String isReparations;
    private String isNoOrder;
    private String isDistribute;
    
	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderId", this.orderId);
		map.put("id", this.id);
		map.put("priority", this.priority);
		map.put("complaintId", this.complaintId);
		map.put("routeId", this.routeId);
		map.put("comeFrom", this.comeFrom);
		map.put("startCity", this.startCity);
		map.put("productLeader", this.productLeader);
		map.put("state", this.state);
		map.put("orderStateTemp", this.orderStateTemp);
		map.put("level", this.level);
		map.put("notInTimeDeal", this.notInTimeDeal);
		map.put("notInTimeDealDate", this.notInTimeDealDate);
		map.put("startTimeBegin", this.startTimeBegin);
		map.put("startTimeEnd", this.startTimeEnd);
		map.put("customerLeader", this.customerLeader);
		map.put("agencyId", this.agencyId);
		map.put("agencyName", this.agencyName);
		map.put("startBuildDate", this.startBuildDate);
		map.put("endBuildDate", this.endBuildDate);
		map.put("startFinishDate", this.startFinishDate);
		map.put("endFinishDate", this.endFinishDate);
		map.put("dealNameText", this.dealNameText);
		map.put("dealDepart", this.dealDepart);
		map.put("tab_flag", this.tab_flag);
		map.put("ownerName", this.ownerName);
		map.put("deal", this.deal);
		map.put("startAssignTime", this.startAssignTime);
		map.put("endAssignTime", this.endAssignTime);
		map.put("dealName", this.dealName);
		map.put("managerName", this.managerName);
		map.put("route", this.route);
		map.put("contactPhone", this.contactPhone);
		map.put("assignee", this.assignee);
		map.put("backTimeStart", this.backTimeStart);
		map.put("backTimeEnd", this.backTimeEnd);
		map.put("complaintStartTime", this.complaintStartTime);
		map.put("complaintEndTime", this.complaintEndTime);
		map.put("destination", this.destination);
		map.put("leader", this.leader);
		map.put("preSales", this.preSales);
		map.put("productManager", this.productManager);
		map.put("producter", this.producter);
		map.put("qcPersonName", this.qcPersonName);
		map.put("reason", this.reason);
		map.put("salesLeader", this.salesLeader);
		map.put("source", this.source);
		map.put("closedStartTime", this.closedStartTime);
		map.put("closedEndTime", this.closedEndTime);
		map.put("stateWork", this.stateWork);
		map.put("ewTimeBegin", this.ewTimeBegin);
		map.put("ewTimeEnd", this.ewTimeEnd);
		map.put("warningLv", this.warningLv);
		map.put("warningType", this.warningType);
		map.put("releaseName", this.releaseName);
		map.put("compCity", this.compCity);
		map.put("compTimeThBgn", this.compTimeThBgn);
		map.put("compTimeThEnd", this.compTimeThEnd);
		map.put("chgFlightFlag", this.chgFlightFlag);
		map.put("signCity", this.signCity);
		map.put("bdpName", bdpName);
		map.put("isReparations", this.isReparations);
		map.put("isNoOrder", this.isNoOrder);
		map.put("isDistribute", this.isDistribute);
		return map;
	}
	

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSignCity() {
		return signCity;
	}

	public void setSignCity(String signCity) {
		this.signCity = signCity;
	}

	public String getChgFlightFlag() {
		return chgFlightFlag;
	}

	public void setChgFlightFlag(String chgFlightFlag) {
		this.chgFlightFlag = chgFlightFlag;
	}

	public String getCompTimeThBgn() {
		return compTimeThBgn;
	}

	public void setCompTimeThBgn(String compTimeThBgn) {
		this.compTimeThBgn = compTimeThBgn;
	}

	public String getCompTimeThEnd() {
		return compTimeThEnd;
	}

	public void setCompTimeThEnd(String compTimeThEnd) {
		this.compTimeThEnd = compTimeThEnd;
	}

	public String getCompCity() {
		return compCity;
	}

	public void setCompCity(String compCity) {
		this.compCity = compCity;
	}

	public String getReleaseName() {
		return releaseName;
	}

	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}

	public String getEwTimeBegin() {
		return ewTimeBegin;
	}

	public void setEwTimeBegin(String ewTimeBegin) {
		this.ewTimeBegin = ewTimeBegin;
	}

	public String getEwTimeEnd() {
		return ewTimeEnd;
	}

	public void setEwTimeEnd(String ewTimeEnd) {
		this.ewTimeEnd = ewTimeEnd;
	}

	public String getWarningLv() {
		return warningLv;
	}

	public void setWarningLv(String warningLv) {
		this.warningLv = warningLv;
	}

	public String getWarningType() {
		return warningType;
	}

	public void setWarningType(String warningType) {
		this.warningType = warningType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
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

	public String getProductLeader() {
		return productLeader;
	}

	public void setProductLeader(String productLeader) {
		this.productLeader = productLeader;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOrderStateTemp() {
		return orderStateTemp;
	}

	public void setOrderStateTemp(String orderStateTemp) {
		this.orderStateTemp = orderStateTemp;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getNotInTimeDeal() {
		return notInTimeDeal;
	}

	public void setNotInTimeDeal(String notInTimeDeal) {
		this.notInTimeDeal = notInTimeDeal;
	}

	public String getNotInTimeDealDate() {
		return notInTimeDealDate;
	}

	public void setNotInTimeDealDate(String notInTimeDealDate) {
		this.notInTimeDealDate = notInTimeDealDate;
	}

	public String getStartTimeBegin() {
		return startTimeBegin;
	}

	public void setStartTimeBegin(String startTimeBegin) {
		this.startTimeBegin = startTimeBegin;
	}

	public String getStartTimeEnd() {
		return startTimeEnd;
	}

	public void setStartTimeEnd(String startTimeEnd) {
		this.startTimeEnd = startTimeEnd;
	}

	public String getCustomerLeader() {
		return customerLeader;
	}

	public void setCustomerLeader(String customerLeader) {
		this.customerLeader = customerLeader;
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

	public String getStartBuildDate() {
		return startBuildDate;
	}

	public void setStartBuildDate(String startBuildDate) {
		this.startBuildDate = startBuildDate;
	}

	public String getEndBuildDate() {
		return endBuildDate;
	}

	public void setEndBuildDate(String endBuildDate) {
		this.endBuildDate = endBuildDate;
	}

	public String getStartFinishDate() {
		return startFinishDate;
	}

	public void setStartFinishDate(String startFinishDate) {
		this.startFinishDate = startFinishDate;
	}

	public String getEndFinishDate() {
		return endFinishDate;
	}

	public void setEndFinishDate(String endFinishDate) {
		this.endFinishDate = endFinishDate;
	}

	public String getDealNameText() {
		return dealNameText;
	}

	public void setDealNameText(String dealNameText) {
		this.dealNameText = dealNameText;
	}

	public String getDealDepart() {
		return dealDepart;
	}

	public void setDealDepart(String dealDepart) {
		this.dealDepart = dealDepart;
	}

	public String getTab_flag() {
		return tab_flag;
	}

	public void setTab_flag(String tab_flag) {
		this.tab_flag = tab_flag;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getDeal() {
		return deal;
	}

	public void setDeal(String deal) {
		this.deal = deal;
	}

	public String getStartAssignTime() {
		return startAssignTime;
	}

	public void setStartAssignTime(String startAssignTime) {
		this.startAssignTime = startAssignTime;
	}

	public String getEndAssignTime() {
		return endAssignTime;
	}

	public void setEndAssignTime(String endAssignTime) {
		this.endAssignTime = endAssignTime;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getBackTimeStart() {
		return backTimeStart;
	}

	public void setBackTimeStart(String backTimeStart) {
		this.backTimeStart = backTimeStart;
	}

	public String getBackTimeEnd() {
		return backTimeEnd;
	}

	public void setBackTimeEnd(String backTimeEnd) {
		this.backTimeEnd = backTimeEnd;
	}

	public String getComplaintStartTime() {
		return complaintStartTime;
	}

	public void setComplaintStartTime(String complaintStartTime) {
		this.complaintStartTime = complaintStartTime;
	}

	public String getComplaintEndTime() {
		return complaintEndTime;
	}

	public void setComplaintEndTime(String complaintEndTime) {
		this.complaintEndTime = complaintEndTime;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getPreSales() {
		return preSales;
	}

	public void setPreSales(String preSales) {
		this.preSales = preSales;
	}

	public String getProductManager() {
		return productManager;
	}

	public void setProductManager(String productManager) {
		this.productManager = productManager;
	}

	public String getProducter() {
		return producter;
	}

	public void setProducter(String producter) {
		this.producter = producter;
	}

	public String getQcPersonName() {
		return qcPersonName;
	}

	public void setQcPersonName(String qcPersonName) {
		this.qcPersonName = qcPersonName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSalesLeader() {
		return salesLeader;
	}

	public void setSalesLeader(String salesLeader) {
		this.salesLeader = salesLeader;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getClosedStartTime() {
		return closedStartTime;
	}

	public void setClosedStartTime(String closedStartTime) {
		this.closedStartTime = closedStartTime;
	}

	public String getClosedEndTime() {
		return closedEndTime;
	}

	public void setClosedEndTime(String closedEndTime) {
		this.closedEndTime = closedEndTime;
	}

	public String getStateWork() {
		return stateWork;
	}

	public void setStateWork(String stateWork) {
		this.stateWork = stateWork;
	}
	
	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

	public String getBdpName() {
		return bdpName;
	}

	public void setBdpName(String bdpName) {
		this.bdpName = bdpName;
	}

    public String getIsReparations() {
        return isReparations;
    }

    public void setIsReparations(String isReparations) {
        this.isReparations = isReparations;
    }

	public String getIsNoOrder() {
		return isNoOrder;
	}

	public void setIsNoOrder(String isNoOrder) {
		this.isNoOrder = isNoOrder;
	}

	public String getIsDistribute() {
		return isDistribute;
	}

	public void setIsDistribute(String isDistribute) {
		this.isDistribute = isDistribute;
	}
}
