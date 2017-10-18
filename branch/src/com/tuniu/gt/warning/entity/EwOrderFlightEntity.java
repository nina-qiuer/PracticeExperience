package com.tuniu.gt.warning.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class EwOrderFlightEntity extends EntityBase implements Serializable{
	
	private static final long serialVersionUID = 1142530108878643356L;
	
	private int ewOrderId; // 预警订单ID
	
	private String flightNo; // 航班号
	
	private Date departureTime; // 起飞时间
	
	private Date arriveTime; // 抵达时间
	
	private String departureCityName; // 出发城市
	
	private String arriveCityName; // 抵达城市

	public int getEwOrderId() {
		return ewOrderId;
	}

	public void setEwOrderId(int ewOrderId) {
		this.ewOrderId = ewOrderId;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getDepartureCityName() {
		return departureCityName;
	}

	public void setDepartureCityName(String departureCityName) {
		this.departureCityName = departureCityName;
	}

	public String getArriveCityName() {
		return arriveCityName;
	}

	public void setArriveCityName(String arriveCityName) {
		this.arriveCityName = arriveCityName;
	}
	
}
