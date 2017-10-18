package com.tuniu.qms.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * 订单，维护公司所有订单数据
 * @author wangmingfang
 */
public class OrderBill implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	/** 产品编号 */
	private Integer prdId;
	
	/** 产品 */
	private Product product;
	
	/** 出发城市 */
	private String departCity;
	
	/** 产品成人价 */
	private Double prdAdultPrice;
	
	/** 机票价格 */
	private Double flightPrice;
	
	/** 出游成人数 */
	private Integer adultNum;
	
	/** 出游儿童数 */
	private Integer childNum;
	
	/**订单类型**/
	private String orderType;
	
	/** 出游日期 */
	private java.sql.Date departDate;
	
	/** 归来日期 */
	private java.sql.Date returnDate;
	
	/** 客服专员ID */
	private Integer salerId;
	
	/** 客服专员姓名 */
	private String salerName;
	
	/** 客服经理ID */
	private Integer salerManagerId;
	
	/** 客服经理姓名 */
	private String salerManagerName;
	
	/** 产品专员ID */
	private Integer producterId;
	
	/** 产品专员 */
	private String producter;
	
	/** 产品经理ID */
	private Integer prdManagerId;
	
	/** 产品经理 */
	private String prdManager;
	
	private Integer delFlag;
	
	/** 会员ID*/
	private Integer custId;
	
	/** 会员星级*/
	private String custLevel;
	
	/** 导游编号*/
	private String guideId;
	
	/** 导游名字*/
	private String guideName;
	
	/**联系电话*/
	private String guideCall;
	
	/**客服事业部**/
	private String  salerBusinessUnit;
	
	/**客服部门**/
	private String salerDep;
	
	/**客服组**/
	private String salerTeam;
	
	/**地接价格**/
	private Double groundPrice;
	
	/** 运营人员 */
	private List<OrderBillOperator> operators;
	
	/** 供应商 */
	private List<OrderBillAgency> angencies;

	/**出行天数**/
	private Integer departDay;
	
	/**平均地接成本价**/
	private Double avlGroundPrice;
	
	private Integer avlPriceFlag =1;
	
	
	

	public Integer getAvlPriceFlag() {
		return avlPriceFlag;
	}

	public void setAvlPriceFlag(Integer avlPriceFlag) {
		this.avlPriceFlag = avlPriceFlag;
	}

	public Double getAvlGroundPrice() {
		return avlGroundPrice;
	}

	public void setAvlGroundPrice(Double avlGroundPrice) {
		this.avlGroundPrice = avlGroundPrice;
	}

	public Integer getDepartDay() {
		return departDay;
	}

	public void setDepartDay(Integer departDay) {
		this.departDay = departDay;
	}

	public Double getGroundPrice() {
		return groundPrice;
	}

	public void setGroundPrice(Double groundPrice) {
		this.groundPrice = groundPrice;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getSalerBusinessUnit() {
		return salerBusinessUnit;
	}

	public void setSalerBusinessUnit(String salerBusinessUnit) {
		this.salerBusinessUnit = salerBusinessUnit;
	}

	public String getSalerDep() {
		return salerDep;
	}

	public void setSalerDep(String salerDep) {
		this.salerDep = salerDep;
	}

	public String getSalerTeam() {
		return salerTeam;
	}

	public void setSalerTeam(String salerTeam) {
		this.salerTeam = salerTeam;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}


	public String getCustLevel() {
		return custLevel;
	}

	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPrdId() {
		return prdId;
	}

	public void setPrdId(Integer prdId) {
		this.prdId = prdId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getDepartCity() {
		return departCity;
	}

	public void setDepartCity(String departCity) {
		this.departCity = departCity;
	}

	public Double getPrdAdultPrice() {
		return prdAdultPrice;
	}

	public void setPrdAdultPrice(Double prdAdultPrice) {
		this.prdAdultPrice = prdAdultPrice;
	}

	public Double getFlightPrice() {
		return flightPrice;
	}

	public void setFlightPrice(Double flightPrice) {
		this.flightPrice = flightPrice;
	}

	public Integer getAdultNum() {
		return adultNum;
	}

	public void setAdultNum(Integer adultNum) {
		this.adultNum = adultNum;
	}

	public Integer getChildNum() {
		return childNum;
	}

	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
	}

	public java.sql.Date getDepartDate() {
		return departDate;
	}

	public void setDepartDate(java.sql.Date departDate) {
		this.departDate = departDate;
	}

	public java.sql.Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(java.sql.Date returnDate) {
		this.returnDate = returnDate;
	}

	public Integer getSalerId() {
		return salerId;
	}

	public void setSalerId(Integer salerId) {
		this.salerId = salerId;
	}

	public String getSalerName() {
		return salerName;
	}

	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}

	public Integer getSalerManagerId() {
		return salerManagerId;
	}

	public void setSalerManagerId(Integer salerManagerId) {
		this.salerManagerId = salerManagerId;
	}

	public String getSalerManagerName() {
		return salerManagerName;
	}

	public void setSalerManagerName(String salerManagerName) {
		this.salerManagerName = salerManagerName;
	}

	public Integer getProducterId() {
		return producterId;
	}

	public void setProducterId(Integer producterId) {
		this.producterId = producterId;
	}

	public String getProducter() {
		return producter;
	}

	public void setProducter(String producter) {
		this.producter = producter;
	}

	public Integer getPrdManagerId() {
		return prdManagerId;
	}

	public void setPrdManagerId(Integer prdManagerId) {
		this.prdManagerId = prdManagerId;
	}

	public String getPrdManager() {
		return prdManager;
	}

	public void setPrdManager(String prdManager) {
		this.prdManager = prdManager;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public List<OrderBillOperator> getOperators() {
		return operators;
	}

	public void setOperators(List<OrderBillOperator> operators) {
		this.operators = operators;
	}

	public List<OrderBillAgency> getAngencies() {
		return angencies;
	}

	public void setAngencies(List<OrderBillAgency> angencies) {
		this.angencies = angencies;
	}

	public String getGuideId() {
		return guideId;
	}

	public void setGuideId(String guideId) {
		this.guideId = guideId;
	}

	public String getGuideName() {
		return guideName;
	}

	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}

	public String getGuideCall() {
		return guideCall;
	}

	public void setGuideCall(String guideCall) {
		this.guideCall = guideCall;
	}
	
}
