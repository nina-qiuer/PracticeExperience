package com.tuniu.qms.qc.dto;

import java.util.List;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.QcAuxiliaryBill;

public class QcAuxiliaryBillDto extends BaseDto<QcAuxiliaryBill> {

	//模板ID
	private Integer templateId;
	
	private Integer qcId;

	private List<Integer>billIds;// 赋值表IDs
	
	private String qcTypeName;
	
	private Integer qcType;
	
	
	
	public Integer getQcType() {
		return qcType;
	}

	public void setQcType(Integer qcType) {
		this.qcType = qcType;
	}

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

	public String getQcTypeName() {
		return qcTypeName;
	}

	public void setQcTypeName(String qcTypeName) {
		this.qcTypeName = qcTypeName;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public List<Integer> getBillIds() {
		return billIds;
	}

	public void setBillIds(List<Integer> billIds) {
		this.billIds = billIds;
	}

	
	
	
}
