package com.tuniu.qms.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author chenhaitao
 *
 */
public class UpLoadAttach implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id ;
	
	private String path;//附件路径
	
	private String name;//附件名称
	
	private String type;//类型
	
	private String descript;//描述
	
	private String addPerson;//添加人姓名
	
	private Date addTime;//添加时间
	
	private Date updateTime;//更新时间
	
	private Integer delFlag;//删除标识位,0：未删除，1：已删除

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getAddPerson() {
		return addPerson;
	}

	public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
	
}
