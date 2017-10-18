package com.tuniu.gt.complaint.vo;

import java.io.Serializable;

public class ComplaintCollectListVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3926616456006427254L;

	private String finishDate;// 质检完成时间
	
	private String cfinishDate;// 投诉完成时间
	
	private String buildDate ;//投诉时间
	
	private String orderId;// 订单号
	
	private String complaintId;//投诉号
	
	private String niuLineFlag;//产品类型
	
	private int  level ;//投诉等级
	
	private String backTime;//归来日期
	
	private String startCity;//出发地
	
	private String route;//线路名称
	
	private String productLeader;//产品经理
	
	private String producter;//产品专员
	
	private String customerLeader;//客服经理
	
	private String descript;//问题描述
	
	private String cdescript;//对客解决方案备注
	
	private String  conclusion;//质检结论
	
	private String responsibility;//责任归属“
	
	private String responsibilityName;//责任归属一级部门“
	
	private String respSecondName;//”责任归属二级部门“

	private String respThirdName;//责任归属三级部门
	
	private String position;//执行岗位
	
	private String responsiblePerson;//责任人
	
	private String improverName ;//改进人
	
	private String bigClassId ;//问题大类型
	
	private String smallClassId ;//问题小类型
	
	private String bigClassName ;//问题大名称
	
	private String smallClassName ;//问题小名称
	
	private String scoreLevel ;//记分等级
	
	private String scoreTarget1 ;//记分对象1
	
	private int scoreValue1 ;//记分值1
	
	private String scoreTarget2 ;//记分对象2
	
	private int scoreValue2 ;//记分值2
	
	private int scoreValue;//总记分
	
	private float special ;//损失金额
	
	private String  qcPersonName  ;// 质检人
	
	private String  dealDepart;//处理岗位
	
	private String dealName;//处理人姓名

	private String typeDescript;//详情描述
	
	private String questionId;//问题ID
	
	private String verify;//核实情况
	
	
	
	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public String getCfinishDate() {
		return cfinishDate;
	}

	public void setCfinishDate(String cfinishDate) {
		this.cfinishDate = cfinishDate;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getCdescript() {
		return cdescript;
	}

	public void setCdescript(String cdescript) {
		this.cdescript = cdescript;
	}

	public String getTypeDescript() {
		return typeDescript;
	}

	public void setTypeDescript(String typeDescript) {
		this.typeDescript = typeDescript;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}


	public String getNiuLineFlag() {
		return niuLineFlag;
	}

	public void setNiuLineFlag(String niuLineFlag) {
		this.niuLineFlag = niuLineFlag;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getBackTime() {
		return backTime;
	}

	public void setBackTime(String backTime) {
		this.backTime = backTime;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getProductLeader() {
		return productLeader;
	}

	public void setProductLeader(String productLeader) {
		this.productLeader = productLeader;
	}

	public String getProducter() {
		return producter;
	}

	public void setProducter(String producter) {
		this.producter = producter;
	}

	public String getCustomerLeader() {
		return customerLeader;
	}

	public void setCustomerLeader(String customerLeader) {
		this.customerLeader = customerLeader;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	

	public String getResponsibilityName() {
		return responsibilityName;
	}

	public void setResponsibilityName(String responsibilityName) {
		this.responsibilityName = responsibilityName;
	}

	public String getRespSecondName() {
		return respSecondName;
	}

	public void setRespSecondName(String respSecondName) {
		this.respSecondName = respSecondName;
	}

	public String getRespThirdName() {
		return respThirdName;
	}

	public void setRespThirdName(String respThirdName) {
		this.respThirdName = respThirdName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}


	public String getImproverName() {
		return improverName;
	}

	public void setImproverName(String improverName) {
		this.improverName = improverName;
	}

	public String getBigClassId() {
		return bigClassId;
	}

	public void setBigClassId(String bigClassId) {
		this.bigClassId = bigClassId;
	}

	public String getSmallClassId() {
		return smallClassId;
	}

	public void setSmallClassId(String smallClassId) {
		this.smallClassId = smallClassId;
	}

	public String getScoreLevel() {
		return scoreLevel;
	}

	public void setScoreLevel(String scoreLevel) {
		this.scoreLevel = scoreLevel;
	}

	public String getScoreTarget1() {
		return scoreTarget1;
	}

	public void setScoreTarget1(String scoreTarget1) {
		this.scoreTarget1 = scoreTarget1;
	}

	public int getScoreValue1() {
		return scoreValue1;
	}

	public void setScoreValue1(int scoreValue1) {
		this.scoreValue1 = scoreValue1;
	}

	public String getScoreTarget2() {
		return scoreTarget2;
	}

	public void setScoreTarget2(String scoreTarget2) {
		this.scoreTarget2 = scoreTarget2;
	}

	public int getScoreValue2() {
		return scoreValue2;
	}

	public void setScoreValue2(int scoreValue2) {
		this.scoreValue2 = scoreValue2;
	}

	public int getScoreValue() {
		return scoreValue;
	}

	public void setScoreValue(int scoreValue) {
		this.scoreValue = scoreValue;
	}

	public float getSpecial() {
		return special;
	}

	public void setSpecial(float special) {
		this.special = special;
	}

	public String getQcPersonName() {
		return qcPersonName;
	}

	public void setQcPersonName(String qcPersonName) {
		this.qcPersonName = qcPersonName;
	}

	public String getDealDepart() {
		return dealDepart;
	}

	public void setDealDepart(String dealDepart) {
		this.dealDepart = dealDepart;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getBigClassName() {
		return bigClassName;
	}

	public void setBigClassName(String bigClassName) {
		this.bigClassName = bigClassName;
	}

	public String getSmallClassName() {
		return smallClassName;
	}

	public void setSmallClassName(String smallClassName) {
		this.smallClassName = smallClassName;
	}

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	

}
