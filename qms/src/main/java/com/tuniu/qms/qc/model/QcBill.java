package com.tuniu.qms.qc.model;

import java.util.Date;
import java.util.List;

import com.tuniu.qms.common.model.BaseModel;
import com.tuniu.qms.common.model.ComplaintBill;
import com.tuniu.qms.common.model.OrderBill;
import com.tuniu.qms.common.model.Product;
import com.tuniu.qms.qc.util.FaultSourceEnum;

public class QcBill extends BaseModel {
	
	private static final long serialVersionUID = 1L;

	/** 产品id */
	private Integer prdId;
	
	/** 团期 */
	private java.sql.Date groupDate;
	
	/** 产品 */
	private Product product;

	/** 订单id */
	private Integer ordId;
	
	/** 研发jira单号 */
	private String jiraNum;
	
	/**订单实体*/
	private OrderBill orderBill;
	
	/** 投诉单id */
	private Integer cmpId;
	
	/**投诉单实体*/
	private ComplaintBill complaintBill;
	
	/** 使用方（组别），1：投诉质检组，2：运营质检组，3：研发质检组 */
	private Integer groupFlag;
	
	/** 质检类型id */
	private Integer qcTypeId;
	
	/** 质检类型名称 */
	private String qcTypeName;
	
	/** 质检事宜概述 */
	private String qcAffairSummary;
	
	/** 质检事宜详述 */
	private String qcAffairDesc;
	
	/** 备注 */
	private String remark;
	
	/** 损失金额 */
	private Double lossAmount;
	
	/** 状态标志位，1：发起中，2：待分配，3：质检中，4：已完成，5：已撤销 */
	private Integer state;
	
	private String stateName;
	
	/** 质检员id */
	private Integer qcPersonId;
	
	/** 质检员姓名 */
	private String qcPerson;
	
	/** 分配时间 */
	private Date distribTime;
	
	/** 质检期开始时间 */
	private Date qcPeriodBgnTime;
	
	/** 质检期结束时间 */
	private Date qcPeriodEndTime;
	
	/** 完成时间 */
	private Date finishTime;
	
	/** 提交时间 */
	private Date submitTime;
	
	/** 申请人id */
	private Integer addPersonId;

	private Integer cmpLevel;
	
	/** 故障来源*/
	private Integer faultSource;
	
	private String faultSourceName;
	
	/** 影响时长*/
	private Integer influenceTime;
	
	/** 影响范围*/
	private String influenceRange;
	
	/** 质量事件等级*/
	private String qualityEventClass;
	
	/** 核实情况*/
	private String verification;
	
	/**重要标记   0:普通  1:重要 2：非常重要*/
    private Integer importantFlag;
    
    /**影响结果*/
    private String  influenceResult;
    
    /**影响系统*/
    private List<QcInfluenceSystem> influenceSystem;
    
    /**jira单*/
    private String jiraIds;
    
    /**关联单*/
    private String relationIds;
    
    /**证据*/
    private String evidence ;

    /**人工发起 1 非人工 0*/
    private Integer userFlag ; 
    
    /**日期标识*/
    private Integer timeFlag;
    
    /**邮件主题*/
    private String subject;
    
    /**收件人*/
    private String reAddrs;
    
    /**抄送人*/
    private String ccAddrs;
    
    /**退回标识*/
    private Integer returnFlag ;
    
    /**附件*/
    private List<QcPointAttach> attachList;
    
    private int redLine;
    
    /** 撤销标识*/
    private Integer cancelFlag;
    
    /** 是否存在改进报告   0:没有   1：有 */
    private Integer isExistImpBill; 
    
    /** 是否存在改进报告   0:没有   1：有**/
    private String isExistImpBillName;
    
    /** 撤销时间 */
	private Date cancelTime;
	
	private String faultHappenTime;
	
	private String faultFinishTime;
	
    public Integer getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(Integer cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public int getRedLine() {
		return redLine;
	}

	public void setRedLine(int redLine) {
		this.redLine = redLine;
	}

	public String getRelationIds() {
		return relationIds;
	}

	public void setRelationIds(String relationIds) {
		this.relationIds = relationIds;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getQcTypeName() {
		return qcTypeName;
	}

	public void setQcTypeName(String qcTypeName) {
		this.qcTypeName = qcTypeName;
	}

	public List<QcPointAttach> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<QcPointAttach> attachList) {
		this.attachList = attachList;
	}

	public Integer getReturnFlag() {
		return returnFlag;
	}

	public void setReturnFlag(Integer returnFlag) {
		this.returnFlag = returnFlag;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getReAddrs() {
		return reAddrs;
	}

	public void setReAddrs(String reAddrs) {
		this.reAddrs = reAddrs;
	}

	public String getCcAddrs() {
		return ccAddrs;
	}

	public void setCcAddrs(String ccAddrs) {
		this.ccAddrs = ccAddrs;
	}

	public Integer getFaultSource() {
		return faultSource;
	}

	public void setFaultSource(Integer faultSource) {
		this.faultSource = faultSource;
	}

	public Integer getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(Integer timeFlag) {
		this.timeFlag = timeFlag;
	}

	public Date getQcPeriodBgnTime() {
		return qcPeriodBgnTime;
	}

	public void setQcPeriodBgnTime(Date qcPeriodBgnTime) {
		this.qcPeriodBgnTime = qcPeriodBgnTime;
	}

	
	public Date getQcPeriodEndTime() {
		return qcPeriodEndTime;
	}

	public void setQcPeriodEndTime(Date qcPeriodEndTime) {
		this.qcPeriodEndTime = qcPeriodEndTime;
	}

	public Integer getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(Integer userFlag) {
		this.userFlag = userFlag;
	}

	public String getEvidence() {
		return evidence;
	}

	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}

	public String getJiraIds() {
		return jiraIds;
	}

	public void setJiraIds(String jiraIds) {
		this.jiraIds = jiraIds;
	}

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}

	public Integer getInfluenceTime() {
		return influenceTime;
	}

	public void setInfluenceTime(Integer influenceTime) {
		this.influenceTime = influenceTime;
	}

	public String getInfluenceRange() {
		return influenceRange;
	}

	public void setInfluenceRange(String influenceRange) {
		this.influenceRange = influenceRange;
	}

	public String getQualityEventClass() {
		return qualityEventClass;
	}

	public void setQualityEventClass(String qualityEventClass) {
		this.qualityEventClass = qualityEventClass;
	}

	public Integer getCmpLevel() {
		return cmpLevel;
	}

	public void setCmpLevel(Integer cmpLevel) {
		this.cmpLevel = cmpLevel;
	}

	public Integer getPrdId() {
		return prdId;
	}

	public void setPrdId(Integer prdId) {
		this.prdId = prdId;
	}

	public java.sql.Date getGroupDate() {
		return groupDate;
	}

	public void setGroupDate(java.sql.Date groupDate) {
		this.groupDate = groupDate;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public OrderBill getOrderBill() {
		return orderBill;
	}

	public void setOrderBill(OrderBill orderBill) {
		this.orderBill = orderBill;
	}

	public Integer getCmpId() {
		return cmpId;
	}

	public void setCmpId(Integer cmpId) {
		this.cmpId = cmpId;
	}

	public ComplaintBill getComplaintBill() {
		return complaintBill;
	}

	public void setComplaintBill(ComplaintBill complaintBill) {
		this.complaintBill = complaintBill;
	}

	public Integer getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(Integer groupFlag) {
		this.groupFlag = groupFlag;
	}

	public Integer getQcTypeId() {
		return qcTypeId;
	}

	public void setQcTypeId(Integer qcTypeId) {
		this.qcTypeId = qcTypeId;
	}

	public String getQcAffairSummary() {
		return qcAffairSummary;
	}

	public void setQcAffairSummary(String qcAffairSummary) {
		this.qcAffairSummary = qcAffairSummary;
	}

	public String getQcAffairDesc() {
		return qcAffairDesc;
	}

	public void setQcAffairDesc(String qcAffairDesc) {
		this.qcAffairDesc = qcAffairDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getLossAmount() {
		return lossAmount;
	}

	public void setLossAmount(Double lossAmount) {
		this.lossAmount = lossAmount;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStateName() {
		if(state!=null){
			switch(state){
			case 1:stateName="发起中";break;
			case 2:stateName="待分配";break;
			case 3:stateName="质检中";break;
			case 4:stateName="已完成";break;
			case 5:stateName="已撤销";break;
			default:stateName="";
			}
		}
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Integer getQcPersonId() {
		return qcPersonId;
	}

	public void setQcPersonId(Integer qcPersonId) {
		this.qcPersonId = qcPersonId;
	}

	public String getQcPerson() {
		return qcPerson;
	}

	public void setQcPerson(String qcPerson) {
		this.qcPerson = qcPerson;
	}

	public Date getDistribTime() {
		return distribTime;
	}

	public void setDistribTime(Date distribTime) {
		this.distribTime = distribTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getAddPersonId() {
		return addPersonId;
	}

	public void setAddPersonId(Integer addPersonId) {
		this.addPersonId = addPersonId;
	}

	public String getJiraNum() {
		return jiraNum;
	}

	public void setJiraNum(String jiraNum) {
		this.jiraNum = jiraNum;
	}

    public Integer getImportantFlag() {
        return importantFlag;
    }

    public void setImportantFlag(Integer importantFlag) {
        this.importantFlag = importantFlag;
    }

	public String getInfluenceResult() {
		return influenceResult;
	}

	public void setInfluenceResult(String influenceResult) {
		this.influenceResult = influenceResult;
	}

	public List<QcInfluenceSystem> getInfluenceSystem() {
		return influenceSystem;
	}

	public void setInfluenceSystem(List<QcInfluenceSystem> influenceSystem) {
		this.influenceSystem = influenceSystem;
	}

	public Integer getIsExistImpBill() {
		return isExistImpBill;
	}

	public void setIsExistImpBill(Integer isExistImpBill) {
		this.isExistImpBill = isExistImpBill;
	}

	public String getIsExistImpBillName() {
		if(isExistImpBill != null){
			switch(isExistImpBill){
			case 0:isExistImpBillName="无";break;
			case 1:isExistImpBillName="有";break;
			default:isExistImpBillName="无";
			}
		}
		return isExistImpBillName;
	}

	public void setIsExistImpBillName(String isExistImpBillName) {
		this.isExistImpBillName = isExistImpBillName;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getFaultSourceName() {
		if(null != faultSource && faultSource > 0){
			faultSourceName = FaultSourceEnum.getContents(faultSource);
		}else{
			faultSourceName = "";
		}
		
		return faultSourceName;
	}

	public void setFaultSourceName(String faultSourceName) {
		this.faultSourceName = faultSourceName;
	}

	public String getFaultHappenTime() {
		
		return null != faultHappenTime ? faultHappenTime.substring(0, faultHappenTime.length() -2) : faultHappenTime;
	}

	public void setFaultHappenTime(String faultHappenTime) {
		this.faultHappenTime = faultHappenTime;
	}

	public String getFaultFinishTime() {
		return null != faultFinishTime ? faultFinishTime.substring(0, faultFinishTime.length() -2) : faultFinishTime;
	}

	public void setFaultFinishTime(String faultFinishTime) {
		this.faultFinishTime = faultFinishTime;
	}

}
