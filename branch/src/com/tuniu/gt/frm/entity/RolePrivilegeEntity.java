package com.tuniu.gt.frm.entity; 
import java.io.Serializable;
import com.tuniu.gt.frm.entity.EntityBase;


public class RolePrivilegeEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -1946152436376340647L;


	private Integer privilegeId=0; //权限id

	private Integer menuId=0; //菜单id

	private Integer manageLevel=0; //级别0:普通人员,1:普通管理员 2:超级管理员

	private Integer roleId=0; //解色id



	public Integer getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId; 
	}

	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId; 
	}

	public Integer getManageLevel() {
		return manageLevel;
	}
	public void setManageLevel(Integer manageLevel) {
		this.manageLevel = manageLevel; 
	}

	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId; 
	}
	
}
