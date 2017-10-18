package com.tuniu.gt.punishprd.entity;

import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class LowSatisfyRouteEntity extends EntityBase {

	private Integer routeId;// 线路id
	private Integer routeType;// 1:普通线路 2：牛人专线
	private Double satisfaction;// 周满意度
	private Integer year;// 年
	private Integer week;// 周
	private String niuFlag; //牛人专线标示   "1"：牛人专线    "0":非牛人专线
	
	public Integer getRouteId() {
		return routeId;
	}
	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}
	public Integer getRouteType() {
		return routeType;
	}
	public void setRouteType(Integer routeType) {
		this.routeType = routeType;
	}
	public Double getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(Double satisfaction) {
		this.satisfaction = satisfaction;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	public String getNiuFlag() {
		return niuFlag;
	}
	public void setNiuFlag(String niuFlag) {
		this.niuFlag = niuFlag;
	}
}
