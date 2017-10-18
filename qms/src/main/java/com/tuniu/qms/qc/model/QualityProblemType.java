package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;

public class QualityProblemType extends BaseModel {
	
	private static final long serialVersionUID = -4525421406132313933L;

	/** ParentID */
	private Integer pid;
	
	/** 问题名称 */
	private String name;
	
	/** 触红标识位，0：未触红，1：触红 */
	private Integer touchRedFlag; 
	
	/** 投诉质检是否使用，0：否，1：是 */
	private Integer cmpQcUse;
	
	/** 运营质检是否使用，0：否，1：是 */
	private Integer operQcUse;
	
	/** 研发质检是否使用，0：否，1：是 */
	private Integer devQcUse;
	
	/** 内部质检是否使用，0：否，1：是 */
	private Integer innerQcUse;

	/** 问题全名 */
	private String fullName;
	
	/**一级质量问题类型id*/
	private Integer qptLv1Id;
	
	
	public Integer getInnerQcUse() {
		return innerQcUse;
	}

	public void setInnerQcUse(Integer innerQcUse) {
		this.innerQcUse = innerQcUse;
	}

	public Integer getQptLv1Id() {
		return qptLv1Id;
	}

	public void setQptLv1Id(Integer qptLv1Id) {
		this.qptLv1Id = qptLv1Id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public Integer getTouchRedFlag() {
		return touchRedFlag;
	}

	public void setTouchRedFlag(Integer touchRedFlag) {
		this.touchRedFlag = touchRedFlag;
	}

	public Integer getCmpQcUse() {
		return cmpQcUse;
	}

	public void setCmpQcUse(Integer cmpQcUse) {
		this.cmpQcUse = cmpQcUse;
	}

	public Integer getOperQcUse() {
		return operQcUse;
	}

	public void setOperQcUse(Integer operQcUse) {
		this.operQcUse = operQcUse;
	}

	public Integer getDevQcUse() {
		return devQcUse;
	}

	public void setDevQcUse(Integer devQcUse) {
		this.devQcUse = devQcUse;
	}

}
