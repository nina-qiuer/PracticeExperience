package com.tuniu.gt.innerqc.vo;

import java.util.List;

import com.tuniu.gt.innerqc.entity.InnerQcEntity;
import com.tuniu.gt.toolkit.PageBase;

public class InnerQcPage extends PageBase {
	
	private String iqcId;
	private String state;
	private String relBillType;
	private String relBillNum;
	private String addPersonId;
	private String addPersonName;
	private String dealPersonId;
	private String dealPersonName;
	private String addTimeBgn;
	private String addTimeEnd;
	private String personIdStr;
	private String typeId;
	private String[] iqcIds;
	private List<InnerQcEntity> iqcList;
	
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getPersonIdStr() {
		return personIdStr;
	}
	public void setPersonIdStr(String personIdStr) {
		this.personIdStr = personIdStr;
	}
	public String getDealPersonId() {
		return dealPersonId;
	}
	public void setDealPersonId(String dealPersonId) {
		this.dealPersonId = dealPersonId;
	}
	public String getDealPersonName() {
		return dealPersonName;
	}
	public void setDealPersonName(String dealPersonName) {
		this.dealPersonName = dealPersonName;
	}
	public String getAddPersonId() {
		return addPersonId;
	}
	public void setAddPersonId(String addPersonId) {
		this.addPersonId = addPersonId;
	}
	public String[] getIqcIds() {
		return iqcIds;
	}
	public void setIqcIds(String[] iqcIds) {
		this.iqcIds = iqcIds;
	}
	public List<InnerQcEntity> getIqcList() {
		return iqcList;
	}
	public void setIqcList(List<InnerQcEntity> iqcList) {
		this.iqcList = iqcList;
	}
	public String getIqcId() {
		return iqcId;
	}
	public void setIqcId(String iqcId) {
		this.iqcId = iqcId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRelBillType() {
		return relBillType;
	}
	public void setRelBillType(String relBillType) {
		this.relBillType = relBillType;
	}
	public String getRelBillNum() {
		return relBillNum;
	}
	public void setRelBillNum(String relBillNum) {
		this.relBillNum = relBillNum;
	}
	public String getAddPersonName() {
		return addPersonName;
	}
	public void setAddPersonName(String addPersonName) {
		this.addPersonName = addPersonName;
	}
	public String getAddTimeBgn() {
		return addTimeBgn;
	}
	public void setAddTimeBgn(String addTimeBgn) {
		this.addTimeBgn = addTimeBgn;
	}
	public String getAddTimeEnd() {
		return addTimeEnd;
	}
	public void setAddTimeEnd(String addTimeEnd) {
		this.addTimeEnd = addTimeEnd;
	}
	
}
