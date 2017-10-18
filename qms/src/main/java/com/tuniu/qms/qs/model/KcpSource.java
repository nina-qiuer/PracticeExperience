package com.tuniu.qms.qs.model;

import com.tuniu.qms.common.model.BaseModel;

public class KcpSource  extends BaseModel {

	/**
	 * KCP来源
	 */
	private static final long serialVersionUID = 1L;

	/** 名称 */
	private String name ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
