package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.tuniu.gt.frm.entity.EntityBase;

public class QcEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = 2867557879229106701L;
	private Integer orderId=0; // 订单id
	private Integer complaintId=0; // 投诉单id
	private String verify = ""; // 核实情况
	private Integer qcPerson=0; // 质检人id
	private String qcPersonName=""; // 质检人姓名
	private Integer leader=0; // 负责人id
	private String leaderName = ""; // 负责人姓名
	private Integer associater=0; // 交接人id
	private String associaterName = ""; // 交接人姓名
	private Integer oldQcPerson=0; // 原质检人id
	private String oldQcPersonName = ""; // 原质检人姓名
	private Date distributionDate = new Date(); // 分配时间
	private Date finishDate = new Date(); // 完成时间
	private Integer status=0; // 状态。0为待处理，1为处理中，2为已处理
	private Date addTime = new Date(); // 添加时间
	private Date updateTime = new Date(); // 更新时间
	private Integer delFlag = 0; // 标示位。1为已删除，0为正常
	private Integer impFlag=0; //标示位.1 为重点跟进单,0为否
	private Integer checkFlag=0; //标示位.1 为发起过核实请求,0为否
	private String recipients = ""; // 收件人（通过分号间隔）
	private String ccs = ""; // 抄送人
	private Integer consultation;
	private Integer specialConsultation;
	private Integer timeOutFlag; // 超时完成标志位，-1：默认值，0：及时完成，1：超时完成
	
	private ComplaintEntity complaint;	// 1:n
	private List<QcReportEntity> qcReports; //1:n
	

	public static final int PENDING_STATE = 0; // 待处理状态， 未分配质检人
	public static final int PROCESSING_STATE = 1; // 处理中状态，已分配质检人，未填写质检报告
	public static final int PROCESSED_STATE = 2; // 已处理状态， 已分配质检人，已填写质检报告

	public static final int DELETE_FLAG = 1;
	//标记的质检单
	public static final int IMPT_FLAG = 1;
	//没有标记的质检单
	public static final int NO_IMPT_FLAG=0;
	public static final int CHECKED_FLAG = 1;
	public static final int NORMAL_FLAG = 0;
	
	private int red_color_flag;//超过3个工作日
	private int haveReport;//1 有报告  0 无报告
	private int redColorFlag;//1 标红色  0不改变
	
	private double paymentForCust;//对客赔偿金额=现金+旅游卷
	
	private String table = "ct_qc";//表名
	
	public Integer getSpecialConsultation() {
		return specialConsultation;
	}

	public void setSpecialConsultation(Integer specialConsultation) {
		this.specialConsultation = specialConsultation;
	}

	public Integer getConsultation() {
		return consultation;
	}

	public void setConsultation(Integer consultation) {
		this.consultation = consultation;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Date getDistributionDate() {
		return distributionDate;
	}

	public void setDistributionDate(Date distributionDate) {
		this.distributionDate = distributionDate;
	}

	public Integer getQcPerson() {
		return qcPerson;
	}

	public void setQcPerson(Integer qcPerson) {
		this.qcPerson = qcPerson;
	}

	public String getQcPersonName() {
		return qcPersonName;
	}

	public void setQcPersonName(String qcPersonName) {
		this.qcPersonName = qcPersonName;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public Integer getLeader() {
		return leader;
	}

	public void setLeader(Integer leader) {
		this.leader = leader;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	
	public Integer getAssociater() {
		return associater;
	}

	public void setAssociater(Integer associater) {
		this.associater = associater;
	}

	public Integer getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(Integer checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getAssociaterName() {
		return associaterName;
	}

	public void setAssociaterName(String associaterName) {
		this.associaterName = associaterName;
	}
	
	public Integer getOldQcPerson() {
		return oldQcPerson;
	}

	public void setOldQcPerson(Integer oldQcPerson) {
		this.oldQcPerson = oldQcPerson;
	}

	public String getOldQcPersonName() {
		return oldQcPersonName;
	}

	public void setOldQcPersonName(String oldQcPersonName) {
		this.oldQcPersonName = oldQcPersonName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public ComplaintEntity getComplaint() {
		return complaint;
	}

	public void setComplaint(ComplaintEntity complaint) {
		this.complaint = complaint;
	}

	public List<QcReportEntity> getQcReports() {
		return qcReports;
	}

	public void setQcReports(List<QcReportEntity> qcReports) {
		this.qcReports = qcReports;
	}

	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	public String getCcs() {
		return ccs;
	}

	public void setCcs(String ccs) {
		this.ccs = ccs;
	}


	public int getRed_color_flag() {
		return red_color_flag;
	}

	public void setRed_color_flag(int red_color_flag) {
		this.red_color_flag = red_color_flag;
	}

	public int getRedColorFlag() {
		return redColorFlag;
	}

	public void setRedColorFlag(int redColorFlag) {
		this.redColorFlag = redColorFlag;
	}

	public int getHaveReport() {
		return haveReport;
	}

	public void setHaveReport(int haveReport) {
		this.haveReport = haveReport;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String toString() {
		return "(id=" + getId() + "complaint_id=" + complaintId + ")";
	}

	public Integer getImpFlag() {
		return impFlag;
	}

	public void setImpFlag(Integer impFlag) {
		this.impFlag = impFlag;
	}

	public Integer getTimeOutFlag() {
		return timeOutFlag;
	}

	public void setTimeOutFlag(Integer timeOutFlag) {
		this.timeOutFlag = timeOutFlag;
	}

	public double getPaymentForCust() {
		return paymentForCust;
	}

	public void setPaymentForCust(double paymentForCust) {
		this.paymentForCust = paymentForCust;
	}

}
