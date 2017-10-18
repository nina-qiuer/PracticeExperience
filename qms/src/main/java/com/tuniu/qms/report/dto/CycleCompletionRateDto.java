package com.tuniu.qms.report.dto;

import com.tuniu.qms.common.model.BaseModel;

public class CycleCompletionRateDto extends BaseModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 质检期日期Bgn */
	private String qcPeriodTimeBgn;
	
	/**质检期日期End */
	private String qcPeriodTimeEnd;
	
	/** 统计日期 */
	private String statisticDate;


	public String getQcPeriodTimeBgn() {
		return qcPeriodTimeBgn;
	}

	public void setQcPeriodTimeBgn(String qcPeriodTimeBgn) {
		this.qcPeriodTimeBgn = qcPeriodTimeBgn;
	}

	public String getQcPeriodTimeEnd() {
		return qcPeriodTimeEnd;
	}

	public void setQcPeriodTimeEnd(String qcPeriodTimeEnd) {
		this.qcPeriodTimeEnd = qcPeriodTimeEnd;
	}

	public String getStatisticDate() {
		return statisticDate;
	}

	public void setStatisticDate(String statisticDate) {
		this.statisticDate = statisticDate;
	} 

	
}
