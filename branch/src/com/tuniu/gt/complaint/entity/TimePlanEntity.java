package com.tuniu.gt.complaint.entity;

import java.io.Serializable;

public class TimePlanEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 月份
	 */
	private String month;
    /**
     * 日期
     */
	private String day ;
	/**
	 * 早班
	 */
	private int zaoban ;
	/**
	 * 中班1
	 */
	private int zhongban1;
	/**
	 * 中班2
	 */
	private int  zhongban2;
	/**
	 * 晚班
	 */
	private int wanban;
	/**
	 * 夜班
	 */
	private int yeban;
	
	private String title;
	
	private String zaobanbengin ;
	
	private String zaobanend ;
	
	private String zhongban1bengin ;
	
	private String zhongban1end ;
	
	private String zhongban2bengin ;
	
	private String zhongban2end ;
	
	private String wanbanbengin ;
	
	private String wanbanend ;
	
	private String yebanbengin ;
	
	private String yebanend ;
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getZaoban() {
		return zaoban;
	}
	public void setZaoban(int zaoban) {
		this.zaoban = zaoban;
	}
	public int getZhongban1() {
		return zhongban1;
	}
	public void setZhongban1(int zhongban1) {
		this.zhongban1 = zhongban1;
	}
	public int getZhongban2() {
		return zhongban2;
	}
	public void setZhongban2(int zhongban2) {
		this.zhongban2 = zhongban2;
	}
	public int getWanban() {
		return wanban;
	}
	public void setWanban(int wanban) {
		this.wanban = wanban;
	}
	public int getYeban() {
		return yeban;
	}
	public void setYeban(int yeban) {
		this.yeban = yeban;
	}
	public String getZaobanbengin() {
		return zaobanbengin;
	}
	public void setZaobanbengin(String zaobanbengin) {
		this.zaobanbengin = zaobanbengin;
	}
	public String getZaobanend() {
		return zaobanend;
	}
	public void setZaobanend(String zaobanend) {
		this.zaobanend = zaobanend;
	}
	public String getZhongban1bengin() {
		return zhongban1bengin;
	}
	public void setZhongban1bengin(String zhongban1bengin) {
		this.zhongban1bengin = zhongban1bengin;
	}
	public String getZhongban1end() {
		return zhongban1end;
	}
	public void setZhongban1end(String zhongban1end) {
		this.zhongban1end = zhongban1end;
	}
	public String getZhongban2bengin() {
		return zhongban2bengin;
	}
	public void setZhongban2bengin(String zhongban2bengin) {
		this.zhongban2bengin = zhongban2bengin;
	}
	public String getZhongban2end() {
		return zhongban2end;
	}
	public void setZhongban2end(String zhongban2end) {
		this.zhongban2end = zhongban2end;
	}
	public String getWanbanbengin() {
		return wanbanbengin;
	}
	public void setWanbanbengin(String wanbanbengin) {
		this.wanbanbengin = wanbanbengin;
	}
	public String getWanbanend() {
		return wanbanend;
	}
	public void setWanbanend(String wanbanend) {
		this.wanbanend = wanbanend;
	}
	public String getYebanbengin() {
		return yebanbengin;
	}
	public void setYebanbengin(String yebanbengin) {
		this.yebanbengin = yebanbengin;
	}
	public String getYebanend() {
		return yebanend;
	}
	public void setYebanend(String yebanend) {
		this.yebanend = yebanend;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	
	
}
