package com.tuniu.gt.complaint.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class SystemVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sysName; // 系统名称
	
	private Integer sysCode; // 系统编号
	
	private Integer sysType; // 系统类型：1-产品，2-订单
	
	private String sysUrl; // 系统基础URL
	
	private List<Map<String, Integer>> numericals; // 号段

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public Integer getSysCode() {
		return sysCode;
	}

	public void setSysCode(Integer sysCode) {
		this.sysCode = sysCode;
	}

	public Integer getSysType() {
		return sysType;
	}

	public void setSysType(Integer sysType) {
		this.sysType = sysType;
	}

	public String getSysUrl() {
		return sysUrl;
	}

	public void setSysUrl(String sysUrl) {
		this.sysUrl = sysUrl;
	}

	public List<Map<String, Integer>> getNumericals() {
		return numericals;
	}

	public void setNumericals(List<Map<String, Integer>> numericals) {
		this.numericals = numericals;
	}
	
}
