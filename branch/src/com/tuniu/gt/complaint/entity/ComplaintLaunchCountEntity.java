package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ComplaintLaunchCountEntity implements Serializable {

	private static final long serialVersionUID = -5343922891502117038L;

	private String dep_name;// 统计的部门

	private String father_dep;// 父级部门

	private String grandpa_dep;// 爷级部门

	private Long countNum;// 统计数量

	private String countPercent;// 统计百分比

	public String getDep_name() {
		return dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

	public String getFather_dep() {
		return father_dep;
	}

	public void setFather_dep(String father_dep) {
		this.father_dep = father_dep;
	}

	public String getGrandpa_dep() {
		return grandpa_dep;
	}

	public void setGrandpa_dep(String grandpa_dep) {
		this.grandpa_dep = grandpa_dep;
	}

	public Long getCountNum() {
		return countNum;
	}

	public void setCountNum(Long countNum) {
		this.countNum = countNum;
	}

	public String getCountPercent() {
		return countPercent;
	}

	public void setCountPercent(String countPercent) {
		this.countPercent = countPercent;
	}

}
