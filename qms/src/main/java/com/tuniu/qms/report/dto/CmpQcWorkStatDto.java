package com.tuniu.qms.report.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.report.model.CmpQcWorkStat;

public class CmpQcWorkStatDto extends BaseDto<CmpQcWorkStat> {
	
	/** 统计日期Bgn */
	private String statDateBgn;
	
	/** 统计日期End */
	private String statDateEnd;

	public String getStatDateBgn() {
		return statDateBgn;
	}

	public void setStatDateBgn(String statDateBgn) {
		this.statDateBgn = statDateBgn;
	}

	public String getStatDateEnd() {
		return statDateEnd;
	}

	public void setStatDateEnd(String statDateEnd) {
		this.statDateEnd = statDateEnd;
	}
	
}
