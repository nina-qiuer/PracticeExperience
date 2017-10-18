package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;

public class QcNote extends BaseModel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**质检id*/
    private Integer qcBillId;
    
    /**备忘详情*/
    private String content;

    /**对接人*/
    private String dockingPeople;
    
    /**对接部门*/
    private String dockingDep;
    
    public String getContent() {
        return content;
    }

    public Integer getQcBillId() {
        return qcBillId;
    }

    public void setQcBillId(Integer qcBillId) {
        this.qcBillId = qcBillId;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public String getDockingPeople() {
		return dockingPeople;
	}

	public void setDockingPeople(String dockingPeople) {
		this.dockingPeople = dockingPeople;
	}

	public String getDockingDep() {
		return dockingDep;
	}

	public void setDockingDep(String dockingDep) {
		this.dockingDep = dockingDep;
	}
    
}
