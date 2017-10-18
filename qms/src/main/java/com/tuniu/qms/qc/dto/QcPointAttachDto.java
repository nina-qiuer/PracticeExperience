package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.QcPointAttach;

/**
 * 上传
 * @author chenhaitao
 *
 */
public class QcPointAttachDto extends  BaseDto<QcPointAttach>{

	/** 质检单号*/
   private Integer qcId;
   
   /** 关联单据类型  1：质检单，2：投诉改进报告*/
   private Integer billType;
   
   /** 附件id*/
   private Integer upId;

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public Integer getUpId() {
		return upId;
	}

	public void setUpId(Integer upId) {
		this.upId = upId;
	}
	
}
