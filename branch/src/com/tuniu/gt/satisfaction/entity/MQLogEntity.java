package com.tuniu.gt.satisfaction.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class MQLogEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = 6197028571162335162L;
	
	private String mqMsg;			//mq消息实体
	private String inteMsg;			//接口返回值
	private Date createTime;		//记录创建时间
	
	public String getMqMsg() {
		return mqMsg;
	}
	public void setMqMsg(String mqMsg) {
		this.mqMsg = mqMsg;
	}
	public String getInteMsg() {
		return inteMsg;
	}
	public void setInteMsg(String inteMsg) {
		this.inteMsg = inteMsg;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	

}
