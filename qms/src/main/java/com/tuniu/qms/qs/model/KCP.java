package com.tuniu.qms.qs.model;

import java.util.Date;

import com.tuniu.qms.common.model.BaseModel;

public class KCP extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	/** 名称 */
	private String name;
	
	/** 问题描述 */
	private String description;
	
	/** 举例说明 */
	private String example;
	
	/** 问题分析 */
	private String analysis;
	
	/** kcp类型ID */
	private Integer kcpTypeId;
	
	/** kcp类型名称 */
	private String kcpTypeName;
	
	/** kcp来源ID */
	private Integer kcpSourceId;
	
	/** kcp来源名称 */
	private String kcpSourceName;
	
	/** 重要程度标识位，1：普通，2：重要，3：非常重要 */
	private Integer importantFlag;
	
	/** 状态，0：发起中，1：待初审，2：待终审，3：审核通过 */
	private Integer state;
	
	/** 初审人ID */
	private Integer audit1Id;
	
	/** 初审人姓名 */
	private String audit1Name;
	
	/** 终审人ID */
	private Integer audit2Id;
	
	/** 终审人姓名 */
	private String audit2Name;
	
	/** 审核通过时间 */
	private Date auditTime;
	
	/** 申请人ID */
	private Integer addPersonId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getAudit1Id() {
		return audit1Id;
	}

	public void setAudit1Id(Integer audit1Id) {
		this.audit1Id = audit1Id;
	}

	public String getAudit1Name() {
		return audit1Name;
	}

	public void setAudit1Name(String audit1Name) {
		this.audit1Name = audit1Name;
	}

	public Integer getAudit2Id() {
		return audit2Id;
	}

	public void setAudit2Id(Integer audit2Id) {
		this.audit2Id = audit2Id;
	}

	public String getAudit2Name() {
		return audit2Name;
	}

	public void setAudit2Name(String audit2Name) {
		this.audit2Name = audit2Name;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Integer getAddPersonId() {
		return addPersonId;
	}

	public void setAddPersonId(Integer addPersonId) {
		this.addPersonId = addPersonId;
	}

	public String getKcpTypeName() {
		return kcpTypeName;
	}

	public void setKcpTypeName(String kcpTypeName) {
		this.kcpTypeName = kcpTypeName;
	}

	public String getKcpSourceName() {
		return kcpSourceName;
	}

	public void setKcpSourceName(String kcpSourceName) {
		this.kcpSourceName = kcpSourceName;
	}
	
}
