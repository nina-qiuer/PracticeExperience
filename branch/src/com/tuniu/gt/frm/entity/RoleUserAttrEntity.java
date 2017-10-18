package com.tuniu.gt.frm.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class RoleUserAttrEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -3694265978690163772L;


	private Integer delFlag=0; //表示数据是否有效

	private Date updateTime=new Date(); //更新时间

	private Date addTime=new Date(); //添加时间

	private String priType=""; //主键类型,user:用户,dept:部门

	private Integer priId=0; //主键值

	private Integer roleId=0; //角色ID



	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime; 
	}

	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime; 
	}

	public String getPriType() {
		return priType;
	}
	public void setPriType(String priType) {
		this.priType = priType; 
	}

	public Integer getPriId() {
		return priId;
	}
	public void setPriId(Integer priId) {
		this.priId = priId; 
	}

	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId; 
	}
	
}
