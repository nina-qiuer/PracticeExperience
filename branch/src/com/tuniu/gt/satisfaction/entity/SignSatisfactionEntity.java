package com.tuniu.gt.satisfaction.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class SignSatisfactionEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = -6386454808402769850L;
	
	private int orderId;					//'关联订单id',
	private String faceSaleId;				//'门市/上门签约人员id',
	private String faceSaleName;			//'门市/上门签约人员姓名',
	private String faceSaleSatisfaction;//'门市/上门签约人员服务满意度',
	private String signType;					//'签约方式',
	private String customerLeaderId;		//'客服经理id',
	private String customerLeader;			//'客服经理',
	private String productLeaderId;			//'产品经理id',
	private String productLeader;				//'产品经理',
	private String orderType;						//'订单类型0:跟团,1:自助游,3:团队',
	private int routeId;								//'线路编号',
	private Date outDate;							//'出游日期',
	private Date backDate;						//'归来日期',
	private String customName;				//'订单联系人姓名',
	private String telNo;								//'订单联系人电话',
	private Date createTime;						//'记录创建时间',
	private Date lastModifyTime;				//'最后修改时间',
	private int bookCityCode;		//预订城市编号
	private String bookCity;		//预订城市
	private int startCityCode;		//出发城市编号
	private String startCity;		//出发城市
	private int desCityCode;		//目的地编号
	private String desCity;		//目的地
	
	public int getBookCityCode() {
		return bookCityCode;
	}
	public void setBookCityCode(int bookCityCode) {
		this.bookCityCode = bookCityCode;
	}
	public int getStartCityCode() {
		return startCityCode;
	}
	public void setStartCityCode(int startCityCode) {
		this.startCityCode = startCityCode;
	}
	public String getStartCity() {
		return startCity;
	}
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	public int getDesCityCode() {
		return desCityCode;
	}
	public void setDesCityCode(int desCityCode) {
		this.desCityCode = desCityCode;
	}
	public String getDesCity() {
		return desCity;
	}
	public void setDesCity(String desCity) {
		this.desCity = desCity;
	}
	public String getBookCity() {
		return bookCity;
	}
	public void setBookCity(String bookCity) {
		this.bookCity = bookCity;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getFaceSaleId() {
		return faceSaleId;
	}
	public void setFaceSaleId(String faceSaleId) {
		this.faceSaleId = faceSaleId;
	}
	public String getFaceSaleName() {
		return faceSaleName;
	}
	public void setFaceSaleName(String faceSaleName) {
		this.faceSaleName = faceSaleName;
	}
	public String getFaceSaleSatisfaction() {
		return faceSaleSatisfaction;
	}
	public void setFaceSaleSatisfaction(String faceSaleSatisfaction) {
		this.faceSaleSatisfaction = faceSaleSatisfaction;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getCustomerLeaderId() {
		return customerLeaderId;
	}
	public void setCustomerLeaderId(String customerLeaderId) {
		this.customerLeaderId = customerLeaderId;
	}
	public String getCustomerLeader() {
		return customerLeader;
	}
	public void setCustomerLeader(String customerLeader) {
		this.customerLeader = customerLeader;
	}
	public String getProductLeaderId() {
		return productLeaderId;
	}
	public void setProductLeaderId(String productLeaderId) {
		this.productLeaderId = productLeaderId;
	}
	public String getProductLeader() {
		return productLeader;
	}
	public void setProductLeader(String productLeader) {
		this.productLeader = productLeader;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	public Date getOutDate() {
		return outDate;
	}
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	public Date getBackDate() {
		return backDate;
	}
	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	
}
