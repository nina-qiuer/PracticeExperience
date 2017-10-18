package com.tuniu.gt.frm.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class RoleEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = 3749965467901054490L;


	private String roleName=""; //角色名

	private Integer superManageId=0; //超级管理员id

	private String manageIds=""; //普通管理员ids

	private Date addTime=new Date(); //添加时间

	private Date updateTime=new Date(); //更新时间



	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName; 
	}

	public Integer getSuperManageId() {
		return superManageId;
	}
	public void setSuperManageId(Integer superManageId) {
		this.superManageId = superManageId; 
	}

	public String getManageIds() {
		return manageIds;
	}
	public void setManageIds(String manageIds) {
		this.manageIds = manageIds; 
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
	
}
