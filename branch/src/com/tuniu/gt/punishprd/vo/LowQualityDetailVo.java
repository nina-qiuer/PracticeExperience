package com.tuniu.gt.punishprd.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tuniu.gt.punishprd.entity.PunishPrdEntity;

public class LowQualityDetailVo {
	private Integer id;
	private Long routeId;//线路id
	private String routeName;//线路名
	private Integer month;//统计周期：月
	private Double satisfation;//满意度
	private Integer remarkAmount;//点评次数
	private Integer touchRedCount;//触红次数
	private String prdName; // 产品名称（目的地）
	private String prdArea; // 线路所属方向
	private Integer areaPrdCnt; // 同方向后5%线路数
	private Integer scoreRank; // 满意度排名
	private String prdDepartment;//产品部门
	private String prdManager;//产品经理
	private String prdOfficer;//产品专员
	private Map<Integer,List<Date>> historyDateList; // 历史下线日期表   key：1 触红、2 地满意  、 3 低质量
	private Integer offlineCause; // 低质量下线原因   1：上个月连续两次触红   2：上个自然月触犯低满意度    3：gmv排名在后5%的后5%
	private List<PunishPrdEntity> touchRedCauseList; // 触红原因触发低质量列表
	
	public Long getRouteId() {
		return routeId;
	}
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Double getSatisfation() {
		return satisfation;
	}
	public void setSatisfation(Double satisfation) {
		this.satisfation = satisfation;
	}
	
	public Integer getRemarkAmount() {
		return remarkAmount;
	}
	public void setRemarkAmount(Integer remarkAmount) {
		this.remarkAmount = remarkAmount;
	}
	public Integer getTouchRedCount() {
		return touchRedCount;
	}
	public void setTouchRedCount(Integer touchRedCount) {
		this.touchRedCount = touchRedCount;
	}
	
	public String getPrdArea() {
		return prdArea;
	}
	public void setPrdArea(String prdArea) {
		this.prdArea = prdArea;
	}
	public String getPrdDepartment() {
		return prdDepartment;
	}
	public void setPrdDepartment(String prdDepartment) {
		this.prdDepartment = prdDepartment;
	}
	public String getPrdManager() {
		return prdManager;
	}
	public void setPrdManager(String prdManager) {
		this.prdManager = prdManager;
	}
	public String getPrdOfficer() {
		return prdOfficer;
	}
	public void setPrdOfficer(String prdOfficer) {
		this.prdOfficer = prdOfficer;
	}
	
	public Map<Integer, List<Date>> getHistoryDateList() {
		return historyDateList;
	}
	public void setHistoryDateList(Map<Integer, List<Date>> historyDateList) {
		this.historyDateList = historyDateList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPrdName() {
		return prdName;
	}
	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}
	public Integer getOfflineCause() {
		return offlineCause;
	}
	public void setOfflineCause(Integer offlineCause) {
		this.offlineCause = offlineCause;
	}
	public List<PunishPrdEntity> getTouchRedCauseList() {
		return touchRedCauseList;
	}
	public void setTouchRedCauseList(List<PunishPrdEntity> touchRedCauseList) {
		this.touchRedCauseList = touchRedCauseList;
	}
	public Integer getAreaPrdCnt() {
		return areaPrdCnt;
	}
	public void setAreaPrdCnt(Integer areaPrdCnt) {
		this.areaPrdCnt = areaPrdCnt;
	}
	public Integer getScoreRank() {
		return scoreRank;
	}
	public void setScoreRank(Integer scoreRank) {
		this.scoreRank = scoreRank;
	}
	
}
