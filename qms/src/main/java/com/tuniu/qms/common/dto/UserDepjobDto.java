package com.tuniu.qms.common.dto;

import java.util.Date;

public class UserDepjobDto {
	
	private Integer id;
	
	/** 人员ID */
	private Integer userId;
	
	/** 部门岗位ID */
	private Integer depjobId;
	
	/** 关联类型，0：主部门岗位，1：兼任部门岗位 */
	private Integer corType;
	
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDepjobId() {
		return depjobId;
	}

	public void setDepjobId(Integer depjobId) {
		this.depjobId = depjobId;
	}

	public Integer getCorType() {
		return corType;
	}

	public void setCorType(Integer corType) {
		this.corType = corType;
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
