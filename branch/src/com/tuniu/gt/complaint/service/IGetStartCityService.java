package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;
public interface IGetStartCityService extends IServiceBase {

	/**
	 * @throws Exception 
	* @Title: getOrderInfo
	* @Description: TODO(根据订单号从接口获取订单相关数据)
	* @param orderId 订单号
	* @return ComplaintEntity    对象
	* @throws
	*/
	public List<Map> getAllStartCityInfo() throws Exception;	
}
