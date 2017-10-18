package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tuniu.gt.frm.entity.EntityBase;


public class ShareSolutionEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer orderId; //关联订单id
	
	private Integer complaintId; //关联投诉id
	
	private double total; //分担总额
	
	private double orderGains; //订单利润承担总额

	private double special; //公司承担总额

	private double supplierTotal; //供应商承担总额

	private double qualityToolTotal; //质量工具承担总额

	private double employeeTotal; //员工承担总额

	private double refundToIndemnity; //退转赔金额
	
	private Integer dealId; // 提交人ID
	
	private String dealName; //提交人姓名
	
	private Date addTime; //添加时间

	private Date updateTime; //更新时间

	private Integer delFlag=1; //删除标示位，0为删除，1为未删除
	
	private Integer submitFlag; //保存提交标志位，0：保存，1：提交
	
	private Integer auditFlag; //审核状态标志位，-1：无需审核，0：待审核，1：已初审，2：已复审一，3：已复审二，4：通过审核，9：审核不通过
	
	private Date auditTime;
	
	private List<SupportShareEntity> supportShareList = new ArrayList<SupportShareEntity>(); //供应商承担信息
	
	private List<ComplaintQualityToolEntity> qualityToolList = new ArrayList<ComplaintQualityToolEntity>(); //质量工具承担信息
	
	private List<EmployeeShareEntity> employeeShareList = new ArrayList<EmployeeShareEntity>(); //员工承担信息
	
	private int saveOrSubmit; // 非表字段，标志保存或提交操作，0：保存，1：提交
	
	private Date submitTime;
	
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	
	public int getSaveOrSubmit() {
		return saveOrSubmit;
	}

	public void setSaveOrSubmit(int saveOrSubmit) {
		this.saveOrSubmit = saveOrSubmit;
	}

	public List<EmployeeShareEntity> getEmployeeShareList() {
		return employeeShareList;
	}

	public void setEmployeeShareList(List<EmployeeShareEntity> employeeShareList) {
		this.employeeShareList = employeeShareList;
	}

	public List<ComplaintQualityToolEntity> getQualityToolList() {
		return qualityToolList;
	}

	public void setQualityToolList(List<ComplaintQualityToolEntity> qualityToolList) {
		this.qualityToolList = qualityToolList;
	}

	public List<SupportShareEntity> getSupportShareList() {
		return supportShareList;
	}

	public void setSupportShareList(List<SupportShareEntity> supportShareList) {
		this.supportShareList = supportShareList;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getOrderGains() {
		return orderGains;
	}

	public void setOrderGains(double orderGains) {
		this.orderGains = orderGains;
	}

	public double getSpecial() {
		return special;
	}

	public void setSpecial(double special) {
		this.special = special;
	}

	public double getSupplierTotal() {
		return supplierTotal;
	}

	public void setSupplierTotal(double supplierTotal) {
		this.supplierTotal = supplierTotal;
	}

	public double getQualityToolTotal() {
		return qualityToolTotal;
	}

	public void setQualityToolTotal(double qualityToolTotal) {
		this.qualityToolTotal = qualityToolTotal;
	}

	public double getEmployeeTotal() {
		return employeeTotal;
	}

	public void setEmployeeTotal(double employeeTotal) {
		this.employeeTotal = employeeTotal;
	}

	public double getRefundToIndemnity() {
		return refundToIndemnity;
	}

	public void setRefundToIndemnity(double refundToIndemnity) {
		this.refundToIndemnity = refundToIndemnity;
	}

	public Integer getDealId() {
		return dealId;
	}

	public void setDealId(Integer dealId) {
		this.dealId = dealId;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
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

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getSubmitFlag() {
		return submitFlag;
	}

	public void setSubmitFlag(Integer submitFlag) {
		this.submitFlag = submitFlag;
	}

	public Integer getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(Integer auditFlag) {
		this.auditFlag = auditFlag;
	}

}
