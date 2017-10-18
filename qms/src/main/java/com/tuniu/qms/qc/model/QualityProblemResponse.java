package com.tuniu.qms.qc.model;

import java.util.List;

/**
 *获取导游所有质量问题明细信息
 * @author chenhaitao
 *
 */
public class QualityProblemResponse  {

	
	private List<QualityProblemDetail> rows;
	
	private int count;

	public List<QualityProblemDetail> getRows() {
		return rows;
	}

	public void setRows(List<QualityProblemDetail> rows) {
		this.rows = rows;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	

	
	
	
}
