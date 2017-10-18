package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class SolutionVoucherEntity extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer solutionId; // 对客解决方案ID
	
	private String mobileNo; // 手机号
	
	private String custId; // 会员号
	
	private double amount; // 充值金额
	
	private Date addTime; // 添加时间
	
	private Date updateTime; // 更新时间
	
	private int delFlag; // 删除标志位，0：正常，1：删除

	public Integer getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Integer solutionId) {
		this.solutionId = solutionId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	
}
