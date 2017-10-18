package com.tuniu.qms.qc.model; 
import java.io.Serializable;
import java.util.Date;


public class QcPointAttach implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer delFlag; //删除标识位
	
	private Integer qcId;//质检单号

	private String name; //附件名称

	private Integer connectionId; //关联id
	
	private Integer complaintId; //投诉单id

	private String path; //附件路劲

	private String tableName; //关联表名
	
	private String addPerson ; // 添加人
	
	 /** 更新人姓名*/
    private String updatePerson;

	private Date addTime ; //添加时间
	
	private Date updateTime ; //更新时间
	
	private String descript; //描述

	private String type; //类型
	
	private Integer billType;//关联单据类型

	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUpdatePerson() {
		return updatePerson;
	}
	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}
	public Integer getQcId() {
		return qcId;
	}
	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}
	public Integer getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}
	public String getAddPerson() {
		return addPerson;
	}
	public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name; 
	}

	public Integer getConnectionId() {
		return connectionId;
	}
	public void setConnectionId(Integer connectionId) {
		this.connectionId = connectionId; 
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path; 
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime; 
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName; 
	}

	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime; 
	}

	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript; 
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type; 
	}
	public Integer getBillType() {
		return billType;
	}
	public void setBillType(Integer billType) {
		this.billType = billType;
	}
	
	
}
