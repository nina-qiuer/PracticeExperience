package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class FollowUpRecordEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = -6859323159504057065L;

	private Integer orderId; //订单id

	private Integer complaintId; //关联投诉id

	private Integer delFlag; //删除标示位

	private Date addTime; //加入时间
	
	private String add_time;//加入时间

	private String note; //本次跟进记录

	private Integer addUser; //添加人
	
	private String addUserName;
	
	private Integer contactType;//联系方式 0 电话 1邮件2第三方沟通工具
	
	private String tel;//联系人手机
	
	private Integer deal;//处理人
	
	private Integer associater;//交接人
	
	private String smsContent;//短信内容

	public String getAddUserName() {
		return addUserName;
	}
	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getDeal() {
		return deal;
	}
	public void setDeal(Integer deal) {
		this.deal = deal;
	}
	public Integer getAssociater() {
		return associater;
	}
	public void setAssociater(Integer associater) {
		this.associater = associater;
	}
	public Integer getContactType() {
		return contactType;
	}
	public void setContactType(Integer contactType) {
		this.contactType = contactType;
	}
	public Integer getAddUser() {
		return addUser;
	}
	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId; 
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
		return note;
	}
	public void setNote(String note) {
		this.note = note; 
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	
}
