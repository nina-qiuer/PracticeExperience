package com.tuniu.qms.qs.model;

import com.tuniu.qms.common.model.BaseModel;

/**
 * 责任部门
 * @author chenhaitao
 *
 */
public class RespDepartment  extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
  
	/** 一级责任部门ID*/
	private Integer firstDepId;
	
	/** 一级责任部门*/
	private String firstDepName;
	
	/** 二级责任部门ID*/
	private Integer twoDepId;
	
	/** 二级责任部门*/
	private String twoDepName;
	
	/** 三级责任部门ID*/
	private Integer threeDepId;
	
	/** 三级责任部门*/
	private String threeDepName;


	public Integer getFirstDepId() {
		return firstDepId;
	}

	public void setFirstDepId(Integer firstDepId) {
		this.firstDepId = firstDepId;
	}

	public String getFirstDepName() {
		return firstDepName;
	}

	public void setFirstDepName(String firstDepName) {
		this.firstDepName = firstDepName;
	}

	public Integer getTwoDepId() {
		return twoDepId;
	}

	public void setTwoDepId(Integer twoDepId) {
		this.twoDepId = twoDepId;
	}

	public String getTwoDepName() {
		return twoDepName;
	}

	public void setTwoDepName(String twoDepName) {
		this.twoDepName = twoDepName;
	}

	public Integer getThreeDepId() {
		return threeDepId;
	}

	public void setThreeDepId(Integer threeDepId) {
		this.threeDepId = threeDepId;
	}

	public String getThreeDepName() {
		return threeDepName;
	}

	public void setThreeDepName(String threeDepName) {
		this.threeDepName = threeDepName;
	}
	
	
}
