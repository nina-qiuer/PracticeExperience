package com.tuniu.gt.punishprd.vo;

import java.util.List;

import com.tuniu.gt.punishprd.entity.PunishPrdEntity;
import com.tuniu.gt.punishprd.entity.RemarkEntity;

public class LowSatisfyDetailVo {

	private String routeName;
	private Long routeId;
	private String supplier;
	private Integer offlineCount;
	private Double twoWeeksAgoSasisfaction;
	private String twoWeeksAgoBgn;
	private String twoWeeksAgoEnd;
	private List<RemarkEntity> twoWeeksAgoRemarkList;
	private Double lastWeekSatisfaction;
	private String lastWeekBgn;
	private String lastWeekEnd;
	private List<RemarkEntity> lastWeekRemarkList;
	private List<PunishPrdEntity> offlineHistory;
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	
	public Long getRouteId() {
		return routeId;
	}
	public void setRouteId(Long routeId) {
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
	public List<RemarkEntity> getTwoWeeksAgoRemarkList() {
		return twoWeeksAgoRemarkList;
	}
	public void setTwoWeeksAgoRemarkList(List<RemarkEntity> twoWeeksAgoRemarkList) {
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
	public List<RemarkEntity> getLastWeekRemarkList() {
		return lastWeekRemarkList;
	}
	public void setLastWeekRemarkList(List<RemarkEntity> lastWeekRemarkList) {
		this.lastWeekRemarkList = lastWeekRemarkList;
	}
	public List<PunishPrdEntity> getOfflineHistory() {
		return offlineHistory;
	}
	public void setOfflineHistory(List<PunishPrdEntity> offlineHistory) {
		this.offlineHistory = offlineHistory;
	}
	
}
