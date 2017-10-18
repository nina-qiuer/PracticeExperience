package com.tuniu.gt.workorder.entity; 
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;


public class WorkOrderFollowTimeEntity extends EntityBase{


	private Integer userId; //提醒用户
	
	private String userName; //提醒用户

	private Date remindTime; //提醒时间

	private Integer woId; //关联工单id

	private Integer delFlag; //删除标示位

	private Date addTime; //加入时间

	private String note; //跟进备注

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId; 
	}

	public Date getRemindTime() {
		return remindTime;
	}
	
	public void setRemindTime(Date remindTime) {
		this.remindTime = remindTime;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}

	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime; 
	}

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note; 
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * @return the woId
	 */
	public Integer getWoId() {
		return woId;
	}
	
	/**
	 * @param woId the woId to set
	 */
	public void setWoId(Integer woId) {
		this.woId = woId;
	}
	
}
