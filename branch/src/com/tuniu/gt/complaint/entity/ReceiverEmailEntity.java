package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class ReceiverEmailEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -6547473442224408359L;


	private Integer delFlag = 1; //0为已删除，1为正常

	private Date updateTime=new Date(); //更新时间

	private Date addTime=new Date(); //添加时间

	private String departmentName=""; //部门名称

	private Integer departmentId=0; //所在部门id

	private String userName=""; //姓名

	private Integer userId = 0; //人员id
	
	private String userMail=""; //邮箱

	private Integer receiverType=0; //0为收件人，1为邮件组

	private Integer type=0; //收件类别，1~5对应1到5级投诉，6质检收件人

	private Integer orderState=0;//订单状态，1、出游前，2出游中、3、出游后
	
	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
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

	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName; 
	}

	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId; 
	}

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

	public Integer getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(Integer receiverType) {
		this.receiverType = receiverType; 
	}

	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type; 
	}
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	
}
