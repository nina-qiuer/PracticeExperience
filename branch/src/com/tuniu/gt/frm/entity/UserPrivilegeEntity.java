package com.tuniu.gt.frm.entity; 
import java.io.Serializable;
import com.tuniu.gt.frm.entity.EntityBase;


public class UserPrivilegeEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = 4178315792280605677L;


	private Integer userId=0; //用户id

	private Integer menuId=0; //菜单id

	private Integer privilegeId=0; //权限id

	private Integer delFlag=0; //删除标识



	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId; 
	}

	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId; 
	}

	public Integer getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId; 
	}

	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}
	
}
