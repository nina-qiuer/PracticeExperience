package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class CcEmailCfgEntity extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int cmid; // 投诉邮件抄送表ID
	
	private int compLevel; // 投诉等级
	
	private String orderStates; // 订单状态
	
	private String[] orderStateArr;
	
	private String comeFroms; // 投诉来源
	
	private String[] comeFromArr;
	
	private int isMedia; // 是否接收有媒体参与的投诉，0：否，1：是
	
	private Date addTime; // 添加时间
	
	private Date updateTime; // 更新时间
	
	private int delFlag; // 删除标志位，0：未删除，1：已删除

	public int getCmid() {
		return cmid;
	}

	public void setCmid(int cmid) {
		this.cmid = cmid;
	}

	public int getCompLevel() {
		return compLevel;
	}

	public void setCompLevel(int compLevel) {
		this.compLevel = compLevel;
	}

	public String getOrderStates() {
		return orderStates;
	}

	public void setOrderStates(String orderStates) {
		this.orderStates = orderStates;
	}

	public String getComeFroms() {
		return comeFroms;
	}

	public void setComeFroms(String comeFroms) {
		this.comeFroms = comeFroms;
	}

	public int getIsMedia() {
		return isMedia;
	}

	public void setIsMedia(int isMedia) {
		this.isMedia = isMedia;
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

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public String[] getOrderStateArr() {
		return orderStateArr;
	}

	public void setOrderStateArr(String[] orderStateArr) {
		this.orderStateArr = orderStateArr;
	}

	public String[] getComeFromArr() {
		return comeFromArr;
	}

	public void setComeFromArr(String[] comeFromArr) {
		this.comeFromArr = comeFromArr;
	}
	
}
