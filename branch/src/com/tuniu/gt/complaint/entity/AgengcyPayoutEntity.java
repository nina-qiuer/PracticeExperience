package com.tuniu.gt.complaint.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public class AgengcyPayoutEntity {
	
	private Integer complaintId;//投诉单号
	
	private Integer orderId;//订单号
	
	private String haveQualityReport;//是否有质检报告：是，否
	
	private String qualityReportUrl;//是否有质检报告：是，否
	
	/**
	 * 人员信息
	 */
	private Integer salerId;//客售后服id
	
	private Integer appealerId;//申请人id
	
	/**
	 * 产品信息
	 */
	private Integer productId;//产品编号
	
	private String productName;//产品名称
	
	private String productDate;//团期
	
	/**
	 * 原始供应商信息
	 */
	private Integer agencyId;//供应商id
	
	private String companyName;//供应商品牌名
	
	private String agencyName;//供应商名称
	
	private BigDecimal agencyPayout;//原始供应商承担金额
	
	private String agencyNbFlag;//原始供应商对接nb状态
	
	private String appealContent;//申诉原因

	public AgengcyPayoutEntity(ComplaintEntity complaint){
		this.salerId = complaint.getDeal();
		this.complaintId = complaint.getId();
		this.orderId = complaint.getOrderId();
		this.productId = complaint.getRouteId();
		this.productName = complaint.getRoute();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		this.productDate = dateFormat.format(complaint.getStartTime());
	}

	public Integer getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getHaveQualityReport() {
		return haveQualityReport;
	}

	public void setHaveQualityReport(String haveQualityReport) {
		this.haveQualityReport = haveQualityReport;
	}

	public String getQualityReportUrl() {
		return qualityReportUrl;
	}

	public void setQualityReportUrl(String qualityReportUrl) {
		this.qualityReportUrl = qualityReportUrl;
	}

	public Integer getSalerId() {
		return salerId;
	}

	public void setSalerId(Integer salerId) {
		this.salerId = salerId;
	}

	public Integer getAppealerId() {
		return appealerId;
	}

	public void setAppealerId(Integer appealerId) {
		this.appealerId = appealerId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDate() {
		return productDate;
	}

	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public BigDecimal getAgencyPayout() {
		return agencyPayout;
	}

	public void setAgencyPayout(BigDecimal agencyPayout) {
		this.agencyPayout = agencyPayout;
	}

	public String getAgencyNbFlag() {
		return agencyNbFlag;
	}

	public void setAgencyNbFlag(String agencyNbFlag) {
		this.agencyNbFlag = agencyNbFlag;
	}

	public String getAppealContent() {
		return appealContent;
	}

	public void setAppealContent(String appealContent) {
		this.appealContent = appealContent;
	}
}
