package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;


public class ComplaintReleaserEntity extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 508933427093940043L;
	
	private Integer userId;
	
	private String userName;
	
	private String citys;
	
	private Date addTime;
	
	private Date updateTime;
	
	private int delFlag; // 0:未删除，1：已删除

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCitys() {
		return citys;
	}

	public void setCitys(String citys) {
		this.citys = citys;
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
