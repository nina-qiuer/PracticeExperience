package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.util.Date;

public class ComplaintEmailConfigEntity implements Serializable {

	private static final long serialVersionUID = 4907361967030645554L;

	private Integer id;

	private String deal_depart;// 处理岗

	private Integer complaint_level;// 投诉等级

	private String come_from;// 投诉来源

	private Integer class_brand_parent_id;// 一级品类

	private String class_brand_parent_name;// 一级品类名称

	private Integer class_brand_id;// 二级品类

	private String class_brand_name;// 二级品类名称

	private Integer product_new_line_type_id;// 新产品线目的地大类id

	private String product_new_line_type_name;// 新产品线目的地大类名称

	private Integer dest_group_id;// 目的地分组

	private String dest_group_name;// 目的地分组

	private String emails;// 邮件收件人

	private String content;// 邮件收件人

	private Integer mail_type;// 邮件类型

	private Integer add_id;// 添加人

	private Date add_time;// 添加时间

	private Integer update_id;// 更新人

	private Date update_time;// 更新时间

	private Integer del_flag;// 删除标识

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeal_depart() {
		return deal_depart;
	}

	public void setDeal_depart(String deal_depart) {
		this.deal_depart = deal_depart;
	}

	public Integer getComplaint_level() {
		return complaint_level;
	}

	public void setComplaint_level(Integer complaint_level) {
		this.complaint_level = complaint_level;
	}

	public String getCome_from() {
		return come_from;
	}

	public void setCome_from(String come_from) {
		this.come_from = come_from;
	}

	public Integer getClass_brand_parent_id() {
		return class_brand_parent_id;
	}

	public void setClass_brand_parent_id(Integer class_brand_parent_id) {
		this.class_brand_parent_id = class_brand_parent_id;
	}

	public String getClass_brand_parent_name() {
		return class_brand_parent_name;
	}

	public void setClass_brand_parent_name(String class_brand_parent_name) {
		this.class_brand_parent_name = class_brand_parent_name;
	}

	public Integer getClass_brand_id() {
		return class_brand_id;
	}

	public void setClass_brand_id(Integer class_brand_id) {
		this.class_brand_id = class_brand_id;
	}

	public String getClass_brand_name() {
		return class_brand_name;
	}

	public void setClass_brand_name(String class_brand_name) {
		this.class_brand_name = class_brand_name;
	}

	public Integer getProduct_new_line_type_id() {
		return product_new_line_type_id;
	}

	public void setProduct_new_line_type_id(Integer product_new_line_type_id) {
		this.product_new_line_type_id = product_new_line_type_id;
	}

	public String getProduct_new_line_type_name() {
		return product_new_line_type_name;
	}

	public void setProduct_new_line_type_name(String product_new_line_type_name) {
		this.product_new_line_type_name = product_new_line_type_name;
	}

	public Integer getDest_group_id() {
		return dest_group_id;
	}

	public void setDest_group_id(Integer dest_group_id) {
		this.dest_group_id = dest_group_id;
	}

	public String getDest_group_name() {
		return dest_group_name;
	}

	public void setDest_group_name(String dest_group_name) {
		this.dest_group_name = dest_group_name;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getMail_type() {
		return mail_type;
	}

	public void setMail_type(Integer mail_type) {
		this.mail_type = mail_type;
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

}
