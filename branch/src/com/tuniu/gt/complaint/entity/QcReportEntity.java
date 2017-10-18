package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.tuniu.gt.frm.entity.EntityBase;


public class QcReportEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -6718622855657594406L;


	private Date bulidTime=new Date(); //报告添加时间

	private Date addTime=new Date(); //增加时间

	private Integer qcId = 0; //质检ID

	private Date updateTime=new Date(); //更新时间

	private Integer delFlag = 0; //删除标记；0 未删除，1 已删除

	private List<QcQuestionEntity> questions; // 1:n


	public Date getBulidTime() {
		return bulidTime;
	}
	public void setBulidTime(Date bulidTime) {
		this.bulidTime = bulidTime; 
	}

	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime; 
	}

	public Integer getQcId() {
		return qcId;
	}
	public void setQcId(Integer qcId) {
		this.qcId = qcId; 
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
	
	public List<QcQuestionEntity> getQuestions() {
		return questions;
	}
	
	public void setQuestions(List<QcQuestionEntity> questions) {
		this.questions = questions;
	}
	
}
