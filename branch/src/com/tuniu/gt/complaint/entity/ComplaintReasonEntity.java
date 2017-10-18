package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class ComplaintReasonEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = 1202147848537236938L;

	private Date addTime=new Date(); //

	private String descript=""; //其他情况描述

	private Integer delFlag=1; //0删除，1正常

	private Date updateTime=new Date(); //

	private Integer orderId = 0; //关联订单id

	private String typeDescript=""; //类别投诉详细情况

	private String type=""; //投诉一级分类
	
	private String secondType=""; //二级分类
	
	private Integer complaintId = 0; //关联投诉id

	private Integer accuracy;//正确性
	
	private String accuracyRe;//正确性备注
	
	private String agencyProblem;
	
	private String companyProblem;
	
	private String qualityTool;
	
	private String others;
	
	public String getAgencyProblem() {
		return agencyProblem;
	}
	public void setAgencyProblem(String agencyProblem) {
		this.agencyProblem = agencyProblem;
	}
	public String getCompanyProblem() {
		return companyProblem;
	}
	public void setCompanyProblem(String companyProblem) {
		this.companyProblem = companyProblem;
	}
	public String getQualityTool() {
		return qualityTool;
	}
	public void setQualityTool(String qualityTool) {
		this.qualityTool = qualityTool;
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	public String getAccuracyRe() {
		return accuracyRe;
	}
	public void setAccuracyRe(String accuracyRe) {
		this.accuracyRe = accuracyRe;
	}
	public Integer getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Integer accuracy) {
		this.accuracy = accuracy;
	}
	public String getSecondType() {
		return secondType;
	}
	public void setSecondType(String secondType) {
		this.secondType = secondType;
	}

	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime; 
	}

	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript; 
	}

	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime; 
	}

	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId; 
	}

	public String getTypeDescript() {
		return typeDescript;
	}
	public void setTypeDescript(String typeDescript) {
		this.typeDescript = typeDescript; 
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type; 
	}

	public Integer getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId; 
	}
	
}
