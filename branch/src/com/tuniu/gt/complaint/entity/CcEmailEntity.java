package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.tuniu.gt.frm.entity.EntityBase;


public class CcEmailEntity extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String emailName; // 邮箱名称
	
	private String emailAddress; // 邮箱地址
	
	private Date addTime; // 添加时间
	
	private Date updateTime; // 更新时间
	
	private int delFlag; // 删除标志位，0：未删除，1：已删除
	
	private List<CcEmailCfgEntity> cfgList; // 配置信息

	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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

	public List<CcEmailCfgEntity> getCfgList() {
		return cfgList;
	}

	public void setCfgList(List<CcEmailCfgEntity> cfgList) {
		this.cfgList = cfgList;
	}
	
}
