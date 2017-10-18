package com.tuniu.gt.complaint.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ComplaintVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer ordId;
	
	private Integer cmpLevel;
	
	private Integer state;
	
	private String stateName;
	
	private Integer delFlag;
	
	private Date addTime1;
	
	private Long addTime;
	
	private Date finishTime1;
	
	private Long finishTime;
	
	private Integer dealPersonId;
	
	private String dealPerson;
	
	private Double indemnifyAmount;
	
	private Double claimAmount;
	
	private Double qualityToolAmount;
	
	private Double repVoucherAmount;
	
	private Double giftAmount;
	
	private String comeFrom;
	
	private String payBasis;
	
	private String orderState;
	
	private String niuLineFlag;
	
	private String depName;
	
	private String depManager;
	
	private String seniorManager;
	
	private String productManager;
	
	private String productLeader;
	
	private String producter;
	
	/** 客服专员*/
	private String customer;
	
	/** 客服经理*/
	private String customerLeader;
	
	/** 高级客服经理*/
	private String serviceManager;
	
	/** 线路（产品）id*/
	private Integer routeId;
	
	/** 线路或产品名称*/
	private String routeName;
	
	/** 供应商名称*/
	private String agencyName;
	
	/** 理论赔付总金额*/
	private Double theoryPayOutAmount;
	
	/** 分担总额*/
	private Double shareAmount;
	
	/** 发起人ID*/
	private Integer ownerId;
	/** 发起人姓名*/
	private String ownerName;
	
	
	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getNiuLineFlag() {
		return niuLineFlag;
	}

	public void setNiuLineFlag(String niuLineFlag) {
		this.niuLineFlag = niuLineFlag;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getDepManager() {
		return depManager;
	}

	public void setDepManager(String depManager) {
		this.depManager = depManager;
	}

	public String getProductManager() {
		return productManager;
	}

	public void setProductManager(String productManager) {
		this.productManager = productManager;
	}

	public String getSeniorManager() {
		return seniorManager;
	}

	public void setSeniorManager(String seniorManager) {
		this.seniorManager = seniorManager;
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

	private String finalConclution; // 投诉处理报告最终处理方案
	
	private List<ComplaintReasonVo> reasonList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public Integer getCmpLevel() {
		return cmpLevel;
	}

	public void setCmpLevel(Integer cmpLevel) {
		this.cmpLevel = cmpLevel;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Date getAddTime1() {
		return addTime1;
	}

	public void setAddTime1(Date addTime1) {
		this.addTime1 = addTime1;
	}

	public Long getAddTime() {
		return addTime;
	}

	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}

	public Date getFinishTime1() {
		return finishTime1;
	}

	public void setFinishTime1(Date finishTime1) {
		this.finishTime1 = finishTime1;
	}

	public Long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getDealPersonId() {
		return dealPersonId;
	}

	public void setDealPersonId(Integer dealPersonId) {
		this.dealPersonId = dealPersonId;
	}

	public String getDealPerson() {
		return dealPerson;
	}

	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}

	public Double getIndemnifyAmount() {
		return indemnifyAmount;
	}

	public void setIndemnifyAmount(Double indemnifyAmount) {
		this.indemnifyAmount = indemnifyAmount;
	}

	public Double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(Double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public List<ComplaintReasonVo> getReasonList() {
		return reasonList;
	}

	public void setReasonList(List<ComplaintReasonVo> reasonList) {
		this.reasonList = reasonList;
	}

	public Double getQualityToolAmount() {
		return qualityToolAmount;
	}

	public void setQualityToolAmount(Double qualityToolAmount) {
		this.qualityToolAmount = qualityToolAmount;
	}

	public Double getRepVoucherAmount() {
		return repVoucherAmount;
	}

	public void setRepVoucherAmount(Double repVoucherAmount) {
		this.repVoucherAmount = repVoucherAmount;
	}

	public Double getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(Double giftAmount) {
		this.giftAmount = giftAmount;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getPayBasis() {
		return payBasis;
	}

	public void setPayBasis(String payBasis) {
		this.payBasis = payBasis;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

    public String getFinalConclution() {
        return finalConclution;
    }

    public void setFinalConclution(String finalConclution) {
        this.finalConclution = finalConclution;
    }

	public String getCustomerLeader() {
		return customerLeader;
	}

	public void setCustomerLeader(String customerLeader) {
		this.customerLeader = customerLeader;
	}

	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getServiceManager() {
		return serviceManager;
	}

	public void setServiceManager(String serviceManager) {
		this.serviceManager = serviceManager;
	}

	public Double getTheoryPayOutAmount() {
		return theoryPayOutAmount;
	}

	public void setTheoryPayOutAmount(Double theoryPayOutAmount) {
		this.theoryPayOutAmount = theoryPayOutAmount;
	}

	public Double getShareAmount() {
		return shareAmount;
	}

	public void setShareAmount(Double shareAmount) {
		this.shareAmount = shareAmount;
	}
    
}
