package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;

/**
 * 研发质检影响系统
 * @author chenhaitao
 *
 */
public class QcInfluenceSystem  extends BaseModel{

	
	private static final long serialVersionUID = 1L;
	
    /**研发质检单*/
	private Integer qcId;
	
	 /**影响系统*/
	private String influenceSystem;

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

	public String getInfluenceSystem() {
		return influenceSystem;
	}

	public void setInfluenceSystem(String influenceSystem) {
		this.influenceSystem = influenceSystem;
	}
	
	
}
