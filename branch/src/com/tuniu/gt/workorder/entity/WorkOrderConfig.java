/**
 * 
 */
package com.tuniu.gt.workorder.entity;

import com.tuniu.gt.frm.entity.EntityBase;

/**
 * @author jiangye
 *
 */
public class WorkOrderConfig extends EntityBase {
	private String department; // 工单项目组
	private String principals; // 项目组负责人
	private String assigners; // 分配人
	private String members; // 项目组组员
	private Integer delFlag; // 删除标志位 0：未删除，1：已删除 
	private Integer pid;//父节点
	private String parentName;//父节点名称
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getPrincipals() {
        return principals;
    }
    public void setPrincipals(String principals) {
        this.principals = principals;
    }
    public String getMembers() {
        return members;
    }
    public void setMembers(String members) {
        this.members = members;
    }
    public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
    public String getAssigners() {
        return assigners;
    }
    public void setAssigners(String assigners) {
        this.assigners = assigners;
    }
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
