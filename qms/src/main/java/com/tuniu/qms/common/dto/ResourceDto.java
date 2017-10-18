package com.tuniu.qms.common.dto;

import com.tuniu.qms.common.model.Resource;

public class ResourceDto extends BaseDto<Resource> {
	
	private String pid;
	private String menuFlag;
	private String chkAuthFlag;
	private Integer roleId;
	
	public String getChkAuthFlag() {
		return chkAuthFlag;
	}
	public void setChkAuthFlag(String chkAuthFlag) {
		this.chkAuthFlag = chkAuthFlag;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getMenuFlag() {
		return menuFlag;
	}
	public void setMenuFlag(String menuFlag) {
		this.menuFlag = menuFlag;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
