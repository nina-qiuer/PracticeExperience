/**
 * 
 */
package com.tuniu.gt.complaint.vo;

import java.math.BigDecimal;

/**
 * @author chenyu
 * 
 */
public class ComplaintDtReportVo {
	private String id;// 投诉单号
	private String order_id;// 订单号
	private int userid;// 用户编号
	private String real_name;// 用户姓名
	private String dept_third;// 三级部门
	private String dept_second;// 二级部门
	private String dept_first;// 一级部门
	private BigDecimal theoryPayout;// 理论赔付
	private BigDecimal supplier_total;// 供应商承担金额
	private BigDecimal company_total;// 公司承担金额
	private BigDecimal quality_tool_total;// 质量工具承担金额
	private BigDecimal realPayout;// 实际赔付总额
	private BigDecimal savePayout;// 节约金额
	private BigDecimal above_scale;// 超额赔付率
	
	private BigDecimal payout_num;//理赔单数
	private BigDecimal theory_num;//填写理论赔付数
	private BigDecimal share_total;//分担总额
	private BigDecimal order_gains;//订单利润承担
	
	private BigDecimal realGain;//实际收益
	private BigDecimal theoryGain;//理论收益

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getDept_third() {
		return dept_third;
	}

	public void setDept_third(String dept_third) {
		this.dept_third = dept_third;
	}

	public String getDept_second() {
		return dept_second;
	}

	public void setDept_second(String dept_second) {
		this.dept_second = dept_second;
	}

	public String getDept_first() {
		return dept_first;
	}

	public void setDept_first(String dept_first) {
		this.dept_first = dept_first;
	}

	public BigDecimal getTheoryPayout() {
		return theoryPayout;
	}

	public void setTheoryPayout(BigDecimal theoryPayout) {
		this.theoryPayout = theoryPayout;
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

	public BigDecimal getRealPayout() {
		return realPayout;
	}

	public void setRealPayout(BigDecimal realPayout) {
		this.realPayout = realPayout;
	}

	public BigDecimal getSavePayout() {
		return savePayout;
	}

	public void setSavePayout(BigDecimal savePayout) {
		this.savePayout = savePayout;
	}

	public BigDecimal getAbove_scale() {
		return above_scale;
	}

	public void setAbove_scale(BigDecimal above_scale) {
		this.above_scale = above_scale;
	}

	public BigDecimal getPayout_num() {
		return payout_num;
	}

	public void setPayout_num(BigDecimal payout_num) {
		this.payout_num = payout_num;
	}

	public BigDecimal getTheory_num() {
		return theory_num;
	}

	public void setTheory_num(BigDecimal theory_num) {
		this.theory_num = theory_num;
	}

	public BigDecimal getShare_total() {
		return share_total;
	}

	public void setShare_total(BigDecimal share_total) {
		this.share_total = share_total;
	}

	public BigDecimal getOrder_gains() {
		return order_gains;
	}

	public void setOrder_gains(BigDecimal order_gains) {
		this.order_gains = order_gains;
	}

	public BigDecimal getRealGain() {
		return realGain;
	}

	public void setRealGain(BigDecimal realGain) {
		this.realGain = realGain;
	}

	public BigDecimal getTheoryGain() {
		return theoryGain;
	}

	public void setTheoryGain(BigDecimal theoryGain) {
		this.theoryGain = theoryGain;
	}
}
