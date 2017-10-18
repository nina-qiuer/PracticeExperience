package com.tuniu.gt.complaint.entity;

import java.io.Serializable;

public class AgencyToChatEntity implements Serializable {

	/**
	 * 供应商沟通类
	 */
	private static final long serialVersionUID = 1L;

	
	private String complaintId ;//投诉id
	
	private String orderId;//订单id
	
	private String commitTime;//沟通时间
	
	private String userIp ;//用户ip
	
	private String dealName;//处理人名称
	
	private String deal;//处理人
	
	private String descript ;//描述
	
	private String statusName  ;//状态
	
	private String statusNum  ;//状态编码
	
	private String typeName ;//沟通类型
	
	private String typeNum ;//沟通类型编码
	
	private String agencyName;//供应商名称
	
	private String agencyId;//供应商编码

	private String flag;//标识1 途牛2 供应商 3系统

	private String roomId;//房间ID
	
	private int contentType;//0文字，1图片

	private String timeOutflag;//超时标识
	
	private String timeOut;//超时
	
    private String pictName ;//图片名称

    
	public String getPictName() {
		return pictName;
	}

	public void setPictName(String pictName) {
		this.pictName = pictName;
	}

	public String getTimeOutflag() {
		return timeOutflag;
	}

	public void setTimeOutflag(String timeOutflag) {
		this.timeOutflag = timeOutflag;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}



	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public String getDeal() {
		return deal;
	}

	public void setDeal(String deal) {
		this.deal = deal;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStatusNum() {
		return statusNum;
	}

	public void setStatusNum(String statusNum) {
		this.statusNum = statusNum;
	}

	public String getTypeNum() {
		return typeNum;
	}

	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}
	
	
	
	
}
