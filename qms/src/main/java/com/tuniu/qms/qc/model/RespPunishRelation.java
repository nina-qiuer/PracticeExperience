package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;

/**
 * 责任处罚关联单
 * @author zhangsensen
 *
 */
public class RespPunishRelation extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 责任单Id*/
	private Integer respId;
	/** 责任单类型     1：内部责任单，2：外部责任单*/
	private Integer respType;
	/** 处罚单id*/
	private Integer punishId;
	/** 处罚单类型   1：内部处罚单，2：外部处罚单*/
	private Integer punishType;
	
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
	public Integer getPunishId() {
		return punishId;
	}
	public void setPunishId(Integer punishId) {
		this.punishId = punishId;
	}
	public Integer getPunishType() {
		return punishType;
	}
	public void setPunishType(Integer punishType) {
		this.punishType = punishType;
	}
}
