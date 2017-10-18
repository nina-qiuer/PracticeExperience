package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.OuterPunishBill;

public class OuterPunishBillDto extends BaseDto<OuterPunishBill>{

	
	private Integer qcId;
	
	private Integer state =-1;
	
	/**供应商编号*/
	private Integer agencyId;
	
	/**供应商名称*/
	private String agencyName;
	
	/**记分处罚区间*/
	private Integer scorePunishBgn;
	
	/**记分处罚区间*/
	private Integer scorePunishEnd;
	
	/**经济处罚区间*/
	private Double economicPunishBgn;
	
	/**经济处罚区间*/
	private Double economicPunishEnd;
	
	/**投诉单号*/
	private Integer cmpId;
	
	/**订单号*/
	private Integer ordId;
	
	/**产品ID*/
	private Integer prdId;
	
	/**使用方（组别），1：投诉质检组，2：运营质检组，3：研发质检组*/
	private Integer groupFlag;
	
	/**jira号*/
	private Integer jiraNum ;
	
	/**质检类型*/
	private Integer qcTypeId;
	
	/**质检类型名称*/
	private String qcTypeName;
	
	private String addPunishTimeBgn;
	
	private String addPunishTimeEnd;
	

	

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAddPunishTimeBgn() {
		return addPunishTimeBgn;
	}

	public void setAddPunishTimeBgn(String addPunishTimeBgn) {
		this.addPunishTimeBgn = addPunishTimeBgn;
	}

	public String getAddPunishTimeEnd() {
		return addPunishTimeEnd;
	}

	public void setAddPunishTimeEnd(String addPunishTimeEnd) {
		this.addPunishTimeEnd = addPunishTimeEnd;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public Integer getScorePunishBgn() {
		return scorePunishBgn;
	}

	public void setScorePunishBgn(Integer scorePunishBgn) {
		this.scorePunishBgn = scorePunishBgn;
	}

	public Integer getScorePunishEnd() {
		return scorePunishEnd;
	}

	public void setScorePunishEnd(Integer scorePunishEnd) {
		this.scorePunishEnd = scorePunishEnd;
	}

	public Double getEconomicPunishBgn() {
		return economicPunishBgn;
	}

	public void setEconomicPunishBgn(Double economicPunishBgn) {
		this.economicPunishBgn = economicPunishBgn;
	}

	public Double getEconomicPunishEnd() {
		return economicPunishEnd;
	}

	public void setEconomicPunishEnd(Double economicPunishEnd) {
		this.economicPunishEnd = economicPunishEnd;
	}

	public Integer getCmpId() {
		return cmpId;
	}

	public void setCmpId(Integer cmpId) {
		this.cmpId = cmpId;
	}

	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public Integer getPrdId() {
		return prdId;
	}

	public void setPrdId(Integer prdId) {
		this.prdId = prdId;
	}

	public Integer getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(Integer groupFlag) {
		this.groupFlag = groupFlag;
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

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}
	
}
