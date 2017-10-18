package com.tuniu.qms.report.model;

import com.tuniu.qms.common.model.BaseModel;

public class CmpQcBUStat extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	/** 事业部 */
	private String businessUnit;
	
	/** 质检单数 */
	private Integer qcNum;
	
	/** 质检中单数 */
	private Integer qcIngNum;
	
	/** 质检完成单数 */
	private Integer qcDoneNum;
	
	/** 撤销单数 */
	private Integer cancelNum;
	
	/** 总完成单数 */
	private Integer doneTotalNum;
	
	/** 撤销率 */
	private Double cancelRate;
	
	private Integer comeFrom1;//来电投诉
	
	private Integer comeFrom2;//其他
	
	private Integer comeFrom3;//回访
	
	private Integer comeFrom4;//门市
	
	private Integer comeFrom5;//旅游局
	
	private Integer comeFrom6;//微博
	
	private Integer comeFrom7;//CS邮箱
	
	private Integer comeFrom8;//点评
	
	private Integer comeFrom9;//网站

	private Integer comeFrom10;//当地质检
	
	private Integer comeFrom11;//变更
	
	private Integer comeFrom12;//APP


	public Integer getComeFrom1() {
		return comeFrom1;
	}

	public void setComeFrom1(Integer comeFrom1) {
		this.comeFrom1 = comeFrom1;
	}

	public Integer getComeFrom2() {
		return comeFrom2;
	}

	public void setComeFrom2(Integer comeFrom2) {
		this.comeFrom2 = comeFrom2;
	}

	public Integer getComeFrom3() {
		return comeFrom3;
	}

	public void setComeFrom3(Integer comeFrom3) {
		this.comeFrom3 = comeFrom3;
	}

	public Integer getComeFrom4() {
		return comeFrom4;
	}

	public void setComeFrom4(Integer comeFrom4) {
		this.comeFrom4 = comeFrom4;
	}

	public Integer getComeFrom5() {
		return comeFrom5;
	}

	public void setComeFrom5(Integer comeFrom5) {
		this.comeFrom5 = comeFrom5;
	}

	public Integer getComeFrom6() {
		return comeFrom6;
	}

	public void setComeFrom6(Integer comeFrom6) {
		this.comeFrom6 = comeFrom6;
	}

	public Integer getComeFrom7() {
		return comeFrom7;
	}

	public void setComeFrom7(Integer comeFrom7) {
		this.comeFrom7 = comeFrom7;
	}

	public Integer getComeFrom8() {
		return comeFrom8;
	}

	public void setComeFrom8(Integer comeFrom8) {
		this.comeFrom8 = comeFrom8;
	}

	public Integer getComeFrom9() {
		return comeFrom9;
	}

	public void setComeFrom9(Integer comeFrom9) {
		this.comeFrom9 = comeFrom9;
	}

	public Integer getComeFrom10() {
		return comeFrom10;
	}

	public void setComeFrom10(Integer comeFrom10) {
		this.comeFrom10 = comeFrom10;
	}

	public Integer getComeFrom11() {
		return comeFrom11;
	}

	public void setComeFrom11(Integer comeFrom11) {
		this.comeFrom11 = comeFrom11;
	}

	public Integer getComeFrom12() {
		return comeFrom12;
	}

	public void setComeFrom12(Integer comeFrom12) {
		this.comeFrom12 = comeFrom12;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public Integer getQcNum() {
		return qcNum;
	}

	public void setQcNum(Integer qcNum) {
		this.qcNum = qcNum;
	}

	public Integer getQcIngNum() {
		return qcIngNum;
	}

	public void setQcIngNum(Integer qcIngNum) {
		this.qcIngNum = qcIngNum;
	}

	public Integer getQcDoneNum() {
		return qcDoneNum;
	}

	public void setQcDoneNum(Integer qcDoneNum) {
		this.qcDoneNum = qcDoneNum;
	}

	public Integer getCancelNum() {
		return cancelNum;
	}

	public void setCancelNum(Integer cancelNum) {
		this.cancelNum = cancelNum;
	}

	public Integer getDoneTotalNum() {
		return doneTotalNum;
	}

	public void setDoneTotalNum(Integer doneTotalNum) {
		this.doneTotalNum = doneTotalNum;
	}

	public Double getCancelRate() {
		return cancelRate;
	}

	public void setCancelRate(Double cancelRate) {
		this.cancelRate = cancelRate;
	}
	
}
