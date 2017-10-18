package com.tuniu.gt.innerqc.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class InnerQcDutyEntity extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 质量问题id */
	private Integer questionId;
	
	/** 一级责任部门ID */
	private Integer depId1;
	
	private String depName1;
	
	/** 二级责任部门ID */
	private Integer depId2;
	
	private String depName2;
	
	/** 责任岗位ID */
	private Integer positionId;
	
	private String positionName;
	
	/** 责任人ID */
	private Integer respPersonId;
	
	private String respPersonName;
	
	/** 记分值 */
	private Integer scoreValue;
	
	/** 罚款金额 */
	private Double fineAmount;
	
	/** 添加人id */
	private Integer addPersonId;
	
	/** 添加时间 */
	private Date addTime;
	
	/** 最后更新时间 */
	private Date updateTime;
	
	/** 删除标志位，0：正常，1：删除 */
	private Integer delFlag;

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getDepId1() {
		return depId1;
	}

	public void setDepId1(Integer depId1) {
		this.depId1 = depId1;
	}

	public String getDepName1() {
		return depName1;
	}

	public void setDepName1(String depName1) {
		this.depName1 = depName1;
	}

	public Integer getDepId2() {
		return depId2;
	}

	public void setDepId2(Integer depId2) {
		this.depId2 = depId2;
	}

	public String getDepName2() {
		return depName2;
	}

	public void setDepName2(String depName2) {
		this.depName2 = depName2;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Integer getRespPersonId() {
		return respPersonId;
	}

	public void setRespPersonId(Integer respPersonId) {
		this.respPersonId = respPersonId;
	}

	public String getRespPersonName() {
		return respPersonName;
	}

	public void setRespPersonName(String respPersonName) {
		this.respPersonName = respPersonName;
	}

	public Integer getScoreValue() {
		return scoreValue;
	}

	public void setScoreValue(Integer scoreValue) {
		this.scoreValue = scoreValue;
	}

	public Double getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(Double fineAmount) {
		this.fineAmount = fineAmount;
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
	
}
