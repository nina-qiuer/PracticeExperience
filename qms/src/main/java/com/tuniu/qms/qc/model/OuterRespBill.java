package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;

public class OuterRespBill extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	/** 质量问题id */
	private Integer qpId;

	/** 供应商ID */
	private Integer agencyId;
	
	/** 供应商名称 */
	private String  agencyName;
	
	/** 责任事由 */
	private String respReason;
	
	/** 改进人ID */
	private Integer impPersonId;
	
	/** 改进人Name */
	private String  impPersonName;
	
	/** 责任人ID */
	private Integer respPersonId;
	
	/** 责任人名称 */
	private String  respPersonName;
	
	/** 责任部门ID */
	private Integer depId;
	
	/** 责任部门名称 */
	private String depName;
	
	/** 质检单ID */
	private Integer qcId;
	
	/** 异议提出人ID */
	private Integer appealPersonId;
	
	/** 异议提出人姓名 */
	private String appealPersonName;
	
	/** 旧责任单id*/
	private Integer oldRespId;
	
	/** 责任经理*/
	private Integer respManagerId;
	private String respManagerName;
	private Integer managerJobId;
	private String managerJobName;
	
	/** 责任总监*/
	private Integer respGeneralId;
	private String respGeneralName;
	private Integer generalJobId;
	private String generalJobName;
	
	/** 质量问题类型Id*/
	private Integer qualityPrombleTypeId;
	
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

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

	public Integer getQpId() {
		return qpId;
	}

	public void setQpId(Integer qpId) {
		this.qpId = qpId;
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getRespReason() {
		return respReason;
	}

	public void setRespReason(String respReason) {
		this.respReason = respReason;
	}

	public Integer getImpPersonId() {
		return impPersonId;
	}

	public void setImpPersonId(Integer impPersonId) {
		this.impPersonId = impPersonId;
	}

	public String getImpPersonName() {
		return impPersonName;
	}

	public void setImpPersonName(String impPersonName) {
		this.impPersonName = impPersonName;
	}

	public Integer getAppealPersonId() {
		return appealPersonId;
	}

	public void setAppealPersonId(Integer appealPersonId) {
		this.appealPersonId = appealPersonId;
	}

	public String getAppealPersonName() {
		return appealPersonName;
	}

	public void setAppealPersonName(String appealPersonName) {
		this.appealPersonName = appealPersonName;
	}

	public Integer getOldRespId() {
		return oldRespId;
	}

	public void setOldRespId(Integer oldRespId) {
		this.oldRespId = oldRespId;
	}

	public Integer getRespManagerId() {
		return respManagerId;
	}

	public void setRespManagerId(Integer respManagerId) {
		this.respManagerId = respManagerId;
	}

	public String getRespManagerName() {
		return respManagerName;
	}

	public void setRespManagerName(String respManagerName) {
		this.respManagerName = respManagerName;
	}

	public Integer getManagerJobId() {
		return managerJobId;
	}

	public void setManagerJobId(Integer managerJobId) {
		this.managerJobId = managerJobId;
	}

	public String getManagerJobName() {
		return managerJobName;
	}

	public void setManagerJobName(String managerJobName) {
		this.managerJobName = managerJobName;
	}

	public Integer getRespGeneralId() {
		return respGeneralId;
	}

	public void setRespGeneralId(Integer respGeneralId) {
		this.respGeneralId = respGeneralId;
	}

	public String getRespGeneralName() {
		return respGeneralName;
	}

	public void setRespGeneralName(String respGeneralName) {
		this.respGeneralName = respGeneralName;
	}

	public Integer getGeneralJobId() {
		return generalJobId;
	}

	public void setGeneralJobId(Integer generalJobId) {
		this.generalJobId = generalJobId;
	}

	public String getGeneralJobName() {
		return generalJobName;
	}

	public void setGeneralJobName(String generalJobName) {
		this.generalJobName = generalJobName;
	}

	public Integer getQualityPrombleTypeId() {
		return qualityPrombleTypeId;
	}

	public void setQualityPrombleTypeId(Integer qualityPrombleTypeId) {
		this.qualityPrombleTypeId = qualityPrombleTypeId;
	}
	
}
