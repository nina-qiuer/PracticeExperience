package com.tuniu.gt.satisfaction.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class SignSatisfactionMsgReplyEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = 8523768364260272442L;
	
	private String telNo;								//'短信来源号码
	private String msg;	//'回复短信内容
	private int updateSatisId;								//'更新的签约满意度id
	private Date createTime;						//'记录创建时间',
	
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
	public int getUpdateSatisId() {
		return updateSatisId;
	}
	public void setUpdateSatisId(int updateSatisId) {
		this.updateSatisId = updateSatisId;
	}
	
}
