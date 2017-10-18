package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tuniu.gt.frm.entity.EntityBase;

public class QcQuestionEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -4780467695761548482L;

	private Integer reportId = 0; // 报告id
	
	private Integer bigClassId = 0; // 问题大类id
	private Integer smallClassId = 0; // 问题小类id

	private String bigClassName = ""; // 问题大类名称
	private String smallClassName = ""; // 问题小类名称
	
	private int compLevel = -1; // 投诉问题等级
	private String conclusion; // 质检结论
	private String scoreLevel; // 记分等级
	private int scoreValue; // 记分值
	private String scoreTarget1; // 记分对象1
	private String scoreTarget2; // 记分对象2
	
	private String startCity; // 出发地
	private String endCity; // 目的地
	private String airfare; // 机票价格
	private String productPrice; // 产品价格
	
	private Date buildDate = new Date(); // 设立时间
	private Date addTime = new Date(); // 添加时间
	private Date updateTime = new Date(); // 更新时间
	private Integer delFlag = 0; // 标示位。1为已删除，0为正常
	
	private QcQuestionClassEntity bigClass; // 问题大类 （1：1）
	private QcQuestionClassEntity smallClass; // 问题小类 (1:1)
	
	private List<QcTrackerEntity> trackers = new ArrayList<QcTrackerEntity>(); // 问题追踪者 (1:n) 
	
	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	public String getAirfare() {
		return airfare;
	}

	public void setAirfare(String airfare) {
		this.airfare = airfare;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getScoreLevel() {
		return scoreLevel;
	}

	public void setScoreLevel(String scoreLevel) {
		this.scoreLevel = scoreLevel;
	}

	public int getScoreValue() {
		return scoreValue;
	}

	public void setScoreValue(int scoreValue) {
		this.scoreValue = scoreValue;
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

	public int getCompLevel() {
		return compLevel;
	}

	public void setCompLevel(int compLevel) {
		this.compLevel = compLevel;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public Integer getSmallClassId() {
		return smallClassId;
	}

	public void setSmallClassId(Integer smallClassId) {
		this.smallClassId = smallClassId;
	}

	public String getBigClassName() {
		return bigClassName;
	}

	public void setBigClassName(String bigClassName) {
		this.bigClassName = bigClassName;
	}

	public String getSmallClassName() {
		return smallClassName;
	}

	public void setSmallClassName(String smallClassName) {
		this.smallClassName = smallClassName;
	}

	public Integer getBigClassId() {
		return bigClassId;
	}

	public void setBigClassId(Integer bigClassId) {
		this.bigClassId = bigClassId;
	}

	public Date getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public QcQuestionClassEntity getBigClass() {
		return bigClass;
	}

	public void setBigClass(QcQuestionClassEntity bigClass) {
		this.bigClass = bigClass;
	}

	public QcQuestionClassEntity getSmallClass() {
		return smallClass;
	}

	public void setSmallClass(QcQuestionClassEntity smallClass) {
		this.smallClass = smallClass;
	}

	public List<QcTrackerEntity> getTrackers() {
		return trackers;
	}

	public void setTrackers(List<QcTrackerEntity> trackers) {
		this.trackers = trackers;
	}

	public String toString() {
		return "bigClassId:" + bigClassId + "smallClassId:" + smallClassId
				+ "[" + trackers.toString() +  "]";
	}
	
}
