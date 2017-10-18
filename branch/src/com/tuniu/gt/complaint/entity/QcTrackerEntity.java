package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class QcTrackerEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -3479542236520541792L;

	private Integer questionId = 0; // 问题id
	private String responsibility = "0"; // 责任归属
	private String respSecond = "0"; // 二级责任归属
	private String respThird = "0"; // 三级责任归属
	private String position = "0"; // 执行岗位
	private String responsiblePerson = ""; // 责任人
	private String improver = ""; // 改进人
	private Date addTime = new Date(); // 添加时间
	private Date updateTime = new Date(); // 更新时间
	private Integer delFlag = 0; // 1为已删除，0  为正常

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	public String getRespSecond() {
		return respSecond;
	}

	public void setRespSecond(String respSecond) {
		this.respSecond = respSecond;
	}

	public String getRespThird() {
		return respThird;
	}

	public void setRespThird(String respThird) {
		this.respThird = respThird;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getImprover() {
		return improver;
	}

	public void setImprover(String improver) {
		this.improver = improver;
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

	public String toString() {
		return "责任归属:" + responsibility + "二级责任归属:" + respSecond + "三级责任归属:" + respThird + "责任人:" + responsiblePerson + "执行岗位:" + position + "改进人:" + improver;
	}
}
