package com.tuniu.gt.complaint.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tuniu.gt.common.BaseEntity;
import com.tuniu.gt.complaint.entity.AgengcyPayoutEntity;
import com.tuniu.gt.complaint.entity.CardUniqueEntity;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
import com.tuniu.gt.complaint.entity.AgencyEntity;
import com.tuniu.gt.complaint.vo.SystemVo;
import com.tuniu.gt.toolkit.MailerThread;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public interface IComplaintTSPService {
	
	/**
	 * 查询产品基础信息
	 */
	Map<String, Object> getProductInfo(Long productId);
	
	/**
	 * 通过天气预警关键词查询影响地区，只取第二天的预警信息
	 */
    String[] getWetherAlarmCitys(String alarmCode);
    
    /**
     * 查询订单号或产品ID所属系统
     * @param id
     * @param type：1-产品，2-订单
     * @return
     */
    SystemVo getSystem(long id, int type);
    
    /**
     * 查询跟团线路关联的主从线路id
     * @param routeId
     * @return
     */
    long queryMainRouteId(int routeId);
    
    /**
     * 根据线路id获取所有主从线路id
     */
    List<Integer>  queryAllRelatedRouteIds(int routeId);
    
   /**
    * 查询boss3供应商信息  
    * @param orderId
    * @return
    */
   Map<String, Object> getAgencyInfo(int orderId) ;
   /**
    * 获取导游信息
    * @param orderId
    * @return
    */
   Map<String, Object>  getGuideInfo(int orderId);

	/**
	 * 获取供应商信息
	 * @param valueOf
	 * @return
	 */
	JSONObject getAgencyInfo(String agencyId);
	
	 void syncSubBankInfo(String lastUpdate);
	
	/**
	 * @param paramMap
	 * @return
	 */
	boolean changePrdStatus(Map<String, Object> paramMap);
	
	JSONArray fetchBigBanks();
	
	boolean sendMail(MailerThread mailerThread);

	int checkAgencyInfo(String name, Integer complaint);

	/**
	 * 返回日期的节假日类型
	 * @param startDate
	 * @return
	 */
	Integer getRestdayType(Date startDate);
	
	/**
	 * 添加账户信息
	 * @param complaintSolution
	 * @return
	 */
	String addInCompleteCard(ComplaintSolutionEntity complaintSolution);
	
	/**
	 * 根据账户id查询账户信息
	 * @param cardUniqueId
	 * @return
	 */
	CardUniqueEntity queryCardInfoByCardId(String cardUniqueId);

	/**
	 * 发送短信接口
	 * @param params
	 * @return
	 */
	String sendShortMessageService(JSONObject params);

	/**
	 * 根据订单号获取交接客服
	 * @param params
	 * @return
	 */
	String getHandoverSaler(JSONObject params);
	
	/**
	 * 根据供应商id获取供应商联系人信息
	 * @param agencyId
	 * @param complaint 查询投诉单是否为新分销订单投诉
	 * @return
	 */
	JSONObject getAgencyContactInfo(Integer agencyId, Integer complaint);

	/**
	 * 根据订单号查询订单签约公司
	 * @param orderId
	 * @return
	 */
	Integer queryRefundCompanyId(Integer orderId);
	
	/**
	 * 根据产品一级品类获取一级品类信息
	 * @param productLevel
	 * @param productTypeId
	 * @return
	 */
	List<BaseEntity> getClassBrandParentFromBoh(Integer productTypeId);
	
	/**
	 * 根据产品一级品类获取二品类信息
	 * @param productLevel
	 * @param productTypeId
	 * @return
	 */
	List<BaseEntity> getClassBrandFromBoh(Integer productTypeId);
	
	/**
	 * 根据产品二级品类获取目的地信息
	 * @param productLevel
	 * @param productTypeId
	 * @return
	 */
	List<BaseEntity> getDestClassFormBoh(Integer productTypeId);
	
	/**
	 * 根据产品目的地和二级分组获取目的地分组信息
	 * @param class_brand_id
	 * @param product_new_line_type_id
	 * @return
	 */
	List<BaseEntity> getDestGroupInfoFromBoh(Integer class_brand_id,Integer product_new_line_type_id);

	/**
	 * 根据订单号从pga提供的接口获取订单联系人信息
	 * @param orderId
	 * @return
	 */
	Map<String,String> getContactPhoneMapFromPGA(Integer orderId);
	
	/**
	 * 根据电话号码从crm获取特殊会员信息
	 * @param phoneList
	 * @return
	 */
	JSONArray getSpecialCustListFromCRM(List<String> phoneList);
	
	/**
	 * 根据订单号从pga提供的接口获取订单信息
	 * @param orderId
	 * @return
	 */
	JSONObject getComplaintOrderInfoFromPGA(Integer orderId);
	
	/**
	 * 根据订单号查询路由地址
	 * @param orderId
	 * @return
	 */
	String getOrderDetailPageUrl(Integer orderId);
	
	/**
	 * 供应商申诉
	 * @param agengcyPayoutEntity
	 * @return
	 */
	String launchSupplierAppeal(AgengcyPayoutEntity agengcyPayoutEntity);

	/**
	 * 根据投诉单号和品牌名获取供应商信息
	 * @param name
	 * @param complaint
	 * @return
	 */
	AgencyEntity getAgencyEntity(String name, Integer complaint);
	/**
	 * 查询机票订单运营人员名称
	 * @param orderId
	 * @return
	 */
    List<String> queryTicketOrder(Integer orderId);
}
