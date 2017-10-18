package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tuniu.gt.frm.entity.EntityBase;


public class GiftNoteEntity extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer solutionId; // 对客解决方案ID
	
	private String receiver; //收件人
	
	private String phone; //联系电话
	
	private Integer express; //是否快递，1-本人领取，2-快递领取
	
	private String address; //收件人地址
	
	private Date addTime; //添加时间
	
	private Date updateTime; //更新时间
	
	private Integer delFlag; //删除标示位，0为已删除，1正常
	
	private List<GiftEntity> giftList = new ArrayList<GiftEntity>();
	
	/* 以下字段废弃 */
	
	private Integer complaintId; //关联投诉id

	private String userName; //申请人姓名

	private Integer userId; //申请人id

	private String remark; //礼品申请备注

	private Integer departmentId; //部门id

	private String departmentName; //部门名称

	private String zipCode; //邮编

	private Integer orderId; //订单号

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName; 
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId; 
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark; 
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address; 
	}

	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId; 
	}

	public Integer getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId; 
	}

	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime; 
	}

	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName; 
	}

	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone; 
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime; 
	}

	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode; 
	}

	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver; 
	}

	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId; 
	}
	public Integer getExpress() {
		return express;
	}
	public void setExpress(Integer express) {
		this.express = express;
	}
	public List<GiftEntity> getGiftList() {
		return giftList;
	}
	public void setGiftList(List<GiftEntity> giftList) {
		this.giftList = giftList;
	}
	public Integer getSolutionId() {
		return solutionId;
	}
	public void setSolutionId(Integer solutionId) {
		this.solutionId = solutionId;
	}
	
}
