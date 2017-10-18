package com.tuniu.qms.qc.model;

import java.util.Date;

import com.tuniu.qms.common.model.BaseModel;
/**
 *质检关联单
 * @author chenhaitao
 *
 */
public class QcBillRelation extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**投诉质检单号**/
	private Integer qcId;   
	
	/**研发质检单号**/
	private Integer devId;
	
	/**投诉单号 **/
	private Integer cmpId;
	
	/**订单号 **/
	private Integer ordId;
	
	/**投诉质检员 **/
	private String qcPerson;
	
	/**投诉时间**/
	private Date cmpTime ;
	
	/**对客赔偿**/
	private Double indemnifyAmount;
	
	/**供应商赔偿**/
	private Double claimAmount;
	
	/** 关联标识 --0：未关联，1：已关联，2：已关闭*/
	private Integer flag ;
	
	/** 质检标识 --:1：投诉质检 2 研发质检   3 双方质检*/
	private Integer qcFlag ;
	
	/** 关闭原因类型*/
	private Integer closeType;
	
	/** 备注*/
	private String remark;
	
	public Integer getCloseType() {
		return closeType;
	}

	public void setCloseType(Integer closeType) {
		this.closeType = closeType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDevId() {
		return devId;
	}

	public void setDevId(Integer devId) {
		this.devId = devId;
	}


	public Integer getQcFlag() {
		return qcFlag;
	}

	public void setQcFlag(Integer qcFlag) {
		this.qcFlag = qcFlag;
	}

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

	public Integer getCmpId() {
		return cmpId;
	}

	public void setCmpId(Integer cmpId) {
		this.cmpId = cmpId;
	}

	public String getQcPerson() {
		return qcPerson;
	}

	public void setQcPerson(String qcPerson) {
		this.qcPerson = qcPerson;
	}

	public Date getCmpTime() {
		return cmpTime;
	}

	public void setCmpTime(Date cmpTime) {
		this.cmpTime = cmpTime;
	}
	
	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public Double getIndemnifyAmount() {
		return indemnifyAmount;
	}

	public void setIndemnifyAmount(Double indemnifyAmount) {
		this.indemnifyAmount = indemnifyAmount;
	}

	public Double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(Double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}	
}
