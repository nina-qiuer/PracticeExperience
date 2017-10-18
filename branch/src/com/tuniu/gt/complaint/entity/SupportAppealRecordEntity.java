package com.tuniu.gt.complaint.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SupportAppealRecordEntity {

	private Integer id;

	private String oa_id;// oa单号

	private Integer complaint_id;// 投诉单号

	private Integer order_id;// 订单号

	private Integer route_id;// 线路号

	private Integer old_agency_id;// 老供应商id

	private String old_agency_name;// 老供应商名称

	private BigDecimal old_agency_payout;// 老供应商承担金额

	private Integer new_agency_id;// 新供应商id

	private String new_agency_name;// 新供应商名称

	private BigDecimal new_agency_payout;// 新供应商承担金额

	private BigDecimal company_payout;// 公司承担

	private Integer approval_result;// 审批结果

	private Integer approval_id;// 审批人id

	private String approval_comment;// 审批人意见

	private Date add_time;// 添加时间

	private Integer del_flag;// 删除标识

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOa_id() {
		return oa_id;
	}

	public void setOa_id(String oa_id) {
		this.oa_id = oa_id;
	}

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

	public Integer getRoute_id() {
		return route_id;
	}

	public void setRoute_id(Integer route_id) {
		this.route_id = route_id;
	}

	public Integer getOld_agency_id() {
		return old_agency_id;
	}

	public void setOld_agency_id(Integer old_agency_id) {
		this.old_agency_id = old_agency_id;
	}

	public String getOld_agency_name() {
		return old_agency_name;
	}

	public void setOld_agency_name(String old_agency_name) {
		this.old_agency_name = old_agency_name;
	}

	public BigDecimal getOld_agency_payout() {
		return old_agency_payout;
	}

	public void setOld_agency_payout(BigDecimal old_agency_payout) {
		this.old_agency_payout = old_agency_payout;
	}

	public Integer getNew_agency_id() {
		return new_agency_id;
	}

	public void setNew_agency_id(Integer new_agency_id) {
		this.new_agency_id = new_agency_id;
	}

	public String getNew_agency_name() {
		return new_agency_name;
	}

	public void setNew_agency_name(String new_agency_name) {
		this.new_agency_name = new_agency_name;
	}

	public BigDecimal getNew_agency_payout() {
		return new_agency_payout;
	}

	public void setNew_agency_payout(BigDecimal new_agency_payout) {
		this.new_agency_payout = new_agency_payout;
	}

	public BigDecimal getCompany_payout() {
		return company_payout;
	}

	public void setCompany_payout(BigDecimal company_payout) {
		this.company_payout = company_payout;
	}

	public Integer getApproval_result() {
		return approval_result;
	}

	public void setApproval_result(Integer approval_result) {
		this.approval_result = approval_result;
	}

	public Integer getApproval_id() {
		return approval_id;
	}

	public void setApproval_id(Integer approval_id) {
		this.approval_id = approval_id;
	}

	public String getApproval_comment() {
		return approval_comment;
	}

	public void setApproval_comment(String approval_comment) {
		this.approval_comment = approval_comment;
	}

	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}

	public Integer getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(Integer del_flag) {
		this.del_flag = del_flag;
	}
}
