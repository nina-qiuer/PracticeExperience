package com.tuniu.qms.qc.model;

import java.util.Date;

/**
 * 研发质检导出专用
 * @author chenhaitao
 *
 */
public class ResDevExportBill {
    //周数 
	private Integer week;
	
	//质检单号
	private Integer qcId;
	
	//组别
	private Integer groupFlag;
	//质检人
	private String qcPerson;
	
	// 故障来源
	private String faultSource;
	
    //质检事宜概述
	private String qcAffairSummary;
	
	//故障级别
	private String qualityEventClass;
	
	//状态
	private String state;
	
	//影响时长
	private Integer influenceTime;
	
	// 影响系统
	private String influenceSystem;
	
	//影响结果
	private String influenceResult;
	
	//故障类型
	private String qptName;
	
	//处理措施
	private String treMeasures;
	
	//故障类型ID
	private Integer qptId;
	
	//责任人
	private String respPersonName;
	
	//责任部门ID 
	private Integer depId;
	
	//责任部门ID 
   private Integer punishDepId;
		
	
	//责任部门
	private String depName;
	
	//原因分析
	private String causeAnalysis;
	
	//责任系统
	private String respSystem;
	
	//改进措施
	private String impMeasures;
	
	//月份
	private Integer month;
	
	//故障得分
    private float failureScore;
    
    //质检完成时间
    private Date finishTime;
    
    //记分
	private Integer scorePunish;
	
	//处罚金额
	private Double economicPunish;

	
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

	public Integer getPunishDepId() {
		return punishDepId;
	}

	public void setPunishDepId(Integer punishDepId) {
		this.punishDepId = punishDepId;
	}

	public Integer getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(Integer groupFlag) {
		this.groupFlag = groupFlag;
	}

	public String getTreMeasures() {
		return treMeasures;
	}

	public void setTreMeasures(String treMeasures) {
		this.treMeasures = treMeasures;
	}

	public Integer getQptId() {
		return qptId;
	}

	public void setQptId(Integer qptId) {
		this.qptId = qptId;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

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

	public String getQcPerson() {
		return qcPerson;
	}

	public void setQcPerson(String qcPerson) {
		this.qcPerson = qcPerson;
	}

	public String getFaultSource() {
		return faultSource;
	}

	public void setFaultSource(String faultSource) {
		this.faultSource = faultSource;
	}

	public String getQcAffairSummary() {
		return qcAffairSummary;
	}

	public void setQcAffairSummary(String qcAffairSummary) {
		this.qcAffairSummary = qcAffairSummary;
	}

	public String getQualityEventClass() {
		return qualityEventClass;
	}

	public void setQualityEventClass(String qualityEventClass) {
		this.qualityEventClass = qualityEventClass;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getInfluenceTime() {
		return influenceTime;
	}

	public void setInfluenceTime(Integer influenceTime) {
		this.influenceTime = influenceTime;
	}

	public String getInfluenceSystem() {
		return influenceSystem;
	}

	public void setInfluenceSystem(String influenceSystem) {
		this.influenceSystem = influenceSystem;
	}

	public String getInfluenceResult() {
		return influenceResult;
	}

	public void setInfluenceResult(String influenceResult) {
		this.influenceResult = influenceResult;
	}



	public String getQptName() {
		return qptName;
	}

	public void setQptName(String qptName) {
		this.qptName = qptName;
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

	public String getCauseAnalysis() {
		return causeAnalysis;
	}

	public void setCauseAnalysis(String causeAnalysis) {
		this.causeAnalysis = causeAnalysis;
	}

	public String getRespSystem() {
		return respSystem;
	}

	public void setRespSystem(String respSystem) {
		this.respSystem = respSystem;
	}

	public String getImpMeasures() {
		return impMeasures;
	}

	public void setImpMeasures(String impMeasures) {
		this.impMeasures = impMeasures;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public float getFailureScore() {
		return failureScore;
	}

	public void setFailureScore(float failureScore) {
		this.failureScore = failureScore;
	}
    
    
}
