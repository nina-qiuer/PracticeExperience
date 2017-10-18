package com.tuniu.qms.common.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ComplaintBill implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	/** 订单ID */
	private Integer ordId;

	/** 投诉级别 */
	private Integer cmpLevel;

	/** 投诉处理状态 */
	private Integer state;

	/** 投诉处理状态名称 */
	private String stateName;

	/** 处理完成时间 */
	private Date finishTime;

	/** 处理人id */
	private Integer dealPersonId;

	/** 处理人 */
	private String dealPerson;

	/** 对客赔偿总额 */
	private Double indemnifyAmount;

	/** 分担总额 */
	private Double shareAmount;

	/** 向供应商索赔总额 */
	private Double claimAmount;

	/** 公司损失 **/
	private Double companyLose;

	/** 质量工具承担总额 */
	private Double qualityToolAmount;

	/** 抵用券赔付总额 */
	private Double repVoucherAmount;

	/** 礼品赔付总额 */
	private Double giftAmount;

	/** 投诉来源 */
	private String comeFrom;

	/** 对客赔付理据 */
	private String payBasis;

	private Date addTime;

	private Date updateTime;

	/** 质检标识 **/
	private Integer qcFlag;

	/** 订单状态 **/
	private String orderState;

	/** 牛人专线 **/
	private Integer niuLineFlag;

	/** 事业部 **/
	private String depName;

	/** 事业部总经理 **/
	private String depManager;

	/** 高级经理 **/
	private String seniorManager;

	/** 产品总监 **/
	private String productManager;

	/** 产品经理 **/
	private String productLeader;

	/** 产品专员 **/
	private String producter;

	/** 客服专员 */
	private String customer;

	/** 客服经理 */
	private String customerLeader;

	/** 高级客服经理 */
	private String serviceManager;

	/** 线路（产品）id */
	private Integer routeId;

	/** 线路或产品名称 */
	private String routeName;

	/** 供应商名称 */
	private String agencyName;

	/** 理论赔付总金额 */
	private Double theoryPayOutAmount;

	public Integer getNiuLineFlag() {
		return niuLineFlag;
	}

	public void setNiuLineFlag(Integer niuLineFlag) {
		this.niuLineFlag = niuLineFlag;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
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

	public Integer getQcFlag() {
		return qcFlag;
	}

	public void setQcFlag(Integer qcFlag) {
		this.qcFlag = qcFlag;
	}

	public Double getCompanyLose() {
		return companyLose;
	}

	public void setCompanyLose(Double companyLose) {
		this.companyLose = companyLose;
	}

	/** 投诉事宜 */
	private List<ComplaintReason> reasonList;

	/** 删除标识位，0：未删除，1：已删除 */
	private Integer delFlag;

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

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
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

	public List<ComplaintReason> getReasonList() {
		return reasonList;
	}

	public void setReasonList(List<ComplaintReason> reasonList) {
		this.reasonList = reasonList;
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
