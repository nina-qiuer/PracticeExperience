package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class FollowNoteThEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = -6859323159504057065L;

	private Integer complaintId; // 关联投诉id
	
	private Integer personId; // 操作人ID
	
	private String personName; // 操作人姓名
	
	private String flowName; // 事件
	
	private String content; // 详情
	
	private Date addTime; // 添加时间
	
	private Date updateTime; // 更新时间
	
	private int visibleFlag; // 第三方可见状态

	private Integer delFlag; // 删除标志位，0：未删除，1：已删除

	public Integer getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
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

	public int getVisibleFlag() {
		return visibleFlag;
	}

	public void setVisibleFlag(int visibleFlag) {
		this.visibleFlag = visibleFlag;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
}
