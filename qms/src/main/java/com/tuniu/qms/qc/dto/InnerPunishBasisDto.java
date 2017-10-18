package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.InnerPunishBasis;

public class InnerPunishBasisDto extends  BaseDto<InnerPunishBasis> {

	private Integer psId ;

	private Integer ipbId;
	
	private Integer basisId;
	
	

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

	public Integer getIpbId() {
		return ipbId;
	}

	public void setIpbId(Integer ipbId) {
		this.ipbId = ipbId;
	}
	
	
}
