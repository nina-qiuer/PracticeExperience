package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class ReasonTypeEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -1131928527059792584L;


	private String descript=""; //描述

	private Integer fatherId=0; //父id

	private Date updateTime=new Date(); //更新时间

	private Date addTime=new Date(); //添加时间

	private String name=""; //类别名称

	private Integer delFlag=1; //删除标示



	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript; 
	}

	public Integer getFatherId() {
		return fatherId;
	}
	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId; 
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime; 
	}

	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime; 
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name; 
	}

	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}
	
}
