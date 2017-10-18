package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 
 * @author chenhaitao
 *
 */
public class OrderEntity  implements Serializable {

	/**
	 * 订单
	 */
	private static final long serialVersionUID = 1L;
	
    private Integer userId ;//	用户Id
    
    private Integer adultNum ;//	出游成人数	
    
    private Integer childrenNum	;//出游儿童数	
    
    private String startCity;//	出发城市	
    
    private String endCity;//	目的地	 
    
    private String route;//	产品名称	 
    
    private Integer routeId;//	产品id	
    
    private Integer salerManagerId;//	客服经理 
    
    private Integer salerId;//	客服	 	
    
    private String salerManagerName;//	客服经理名	 
    
    private String salerName;//	客服名 	 
    
    private String orderType ;//订单类型
    
    private Date startTime;//	出发时间	
    
    private Date backTime	;//归来时间	
    
    private Integer orderId;//	订单id	 	
    
    private Integer airFlag	;//是否包含机票 
    
    private Integer signCityCode	;//签约城市编码
    
    private String signCityName	;//签约城市	
    
    private double  adultPrice;//	产品价格	 成人单价	
	
    private double airfare; //机票价格	 	 	 
	
    private String source;//订单来源
    
    private String orderState;//订单状态
    
	List<ContactDto> contactList ;//联系人信息	 	 	 

	List<AgentDto>	agentList ;//人员信息	 
	
	List<AgencyDto> agencyList;//供应商信息
	
	private int errCode;//异常编码
	
	private String errorMesg ;//异常消息
	
	private Integer clientTypeExpand;//订单标识  620000代表新笛风平台

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrorMesg() {
		return errorMesg;
	}

	public void setErrorMesg(String errorMesg) {
		this.errorMesg = errorMesg;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAdultNum() {
		return adultNum;
	}

	public void setAdultNum(Integer adultNum) {
		this.adultNum = adultNum;
	}

	public Integer getChildrenNum() {
		return childrenNum;
	}

	public void setChildrenNum(Integer childrenNum) {
		this.childrenNum = childrenNum;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}


	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}



	public Integer getSalerManagerId() {
		return salerManagerId;
	}

	public void setSalerManagerId(Integer salerManagerId) {
		this.salerManagerId = salerManagerId;
	}

	public Integer getSalerId() {
		return salerId;
	}

	public void setSalerId(Integer salerId) {
		this.salerId = salerId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getAirFlag() {
		return airFlag;
	}

	public void setAirFlag(Integer airFlag) {
		this.airFlag = airFlag;
	}

	public String getSignCityName() {
		return signCityName;
	}

	public void setSignCityName(String signCityName) {
		this.signCityName = signCityName;
	}


	public double getAdultPrice() {
		return adultPrice;
	}

	public void setAdultPrice(double adultPrice) {
		this.adultPrice = adultPrice;
	}

	public double getAirfare() {
		return airfare;
	}

	public void setAirfare(double airfare) {
		this.airfare = airfare;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public List<ContactDto> getContactList() {
		return contactList;
	}

	public void setContactList(List<ContactDto> contactList) {
		this.contactList = contactList;
	}


	public List<AgentDto> getAgentList() {
		return agentList;
	}

	public void setAgentList(List<AgentDto> agentList) {
		this.agentList = agentList;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getSalerManagerName() {
		return salerManagerName;
	}

	public void setSalerManagerName(String salerManagerName) {
		this.salerManagerName = salerManagerName;
	}

	public String getSalerName() {
		return salerName;
	}

	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}

	public Integer getSignCityCode() {
		return signCityCode;
	}

	public void setSignCityCode(Integer signCityCode) {
		this.signCityCode = signCityCode;
	}

	public List<AgencyDto> getAgencyList() {
		return agencyList;
	}

	public void setAgencyList(List<AgencyDto> agencyList) {
		this.agencyList = agencyList;
	}

	public Integer getClientTypeExpand() {
		return clientTypeExpand;
	}

	public void setClientTypeExpand(Integer clientTypeExpand) {
		this.clientTypeExpand = clientTypeExpand;
	}

}
