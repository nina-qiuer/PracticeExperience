package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.sql.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class AssignRecordEntity extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 配置类型，1：投诉处理配置，5：在线问答配置，42：质检处理人-自助游，43：质检处理人-团队，44：质检处理人-基础
	 */
	private Integer type;
	
	/**
	 * 用户id
	 */
	private Integer userId; 
	
	/**
	 * 姓名
	 */
	private String userName=""; 

	/**
	 * 部门名称
	 */
	private String departmentName="";
	
	/**
	 * 旅游时间节点，1：出游前，2：出游中，3：出游后  4:机票组
	 */
	private int tourTimeNode;
	
	/**
	 * 统计日期
	 */
	private Date statDate;
	
	/**
	 * 当日分配订单IDs
	 */
	private String orderIds;
	
	/**
	 * 当日分配订单数量
	 */
	private int orderNum;
	
	/**
	 * 删除标示位
	 */
	private Integer delFlag;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getTourTimeNode() {
		return tourTimeNode;
	}

	public void setTourTimeNode(int tourTimeNode) {
		this.tourTimeNode = tourTimeNode;
	}

	public Date getStatDate() {
		return statDate;
	}

	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}
