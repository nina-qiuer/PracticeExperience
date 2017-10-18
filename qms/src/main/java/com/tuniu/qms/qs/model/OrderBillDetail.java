package com.tuniu.qms.qs.model;

/**
 * 订单
 * @author chenhaitao
 *
 */
public class OrderBillDetail {

	private  Integer orderId;
	
	private  Integer orderType;
	
	private String beginTime;//出游日期
	
	private String salerName;//客服专员
	
	private String salerManagerName;////客服经理
	
	private String mobPhone;//电话
	
	private Integer isCancel;//是否取消 1未取消  2已取消
	
	private String prdName;//产品名称
	
	private String addTime ;//下单时间

	private String signDate ;//签约时间
	
	private String allPersonPhone;//所有联系人手机号
	
	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getPrdName() {
		return prdName;
	}

	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getSalerName() {
		return salerName;
	}

	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}

	public String getSalerManagerName() {
		return salerManagerName;
	}

	public void setSalerManagerName(String salerManagerName) {
		this.salerManagerName = salerManagerName;
	}

	public String getMobPhone() {
		return mobPhone;
	}

	public void setMobPhone(String mobPhone) {
		this.mobPhone = mobPhone;
	}

	public Integer getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Integer isCancel) {
		this.isCancel = isCancel;
	}

	public String getAllPersonPhone() {
		return allPersonPhone;
	}

	public void setAllPersonPhone(String allPersonPhone) {
		this.allPersonPhone = allPersonPhone;
	}
	
}
