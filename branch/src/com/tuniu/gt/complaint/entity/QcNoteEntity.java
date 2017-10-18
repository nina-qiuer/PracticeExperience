package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class QcNoteEntity extends EntityBase implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Integer qcId; //关联的质检单id
	private Date addTime;//添加时间
	private String remark;//备注内容
	private String addPerson;//添加人姓名
	private int delFlag;//删除标志位  0:未删除   1：已删除
	
	public Integer getQcId()
    {
    	return qcId;
    }
	public void setQcId(Integer qcId)
    {
    	this.qcId = qcId;
    }
	public Date getAddTime()
    {
    	return addTime;
    }
	public void setAddTime(Date addTime)
    {
    	this.addTime = addTime;
    }
	public String getRemark()
    {
    	return remark;
    }
	public void setRemark(String remark)
    {
    	this.remark = remark;
    }

	public String getAddPerson()
    {
    	return addPerson;
    }
	public void setAddPerson(String addPerson)
    {
    	this.addPerson = addPerson;
    }
	public int getDelFlag()
    {
    	return delFlag;
    }
	public void setDelFlag(int delFlag)
    {
    	this.delFlag = delFlag;
    }
	@Override
    public String toString()
    {
	    return "QcNoteEntity [qcId=" + qcId + ", addTime=" + addTime + ", remark=" + remark + ", addPerson=" + addPerson
	            + ", delFlag=" + delFlag + "]";
    }
	
	
}
