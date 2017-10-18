package com.tuniu.qms.common.dto;

import java.util.Date;

public class DepartmentUserDto {
	
	private Integer id;
	
	/** 部门ID */
	private Integer depId;
	
	/** 人员ID */
	private Integer userId;
	
	/** 添加时间 */
	private Date addTime;
	
	/** 修改时间 */
	private Date updateTime;
	
	/** 删除标识位，0：未删除，1：已删除 */
	private Integer delFlag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
}
