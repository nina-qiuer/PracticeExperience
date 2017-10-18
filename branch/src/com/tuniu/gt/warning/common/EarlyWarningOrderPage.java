package com.tuniu.gt.warning.common;

import java.util.List;

import com.tuniu.gt.toolkit.PageBase;
import com.tuniu.gt.warning.entity.EarlyWarningOrderEntity;

public class EarlyWarningOrderPage extends PageBase {
	
	private String menuId = "menu_1";
	private String ewId;
	private String orderId;
	private String orderType; // 订单类型，"1,2,3,4,0"
	private String flightNo;
	private String flightDtBegin;
	private String flightDtEnd;
	private String flightLtBegin;
	private String flightLtEnd;
	private String flightDcitys;
	private String flightLcitys;
	private String ewTimeBegin;
	private String ewTimeEnd;
	private String warningLv;
	private String warningType;
	private String chgFlightFlag;
	private String destCategoryId;
	private String destCategoryName;
	private String productType; // 产品类型，"1,11,0"
	private String groupTermNum;
	private String selfGroupNum;
	private String warningContent;
	private String sendScope;
	private String launchScope;
	private String createScope;
	private String smsContent;
	private String compContent;
	private String compRemark;
	private String level;
	private String startTimeBegin;
	private String startTimeEnd;
	private String backTimeStart;
	private String backTimeEnd;
	private String routeId;
	private String startCity;
	private String backCity;
	private String agencyId;
	private String agencyName;
	private String agencyName2;
	private String idsStr;
	private int toggleFlag;
	private List<EarlyWarningOrderEntity> ewoList;
	
	public String getCreateScope() {
		return createScope;
	}
	public void setCreateScope(String createScope) {
		this.createScope = createScope;
	}
	public String getDestCategoryId() {
		return destCategoryId;
	}
	public void setDestCategoryId(String destCategoryId) {
		this.destCategoryId = destCategoryId;
	}
	public String getFlightDcitys() {
		return flightDcitys;
	}
	public void setFlightDcitys(String flightDcitys) {
		this.flightDcitys = flightDcitys;
	}
	public String getFlightLcitys() {
		return flightLcitys;
	}
	public void setFlightLcitys(String flightLcitys) {
		this.flightLcitys = flightLcitys;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getAgencyName2() {
		return agencyName2;
	}
	public void setAgencyName2(String agencyName2) {
		this.agencyName2 = agencyName2;
	}
	public int getToggleFlag() {
		return toggleFlag;
	}
	public void setToggleFlag(int toggleFlag) {
		this.toggleFlag = toggleFlag;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getEwId() {
		return ewId;
	}
	public void setEwId(String ewId) {
		this.ewId = ewId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getFlightDtBegin() {
		return flightDtBegin;
	}
	public void setFlightDtBegin(String flightDtBegin) {
		this.flightDtBegin = flightDtBegin;
	}
	public String getFlightDtEnd() {
		return flightDtEnd;
	}
	public void setFlightDtEnd(String flightDtEnd) {
		this.flightDtEnd = flightDtEnd;
	}
	public String getFlightLtBegin() {
		return flightLtBegin;
	}
	public void setFlightLtBegin(String flightLtBegin) {
		this.flightLtBegin = flightLtBegin;
	}
	public String getFlightLtEnd() {
		return flightLtEnd;
	}
	public void setFlightLtEnd(String flightLtEnd) {
		this.flightLtEnd = flightLtEnd;
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
	public String getChgFlightFlag() {
		return chgFlightFlag;
	}
	public void setChgFlightFlag(String chgFlightFlag) {
		this.chgFlightFlag = chgFlightFlag;
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
	public String getWarningContent() {
		return warningContent;
	}
	public void setWarningContent(String warningContent) {
		this.warningContent = warningContent;
	}
	public String getSendScope() {
		return sendScope;
	}
	public void setSendScope(String sendScope) {
		this.sendScope = sendScope;
	}
	public String getLaunchScope() {
		return launchScope;
	}
	public void setLaunchScope(String launchScope) {
		this.launchScope = launchScope;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public String getCompContent() {
		return compContent;
	}
	public void setCompContent(String compContent) {
		this.compContent = compContent;
	}
	public String getCompRemark() {
		return compRemark;
	}
	public void setCompRemark(String compRemark) {
		this.compRemark = compRemark;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
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
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
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
	public String getIdsStr() {
		return idsStr;
	}
	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}
	public List<EarlyWarningOrderEntity> getEwoList() {
		return ewoList;
	}
	public void setEwoList(List<EarlyWarningOrderEntity> ewoList) {
		this.ewoList = ewoList;
	}
	
}
