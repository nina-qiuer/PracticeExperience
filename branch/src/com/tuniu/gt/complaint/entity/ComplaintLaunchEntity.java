package com.tuniu.gt.complaint.entity;

import java.io.Serializable;

public class ComplaintLaunchEntity implements Serializable {

	private static final long serialVersionUID = -1787467112498691948L;
	
	private Integer complaint_id;//投诉单号
	
	private String route_type;//产品品类
	
	private String launcher_name;//发起人
	
	private String depname_first;//一级部门
	
	private String depname_second;//二级部门
	
	private String depname_third;//三级部门
	
	private String depname_fourth;//四级部门

	public Integer getComplaint_id() {
		return complaint_id;
	}

	public void setComplaint_id(Integer complaint_id) {
		this.complaint_id = complaint_id;
	}

	public String getRoute_type() {
		return route_type;
	}

	public void setRoute_type(String route_type) {
		this.route_type = route_type;
	}

	public String getLauncher_name() {
		return launcher_name;
	}

	public void setLauncher_name(String launcher_name) {
		this.launcher_name = launcher_name;
	}

	public String getDepname_first() {
		return depname_first;
	}

	public void setDepname_first(String depname_first) {
		this.depname_first = depname_first;
	}

	public String getDepname_second() {
		return depname_second;
	}

	public void setDepname_second(String depname_second) {
		this.depname_second = depname_second;
	}

	public String getDepname_third() {
		return depname_third;
	}

	public void setDepname_third(String depname_third) {
		this.depname_third = depname_third;
	}

	public String getDepname_fourth() {
		return depname_fourth;
	}

	public void setDepname_fourth(String depname_fourth) {
		this.depname_fourth = depname_fourth;
	}
}
