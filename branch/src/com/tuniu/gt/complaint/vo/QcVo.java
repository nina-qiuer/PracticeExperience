package com.tuniu.gt.complaint.vo;

import java.io.Serializable;

public class QcVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 产品id */
	private Integer prdId;
	
	/** 团期 */
	private Long groupDate;
	
	/** 订单id */
	private Integer ordId;
	
	/** 投诉单id */
	private Integer cmpId;
	
	/** 使用方（组别），1：投诉质检组，2：运营质检组，3：研发质检组 */
	private Integer groupFlag;
	
	/** 质检类型id */
	private Integer qcTypeId;
	
	/** 质检事宜概述 */
	private String qcAffairSummary;
	
	/** 质检事宜详述 */
	private String qcAffairDesc;
	
	/** 备注 */
	private String remark;
	
	/** 损失金额 */
	private Double lossAmount;
	
	/** 申请人id */
	private Integer addPersonId;
	
	/** 投诉时间*/
	private String buildDate;

	public Integer getPrdId() {
		return prdId;
	}

	public void setPrdId(Integer prdId) {
		this.prdId = prdId;
	}

	public Long getGroupDate() {
		return groupDate;
	}

	public void setGroupDate(Long groupDate) {
		this.groupDate = groupDate;
	}

	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public Integer getCmpId() {
		return cmpId;
	}

	public void setCmpId(Integer cmpId) {
		this.cmpId = cmpId;
	}

	public Integer getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(Integer groupFlag) {
		this.groupFlag = groupFlag;
	}

	public Integer getQcTypeId() {
		return qcTypeId;
	}

	public void setQcTypeId(Integer qcTypeId) {
		this.qcTypeId = qcTypeId;
	}

	public String getQcAffairSummary() {
		return qcAffairSummary;
	}

	public void setQcAffairSummary(String qcAffairSummary) {
		this.qcAffairSummary = qcAffairSummary;
	}

	public String getQcAffairDesc() {
		return qcAffairDesc;
	}

	public void setQcAffairDesc(String qcAffairDesc) {
		this.qcAffairDesc = qcAffairDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getLossAmount() {
		return lossAmount;
	}

	public void setLossAmount(Double lossAmount) {
		this.lossAmount = lossAmount;
	}

	public Integer getAddPersonId() {
		return addPersonId;
	}

	public void setAddPersonId(Integer addPersonId) {
		this.addPersonId = addPersonId;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}
	
}
