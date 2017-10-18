package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;

public class PunishStandard extends BaseModel {

	private static final long serialVersionUID = -4568476751195060521L;

	/** 处罚等级 */
	private String level;
	
	/** 红线标识位，0：未红线，1：红线 */
	private Integer redLineFlag; 
	
	/** 分级标准描述 */
	private String description;
	
	/** 处罚对象，1：内部员工，2：供应商 */
	private Integer punishObj;
	
	/** 经济处罚（元） */
	private Double economicPunish;
	
	/** 记分处罚（分） */
	private Integer scorePunish;
	
	/** 出处 */
	private String source;
	
	/** 投诉质检是否使用，0：否，1：是 */
	private Integer cmpQcUse;
	
	/** 运营质检是否使用，0：否，1：是 */
	private Integer operQcUse;
	
	/** 研发质检是否使用，0：否，1：是 */
	private Integer devQcUse;
	
	/** 内部质检是否使用，0：否，1：是 */
	private Integer innerQcUse;

	
	public Integer getInnerQcUse() {
		return innerQcUse;
	}

	public void setInnerQcUse(Integer innerQcUse) {
		this.innerQcUse = innerQcUse;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getRedLineFlag() {
		return redLineFlag;
	}

	public void setRedLineFlag(Integer redLineFlag) {
		this.redLineFlag = redLineFlag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPunishObj() {
		return punishObj;
	}

	public void setPunishObj(Integer punishObj) {
		this.punishObj = punishObj;
	}

	public Double getEconomicPunish() {
		return economicPunish;
	}

	public void setEconomicPunish(Double economicPunish) {
		this.economicPunish = economicPunish;
	}

	public Integer getScorePunish() {
		return scorePunish;
	}

	public void setScorePunish(Integer scorePunish) {
		this.scorePunish = scorePunish;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getCmpQcUse() {
		return cmpQcUse;
	}

	public void setCmpQcUse(Integer cmpQcUse) {
		this.cmpQcUse = cmpQcUse;
	}

	public Integer getOperQcUse() {
		return operQcUse;
	}

	public void setOperQcUse(Integer operQcUse) {
		this.operQcUse = operQcUse;
	}

	public Integer getDevQcUse() {
		return devQcUse;
	}

	public void setDevQcUse(Integer devQcUse) {
		this.devQcUse = devQcUse;
	}
	
}
