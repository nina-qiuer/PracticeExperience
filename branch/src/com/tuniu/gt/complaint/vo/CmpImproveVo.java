package com.tuniu.gt.complaint.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.tuniu.gt.complaint.entity.AttachEntity;

/**
 * 投诉改进报告
 * @author zhangsensen
 *
 */
public class CmpImproveVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
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
	/** 投诉事宜 */
	private String cmpAffair;
	/** 改进点  */
	private String improvePoint;
	/**产品总监**/
	private String productManager;
	/**产品经理**/
	private String productLeader;
	/**产品专员**/
	private String producter;
	/** 客服专员*/
	private String customer;
	/** 客服经理*/
	private String customerLeader;
	/** 高级客服经理*/
	private String customerManager;
	/** 线路或产品名称*/
	private String routeName;
	/** 供应商名称*/
	private String agencyName;
	/** 添加人*/
	private String addPerson;
	/** 改进人一级部门*/
	private Integer firstDepId;
	/** 改进人一级部门名称*/
	private String firstDep;
	/** 改进人二级部门*/
	private Integer secondDepId;
	/** 改进人二级部门名称*/
	private String secondDep;
	/** 改进人部门*/
	private Integer impPerDepId;
	/** 附件列表*/
	List<AttachEntity> attachList;
	
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
	public String getCmpAffair() {
		return cmpAffair;
	}
	public void setCmpAffair(String cmpAffair) {
		this.cmpAffair = cmpAffair;
	}
	public String getImprovePoint() {
		return improvePoint;
	}
	public void setImprovePoint(String improvePoint) {
		this.improvePoint = improvePoint;
	}
	public String getProductManager() {
		return productManager;
	}
	public void setProductManager(String productManager) {
		this.productManager = productManager;
	}
	public String getProductLeader() {
		return productLeader;
	}
	public void setProductLeader(String productLeader) {
		this.productLeader = productLeader;
	}
	public String getProducter() {
		return producter;
	}
	public void setProducter(String producter) {
		this.producter = producter;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getCustomerLeader() {
		return customerLeader;
	}
	public void setCustomerLeader(String customerLeader) {
		this.customerLeader = customerLeader;
	}
	public String getCustomerManager() {
		return customerManager;
	}
	public void setCustomerManager(String customerManager) {
		this.customerManager = customerManager;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getAddPerson() {
		return addPerson;
	}
	public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
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
	public List<AttachEntity> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<AttachEntity> attachList) {
		this.attachList = attachList;
	}
	public Integer getImpPerDepId() {
		return impPerDepId;
	}
	public void setImpPerDepId(Integer impPerDepId) {
		this.impPerDepId = impPerDepId;
	}
	
}
