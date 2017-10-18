package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class ComplaintFollowNoteEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -8322054715588981603L;


	private Integer delFlag=1; //删除标示1为未删除

	private String peopleName=""; //跟进人姓名

	private String content=""; //跟进信息

	private Date addTime=new Date(); //添加时间

	private Date updateTime=new Date(); //更新时间

	private Integer complaintId=0; //关联投诉id

	private Integer notePeople=0; //跟进人

	private String dealDept;//操作人所在部门
	
	private String flowName;//业务流程号
	
	private Integer isSys;//是否为系统信息
	
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public Integer getIsSys() {
		return isSys;
	}
	public void setIsSys(Integer isSys) {
		this.isSys = isSys;
	}

	public String getDealDept() {
		return dealDept;
	}
	public void setDealDept(String dealDept) {
		this.dealDept = dealDept;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}

	public String getPeopleName() {
		return peopleName;
	}
	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName; 
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content; 
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

	public Integer getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId; 
	}

	public Integer getNotePeople() {
		return notePeople;
	}
	public void setNotePeople(Integer notePeople) {
		this.notePeople = notePeople; 
	}
	
}
