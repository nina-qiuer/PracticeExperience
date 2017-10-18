package com.tuniu.gt.innerqc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.tuniu.gt.frm.entity.EntityBase;

public class InnerQcQuestionEntity extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 内部质检单id */
	private Integer iqcId;
	
	/** 问题描述 */
	private String description;
	
	/** 问题分类id */
	private Integer questionTypeId;
	
	/** 一级问题分类ID */
	private Integer questionClassId1;
	
	/** 一级问题分类Name */
	private String bigClassName;
	
	/** 二级问题分类Name */
	private String smallClassName;
	
	/** 问题等级，1、2、3 */
	private Integer questionLevel;
	
	/** 核实依据 */
	private String verifyBasis;
	
	/** 质检结论 */
	private String conclusion;
	
	/** 添加人id */
	private Integer addPersonId;
	
	/** 添加时间 */
	private Date addTime;
	
	/** 最后更新时间 */
	private Date updateTime;
	
	/** 删除标志位，0：正常，1：删除 */
	private Integer delFlag;
	
	private List<InnerQcDutyEntity> dutyList;

	public Integer getIqcId() {
		return iqcId;
	}

	public void setIqcId(Integer iqcId) {
		this.iqcId = iqcId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(Integer questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public Integer getQuestionClassId1() {
		return questionClassId1;
	}

	public void setQuestionClassId1(Integer questionClassId1) {
		this.questionClassId1 = questionClassId1;
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

	public Integer getQuestionLevel() {
		return questionLevel;
	}

	public void setQuestionLevel(Integer questionLevel) {
		this.questionLevel = questionLevel;
	}

	public String getVerifyBasis() {
		return verifyBasis;
	}

	public void setVerifyBasis(String verifyBasis) {
		this.verifyBasis = verifyBasis;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public Integer getAddPersonId() {
		return addPersonId;
	}

	public void setAddPersonId(Integer addPersonId) {
		this.addPersonId = addPersonId;
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

	public List<InnerQcDutyEntity> getDutyList() {
		return dutyList;
	}

	public void setDutyList(List<InnerQcDutyEntity> dutyList) {
		this.dutyList = dutyList;
	}
	
}
