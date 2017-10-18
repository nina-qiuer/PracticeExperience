package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class AgencyAppealEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int agencyId;
	
	private int complaintId;
	
	private int orderId;
	
	private double amount;
	
	private String appealer;
	
	private String contactPhone;
	
	private String contactEmail;
	
	private String contactQQ;
	
	private String appealContent;
	
	private String processInfo;
	
	private int resultFlag;
	
	private Date addTime;
	
	private Date updateTime;
	
	private int delFlag;

	public int getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}

	public int getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(int complaintId) {
		this.complaintId = complaintId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getAppealer() {
		return appealer;
	}

	public void setAppealer(String appealer) {
		this.appealer = appealer;
	}

	public String getAppealContent() {
		return appealContent;
	}

	public void setAppealContent(String appealContent) {
		this.appealContent = appealContent;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactQQ() {
		return contactQQ;
	}

	public void setContactQQ(String contactQQ) {
		this.contactQQ = contactQQ;
	}

	public String getProcessInfo() {
		return processInfo;
	}

	public void setProcessInfo(String processInfo) {
		this.processInfo = processInfo;
	}

	public int getResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(int resultFlag) {
		this.resultFlag = resultFlag;
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

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	
}
