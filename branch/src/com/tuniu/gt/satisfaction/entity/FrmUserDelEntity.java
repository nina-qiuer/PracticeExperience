package com.tuniu.gt.satisfaction.entity; 
import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;


public class FrmUserDelEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -107215124030703546L;

	private String realName=""; //真实姓名

	private String userName=""; //用户名
	
	private String workNum=""; // 工号

	private Integer depId=0; //主部门ID

	private Integer approvalNumber=0; //审核次数 
	
	private Date updateTime; //更新时间
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName; 
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName; 
	}

	public String getWorkNum() {
		return workNum;
	}
	public void setWorkNum(String workNum) {
		this.workNum = workNum;
	}
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId; 
	}
	/**
	 * @return the approvalNumber
	 */
	public Integer getApprovalNumber() {
		return approvalNumber;
	}
	/**
	 * @param approvalNumber the approvalNumber to set
	 */
	public void setApprovalNumber(Integer approvalNumber) {
		this.approvalNumber = approvalNumber;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


}
