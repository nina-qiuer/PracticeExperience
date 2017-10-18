package com.tuniu.qms.qc.dto;

import java.util.List;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.PunishStandard;

public class PunishStandardDto extends BaseDto<PunishStandard> {

	/**处罚对象，1：内部员工，2：供应商*/
	private Integer punishObj ;
	
	/**处罚标准ID*/
	private List<Integer> standardIds;
	
	/**处罚单编号*/
	private int punishId ;
	
	/**处罚等级*/
	private String level ;
	
	/** 红线标识位，0：未红线，1：红线 */
	private Integer redLineFlag;
	
	/**投诉质检使用*/
	private Integer cmpQcUse ;
	
	/**运营质检使用*/
	private Integer operQcUse ;
	
	/**研发质检使用*/
	private Integer devQcUse ;
	
	/**内部质检使用*/
	private Integer innerQcUse ;
	
	private Integer qcFlag =-1;
	
	
	public Integer getQcFlag() {
		return qcFlag;
	}

	public void setQcFlag(Integer qcFlag) {
		this.qcFlag = qcFlag;
	}

	public Integer getInnerQcUse() {
		return innerQcUse;
	}

	public void setInnerQcUse(Integer innerQcUse) {
		this.innerQcUse = innerQcUse;
	}

	private Integer useFlag;
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<Integer> getStandardIds() {
		return standardIds;
	}

	public void setStandardIds(List<Integer> standardIds) {
		this.standardIds = standardIds;
	}

	public int getPunishId() {
		return punishId;
	}

	public void setPunishId(int punishId) {
		this.punishId = punishId;
	}

	public Integer getPunishObj() {
		return punishObj;
	}

	public void setPunishObj(Integer punishObj) {
		this.punishObj = punishObj;
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

	public Integer getRedLineFlag() {
		return redLineFlag;
	}

	public void setRedLineFlag(Integer redLineFlag) {
		this.redLineFlag = redLineFlag;
	}

	public Integer getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}
	
}

