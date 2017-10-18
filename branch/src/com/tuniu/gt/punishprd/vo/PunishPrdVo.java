package com.tuniu.gt.punishprd.vo;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tuniu.gt.toolkit.DateUtil;

public class PunishPrdVo {
	private Integer year;//年份
	private Integer week;//第几周
	private Integer cur_year=DateUtil.getField(Calendar.YEAR);//当前年份
	private Integer cur_week=DateUtil.getField(Calendar.WEEK_OF_YEAR);//当前咒术
	
	private Integer id;
	private Date triggerTimeFrom;// 触发时间起始
	private Date triggerTimeTo;//触发时间结束

	private Integer orderId;// 订单号

	private Long routeId;// 线路编号
	private String routeName;// 线路名

	private String BU;// 事业部

	private String prdManager;// 产品经理
	private String prdOfficer;// 产品专员

	private String supplier;// 供应商

	private Integer offlineType;// 下线类型 1:触红 2：低满意度 3：低质量
	private Integer offlineCount;// 下线次数
	private Date offlineTime;// 下线时间

	private Date onlineTime;// 上线时间

	private Integer status;// 状态 1：：待整改 2：整改中 3：已整改  4:永久下线

	private Integer delFlag;// 删除标志位，0：正常，1：删除
	
	private Integer updatePersonId;//操作人id
	private String updatePerson;//操作人姓名
	
	private Integer prdStatus;//产品状态  0：下线  1：上线
	
	private Integer menuId=1;//1:所有线路   2：整改中线路   3：永久下线线路恢复
	
	private String offlineOperPerson;
	
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.id);
		map.put("routeId", routeId);
		map.put("routeName", routeName);
		map.put("orderId", orderId);
		map.put("BU", BU);
		map.put("prdManager", prdManager);
		map.put("prdOfficer", prdOfficer);
		map.put("supplier", supplier);
		map.put("offlineCount", offlineCount);
		map.put("status",status);
		map.put("offlineType", offlineType);
		map.put("menuId", menuId);
		map.put("offlineOperPerson", this.offlineOperPerson);
		
		if(year==null||week==null){
			Date date = new Date();
			year = cur_year;
			week = cur_week;
		}
		
		map.put("triggerTimeFrom", DateUtil.formatDate(DateUtil.getFirstDayOfWeek(year, week)));
		map.put("triggerTimeTo",  DateUtil.formatDate(DateUtil.getFirstDayOfWeek(year, week+1)));
		return map;
	}
	
	public Date getTriggerTimeFrom() {
		return triggerTimeFrom;
	}

	public void setTriggerTimeFrom(Date triggerTimeFrom) {
		this.triggerTimeFrom = triggerTimeFrom;
	}

	public Date getTriggerTimeTo() {
		return triggerTimeTo;
	}

	public void setTriggerTimeTo(Date triggerTimeTo) {
		this.triggerTimeTo = triggerTimeTo;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}


	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public Integer getCur_year() {
		return cur_year;
	}

	public void setCur_year(Integer cur_year) {
		this.cur_year = cur_year;
	}

	public Integer getCur_week() {
		return cur_week;
	}

	public void setCur_week(Integer cur_week) {
		this.cur_week = cur_week;
	}

	public String getBU() {
		return BU;
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

	public void setBU(String bU) {
		BU = bU;
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

	public Integer getOfflineCount() {
		return offlineCount;
	}

	public void setOfflineCount(Integer offlineCount) {
		this.offlineCount = offlineCount;
	}

	public Date getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(Date offlineTime) {
		this.offlineTime = offlineTime;
	}

	public Date getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPrdStatus() {
		return prdStatus;
	}

	public void setPrdStatus(Integer prdStatus) {
		this.prdStatus = prdStatus;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

    public String getOfflineOperPerson() {
        return offlineOperPerson;
    }

    public void setOfflineOperPerson(String offlineOperPerson) {
        this.offlineOperPerson = offlineOperPerson;
    }

}
