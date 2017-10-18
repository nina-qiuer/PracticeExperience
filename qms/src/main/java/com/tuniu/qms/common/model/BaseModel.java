package com.tuniu.qms.common.model;

import java.io.Serializable;
import java.util.Date;

public class BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String addPerson; // 添加人
	private Date addTime; // 添加时间
	private String updatePerson; // 最后修改人
	private Date updateTime; // 最后修改时间
	private Integer delFlag; // 删除标识位，0：未删除，1：已删除
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAddPerson() {
		return addPerson;
	}
	public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getUpdatePerson() {
		return updatePerson;
	}
	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
}
