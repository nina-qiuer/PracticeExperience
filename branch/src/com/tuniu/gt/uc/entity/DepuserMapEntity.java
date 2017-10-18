package com.tuniu.gt.uc.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class DepuserMapEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -3705602390295849164L;


	private Integer depId=0; //部门ID

	private Integer isManage=0; //是否是负责人

	private Integer seq=99; //

	private Date opTime=new Date(); //操作时间

	private Integer opUserId=0; //操作人

	private Integer userId=0; //用户ID



	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId; 
	}

	public Integer getIsManage() {
		return isManage;
	}
	public void setIsManage(Integer isManage) {
		this.isManage = isManage; 
	}

	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq; 
	}

	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime; 
	}

	public Integer getOpUserId() {
		return opUserId;
	}
	public void setOpUserId(Integer opUserId) {
		this.opUserId = opUserId; 
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId; 
	}
	
}
