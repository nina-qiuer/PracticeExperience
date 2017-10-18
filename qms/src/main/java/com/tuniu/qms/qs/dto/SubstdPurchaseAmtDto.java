package com.tuniu.qms.qs.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qs.model.SubstdPurchaseAmt;

public class SubstdPurchaseAmtDto extends BaseDto<SubstdPurchaseAmt> {
	
	/** 添加日期 */
	private String addDate;
	
	/** 添加日期Bgn */
	private String addDateBgn;
	
	/** 添加日期End */
	private String addDateEnd;
	
	/** 订单ID */
	private Integer ordId;
	
	/** 事业部 */
	private String businessUnit;
	
	/** 产品部 */
	private String prdDep;
	
	/** 产品组 */
	private String prdTeam;
	
	/** 产品经理 */
	private String prdManager;
	
	/** 产品专员 */
	private String producter;
	
	/** 统计项 */
	private String statItem;

	/**线路编号*/
	private String routeId ;
	

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	public String getAddDateBgn() {
		return addDateBgn;
	}

	public void setAddDateBgn(String addDateBgn) {
		this.addDateBgn = addDateBgn;
	}

	public String getAddDateEnd() {
		return addDateEnd;
	}

	public void setAddDateEnd(String addDateEnd) {
		this.addDateEnd = addDateEnd;
	}

	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getPrdDep() {
		return prdDep;
	}

	public void setPrdDep(String prdDep) {
		this.prdDep = prdDep;
	}

	public String getPrdTeam() {
		return prdTeam;
	}

	public void setPrdTeam(String prdTeam) {
		this.prdTeam = prdTeam;
	}

	public String getPrdManager() {
		return prdManager;
	}

	public void setPrdManager(String prdManager) {
		this.prdManager = prdManager;
	}

	public String getProducter() {
		return producter;
	}

	public void setProducter(String producter) {
		this.producter = producter;
	}

	public String getStatItem() {
		return statItem;
	}

	public void setStatItem(String statItem) {
		this.statItem = statItem;
	}
	
}
