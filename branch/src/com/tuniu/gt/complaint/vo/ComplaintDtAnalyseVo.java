package com.tuniu.gt.complaint.vo;

import java.math.BigDecimal;
import java.util.Date;

public class ComplaintDtAnalyseVo {
	private Integer complaint_id;// 投诉单号

	private Integer order_id;// 订单编号

	private Date finish_date;// 赔款审核时间

	private Integer route_id;// 线路编号

	private String route;// 线路名称

	private String agency_name;// 供应商名称

	private String belong_dept;// 归属部门

	private String start_city;// 出发地

	private String end_city;// 目的地

	private String sign_city;// 签约城市

	private String route_type;// 产品品类

	private String product_leader;// 产品经理

	private BigDecimal theoryPayout;// 理论赔付总额

	private BigDecimal realPayout;// 实际赔付总额

	private BigDecimal share_total;// 分担总额

	private BigDecimal supplier_total;// 供应商承担金额

	private BigDecimal company_total;// 公司承担金额

	private BigDecimal quality_tool_total;// 质量工具承担金额

	private BigDecimal order_gains;// 订单利润赔付金额

	public Integer getComplaint_id() {
		return complaint_id;
	}

	public void setComplaint_id(Integer complaint_id) {
		this.complaint_id = complaint_id;
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public Date getFinish_date() {
		return finish_date;
	}

	public void setFinish_date(Date finish_date) {
		this.finish_date = finish_date;
	}

	public Integer getRoute_id() {
		return route_id;
	}

	public void setRoute_id(Integer route_id) {
		this.route_id = route_id;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getAgency_name() {
		return agency_name;
	}

	public void setAgency_name(String agency_name) {
		this.agency_name = agency_name;
	}

	public String getBelong_dept() {
		return belong_dept;
	}

	public void setBelong_dept(String belong_dept) {
		this.belong_dept = belong_dept;
	}

	public String getStart_city() {
		return start_city;
	}

	public void setStart_city(String start_city) {
		this.start_city = start_city;
	}

	public String getEnd_city() {
		return end_city;
	}

	public void setEnd_city(String end_city) {
		this.end_city = end_city;
	}

	public String getSign_city() {
		return sign_city;
	}

	public void setSign_city(String sign_city) {
		this.sign_city = sign_city;
	}

	public String getRoute_type() {
		return route_type;
	}

	public void setRoute_type(String route_type) {
		this.route_type = route_type;
	}

	public String getProduct_leader() {
		return product_leader;
	}

	public void setProduct_leader(String product_leader) {
		this.product_leader = product_leader;
	}

	public BigDecimal getTheoryPayout() {
		return theoryPayout;
	}

	public void setTheoryPayout(BigDecimal theoryPayout) {
		this.theoryPayout = theoryPayout;
	}

	public BigDecimal getRealPayout() {
		return realPayout;
	}

	public void setRealPayout(BigDecimal realPayout) {
		this.realPayout = realPayout;
	}

	public BigDecimal getShare_total() {
		return share_total;
	}

	public void setShare_total(BigDecimal share_total) {
		this.share_total = share_total;
	}

	public BigDecimal getSupplier_total() {
		return supplier_total;
	}

	public void setSupplier_total(BigDecimal supplier_total) {
		this.supplier_total = supplier_total;
	}

	public BigDecimal getCompany_total() {
		return company_total;
	}

	public void setCompany_total(BigDecimal company_total) {
		this.company_total = company_total;
	}

	public BigDecimal getQuality_tool_total() {
		return quality_tool_total;
	}

	public void setQuality_tool_total(BigDecimal quality_tool_total) {
		this.quality_tool_total = quality_tool_total;
	}

	public BigDecimal getOrder_gains() {
		return order_gains;
	}

	public void setOrder_gains(BigDecimal order_gains) {
		this.order_gains = order_gains;
	}

}
