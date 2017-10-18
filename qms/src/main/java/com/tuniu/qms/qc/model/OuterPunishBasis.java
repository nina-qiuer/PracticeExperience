package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;

public class OuterPunishBasis extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	/** 外部处罚单id */
	private Integer opbId;

	/** 处罚标准ID */
	private Integer psId;
	
	/** 处罚标准 */
	private PunishStandard punishStandard;
	
	/** 执行标记，0：未执行，1：执行 */
	private Integer execFlag;

	public Integer getOpbId() {
		return opbId;
	}

	public void setOpbId(Integer opbId) {
		this.opbId = opbId;
	}

	public Integer getPsId() {
		return psId;
	}

	public void setPsId(Integer psId) {
		this.psId = psId;
	}

	public Integer getExecFlag() {
		return execFlag;
	}

	public void setExecFlag(Integer execFlag) {
		this.execFlag = execFlag;
	}

	public PunishStandard getPunishStandard() {
		return punishStandard;
	}

	public void setPunishStandard(PunishStandard punishStandard) {
		this.punishStandard = punishStandard;
	}
	
}
