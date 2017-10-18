package com.tuniu.qms.qc.model;

import java.util.List;

import com.tuniu.qms.common.model.BaseModel;
/**
 * 研发故障单
 * @author chenhaitao
 *
 */
public class DevFaultBill extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	/** 质检单id */
	private Integer qcId;
	
	/** 故障类型ID */
	private Integer qptId;
	
	/** 故障类型名称 */
	private String qptName;
	
	/** 原因分析*/
	private String causeAnalysis;
	
	/** 处理措施 */
	private String treMeasures;
	
	/**影响系统*/
	private String influenceSystem;
	
	/** 改进措施 */
	private String impMeasures;
	
	/** 是否为上线（含变更）引起 */
	private Integer onLine;//0标识上线引起 1标识不是
	
	/** 是否为上线（含变更）引起 的内容*/
	private String jiraAddress;
	
	/** 附件列表 */
	private List<DevUpLoadAttach> upLoadList;
	
	/** 责任单 */
	private List<DevRespBill> devList;

	/**故障单类型*/
	private Integer useFlag ;
	
	
	public Integer getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}

	public String getInfluenceSystem() {
		return influenceSystem;
	}

	public void setInfluenceSystem(String influenceSystem) {
		this.influenceSystem = influenceSystem;
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

	public String getQptName() {
		return qptName;
	}

	public void setQptName(String qptName) {
		this.qptName = qptName;
	}

	public String getCauseAnalysis() {
		return causeAnalysis;
	}

	public void setCauseAnalysis(String causeAnalysis) {
		this.causeAnalysis = causeAnalysis;
	}

	public String getTreMeasures() {
		return treMeasures;
	}

	public void setTreMeasures(String treMeasures) {
		this.treMeasures = treMeasures;
	}


	public List<DevUpLoadAttach> getUpLoadList() {
		return upLoadList;
	}

	public void setUpLoadList(List<DevUpLoadAttach> upLoadList) {
		this.upLoadList = upLoadList;
	}

	public List<DevRespBill> getDevList() {
		return devList;
	}

	public void setDevList(List<DevRespBill> devList) {
		this.devList = devList;
	}

	public Integer getOnLine() {
		return onLine;
	}

	public void setOnLine(Integer onLine) {
		this.onLine = onLine;
	}

	public String getJiraAddress() {
		return jiraAddress;
	}

	public void setJiraAddress(String jiraAddress) {
		this.jiraAddress = jiraAddress;
	}

	public String getImpMeasures() {
		return impMeasures;
	}

	public void setImpMeasures(String impMeasures) {
		this.impMeasures = impMeasures;
	}


	

	
	
	
}
