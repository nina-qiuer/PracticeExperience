package com.tuniu.gt.workorder.entity;

/**
 * @author chenyu
 *
 */
public class WorkOrderObj {
	private String id; // 单号
	private String orderId;// 订单号
	private String addTime;// 发起时间
	private String customerName; // 客户姓名
	private String phone; // 联系电话
	private String phoneMatter; // 来电事由
	private String finishTime;// 完成时间
	private String projectTeam;// 项目组
	private String businessClass; // 业务分类
	private String addPerson; // 发起人
	private String dealPerson; // 处理人

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoneMatter() {
		return phoneMatter;
	}

	public void setPhoneMatter(String phoneMatter) {
		this.phoneMatter = phoneMatter;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getProjectTeam() {
		return projectTeam;
	}

	public void setProjectTeam(String projectTeam) {
		this.projectTeam = projectTeam;
	}

	public String getBusinessClass() {
		return businessClass;
	}

	public void setBusinessClass(String businessClass) {
		this.businessClass = businessClass;
	}

	public String getAddPerson() {
		return addPerson;
	}

	public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
	}

	public String getDealPerson() {
		return dealPerson;
	}

	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}

}
