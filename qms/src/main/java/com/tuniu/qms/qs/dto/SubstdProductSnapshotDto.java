package com.tuniu.qms.qs.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qs.model.SubstdProductSnapshot;

public class SubstdProductSnapshotDto extends BaseDto<SubstdProductSnapshot> {
	
	/** 监控项 */
	private String substdKey;
	
	/** 目的地大类 */
	private String destCate;
	
	/** 统计日期 */
	private String statisticDate;
	
	/** 统计日期Bgn */
	private String statisticDateBgn;
	
	/** 统计日期End */
	private String statisticDateEnd;
	
	/** 产品ID */
	private Integer prdId;
	
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
	
	public String getSubstdKey() {
		return substdKey;
	}

	public void setSubstdKey(String substdKey) {
		this.substdKey = substdKey;
	}

	public String getDestCate() {
		return destCate;
	}

	public void setDestCate(String destCate) {
		this.destCate = destCate;
	}

	public String getStatisticDate() {
		return statisticDate;
	}

	public void setStatisticDate(String statisticDate) {
		this.statisticDate = statisticDate;
	}

	public Integer getPrdId() {
		return prdId;
	}

	public void setPrdId(Integer prdId) {
		this.prdId = prdId;
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

	public String getStatisticDateBgn() {
		return statisticDateBgn;
	}

	public void setStatisticDateBgn(String statisticDateBgn) {
		this.statisticDateBgn = statisticDateBgn;
	}

	public String getStatisticDateEnd() {
		return statisticDateEnd;
	}

	public void setStatisticDateEnd(String statisticDateEnd) {
		this.statisticDateEnd = statisticDateEnd;
	}
	
}
