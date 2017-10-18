package com.tuniu.gt.complaint.vo;

import java.util.List;
import java.util.Map;

public class AgencyPayInfoPage {
	
	private int count;
	
	List<Map<String, Object>> rows;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Map<String, Object>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}

}
