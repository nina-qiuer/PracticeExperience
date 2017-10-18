package com.tuniu.qms.qs.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qs.model.KCP;

public class KCPDto extends BaseDto<KCP> {
	
	/** 申请人ID */
	private Integer addPersonId;
	
	/** 状态，0：发起中，1：待初审，2：待终审，3：审核通过 */
	private Integer state;
	
	/** 申请日期 */
	private String applyDateBgn;
	
	/** 申请日期 */
	private String applyDateEnd;
	
	/** 审核日期 */
	private String auditDateBgn;
	
	/** 审核日期 */
	private String auditDateEnd;
	
	/** kcp类型ID */
	private Integer kcpTypeId;
	
	/** kcp来源ID */
	private Integer kcpSourceId;
	
	/** 重要程度标识位，1：普通，2：重要，3：非常重要 */
	private Integer importantFlag;
	
	/** 初审人姓名 */
	private String audit1Name;
	
	/** 终审人姓名 */
	private String audit2Name;

	public Integer getAddPersonId() {
		return addPersonId;
	}

	public void setAddPersonId(Integer addPersonId) {
		this.addPersonId = addPersonId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getApplyDateBgn() {
		return applyDateBgn;
	}

	public void setApplyDateBgn(String applyDateBgn) {
		this.applyDateBgn = applyDateBgn;
	}

	public String getApplyDateEnd() {
		return applyDateEnd;
	}

	public void setApplyDateEnd(String applyDateEnd) {
		this.applyDateEnd = applyDateEnd;
	}

	public String getAuditDateBgn() {
		return auditDateBgn;
	}

	public void setAuditDateBgn(String auditDateBgn) {
		this.auditDateBgn = auditDateBgn;
	}

	public String getAuditDateEnd() {
		return auditDateEnd;
	}

	public void setAuditDateEnd(String auditDateEnd) {
		this.auditDateEnd = auditDateEnd;
	}

	public Integer getKcpTypeId() {
		return kcpTypeId;
	}

	public void setKcpTypeId(Integer kcpTypeId) {
		this.kcpTypeId = kcpTypeId;
	}

	public Integer getKcpSourceId() {
		return kcpSourceId;
	}

	public void setKcpSourceId(Integer kcpSourceId) {
		this.kcpSourceId = kcpSourceId;
	}

	public Integer getImportantFlag() {
		return importantFlag;
	}

	public void setImportantFlag(Integer importantFlag) {
		this.importantFlag = importantFlag;
	}

	public String getAudit1Name() {
		return audit1Name;
	}

	public void setAudit1Name(String audit1Name) {
		this.audit1Name = audit1Name;
	}

	public String getAudit2Name() {
		return audit2Name;
	}

	public void setAudit2Name(String audit2Name) {
		this.audit2Name = audit2Name;
	}

}
