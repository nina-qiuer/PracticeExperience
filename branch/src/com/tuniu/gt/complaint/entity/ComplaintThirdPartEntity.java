package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.util.Date;

public class ComplaintThirdPartEntity implements Serializable {

	private static final long serialVersionUID = -5906017411213917359L;

	private Integer id;

	private Integer complaint_id;

	private String deal_depart;

	private Integer deal_id;

	private String deal_name;

	private Integer third_type;

	private String third_type_name;

	private Integer third_second_type;

	private String third_second_type_name;

	private String content;

	private Integer add_id;

	private Date add_time;

	private Integer update_id;

	private Date update_time;

	private Integer del_flag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getComplaint_id() {
		return complaint_id;
	}

	public void setComplaint_id(Integer complaint_id) {
		this.complaint_id = complaint_id;
	}

	public String getDeal_depart() {
		return deal_depart;
	}

	public void setDeal_depart(String deal_depart) {
		this.deal_depart = deal_depart;
	}

	public Integer getDeal_id() {
		return deal_id;
	}

	public void setDeal_id(Integer deal_id) {
		this.deal_id = deal_id;
	}

	public String getDeal_name() {
		return deal_name;
	}

	public void setDeal_name(String deal_name) {
		this.deal_name = deal_name;
	}

	public Integer getThird_type() {
		return third_type;
	}

	public void setThird_type(Integer third_type) {
		this.third_type = third_type;
	}

	public Integer getThird_second_type() {
		return third_second_type;
	}

	public void setThird_second_type(Integer third_second_type) {
		this.third_second_type = third_second_type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getAdd_id() {
		return add_id;
	}

	public void setAdd_id(Integer add_id) {
		this.add_id = add_id;
	}

	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}

	public Integer getUpdate_id() {
		return update_id;
	}

	public void setUpdate_id(Integer update_id) {
		this.update_id = update_id;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Integer getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(Integer del_flag) {
		this.del_flag = del_flag;
	}

	public String getThird_type_name() {
		return third_type_name;
	}

	public void setThird_type_name(String third_type_name) {
		this.third_type_name = third_type_name;
	}

	public String getThird_second_type_name() {
		return third_second_type_name;
	}

	public void setThird_second_type_name(String third_second_type_name) {
		this.third_second_type_name = third_second_type_name;
	}
}
