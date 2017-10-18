package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.QcBillRelation;

public class QcBillRelationDto extends BaseDto<QcBillRelation> {
	
	/**投诉质检单号**/
	private String qcId;  
	
	
	/**研发质检单号**/
	private String devId; 
	
	/**投诉单号 **/
	private String cmpId;
	
	/**投诉质检员 **/
	private String qcPerson;
	
	/**投诉开始时间**/
	private String cmpStartTime ;
	
	/**投诉结束时间**/
	private String cmpEndTime ;
	
	/** 订单号*/
	private Integer ordId;
	
	private Integer flag =0;
	
	/** 备注*/
	private String remark;
	/** 关闭类型*/
	private Integer closeType;
	
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getQcId() {
		return qcId;
	}

	public void setQcId(String qcId) {
		this.qcId = qcId;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public String getCmpId() {
		return cmpId;
	}

	public void setCmpId(String cmpId) {
		this.cmpId = cmpId;
	}

	public String getQcPerson() {
		return qcPerson;
	}

	public void setQcPerson(String qcPerson) {
		this.qcPerson = qcPerson;
	}

	public String getCmpStartTime() {
		return cmpStartTime;
	}

	public void setCmpStartTime(String cmpStartTime) {
		this.cmpStartTime = cmpStartTime;
	}

	public String getCmpEndTime() {
		return cmpEndTime;
	}

	public void setCmpEndTime(String cmpEndTime) {
		this.cmpEndTime = cmpEndTime;
	}

	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCloseType() {
		return closeType;
	}

	public void setCloseType(Integer closeType) {
		this.closeType = closeType;
	}
	
}
