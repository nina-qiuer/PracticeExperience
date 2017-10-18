package com.tuniu.gt.frm.entity; 
import java.io.Serializable;
import java.util.Date;


public class SessionEntity  implements Serializable {
	private static final long serialVersionUID = -8037498134219676812L;

	
	private String id = "";
	
	private String userInfo=""; //用户信息

	private Integer isSa=0; //是否是超级管理员

	private Date updateTime=new Date(); //最后操作时间

	private Integer uid=0; //用户ID

	private String privileges=""; //

	private Integer needUpdate = 0; //是否需要更新

	


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo; 
	}

	public Integer getIsSa() {
		return isSa;
	}
	public void setIsSa(Integer isSa) {
		this.isSa = isSa; 
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime; 
	}

	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid; 
	}

	public String getPrivileges() {
		return privileges;
	}
	public void setPrivileges(String privileges) {
		this.privileges = privileges; 
	}

	public Integer getNeedUpdate() {
		return needUpdate;
	}
	public void setNeedUpdate(Integer needUpdate) {
		this.needUpdate = needUpdate; 
	}
	
}
