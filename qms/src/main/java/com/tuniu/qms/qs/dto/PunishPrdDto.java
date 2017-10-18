package com.tuniu.qms.qs.dto;

import java.util.Date;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qs.model.PunishPrd;

public class PunishPrdDto extends BaseDto<PunishPrd> {
	 
	 
	  private Integer lineType=1;//线路
	  
	  private Integer orderId;//订单号
	  
	  private Integer routeId;//线路编号
	  
	  private String businessUnit;//事业部
	  
	  private String prdManager;//产品经理
	  
	  private String prdOfficer;//产品专员
	  
	  private String supplier;//供应商
	  
	  private Integer offlineType=-1;//下线类型
	  
	  private Integer status=-1;//整改状态

	  private Integer offlineCount;//下线次数
	  
	  private String offlineOperPerson;//操作人

	  private Integer nowYear;
	  
	  private Integer nowWeek;
	  
	  private Integer  week;
	  
	  private Integer  year;
	  
	  private String triggerTimeFrom;
	  
	  private String triggerTimeTo;
	  
	  private Integer prdStatus;//产品状态  0：下线  1：上线
		
	  private Integer updatePersonId;//操作人id
	 
	  private String updatePerson;//操作人姓名
	  
	  // 实际下线次数
	  private Integer realOffLineCount;
	  // 放过操作人
	  private String passOperPerson;
	  // 放过时间
	  private Date passOperTime;
	  // 情况说明
	  private String remark;
	  
	  private Integer prdId;
	  
	public Integer getPrdId() {
		return prdId;
	}

	public void setPrdId(Integer prdId) {
		this.prdId = prdId;
	}

	public Integer getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(Integer updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public Integer getPrdStatus() {
		return prdStatus;
	}

	public void setPrdStatus(Integer prdStatus) {
		this.prdStatus = prdStatus;
	}

	public String getTriggerTimeFrom() {
		return triggerTimeFrom;
	}

	public void setTriggerTimeFrom(String triggerTimeFrom) {
		this.triggerTimeFrom = triggerTimeFrom;
	}

	public String getTriggerTimeTo() {
		return triggerTimeTo;
	}

	public void setTriggerTimeTo(String triggerTimeTo) {
		this.triggerTimeTo = triggerTimeTo;
	}



	public Integer getNowWeek() {
		return nowWeek;
	}

	public void setNowWeek(Integer nowWeek) {
		this.nowWeek = nowWeek;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getNowYear() {
		return nowYear;
	}

	public void setNowYear(Integer nowYear) {
		this.nowYear = nowYear;
	}


	public Integer getLineType() {
		return lineType;
	}

	public void setLineType(Integer lineType) {
		this.lineType = lineType;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getPrdManager() {
		return prdManager;
	}

	public void setPrdManager(String prdManager) {
		this.prdManager = prdManager;
	}

	public String getPrdOfficer() {
		return prdOfficer;
	}

	public void setPrdOfficer(String prdOfficer) {
		this.prdOfficer = prdOfficer;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public Integer getOfflineType() {
		return offlineType;
	}

	public void setOfflineType(Integer offlineType) {
		this.offlineType = offlineType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOfflineCount() {
		return offlineCount;
	}

	public void setOfflineCount(Integer offlineCount) {
		this.offlineCount = offlineCount;
	}

	public String getOfflineOperPerson() {
		return offlineOperPerson;
	}

	public void setOfflineOperPerson(String offlineOperPerson) {
		this.offlineOperPerson = offlineOperPerson;
	}

	public Integer getRealOffLineCount() {
		return realOffLineCount;
	}

	public void setRealOffLineCount(Integer realOffLineCount) {
		this.realOffLineCount = realOffLineCount;
	}

	public String getPassOperPerson() {
		return passOperPerson;
	}

	public void setPassOperPerson(String passOperPerson) {
		this.passOperPerson = passOperPerson;
	}

	public Date getPassOperTime() {
		return passOperTime;
	}

	public void setPassOperTime(Date passOperTime) {
		this.passOperTime = passOperTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


}
