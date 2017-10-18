package com.tuniu.qms.access.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tuniu.qms.access.dto.ResponseDto;
import com.tuniu.qms.common.init.Config;
import com.tuniu.qms.common.model.OrderBill;
import com.tuniu.qms.common.model.OrderBillAgency;
import com.tuniu.qms.common.model.OrderBillOperator;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.JsonUtil;
import com.tuniu.qms.qs.model.OrderBillDetail;

public class PgaClient {
	
	private static String serverURL = Config.get("pga.url");
	
	private final static Logger logger = LoggerFactory.getLogger(PgaClient.class);
	
	/** 查询订单数据 */
	public static OrderBill getOrderInfo(Integer ordId) {
		OrderBill ord = new OrderBill();
		try {
			if (null != ordId && ordId > 0) {
				Map<String, Object> reqParam = new HashMap<String, Object>();
				reqParam.put("orderId", ordId);
				RestClient rc = new RestClient(serverURL + "/nws/order/complaint", "GET", JSON.toJSON(reqParam));
				ResponseDto result = rc.execute2();
				
				if (null!= result && result.isSuccess()) {
					JSONObject data = (JSONObject) result.getData();
					logger.info("queryFromPga return:"+result);
					ord.setId(ordId);
					ord.setCustId(JsonUtil.getIntValue(data, "userId"));
					ord.setPrdId(JsonUtil.getIntValue(data, "routeId"));
					ord.setOrderType(JsonUtil.getStringValue(data, "orderType"));
					ord.setDepartCity(JsonUtil.getStringValue(data, "startCity"));
					ord.setPrdAdultPrice(JsonUtil.getDoubleValue(data, "price"));
					ord.setFlightPrice(JsonUtil.getDoubleValue(data, "flightPrice"));
					ord.setAdultNum(JsonUtil.getIntValue(data, "adultNum"));
					ord.setChildNum(JsonUtil.getIntValue(data, "childrenNum") );
					
					String startTime = JsonUtil.getStringValue(data, "startTime");
					String backTime = JsonUtil.getStringValue(data, "backTime");
					ord.setDepartDate(DateUtil.parseSqlDate(startTime));
					ord.setReturnDate(DateUtil.parseSqlDate(backTime));
					
					List<OrderBillOperator> opers = new ArrayList<OrderBillOperator>();
					JSONArray agentList = data.getJSONArray("agentList");
					if(null!=agentList){
						for (int i=0; i<agentList.size(); i++) {
							JSONObject agent = agentList.getJSONObject(i);
							Integer agentId = JsonUtil.getIntValue(agent, "agentId");
							String agentName =  JsonUtil.getStringValue(agent, "agentName");  
							Integer agentMangerId = JsonUtil.getIntValue(agent, "agentManagerId"); 
							String agentManagerName = JsonUtil.getStringValue(agent, "agentManagerName");    
							
							int agentType = JsonUtil.getIntValue(agent, "agentType");  
							if (Constant.PGA_AGENTTYPE_CUST == agentType) {
								ord.setSalerId(agentId);
								ord.setSalerName(agentName);
								ord.setSalerManagerId(agentMangerId);
								ord.setSalerManagerName(agentManagerName);
							}
							if (Constant.PGA_AGENTTYPE_OPER == agentType) {
								OrderBillOperator oper = new OrderBillOperator();
								oper.setOrdId(ordId);
								oper.setOperId(agentId);
								oper.setOperName(agentName);
								oper.setManagerId(agentMangerId);
								oper.setManagerName(agentManagerName);
								opers.add(oper);
							}
							if (Constant.PGA_AGENTTYPE_PRD == agentType) {
								ord.setProducterId(agentId);
								ord.setProducter(agentName);
								ord.setPrdManagerId(agentMangerId);
								ord.setPrdManager(agentManagerName);
							}
						}
						ord.setOperators(opers);
					}
					List<OrderBillAgency> angencies = new ArrayList<OrderBillAgency>();
					Set<OrderBillAgency> set = new HashSet<OrderBillAgency>();
					JSONArray agencyList = data.getJSONArray("agencyList");
					if(null!=agencyList){
						for (int i=0; i<agencyList.size(); i++) {
							JSONObject agencyObj = agencyList.getJSONObject(i);
							OrderBillAgency agency = new OrderBillAgency();
							agency.setOrdId(ordId);
							agency.setAgencyId(JsonUtil.getIntValue(agencyObj, "agencyId") );
							agency.setAgencyName(JsonUtil.getStringValue(agencyObj, "agencyName"));
							set.add(agency);
						}
						if(set.size()>0){
							
						    Set<OrderBillAgency> setTemp = removeDuplicate(set);//去除重复的供应商
						    for(OrderBillAgency agency : setTemp) {
					          
						    	angencies.add(agency);
					        }
							ord.setAngencies(angencies);
						}
					}
				}
			}
			//查询会员星级
			if(null!=ord.getCustId() && ord.getCustId()!=0){
				String custLevel = CrmClient.getCustLevel(ordId);
				ord.setCustLevel(custLevel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ord;
	}
	
	 private static Set<OrderBillAgency> removeDuplicate(Set<OrderBillAgency> set) {
	     Map<Integer, OrderBillAgency> map = new HashMap<Integer, OrderBillAgency>();
	     Set<OrderBillAgency> tempSet = new HashSet<OrderBillAgency>();
	     for(OrderBillAgency p : set) {
	         if(map.get(p.getAgencyId()) == null ) {
	             map.put(p.getAgencyId(), p);                
	         } else {
	             tempSet.add(p);
	         }
	     }
	     set.removeAll(tempSet);
	     return set;
	 }
	 
	/** 查询同团期订单数据 */
	public static Map<String, Object> querySameGroupOrders(Map<String, Object> map ) {
	
		Map<String, Object> resultMap =new HashMap<String, Object>();
		try {
			    
				RestClient rc = new RestClient(serverURL+"/nws/order/queryOrdersByPrd", "GET", JSON.toJSON(map));
				logger.info("queryOrdersByPrd send:"+JSON.toJSON(map));
				ResponseDto result = rc.execute2();
				if (null!=result && result.isSuccess()) {
					
					JSONObject data = (JSONObject) result.getData();
					logger.info("queryOrdersByPrd return:"+ data);
					int pageCount = JsonUtil.getIntValue(data, "pageCount");//总页数
					int totalCount = JsonUtil.getIntValue(data, "totalCount");//总记录数
					JSONArray orderList = data.getJSONArray("orderList");
					List<Integer> ordList = new ArrayList<Integer>();
					for (int i=0; i<orderList.size(); i++) {
						JSONObject agent = orderList.getJSONObject(i);
						Integer orderId = JsonUtil.getIntValue(agent, "orderId"); 
						ordList.add(orderId);
					}
					resultMap.put("pageCount", pageCount);
					resultMap.put("totalCount", totalCount);
					resultMap.put("ordList", ordList);
					
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return resultMap;
	}
	
	
	/** 查询供应商详细信息 
	public static Map<String, Object> queryAgencyDetail(Integer agencyId ) {
	
		Map<String, Object> resultMap =new HashMap<String, Object>();
		try {
			
				RestClient rc = new RestClient(supplierURL+"/"+agencyId+"/"+0, "GET",null);
				ResponseDto result = rc.execute2();
				if (null!=result && result.isSuccess()) {
					
					JSONObject data = (JSONObject) result.getData();
					logger.info("queryAgencyDetail return:"+ data);
					String signCompany = JsonUtil.getStringValue(data, "fullName");//签约公司
					String agencyOwner = JsonUtil.getStringValue(data, "owner");//供应商owner
					String agencyAccessTime = JsonUtil.getStringValue(data, "addDate");//供应商准入时间
					resultMap.put("signCompany", signCompany);
					resultMap.put("agencyOwner", agencyOwner);
					resultMap.put("agencyAccessTime", agencyAccessTime);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return resultMap;
	}
	*/
	
	/** 查询每日签约日期的订单数 */
	public static List<Integer> queryOrdersBySignDate(Map<String, Object> map ) {
	
	    List<Integer> list = new ArrayList<Integer>();
		try {
			  
				RestClient rc = new RestClient(serverURL+"/pga/manage/order/querySyncStatus", "GET", JSON.toJSON(map));
				logger.info("PGA url:"+serverURL);
				logger.info("queryOrdersBySignDate send:"+JSON.toJSON(map));
				ResponseDto result = rc.execute2();
				if (null!=result && result.isSuccess()) {
					
					JSONArray orderList = (JSONArray) result.getData();
					logger.info("queryOrdersBySignDate return:"+ orderList);
					if(null!=orderList){
						for (int i=0; i<orderList.size(); i++) {
							JSONObject agent = orderList.getJSONObject(i);
							Integer orderId = JsonUtil.getIntValue(agent, "orderId"); 
							list.add(orderId);
						}
					}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error("queryOrdersBySignDate error",e.getMessage());
		}
		return list;
	}
	
	/** 查询订单明细 */
	public static List<OrderBillDetail> queryOrdersByIds(List<Integer> orderIds) {
	
	    List<OrderBillDetail> list = new ArrayList<OrderBillDetail>();
		try {
			    Map<String, Object> map =new HashMap<String, Object>();
			    map.put("orderIds", orderIds);
				RestClient rc = new RestClient(serverURL+"/nws/order/queryOrdersByIdsForScrm", "GET", JSON.toJSON(map));
				logger.info("queryOrdersByIds send:"+JSON.toJSON(map));
				ResponseDto result = rc.execute2();
				if (null!=result && result.isSuccess()) {
					
					JSONArray orderList = (JSONArray) result.getData();
					logger.info("queryOrdersByIds return:"+ orderList);
					if(null!=orderList){
						for (int i=0; i<orderList.size(); i++) {
							OrderBillDetail bill =new OrderBillDetail();
							JSONObject order = orderList.getJSONObject(i);
							Integer orderId = JsonUtil.getIntValue(order, "orderId"); 
							Integer orderType = JsonUtil.getIntValue(order, "orderType");
							Integer isCancel = JsonUtil.getIntValue(order, "isCancel");
							String beginTime = JsonUtil.getStringValue(order, "beginTime");
							String addTime  = JsonUtil.getStringValue(order, "addTime");
							String signDate  = JsonUtil.getStringValue(order, "signDate");
							bill.setOrderId(orderId);
							bill.setOrderType(orderType);
							bill.setBeginTime(beginTime);
							bill.setIsCancel(isCancel);
							bill.setAddTime(addTime);
							bill.setSignDate(signDate);
							JSONArray agentList = order.getJSONArray("agents");
							if(null!=agentList){
								for (int m=0; m<agentList.size(); m++) {
									
									JSONObject agent = agentList.getJSONObject(m);
									String agentName =  JsonUtil.getStringValue(agent, "agentName");  
									String agentManagerName = JsonUtil.getStringValue(agent, "agentManagerName");    
									int agentType = JsonUtil.getIntValue(agent, "agentType");  
									if (Constant.PGA_AGENTTYPE_CUST == agentType) {
										bill.setSalerName(agentName);
										bill.setSalerManagerName(agentManagerName);
										break;
									}
								}
							}
							JSONArray tourList = order.getJSONArray("contactList");
							if(null != tourList){
								
								String callPersonPhone = "";// 联系人手机号     有默认联系人取自默认联系人，没有取自第一联系人
								StringBuilder allPersonPhones = new StringBuilder("");//所有联系人汇总
								int size = tourList.size();
								for (int k=0; k < size; k++) {
									JSONObject tour = tourList.getJSONObject(k);
									
									int isDefault =  JsonUtil.getIntValue(tour, "isDefault");//是否是默认联系人   0 ：否  1：是
									int personType = JsonUtil.getIntValue(tour, "personType");//人员标识   1：联系人   2：出游人    
									String mobPhone = JsonUtil.getStringValue(tour, "mobPhone");//手机号  
									
									if (isDefault == 1 && !("").equals(mobPhone)) {//是否是默认联系人
										
										callPersonPhone = mobPhone;
										allPersonPhones.append(mobPhone).append(",");
									}else if(personType == 1 && !("").equals(mobPhone)){//不是默认联系人,是否是联系人
										
										if(("").equals(callPersonPhone)){//默认联系人外的第一联系人
											callPersonPhone = mobPhone;
										}
										allPersonPhones.append(mobPhone).append(",");
									}
								}
								
								if(!("").equals(callPersonPhone)){
									bill.setMobPhone(callPersonPhone);
								}
								
								if(!("").equals(allPersonPhones.toString())){
									bill.setAllPersonPhone(allPersonPhones.substring(0, allPersonPhones.length()-1));
								}
							}
							
							JSONObject product = order.getJSONObject("orderProduct");
							if(null!=product){
								
								String prdName = JsonUtil.getStringValue(product, "productName");  
								bill.setPrdName(prdName);
							}
							list.add(bill);
						}
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error("queryOrdersByIds error",e.getMessage());
		}
		return list;
	}
	
	
	
	
	public static void main(String[] args) {
		System.out.println(JSON.toJSONString(getOrderInfo(3961668)));
	}

}
