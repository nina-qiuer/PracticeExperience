package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;
/**
 *获取导游所有质量问题明细信息
 * @author chenhaitao
 *
 */
public class QualityProblemDetail extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 private String guideId ;// 导游ID
	 
	 private String ordId ;// 订单号
	 
	 private String groupDate ;//团期
	 
	 private String cmpId ;// 投诉单号
	 
	 private String cmpTime;//投诉时间
	 
	 private String cmpLevel;// 投诉等级，1/2/3
	 
	 private String qcId;//质检单号
	 
	 private String qcFinishTime ;//质检完成时间
	 
	 private String problemId ;//质量问题单ID
	 
	 private String problemType; //问题类型（直接取三级）
	 
	 private String touchRedFlag ;//触红标识，0：否，1：是
	 
	 private String  description;// 问题描述

	public String getGuideId() {
		return guideId;
	}

	public void setGuideId(String guideId) {
		this.guideId = guideId;
	}

	public String getOrdId() {
		return ordId;
	}

	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}

	public String getGroupDate() {
		return groupDate;
	}

	public void setGroupDate(String groupDate) {
		this.groupDate = groupDate;
	}

	public String getCmpId() {
		return cmpId;
	}

	public void setCmpId(String cmpId) {
		this.cmpId = cmpId;
	}

	public String getCmpTime() {
		return cmpTime;
	}

	public void setCmpTime(String cmpTime) {
		this.cmpTime = cmpTime;
	}

	public String getCmpLevel() {
		return cmpLevel;
	}

	public void setCmpLevel(String cmpLevel) {
		this.cmpLevel = cmpLevel;
	}

	public String getQcId() {
		return qcId;
	}

	public void setQcId(String qcId) {
		this.qcId = qcId;
	}

	public String getQcFinishTime() {
		return qcFinishTime;
	}

	public void setQcFinishTime(String qcFinishTime) {
		this.qcFinishTime = qcFinishTime;
	}

	public String getProblemId() {
		return problemId;
	}

	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}

	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	public String getTouchRedFlag() {
		return touchRedFlag;
	}

	public void setTouchRedFlag(String touchRedFlag) {
		this.touchRedFlag = touchRedFlag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
}
