package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class AttachEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -8884360152964530985L;


	private Integer delFlag=0; //删除标识位

	private String name=""; //附件名称

	private Integer connectionId=0; //关联id
	
	private Integer complaintId=0; //投诉单id

	private String path=""; //附件路劲

	private Date updateTime=new Date(); //更新时间

	private String tableName=""; //关联表名
	
	private String addPerson = ""; // 添加人

	private Date addTime=new Date(); //添加时间

	private String descript=""; //描述

	private String type=""; //类型

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
	@Override
	public String toString() {
		return "AttachEntity [delFlag=" + delFlag + ", name=" + name
				+ ", connectionId=" + connectionId + ", path=" + path
				+ ", updateTime=" + updateTime + ", tableName=" + tableName
				+ ", addPerson=" + addPerson + ", addTime=" + addTime
				+ ", descript=" + descript + ", type=" + type + "]";
	}
	
}
