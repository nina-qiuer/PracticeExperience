package com.tuniu.gt.frm.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class MenuPrivilegeEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -7550980758458741777L;


	private Integer menuId=0; //菜单id

	private Date addTime=new Date(); //添加时间

	private Date updateTime=new Date(); //更新时间

	private String privilegeUrl=""; //权限url

	private String privilegeName=""; //权限名称

	private Integer delFlag=0; //删除标识



	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId; 
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

	public String getPrivilegeUrl() {
		return privilegeUrl;
	}
	public void setPrivilegeUrl(String privilegeUrl) {
		this.privilegeUrl = privilegeUrl; 
	}

	public String getPrivilegeName() {
		return privilegeName;
	}
	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName; 
	}

	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}
	
}
