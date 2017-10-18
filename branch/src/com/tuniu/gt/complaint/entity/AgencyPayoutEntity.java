package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;

public class AgencyPayoutEntity extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer supportId; //供应商承担表ID

	private String complaintInfo; //投诉详情
	
	private String payoutBase; //赔付理据
	
	private Double payoutNum; //赔付金额
	
	private Double foreignCurrencyNumber; //外币数额
	
	private Date addTime; //更新时间
	
	private Date updateTime; //更新时间
	
	private Integer delFlag; //删除标示位，1-正常,0-删除

	public Integer getSupportId() {
		return supportId;
	}

	public void setSupportId(Integer supportId) {
		this.supportId = supportId;
	}

	public String getComplaintInfo() {
		return complaintInfo;
	}

	public void setComplaintInfo(String complaintInfo) {
		this.complaintInfo = complaintInfo;
	}

	public String getPayoutBase() {
		return payoutBase;
	}

	public void setPayoutBase(String payoutBase) {
		this.payoutBase = payoutBase;
	}

	public Double getPayoutNum() {
		return payoutNum;
	}

	public void setPayoutNum(Double payoutNum) {
		this.payoutNum = payoutNum;
	}

	public Double getForeignCurrencyNumber() {
		return foreignCurrencyNumber;
	}

	public void setForeignCurrencyNumber(Double foreignCurrencyNumber) {
		this.foreignCurrencyNumber = foreignCurrencyNumber;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
}
