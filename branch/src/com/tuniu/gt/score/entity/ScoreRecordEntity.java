package com.tuniu.gt.score.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class ScoreRecordEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.sql.Date qcDate; // 质检日期
	
	private String qcPerson; // 质检人
	
	private int orderId; // 订单号
	
	private int routeId; // 线路号
	
	private int complaintId; // 投诉单号
	
	private String jiraNum; // JIRA单号
	
	private int questionClassId; // 质检问题类型ID
	
	private int questionClassId1; // 质检问题类型 - 一级类型ID
	
	private String bigClassName; // 问题大类Name
	
	private String smallClassName; // 问题小类Name
	
	private String responsiblePerson;  // 责任人
	
	private String improver; // 改进人
	
	private int depId1; // 一级部门ID
	
	private String depName1;
	
	private int depId2; // 二级部门ID
	
	private String depName2;
	
	private int positionId; // 岗位ID
	
	private String positionName; // 岗位名称
	
	private String scoreTarget1; // 记分对象1
	
	private String scoreTarget2; // 记分对象2
	
	private int scoreValue1; // 记分值1
	
	private int scoreValue2; // 记分值2
	
	private int scoreTypeId; // 记分类型ID
	
	private int scoreTypeId1; // 记分性质ID
	
	private String scoreTypeName1; // 记分性质
	
	private String scoreTypeName2; // 记分类型
	
	private String description; // 问题描述
	
	private Date addTime;
	
	private Date updateTime;
	
	private int delFlag; // 删除标志位，0：正常，1：删除

	public int getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(int complaintId) {
		this.complaintId = complaintId;
	}

	public int getQuestionClassId1() {
		return questionClassId1;
	}

	public void setQuestionClassId1(int questionClassId1) {
		this.questionClassId1 = questionClassId1;
	}

	public int getScoreTypeId1() {
		return scoreTypeId1;
	}

	public void setScoreTypeId1(int scoreTypeId1) {
		this.scoreTypeId1 = scoreTypeId1;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public java.sql.Date getQcDate() {
		return qcDate;
	}

	public void setQcDate(java.sql.Date qcDate) {
		this.qcDate = qcDate;
	}

	public String getQcPerson() {
		return qcPerson;
	}

	public void setQcPerson(String qcPerson) {
		this.qcPerson = qcPerson;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public String getJiraNum() {
		return jiraNum;
	}

	public void setJiraNum(String jiraNum) {
		this.jiraNum = jiraNum;
	}

	public int getQuestionClassId() {
		return questionClassId;
	}

	public void setQuestionClassId(int questionClassId) {
		this.questionClassId = questionClassId;
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

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getImprover() {
		return improver;
	}

	public void setImprover(String improver) {
		this.improver = improver;
	}

	public int getDepId1() {
		return depId1;
	}

	public void setDepId1(int depId1) {
		this.depId1 = depId1;
	}

	public String getDepName1() {
		return depName1;
	}

	public void setDepName1(String depName1) {
		this.depName1 = depName1;
	}

	public int getDepId2() {
		return depId2;
	}

	public void setDepId2(int depId2) {
		this.depId2 = depId2;
	}

	public String getDepName2() {
		return depName2;
	}

	public void setDepName2(String depName2) {
		this.depName2 = depName2;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
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

	public int getScoreValue1() {
		return scoreValue1;
	}

	public void setScoreValue1(int scoreValue1) {
		this.scoreValue1 = scoreValue1;
	}

	public int getScoreValue2() {
		return scoreValue2;
	}

	public void setScoreValue2(int scoreValue2) {
		this.scoreValue2 = scoreValue2;
	}

	public int getScoreTypeId() {
		return scoreTypeId;
	}

	public void setScoreTypeId(int scoreTypeId) {
		this.scoreTypeId = scoreTypeId;
	}

	public String getScoreTypeName1() {
		return scoreTypeName1;
	}

	public void setScoreTypeName1(String scoreTypeName1) {
		this.scoreTypeName1 = scoreTypeName1;
	}

	public String getScoreTypeName2() {
		return scoreTypeName2;
	}

	public void setScoreTypeName2(String scoreTypeName2) {
		this.scoreTypeName2 = scoreTypeName2;
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

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	
}
