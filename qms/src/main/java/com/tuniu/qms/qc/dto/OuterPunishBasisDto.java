package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.OuterPunishBasis;

public class OuterPunishBasisDto extends  BaseDto<OuterPunishBasis> {

	/**处罚依据表ID*/
	private Integer psId ;

	/**供应商处罚单ID*/
	private Integer opbId;
	
	/**供应商处罚依据ID*/
	private Integer basisId;
	
	/**投诉质检*/
	private Integer cmpQcUse;
	
	/**运营质检*/
	private Integer operQcUse;
	
	/**研发质检*/
	private Integer devQcUse;

	
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

	public Integer getBasisId() {
		return basisId;
	}

	public void setBasisId(Integer basisId) {
		this.basisId = basisId;
	}

	public Integer getPsId() {
		return psId;
	}

	public void setPsId(Integer psId) {
		this.psId = psId;
	}

	public Integer getOpbId() {
		return opbId;
	}

	public void setOpbId(Integer opbId) {
		this.opbId = opbId;
	}

	
	
	
}
