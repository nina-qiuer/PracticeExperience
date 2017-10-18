package com.tuniu.qms.common.dto;

/**
 * 通用类型配置查询条件
 * @author zhangsensen
 *
 */
public class CommonTypeDto {

	/** id */
	private Integer id;
	/** ParentID */
	private Integer pid;
	/** 质检类型名称 */
	private String name;
	/** 类型标识  1：投诉研发关联关闭原因类型 ， 2： ** */
	private Integer typeFlag;
	
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
	public Integer getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(Integer typeFlag) {
		this.typeFlag = typeFlag;
	}
	
}
