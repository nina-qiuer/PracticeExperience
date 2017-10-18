package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.util.Date;

public class DataDictionaryConfigEntity implements Serializable {

	private static final long serialVersionUID = -2236163474367207014L;

	private Integer id;

	private String name;// 中文名称

	private Integer father_id;// 父级id

	private Integer add_id;// 添加人

	private Date add_time;// 添加时间

	private Integer update_id;// 更新人

	private Date update_time;// 更新时间

	private Integer can_delete;// 能否删除

	private Integer sort;// 排序

	private Integer del_flag;// 删除标识

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getFather_id() {
		return father_id;
	}

	public void setFather_id(Integer father_id) {
		this.father_id = father_id;
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

	public Integer getCan_delete() {
		return can_delete;
	}

	public void setCan_delete(Integer can_delete) {
		this.can_delete = can_delete;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(Integer del_flag) {
		this.del_flag = del_flag;
	}
}
