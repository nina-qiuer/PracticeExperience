package com.tuniu.gt.score.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class SrQcTeamEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String teamName;
	
	private String persons;
	
	private Date addTime;
	
	private Date updateTime;
	
	private int delFlag;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getPersons() {
		return persons;
	}

	public void setPersons(String persons) {
		this.persons = persons;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	
}
