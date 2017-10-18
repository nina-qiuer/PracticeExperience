package com.tuniu.qms.report.dto;

import java.util.ArrayList;
import java.util.List;

import com.tuniu.qms.report.model.CmpQcWorkProportion;
import com.tuniu.qms.report.model.CmpQcWorkStat;

public class CmpQcWorkStatReportDto {
	
	//名字
	public String name;
	//数据
	public CmpQcWorkStat data;
	//人均数据
	public CmpQcWorkProportion cmpQcWorkProportion;
	//子节点
	public List<CmpQcWorkStatReportDto> children = new ArrayList<CmpQcWorkStatReportDto>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CmpQcWorkStat getData() {
		return data;
	}
	public void setData(CmpQcWorkStat data) {
		this.data = data;
	}
	public List<CmpQcWorkStatReportDto> getChildren() {
		return children;
	}
	public void setChildren(List<CmpQcWorkStatReportDto> children) {
		this.children = children;
	}
	public CmpQcWorkProportion getCmpQcWorkProportion() {
		return cmpQcWorkProportion;
	}
	public void setCmpQcWorkProportion(CmpQcWorkProportion cmpQcWorkProportion) {
		this.cmpQcWorkProportion = cmpQcWorkProportion;
	}
	
}
