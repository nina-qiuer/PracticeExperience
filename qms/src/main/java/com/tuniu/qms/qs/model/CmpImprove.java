package com.tuniu.qms.qs.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.model.BaseModel;
import com.tuniu.qms.common.model.Product;

/**
 * 投诉改进报告
 * @author zhangsensen
 *
 */
public class CmpImprove extends BaseModel{

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
	/** 改进报告状态  1：  定责     2：  处理中   3：待确认  4：已完成  */
	private Integer state;
	/** 是否有责标示位    0： 有责    1： 无责  */
	private Integer isRespFlag;
	/** 投诉事宜 */
	private String cmpAffair;
	/** 改进点  */
	private String improvePoint;
	/** 改进措施  */
	private String improveMethod;
	/** 预计改进完成时间  */
	private Date improveFinTime;
	/** 预计改进结果  */
	private String improveResult;
	/** 备注  */
	private String remark;
	/** 流程处理到期时间 */
	private Date handleEndTime;
	/** 其他责任人*/
	private String otherPerson;
	/** 其他责任人工号*/
	private Integer otherPersonCode;
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
	/** OA 表单id "oaId":"1000040335"*/
	private String oaId;
	
	/** OA 更新的改进报告id*/
	private Integer impId;
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
	/** 状态转换后中文标示*/
	private String stateStr;
	
	/** 投诉来源 */
	private String comeFrom;
	/** 投诉级别 */
	private Integer cmpLevel;
	/** 投诉时间*/
	private Date cmpAddTime;
	
	List<Map<String, Object>> attachList;
	
	/** 产品信息*/
	private Product product;
	
	public List<Map<String, Object>> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<Map<String, Object>> attachList) {
		this.attachList = attachList;
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
	public String getImproveMethod() {
		return improveMethod;
	}
	public void setImproveMethod(String improveMethod) {
		this.improveMethod = improveMethod;
	}
	public Date getImproveFinTime() {
		return improveFinTime;
	}
	public void setImproveFinTime(Date improveFinTime) {
		this.improveFinTime = improveFinTime;
	}
	public String getImproveResult() {
		return improveResult;
	}
	public void setImproveResult(String improveResult) {
		this.improveResult = improveResult;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getHandleEndTime() {
		return handleEndTime;
	}
	public void setHandleEndTime(Date handleEndTime) {
		this.handleEndTime = handleEndTime;
	}
	public Integer getImpId() {
		return impId;
	}
	public void setImpId(Integer impId) {
		this.impId = impId;
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
	public String getOaId() {
		return oaId;
	}
	public void setOaId(String oaId) {
		this.oaId = oaId;
	}
	public String getStateStr() {
		return stateStr;
	}
	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}
	public Integer getOtherPersonCode() {
		return otherPersonCode;
	}
	public void setOtherPersonCode(Integer otherPersonCode) {
		this.otherPersonCode = otherPersonCode;
	}
	public String getComeFrom() {
		return comeFrom;
	}
	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}
	public Integer getCmpLevel() {
		return cmpLevel;
	}
	public void setCmpLevel(Integer cmpLevel) {
		this.cmpLevel = cmpLevel;
	}
	public Date getCmpAddTime() {
		return cmpAddTime;
	}
	public void setCmpAddTime(Date cmpAddTime) {
		this.cmpAddTime = cmpAddTime;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
}
