package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;

public class EmployeeShareEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer shareId; //关联订单id

	private Integer userId; //用户id

	private String name; //姓名

	private double number; //承担费用

	private Date addTime; //添加时间
	
	private Date updateTime; //更新时间

	private Integer delFlag; //标示位

	public Integer getShareId() {
		return shareId;
	}

	public void setShareId(Integer shareId) {
		this.shareId = shareId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getNumber() {
		return number;
	}

	public void setNumber(double number) {
		this.number = number;
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
