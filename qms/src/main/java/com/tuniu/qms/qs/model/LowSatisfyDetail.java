package com.tuniu.qms.qs.model;

import java.util.List;

import com.tuniu.qms.qc.model.QcPointAttach;

/**
 * 低满意度
 * @author chenhaitao
 *
 */
public class LowSatisfyDetail {

	private String routeName;
	private Integer routeId;
	private String supplier;
	private Integer offlineCount;
	private Double twoWeeksAgoSasisfaction;
	private String twoWeeksAgoBgn;
	private String twoWeeksAgoEnd;
	private List<Remark> twoWeeksAgoRemarkList;
	private Double lastWeekSatisfaction;
	private String lastWeekBgn;
	private String lastWeekEnd;
	private List<Remark> lastWeekRemarkList;
	private List<PunishPrd> offlineHistory;
	private List<PunishPrd> passlineHistory;
	private List<QcPointAttach> attachList;
	
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	

	public Integer getRouteId() {
		return routeId;
	}
	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public Integer getOfflineCount() {
		return offlineCount;
	}
	public void setOfflineCount(Integer offlineCount) {
		this.offlineCount = offlineCount;
	}
	public Double getTwoWeeksAgoSasisfaction() {
		return twoWeeksAgoSasisfaction;
	}
	public void setTwoWeeksAgoSasisfaction(Double twoWeeksAgoSasisfaction) {
		this.twoWeeksAgoSasisfaction = twoWeeksAgoSasisfaction;
	}
	public String getTwoWeeksAgoBgn() {
		return twoWeeksAgoBgn;
	}
	public void setTwoWeeksAgoBgn(String twoWeeksAgoBgn) {
		this.twoWeeksAgoBgn = twoWeeksAgoBgn;
	}
	public String getTwoWeeksAgoEnd() {
		return twoWeeksAgoEnd;
	}
	public void setTwoWeeksAgoEnd(String twoWeeksAgoEnd) {
		this.twoWeeksAgoEnd = twoWeeksAgoEnd;
	}
	public List<Remark> getTwoWeeksAgoRemarkList() {
		return twoWeeksAgoRemarkList;
	}
	public void setTwoWeeksAgoRemarkList(List<Remark> twoWeeksAgoRemarkList) {
		this.twoWeeksAgoRemarkList = twoWeeksAgoRemarkList;
	}
	public Double getLastWeekSatisfaction() {
		return lastWeekSatisfaction;
	}
	public void setLastWeekSatisfaction(Double lastWeekSatisfaction) {
		this.lastWeekSatisfaction = lastWeekSatisfaction;
	}
	public String getLastWeekBgn() {
		return lastWeekBgn;
	}
	public void setLastWeekBgn(String lastWeekBgn) {
		this.lastWeekBgn = lastWeekBgn;
	}
	public String getLastWeekEnd() {
		return lastWeekEnd;
	}
	public void setLastWeekEnd(String lastWeekEnd) {
		this.lastWeekEnd = lastWeekEnd;
	}
	public List<Remark> getLastWeekRemarkList() {
		return lastWeekRemarkList;
	}
	public void setLastWeekRemarkList(List<Remark> lastWeekRemarkList) {
		this.lastWeekRemarkList = lastWeekRemarkList;
	}
	public List<PunishPrd> getOfflineHistory() {
		return offlineHistory;
	}
	public void setOfflineHistory(List<PunishPrd> offlineHistory) {
		this.offlineHistory = offlineHistory;
	}
	public List<PunishPrd> getPasslineHistory() {
		return passlineHistory;
	}
	public void setPasslineHistory(List<PunishPrd> passlineHistory) {
		this.passlineHistory = passlineHistory;
	}
	public List<QcPointAttach> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<QcPointAttach> attachList) {
		this.attachList = attachList;
	}
	
}
