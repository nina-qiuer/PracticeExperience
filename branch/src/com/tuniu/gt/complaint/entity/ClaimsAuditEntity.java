package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class ClaimsAuditEntity extends EntityBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2924011585984107978L;
	
	private String refundOrderId;//退款单编号
	
	private Integer cmpId; // 投诉单号
	
	private ComplaintEntity complaintEntity;
	
	private ShareSolutionEntity shareSolutionEntity;
	
	private ComplaintSolutionEntity complaintSolutionEntity;
	
	private Integer payment;
	
	private Date startDate;
	
	private Date endDate;
	
	private Integer orderId;
	
	private String menuId;
	
	private int adjustFlag;
	
	private String dealDepart;
	
	private Date auditStartTime;
	
	private Date auditEndTime;

	private String auditName;
	
	private Integer depId;
	
	private Integer groupId;
	
	public int getAdjustFlag() {
		return adjustFlag;
	}

	public void setAdjustFlag(int adjustFlag) {
		this.adjustFlag = adjustFlag;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(String refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

	public ShareSolutionEntity getShareSolutionEntity() {
		return shareSolutionEntity;
	}

	public void setShareSolutionEntity(ShareSolutionEntity shareSolutionEntity) {
		this.shareSolutionEntity = shareSolutionEntity;
	}

	public ComplaintSolutionEntity getComplaintSolutionEntity() {
		return complaintSolutionEntity;
	}

	public void setComplaintSolutionEntity(
			ComplaintSolutionEntity complaintSolutionEntity) {
		this.complaintSolutionEntity = complaintSolutionEntity;
	}

	public ComplaintEntity getComplaintEntity() {
		return complaintEntity;
	}

	public void setComplaintEntity(ComplaintEntity complaintEntity) {
		this.complaintEntity = complaintEntity;
	}

	public String getDealDepart() {
		return dealDepart;
	}

	public void setDealDepart(String dealDepart) {
		this.dealDepart = dealDepart;
	}

    public Integer getCmpId() {
        return cmpId;
    }

    public void setCmpId(Integer cmpId) {
        this.cmpId = cmpId;
    }

	public Date getAuditStartTime() {
		return auditStartTime;
	}

	public void setAuditStartTime(Date auditStartTime) {
		this.auditStartTime = auditStartTime;
	}

	public Date getAuditEndTime() {
		return auditEndTime;
	}

	public void setAuditEndTime(Date auditEndTime) {
		this.auditEndTime = auditEndTime;
	}

	public String getAuditName() {
		return auditName;
	}

	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}
