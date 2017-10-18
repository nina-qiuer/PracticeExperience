package com.tuniu.qms.qc.dto;

/**
 * 一键复制功能 质量问题、责任单、处罚单 传递参数 
 * @author zhangsensen
 *
 */
public class QcDetailCopyDto {
	
	/** 初始质检id*/
	private Integer qcIdOld;
	/** 被复制质检id*/
	private Integer qcIdNew;
	/** 旧质量问题id*/
	private Integer qpIdOld;
	/** 新质量问题id*/
	private Integer qpIdNew;
	/** 旧责任单id*/
	private Integer respIdOld;
	/** 新责任单id*/
	private Integer respIdNew;
	/** 责任单类型*/
	private Integer respType;
	/** 旧处罚单id*/
	private Integer punishIdOld;
	/** 新处罚单id*/
	private Integer punishIdNew;
	/** 处罚单类型*/
	private Integer punishType;
	/** 添加人*/
	private String addPerson;

	public Integer getQcIdOld() {
		return qcIdOld;
	}

	public void setQcIdOld(Integer qcIdOld) {
		this.qcIdOld = qcIdOld;
	}

	public Integer getQcIdNew() {
		return qcIdNew;
	}

	public void setQcIdNew(Integer qcIdNew) {
		this.qcIdNew = qcIdNew;
	}

	public Integer getQpIdOld() {
		return qpIdOld;
	}

	public void setQpIdOld(Integer qpIdOld) {
		this.qpIdOld = qpIdOld;
	}

	public Integer getQpIdNew() {
		return qpIdNew;
	}

	public void setQpIdNew(Integer qpIdNew) {
		this.qpIdNew = qpIdNew;
	}

	public Integer getPunishIdOld() {
		return punishIdOld;
	}

	public void setPunishIdOld(Integer punishIdOld) {
		this.punishIdOld = punishIdOld;
	}

	public Integer getPunishIdNew() {
		return punishIdNew;
	}

	public void setPunishIdNew(Integer punishIdNew) {
		this.punishIdNew = punishIdNew;
	}

	public String getAddPerson() {
		return addPerson;
	}

	public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
	}

	public Integer getRespIdOld() {
		return respIdOld;
	}

	public void setRespIdOld(Integer respIdOld) {
		this.respIdOld = respIdOld;
	}

	public Integer getRespIdNew() {
		return respIdNew;
	}

	public void setRespIdNew(Integer respIdNew) {
		this.respIdNew = respIdNew;
	}

	public Integer getRespType() {
		return respType;
	}

	public void setRespType(Integer respType) {
		this.respType = respType;
	}

	public Integer getPunishType() {
		return punishType;
	}

	public void setPunishType(Integer punishType) {
		this.punishType = punishType;
	}
	
}
