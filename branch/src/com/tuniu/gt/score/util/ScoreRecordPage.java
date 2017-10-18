package com.tuniu.gt.score.util;

import java.util.List;

import com.tuniu.gt.score.entity.ScoreRecordEntity;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.gt.toolkit.PageBase;

public class ScoreRecordPage extends PageBase {
	
	private String qcDateBgn;
	private String qcDateEnd;
	private String srId;
	private String qcPerson;
	private String qcPersons;
	private String orderId;
	private String routeId;
	private String jiraNum;
	private String depId1;
	private String depId2;
	private String scoreTarget1;
	private String scoreTarget2;
	private String myName;
	private String[] srIds;
	private String srIdStr;
	private long totalValue;
	private String qcTeamId;
	private List<ScoreRecordEntity> srList;
	
	public String getQcPersons() {
		return qcPersons;
	}
	public void setQcPersons(String qcPersons) {
		this.qcPersons = qcPersons;
	}
	public String getQcTeamId() {
		return qcTeamId;
	}
	public void setQcTeamId(String qcTeamId) {
		this.qcTeamId = qcTeamId;
	}
	public String getMyName() {
		return myName;
	}
	public void setMyName(String myName) {
		this.myName = myName;
	}
	public long getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(long totalValue) {
		this.totalValue = totalValue;
	}
	public String getSrIdStr() {
		return srIdStr;
	}
	public void setSrIdStr(String srIdStr) {
		this.srIdStr = srIdStr;
	}
	public String[] getSrIds() {
		return srIds;
	}
	public void setSrIds(String[] srIds) {
		this.srIds = srIds;
		srIdStr = CommonUtil.arrToStr(srIds);
	}
	public String getQcDateBgn() {
		return qcDateBgn;
	}
	public void setQcDateBgn(String qcDateBgn) {
		this.qcDateBgn = qcDateBgn;
	}
	public String getQcDateEnd() {
		return qcDateEnd;
	}
	public void setQcDateEnd(String qcDateEnd) {
		this.qcDateEnd = qcDateEnd;
	}
	public String getSrId() {
		return srId;
	}
	public void setSrId(String srId) {
		this.srId = srId;
	}
	public String getQcPerson() {
		return qcPerson;
	}
	public void setQcPerson(String qcPerson) {
		this.qcPerson = qcPerson;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	public String getJiraNum() {
		return jiraNum;
	}
	public void setJiraNum(String jiraNum) {
		this.jiraNum = jiraNum;
	}
	public String getDepId1() {
		return depId1;
	}
	public void setDepId1(String depId1) {
		this.depId1 = depId1;
	}
	public String getDepId2() {
		return depId2;
	}
	public void setDepId2(String depId2) {
		this.depId2 = depId2;
	}
	public String getScoreTarget1() {
		return scoreTarget1;
	}
	public void setScoreTarget1(String scoreTarget1) {
		this.scoreTarget1 = scoreTarget1;
	}
	public String getScoreTarget2() {
		return scoreTarget2;
	}
	public void setScoreTarget2(String scoreTarget2) {
		this.scoreTarget2 = scoreTarget2;
	}
	public List<ScoreRecordEntity> getSrList() {
		return srList;
	}
	public void setSrList(List<ScoreRecordEntity> srList) {
		this.srList = srList;
	}
	
}
