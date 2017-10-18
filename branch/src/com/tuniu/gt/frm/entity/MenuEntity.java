package com.tuniu.gt.frm.entity; 
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.tuniu.gt.frm.entity.EntityBase;


public class MenuEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -3104814279528402965L;


	private Date addTime=new Date(); //添加时间

	private Date updateTime=new Date(); //更新时间

	private String target="main_right"; //链接target

	private Integer sequence=100; //顺序

	private Integer isExternal=0; //是否是外链

	private String menuName=""; //菜单名称

	private String menuUrl=""; //菜单对应文件

	private Integer fatherId=0; //上级ID

	private String methods=""; //菜单方法列表

	private Integer isMenu=1; //是否是菜单

	private String treeFatherId=""; //树形结点父id

	private String treeId=""; //树形结点id

	private Integer delFlag=0; //删除标识

	private Integer depth=0; //深度

	private String param=""; //附加参数

	private List<MenuPrivilegeEntity> privileges; //页面的权限点

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

	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target; 
	}

	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence; 
	}

	public Integer getIsExternal() {
		return isExternal;
	}
	public void setIsExternal(Integer isExternal) {
		this.isExternal = isExternal; 
	}

	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName; 
	}

	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl; 
	}

	public Integer getFatherId() {
		return fatherId;
	}
	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId; 
	}

	public String getMethods() {
		return methods;
	}
	public void setMethods(String methods) {
		this.methods = methods; 
	}

	public Integer getIsMenu() {
		return isMenu;
	}
	public void setIsMenu(Integer isMenu) {
		this.isMenu = isMenu; 
	}

	public String getTreeFatherId() {
		return treeFatherId;
	}
	public void setTreeFatherId(String treeFatherId) {
		this.treeFatherId = treeFatherId; 
	}

	public String getTreeId() {
		return treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId; 
	}

	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}

	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth; 
	}

	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param; 
	}
	
	
	public List<MenuPrivilegeEntity> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(List<MenuPrivilegeEntity> privileges) {
		this.privileges = privileges;
	}
}
