package com.tuniu.qms.common.model;

/**
 * 通用类型配置表
 * @author zhangsensen
 *
 */
public class CommonType extends BaseModel{

	/** * */
	private static final long serialVersionUID = 1L;
	
	/** ParentID */
	private Integer pid;
	/** 质检类型名称 */
	private String name;
	/** 类型标识  1：投诉研发关联关闭原因类型 ， 2： ** */
	private Integer typeFlag;
	
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
