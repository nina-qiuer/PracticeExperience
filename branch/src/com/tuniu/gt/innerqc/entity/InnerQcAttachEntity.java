package com.tuniu.gt.innerqc.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class InnerQcAttachEntity extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 内部质检单id */
	private Integer iqcId;
	
	/** 附件路径 */
	private String path;
	
	/** 附件名称 */
	private String name;
	
	/** 添加时间 */
	private Date addTime;
	
	/** 最后更新时间 */
	private Date updateTime;
	
	/** 删除标志位，0：正常，1：删除 */
	private Integer delFlag;

	public Integer getIqcId() {
		return iqcId;
	}

	public void setIqcId(Integer iqcId) {
		this.iqcId = iqcId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
