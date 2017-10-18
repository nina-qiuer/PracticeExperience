package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class CheckEmailEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = 7381733792053579595L;


	private Integer complaintId=0; //关联订单id

	private Date buildDate=new Date(); //发送时间

	private Integer mark=0; //标示位，0发送，1为回复

	private String title=""; //邮件主题

	private String content=""; //内容

	private Integer attachId = 0; //附件id

	private String cc=""; //抄送人

	private String sender=""; //发件人

	private String address=""; //收件人



	public Integer getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId; 
	}

	public Date getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate; 
	}

	public Integer getMark() {
		return mark;
	}
	public void setMark(Integer mark) {
		this.mark = mark; 
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title; 
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content; 
	}

	public Integer getAttachId() {
		return attachId;
	}
	public void setAttachId(Integer attachId) {
		this.attachId = attachId; 
	}

	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc; 
	}

	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender; 
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address; 
	}
	
}
