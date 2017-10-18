package com.tuniu.qms.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 职位，维护公司所有职位，由UC系统同步数据
 * @author wangmingfang
 */
public class Position implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	/** 职位名称 */
	private String name;
	
	/** 职位等级 */
	private Integer level;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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
