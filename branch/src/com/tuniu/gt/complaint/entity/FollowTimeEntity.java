package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class FollowTimeEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = 508933427093940043L;


	private Integer orderId=0; //订单id

	private Integer userId=0; //提醒用户
	
	private String userName=""; //提醒用户

	private Date time=new Date(); //提醒时间

	private Integer complaintId=0; //关联投诉id

	private Integer delFlag=1; //删除标示位

	private Date addTime=new Date(); //加入时间

	private String note=""; //跟进备注



	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId; 
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId; 
	}

	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time; 
	}

	public Integer getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId; 
	}

	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}

	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime; 
	}

	public String getNote() {
		note = note.replaceAll("(\r\n|\r|\n|\n\r)", " "); 
		return note;
	}
	public void setNote(String note) {
		note = note.replaceAll("(\r\n|\r|\n|\n\r)", " ");
		this.note = note; 
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
