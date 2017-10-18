package com.tuniu.infobird.vdnlog.entity;

import java.io.Serializable;

public class CriticalReportEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String dealName;//客服姓名
	
	private String deal;//客服编号
	
	private String state;//所属岗位
	
	private String firstCallRate;//首呼及时率
	
	private String callBackRate;//回呼及时率
	
	private String primarySchemeRate;//初步方案及时率
	
	private String complaintFinishRate ;//投诉完成及时率
	
	private String ivrSatisfaction; //IVR满意度
	
	private String upgradeComplaintRate;//升级投诉率

	private String startTime ;//开始时间
	
	private String endTime;//结束时间
	
	private String addTime;//增加时间
	
	private String weekTime;//第几周
	
	


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWeekTime() {
		return weekTime;
	}

	public void setWeekTime(String weekTime) {
		this.weekTime = weekTime;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDeal() {
		return deal;
	}

	public void setDeal(String deal) {
		this.deal = deal;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getFirstCallRate() {
		return firstCallRate;
	}

	public void setFirstCallRate(String firstCallRate) {
		this.firstCallRate = firstCallRate;
	}

	public String getCallBackRate() {
		return callBackRate;
	}

	public void setCallBackRate(String callBackRate) {
		this.callBackRate = callBackRate;
	}

	public String getPrimarySchemeRate() {
		return primarySchemeRate;
	}

	public void setPrimarySchemeRate(String primarySchemeRate) {
		this.primarySchemeRate = primarySchemeRate;
	}

	public String getComplaintFinishRate() {
		return complaintFinishRate;
	}

	public void setComplaintFinishRate(String complaintFinishRate) {
		this.complaintFinishRate = complaintFinishRate;
	}

	public String getIvrSatisfaction() {
		return ivrSatisfaction;
	}

	public void setIvrSatisfaction(String ivrSatisfaction) {
		this.ivrSatisfaction = ivrSatisfaction;
	}

	public String getUpgradeComplaintRate() {
		return upgradeComplaintRate;
	}

	public void setUpgradeComplaintRate(String upgradeComplaintRate) {
		this.upgradeComplaintRate = upgradeComplaintRate;
	}


	
}
