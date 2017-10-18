package com.tuniu.gt.complaint.entity;

import java.io.Serializable;

public class ComplaintSynchCrmEntity implements Serializable{

	private static final long serialVersionUID = 5482851348651452370L;
	
	private String complaint_id;//投诉单号
	
	private String order_id;//订单号
	
	private String build_date;//投诉时间
	
	private String finish_date;//完成时间
	
	private String come_from;//投诉来源
	
	private String level;//投诉等级
	
	private String cust_id;//会员ID
	
	private String cmp_person;//投诉人
	
	private String cmp_phone;//投诉人电话
	
	private String deal;//处理人ID
	
	private String deal_name;//处理人姓名
	
	private String deal_depart;//处理岗
	
	private String state;//投诉处理状态
	
	private String priority;//处理优先级
	
	private String owner_id;//投诉发起人ID
	
	private String owner_name;//投诉发起人姓名
	
	private String is_media;//是否媒体参与 0：否，1：是 
	
	private String add_time;//新增时间
	
	private String update_time;//更新时间

	public String getComplaint_id() {
		return complaint_id;
	}

	public void setComplaint_id(String complaint_id) {
		this.complaint_id = complaint_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getBuild_date() {
		return build_date;
	}

	public void setBuild_date(String build_date) {
		this.build_date = build_date;
	}

	public String getFinish_date() {
		return finish_date;
	}

	public void setFinish_date(String finish_date) {
		this.finish_date = finish_date;
	}

	public String getCome_from() {
		return come_from;
	}

	public void setCome_from(String come_from) {
		this.come_from = come_from;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getCmp_person() {
		return cmp_person;
	}

	public void setCmp_person(String cmp_person) {
		this.cmp_person = cmp_person;
	}

	public String getCmp_phone() {
		return cmp_phone;
	}

	public void setCmp_phone(String cmp_phone) {
		this.cmp_phone = cmp_phone;
	}

	public String getDeal() {
		return deal;
	}

	public void setDeal(String deal) {
		this.deal = deal;
	}

	public String getDeal_name() {
		return deal_name;
	}

	public void setDeal_name(String deal_name) {
		this.deal_name = deal_name;
	}

	public String getDeal_depart() {
		return deal_depart;
	}

	public void setDeal_depart(String deal_depart) {
		this.deal_depart = deal_depart;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public String getIs_media() {
		return is_media;
	}

	public void setIs_media(String is_media) {
		this.is_media = is_media;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
}
