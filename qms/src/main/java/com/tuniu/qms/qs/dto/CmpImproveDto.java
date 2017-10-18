package com.tuniu.qms.qs.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qs.model.CmpImprove;

/**
 * 投诉改进报告查询条件
 * @author zhangsensen
 *
 */
public class CmpImproveDto  extends BaseDto<CmpImprove>{
	
	/** 投诉id  */
	private Integer cmpId;
	/** 产品id  */
	private Integer prdId;
	/** 订单id  */
	private Integer ordId;
	/** 改进人id  */
	private Integer impPersonId;
	/** 改进人姓名  */
	private String impPerson;
	/** 改进报告状态  1：  定责     2：  处理中   3：待确认  4：已完成  */
	private Integer state;
	/** 是否有责标示位    0： 有责    1： 无责  */
	private Integer isRespFlag;
	/** 流程处理到期开始时间 */
	private String handleEndTimeBgn;
	/** 流程处理到期结束时间 */
	private String handleEndTimeEnd;
	/** 预计改进完成开始时间  */
	private String improveFinTimeBgn;
	/** 预计改进完成完成时间  */
	private String improveFinTimeEnd;
	/** 其他责任人*/
	private String otherPerson;
	/** 其他责任供应商*/
	private String otherAgencyName;
	/** 处理人*/
	private String handlePerson;
	/** 一级部门*/
	private Integer firstDepId;
	/** 一级部门名称*/
	private String firstDep;
	/** 二级部门*/
	private Integer secondDepId;
	/** 二级部门名称*/
	private String secondDep;
	/** 改进人部门*/
	private Integer impPerDepId;
	/** 分配配给处理人的改进报告单号数组*/
	private List<Integer> impBillIds;
	/** 分配的处理人*/
	private String assignTo;
	/** 供应商是否有责*/
	private Integer isAgencyRespFlag;
	
	private List<String> fields = new ArrayList<String>(Arrays.asList("id,cmpId,orderId,prdId,addTime,handleEndTime,cmpAddTime,cmpLevel,comeFrom,impPerson,handlePerson,addPerson,isRespFlag,state".split(",")));
	
	public List<String> getFields() {
		return fields;
	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	public Integer getIsAgencyRespFlag() {
		return isAgencyRespFlag;
	}
	public void setIsAgencyRespFlag(Integer isAgencyRespFlag) {
		this.isAgencyRespFlag = isAgencyRespFlag;
	}
	public Integer getCmpId() {
		return cmpId;
	}
	public void setCmpId(Integer cmpId) {
		this.cmpId = cmpId;
	}
	public Integer getPrdId() {
		return prdId;
	}
	public void setPrdId(Integer prdId) {
		this.prdId = prdId;
	}
	public Integer getOrdId() {
		return ordId;
	}
	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}
	public Integer getImpPersonId() {
		return impPersonId;
	}
	public void setImpPersonId(Integer impPersonId) {
		this.impPersonId = impPersonId;
	}
	public String getImpPerson() {
		return impPerson;
	}
	public void setImpPerson(String impPerson) {
		this.impPerson = impPerson;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getIsRespFlag() {
		return isRespFlag;
	}
	public void setIsRespFlag(Integer isRespFlag) {
		this.isRespFlag = isRespFlag;
	}
	public String getHandleEndTimeBgn() {
		return handleEndTimeBgn;
	}
	public void setHandleEndTimeBgn(String handleEndTimeBgn) {
		this.handleEndTimeBgn = handleEndTimeBgn;
	}
	public String getHandleEndTimeEnd() {
		return handleEndTimeEnd;
	}
	public void setHandleEndTimeEnd(String handleEndTimeEnd) {
		this.handleEndTimeEnd = handleEndTimeEnd;
	}
	public String getImproveFinTimeBgn() {
		return improveFinTimeBgn;
	}
	public void setImproveFinTimeBgn(String improveFinTimeBgn) {
		this.improveFinTimeBgn = improveFinTimeBgn;
	}
	public String getImproveFinTimeEnd() {
		return improveFinTimeEnd;
	}
	public void setImproveFinTimeEnd(String improveFinTimeEnd) {
		this.improveFinTimeEnd = improveFinTimeEnd;
	}
	public String getOtherPerson() {
		return otherPerson;
	}
	public void setOtherPerson(String otherPerson) {
		this.otherPerson = otherPerson;
	}
	public String getOtherAgencyName() {
		return otherAgencyName;
	}
	public void setOtherAgencyName(String otherAgencyName) {
		this.otherAgencyName = otherAgencyName;
	}
	public String getHandlePerson() {
		return handlePerson;
	}
	public void setHandlePerson(String handlePerson) {
		this.handlePerson = handlePerson;
	}
	public Integer getFirstDepId() {
		return firstDepId;
	}
	public void setFirstDepId(Integer firstDepId) {
		this.firstDepId = firstDepId;
	}
	public String getFirstDep() {
		return firstDep;
	}
	public void setFirstDep(String firstDep) {
		this.firstDep = firstDep;
	}
	public Integer getSecondDepId() {
		return secondDepId;
	}
	public void setSecondDepId(Integer secondDepId) {
		this.secondDepId = secondDepId;
	}
	public String getSecondDep() {
		return secondDep;
	}
	public void setSecondDep(String secondDep) {
		this.secondDep = secondDep;
	}
	public Integer getImpPerDepId() {
		return impPerDepId;
	}
	public void setImpPerDepId(Integer impPerDepId) {
		this.impPerDepId = impPerDepId;
	}
	public List<Integer> getImpBillIds() {
		return impBillIds;
	}
	public void setImpBillIds(List<Integer> impBillIds) {
		this.impBillIds = impBillIds;
	}
	public String getAssignTo() {
		return assignTo;
	}
	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}
	
}
