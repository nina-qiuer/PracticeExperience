package com.tuniu.gt.innerqc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.tuniu.gt.frm.entity.EntityBase;

public class InnerQcEntity extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 关联单据类型，0：无关联，1：订单，2：产品，3：Jira */
	private Integer relBillType;
	
	/** 关联单据编号 */
	private String relBillNum;
	
	/** 公司损失 */
	private Double lossAmount;
	
	/** 质检事宜分类ID */
	private Integer typeId;
	
	/** 质检事宜分类Name */
	private String typeName;
	
	/** 质检事宜概述 */
	private String qcEventSummary;
	
	/** 质检事宜详述 */
	private String qcEventDesc;
	
	/** 备注 */
	private String remark;
	
	/** 状态标志位，0：发起中，1：待分配，2：处理中，3：已完成 */
	private Integer state;
	
	/** 申请人id */
	private Integer addPersonId;
	
	/** 申请人Name */
	private String addPersonName;
	
	/** 处理人id */
	private Integer dealPersonId;
	
	/** 处理人Name */
	private String dealPersonName;
	
	/** 添加时间 */
	private Date addTime;
	
	/** 最后更新时间 */
	private Date updateTime;
	
	/** 删除标志位，0：正常，1：删除 */
	private Integer delFlag;
	
	private List<InnerQcAttachEntity> attachList;
	
	private List<InnerQcQuestionEntity> questionList;

	public List<InnerQcAttachEntity> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<InnerQcAttachEntity> attachList) {
		this.attachList = attachList;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getRelBillType() {
		return relBillType;
	}

	public void setRelBillType(Integer relBillType) {
		this.relBillType = relBillType;
	}

	public String getRelBillNum() {
		return relBillNum;
	}

	public void setRelBillNum(String relBillNum) {
		this.relBillNum = relBillNum;
	}

	public Double getLossAmount() {
		return lossAmount;
	}

	public void setLossAmount(Double lossAmount) {
		this.lossAmount = lossAmount;
	}

	public String getQcEventSummary() {
		return qcEventSummary;
	}

	public void setQcEventSummary(String qcEventSummary) {
		this.qcEventSummary = qcEventSummary;
	}

	public String getQcEventDesc() {
		return qcEventDesc;
	}

	public void setQcEventDesc(String qcEventDesc) {
		this.qcEventDesc = qcEventDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getAddPersonId() {
		return addPersonId;
	}

	public void setAddPersonId(Integer addPersonId) {
		this.addPersonId = addPersonId;
	}

	public String getAddPersonName() {
		return addPersonName;
	}

	public void setAddPersonName(String addPersonName) {
		this.addPersonName = addPersonName;
	}

	public Integer getDealPersonId() {
		return dealPersonId;
	}

	public void setDealPersonId(Integer dealPersonId) {
		this.dealPersonId = dealPersonId;
	}

	public String getDealPersonName() {
		return dealPersonName;
	}

	public void setDealPersonName(String dealPersonName) {
		this.dealPersonName = dealPersonName;
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

	public List<InnerQcQuestionEntity> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<InnerQcQuestionEntity> questionList) {
		this.questionList = questionList;
	}
	
}
