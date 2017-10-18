package com.tuniu.gt.workorder.entity;

import java.io.Serializable;

public class WorkOrderReport  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 项目组id*/
	private Integer configId;
	
	/** 项目组名称*/
	private String name;
	
	/** 项目组数量*/
	private Integer workOrderNums;

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWorkOrderNums() {
		return workOrderNums;
	}

	public void setWorkOrderNums(Integer workOrderNums) {
		this.workOrderNums = workOrderNums;
	}
}
