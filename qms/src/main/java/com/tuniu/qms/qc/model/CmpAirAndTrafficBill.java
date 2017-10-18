package com.tuniu.qms.qc.model;


/**
 * 研发质检导出专用
 * @author chenhaitao
 *
 */
public class CmpAirAndTrafficBill {
    //周数 
	private Integer week;
	
	//质检单号
	private Integer qcId;
	
	//组别
	private Integer groupFlag;
	
	//质检人
	private String qcPerson;
	
	// 订单号
	private Integer orderId;
	
	// 产品单号
	private Integer prdId;
	
    //质检事宜概述
	private String qcAffairSummary;
	
	//问题判断
	private String description;
	
	//故障类型
	private String qptName;
	
	//改进措施
	private String impAdvice;
	
	//责任人
	private String respPersonName;
	
	//责任部门
	private String depName;
	
	//月份
	private Integer month;
	
    //质检完成时间
    private String finishTime;
    
    //记分
	private Integer scorePunish;
	
	//处罚金额
	private Double economicPunish;

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

	public Integer getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(Integer groupFlag) {
		this.groupFlag = groupFlag;
	}

	public String getQcPerson() {
		return qcPerson;
	}

	public void setQcPerson(String qcPerson) {
		this.qcPerson = qcPerson;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getPrdId() {
		return prdId;
	}

	public void setPrdId(Integer prdId) {
		this.prdId = prdId;
	}

	public String getQcAffairSummary() {
		return qcAffairSummary;
	}

	public void setQcAffairSummary(String qcAffairSummary) {
		this.qcAffairSummary = qcAffairSummary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQptName() {
		return qptName;
	}

	public void setQptName(String qptName) {
		this.qptName = qptName;
	}

	public String getImpAdvice() {
		return impAdvice;
	}

	public void setImpAdvice(String impAdvice) {
		this.impAdvice = impAdvice;
	}

	public String getRespPersonName() {
		return respPersonName;
	}

	public void setRespPersonName(String respPersonName) {
		this.respPersonName = respPersonName;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getScorePunish() {
		return scorePunish;
	}

	public void setScorePunish(Integer scorePunish) {
		this.scorePunish = scorePunish;
	}

	public Double getEconomicPunish() {
		return economicPunish;
	}

	public void setEconomicPunish(Double economicPunish) {
		this.economicPunish = economicPunish;
	}

	
	
	
    
}
