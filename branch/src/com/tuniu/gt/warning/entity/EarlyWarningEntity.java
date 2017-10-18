package com.tuniu.gt.warning.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class EarlyWarningEntity extends EntityBase implements Serializable{
	
	private static final long serialVersionUID = 1142530108878643356L;
	
	private int warningType; // 预警类型，1：天气预警、2：突发事件、99：其他
	
	private int warningLv; // 预警等级，4：蓝色，3：黄色，2：橙色，1：红色
	
	private String content; // 预警内容
	
	private int state; // 状态，0：待处理，1：处理中，2：已处理
	
	private String addPerson; // 添加人
	
	private Date addTime; // 添加时间
	
	private Date updateTime; // 更新时间
	
	private int delFlag; // 删除标志位，0：未删除，1：已删除

	public int getWarningType() {
		return warningType;
	}

	public void setWarningType(int warningType) {
		this.warningType = warningType;
	}

	public int getWarningLv() {
		return warningLv;
	}

	public void setWarningLv(int warningLv) {
		this.warningLv = warningLv;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
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
