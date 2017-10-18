package com.tuniu.qms.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 部门，维护公司组织架构，由UC系统同步数据
 * @author wangmingfang
 */
public class Department implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer pid;
	
	/** 部门名称 */
	private String name;
	
	/** 部门全名 */
	private String fullName;
	
	/** 部门层级 */
	private Integer depth;
	
	/** 添加时间 */
	private Date addTime;
	
	/** 修改时间 */
	private Date updateTime;
	
	/** 删除标识位，0：未删除，1：已删除 */
	private Integer delFlag;
	
	/** 投诉质检使用标识，0：不使用，1：使用 */
	private Integer cmpQcUseFlag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getCmpQcUseFlag() {
		return cmpQcUseFlag;
	}

	public void setCmpQcUseFlag(Integer cmpQcUseFlag) {
		this.cmpQcUseFlag = cmpQcUseFlag;
	}

}
