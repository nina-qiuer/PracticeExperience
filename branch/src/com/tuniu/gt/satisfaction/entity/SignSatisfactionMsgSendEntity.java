package com.tuniu.gt.satisfaction.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class SignSatisfactionMsgSendEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = 1567700469442110589L;
	
	private String customerName ;		//订单联系人姓名',
	private String telNo;		//'短信发送号码',
	private String msg;		// '回复短信内容',
	private Date createTime;		//'记录创建时间',
	private int satisId;		//关联的签约满意度记录id',
	private int result;		//发送成功与否0：成功,1:失败',
	private String errorCode;		//'失败代码',
	private String errorMsg;		//'失败信息',
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getSatisId() {
		return satisId;
	}
	public void setSatisId(int satisId) {
		this.satisId = satisId;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
