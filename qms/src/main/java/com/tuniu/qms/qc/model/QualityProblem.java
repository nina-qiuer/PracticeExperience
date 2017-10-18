package com.tuniu.qms.qc.model;

import java.util.List;

import com.tuniu.qms.common.model.BaseModel;

public class QualityProblem extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	/** 质检单id */
	private Integer qcId;
	
	/** 质量问题类型ID */
	private Integer qptId;
	
	/** 一级质量问题类型id */
	private Integer qptLv1Id;
	
	/** 质量问题类型名称 */
	private String qptName;
	
	/** 问题描述 */
	private String description;
	
	/** 核实依据 */
	private String verifyBasis;
	
	/** 改进建议 */
	private String impAdvice;
	
	/** 低满意度（满意度小于等于50%） */
	private Integer lowSatisDegree;
	
	private Integer oldQpId;
	
	/** 附件列表 */
	private List<UpLoadAttach> upLoadList;
	
	/** 内部责任单 */
	private List<InnerRespBill> innerList;
	
	/** 外部责任单 */
	private List<OuterRespBill> outerList;

	
	
	public Integer getOldQpId() {
		return oldQpId;
	}

	public void setOldQpId(Integer oldQpId) {
		this.oldQpId = oldQpId;
	}

	public Integer getQptLv1Id() {
		return qptLv1Id;
	}

	public void setQptLv1Id(Integer qptLv1Id) {
		this.qptLv1Id = qptLv1Id;
	}

	public List<InnerRespBill> getInnerList() {
		return innerList;
	}

	public void setInnerList(List<InnerRespBill> innerList) {
		this.innerList = innerList;
	}

	public List<OuterRespBill> getOuterList() {
		return outerList;
	}

	public void setOuterList(List<OuterRespBill> outerList) {
		this.outerList = outerList;
	}

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

	public Integer getQptId() {
		return qptId;
	}

	public void setQptId(Integer qptId) {
		this.qptId = qptId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVerifyBasis() {
		return verifyBasis;
	}

	public void setVerifyBasis(String verifyBasis) {
		this.verifyBasis = verifyBasis;
	}

	public String getImpAdvice() {
		return impAdvice;
	}

	public void setImpAdvice(String impAdvice) {
		this.impAdvice = impAdvice;
	}

	public List<UpLoadAttach> getUpLoadList() {
		return upLoadList;
	}

	public void setUpLoadList(List<UpLoadAttach> upLoadList) {
		this.upLoadList = upLoadList;
	}

	public String getQptName() {
		return qptName;
	}

	public void setQptName(String qptName) {
		this.qptName = qptName;
	}

	public Integer getLowSatisDegree() {
		return lowSatisDegree;
	}

	public void setLowSatisDegree(Integer lowSatisDegree) {
		this.lowSatisDegree = lowSatisDegree;
	}
	
}
