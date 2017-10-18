package com.tuniu.qms.common.model; 
import java.io.Serializable;

/**
 * 对客解决方案
 * @author chenhaitao
 *
 */
public class ComplaintSolution  implements Serializable {
	
	private static final long serialVersionUID = -8753408614685071482L;

	private Integer cmpId; //关联投诉id
	
	private Integer orderId;//订单号
	
	/** 对客处理方案*/
	private String solution;

	/** 质检单号*/
	private Integer qcId;
	
	/** 投诉处理状态*/
	private String cmpStateName;
	
	/**质检状态*/
	private String qcStateName;
	
	/**质检员**/
	private String qcPerson;

	public Integer getCmpId() {
		return cmpId;
	}

	public void setCmpId(Integer cmpId) {
		this.cmpId = cmpId;
	}

	

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

	public String getCmpStateName() {
		return cmpStateName;
	}

	public void setCmpStateName(String cmpStateName) {
		this.cmpStateName = cmpStateName;
	}

	public String getQcStateName() {
		return qcStateName;
	}

	public void setQcStateName(String qcStateName) {
		this.qcStateName = qcStateName;
	}

	public String getQcPerson() {
		return qcPerson;
	}

	public void setQcPerson(String qcPerson) {
		this.qcPerson = qcPerson;
	}
	
	
	
}
