package com.tuniu.qms.qc.model;

import java.util.List;

import com.tuniu.qms.common.model.BaseModel;

public class OuterPunishBill extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	/** 质检单id */
	private Integer qcId;
	
	/**质检状态*/
	private Integer qcState;

	/** 供应商ID */
	private Integer agencyId;
	
	/** 供应商Name */
	private String agencyName;
	
	/** 处罚事由 */
	private String punishReason;
	
	/** 记分处罚（分） */
	private Integer scorePunish;
	
	/** 经济处罚（元） */
	private Double economicPunish;
	
	/** 订单号    */
	private Integer ordId;
	/** 投诉单号  */
	private Integer cmpId;
	/** 产品单号  */
	private Integer prdId;
	/** JIRA单号  */
	private Integer jiraNum;
	
	private Integer qcTypeId;
	
	/** 质检类型  */
	private String qcTypeName;
	
	private Integer oldId;
	
	/** 责任单Id*/
	private Integer respId;
	/** 责任单类型     1：内部责任单，2：外部责任单*/
	private Integer respType;
	
	public Integer getOldId() {
		return oldId;
	}

	public void setOldId(Integer oldId) {
		this.oldId = oldId;
	}

	public Integer getQcState() {
		return qcState;
	}

	public void setQcState(Integer qcState) {
		this.qcState = qcState;
	}

	/** 处罚依据 */
	private List<OuterPunishBasis> opbList;

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

	public Integer getPrdId() {
		return prdId;
	}

	public void setPrdId(Integer prdId) {
		this.prdId = prdId;
	}

	public Integer getJiraNum() {
		return jiraNum;
	}

	public void setJiraNum(Integer jiraNum) {
		this.jiraNum = jiraNum;
	}

	public Integer getQcTypeId() {
		return qcTypeId;
	}

	public void setQcTypeId(Integer qcTypeId) {
		this.qcTypeId = qcTypeId;
	}

	public String getQcTypeName() {
		return qcTypeName;
	}

	public void setQcTypeName(String qcTypeName) {
		this.qcTypeName = qcTypeName;
	}

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
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

	public String getPunishReason() {
		return punishReason;
	}

	public void setPunishReason(String punishReason) {
		this.punishReason = punishReason;
	}

	public Integer getScorePunish() {
		return scorePunish;
	}

	public void setScorePunish(Integer scorePunish) {
		this.scorePunish = scorePunish;
	}

	public Double getEconomicPunish() {
		return economicPunish;
	}

	public void setEconomicPunish(Double economicPunish) {
		this.economicPunish = economicPunish;
	}

	public List<OuterPunishBasis> getOpbList() {
		return opbList;
	}

	public void setOpbList(List<OuterPunishBasis> opbList) {
		this.opbList = opbList;
	}

	public Integer getRespId() {
		return respId;
	}

	public void setRespId(Integer respId) {
		this.respId = respId;
	}

	public Integer getRespType() {
		return respType;
	}

	public void setRespType(Integer respType) {
		this.respType = respType;
	}
		
}
