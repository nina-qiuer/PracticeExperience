package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;

public class InnerRespBill extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	/** 质量问题id */
	private Integer qpId;
	
	/** 质检单号*/
	private Integer qcId;

	/** 责任人ID */
	private Integer respPersonId;
	
	/** 责任人Name */
	private String respPersonName;
	
	/** 责任部门ID */
	private Integer depId;
	
	/** 责任部门 */
	private String depName;
	
	/** 责任占比 */
	private Double respRate;
	
	/** 责任岗位ID */
	private Integer jobId;
	
	/** 责任岗位*/
	private String jobName;
	
	/** 责任事由 */
	private String respReason;
	
	/** 改进人ID */
	private Integer impPersonId;
	
	/** 改进人姓名 */
	private String impPersonName;
	
	/** 改进岗位ID */
	private Integer impJobId;
	
	/** 改进人岗位 */
	private String impJobName;
	
	/** 异议提出人ID */
	private Integer appealPersonId;
	
	/** 异议提出人姓名 */
	private String appealPersonName;
	
	/** 异议提出人岗位ID */
	private Integer appealJobId;
	
	/** 异议提出人岗位名称*/
	private String appealJobName;
	
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

	public Double getRespRate() {
		return respRate;
	}

	public void setRespRate(Double respRate) {
		this.respRate = respRate;
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

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
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

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
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

	public String getAppealJobName() {
		return appealJobName;
	}

	public void setAppealJobName(String appealJobName) {
		this.appealJobName = appealJobName;
	}

	public Integer getImpJobId() {
		return impJobId;
	}

	public void setImpJobId(Integer impJobId) {
		this.impJobId = impJobId;
	}

	public String getImpJobName() {
		return impJobName;
	}

	public void setImpJobName(String impJobName) {
		this.impJobName = impJobName;
	}

	public Integer getAppealJobId() {
		return appealJobId;
	}

	public void setAppealJobId(Integer appealJobId) {
		this.appealJobId = appealJobId;
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
	
}
