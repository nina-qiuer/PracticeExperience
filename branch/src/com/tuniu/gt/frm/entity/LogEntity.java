package com.tuniu.gt.frm.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class LogEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = 3114948702394178677L;


	private String userName=""; //用户名

	private Integer userId=0; //用户ID

	private String clientIp=""; //访问者IP

	private Date addTime=new Date(); //

	private String url=""; //访问地址

	private String param=""; //附加参数



	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName; 
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId; 
	}

	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp; 
	}

	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime; 
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url; 
	}

	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param; 
	}
	
}
