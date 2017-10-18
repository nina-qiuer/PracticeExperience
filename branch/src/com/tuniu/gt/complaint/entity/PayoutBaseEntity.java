package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;

public class PayoutBaseEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = 4514806202181048412L;

	private String payoutBase; // 赔款依据

	private Integer fatherId; // 父节点

	private Date addTime; //

	private Integer delFlag; // 0删除，1正常

	private Date updateTime; //

	public String getPayoutBase() {
		return payoutBase;
	}

	public void setPayoutBase(String payoutBase) {
		this.payoutBase = payoutBase;
	}

	public Integer getFatherId() {
		return fatherId;
	}

	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
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

}
