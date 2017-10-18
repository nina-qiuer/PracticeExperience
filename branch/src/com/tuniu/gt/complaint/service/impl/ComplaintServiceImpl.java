package com.tuniu.gt.complaint.service.impl;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;
import tuniu.frm.service.Constant;

import com.tuniu.bi.secondarydep.service.ISecondaryDepService;
import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.HttpClientHelper;
import com.tuniu.gt.complaint.dao.impl.ComplaintDao;
import com.tuniu.gt.complaint.dao.impl.ComplaintReasonDao;
import com.tuniu.gt.complaint.dao.impl.QcDao;
import com.tuniu.gt.complaint.entity.AgencyDto;
import com.tuniu.gt.complaint.entity.AgencySetDto;
import com.tuniu.gt.complaint.entity.AgentDto;
import com.tuniu.gt.complaint.entity.AppointManagerEntity;
import com.tuniu.gt.complaint.entity.AttachEntity;
import com.tuniu.gt.complaint.entity.AutoAssignEntity;
import com.tuniu.gt.complaint.entity.ComplaintCrmEntity;
import com.tuniu.gt.complaint.entity.ComplaintEmailInfoEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintFollowNoteEntity;
import com.tuniu.gt.complaint.entity.ComplaintOrderStatusEntity;
import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.complaint.entity.ComplaintSynchCrmEntity;
import com.tuniu.gt.complaint.entity.ContactDto;
import com.tuniu.gt.complaint.entity.FollowNoteThEntity;
import com.tuniu.gt.complaint.entity.FollowUpRecordEntity;
import com.tuniu.gt.complaint.entity.HandlerResult;
import com.tuniu.gt.complaint.entity.OrderEntity;
import com.tuniu.gt.complaint.entity.QcEntity;
import com.tuniu.gt.complaint.enums.DealDepartEnum;
import com.tuniu.gt.complaint.enums.DepartIdDealDepartEnum;
import com.tuniu.gt.complaint.enums.OrderStateEnum;
import com.tuniu.gt.complaint.enums.TourTimeNodeEnum;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.restful.service.PGARestService;
import com.tuniu.gt.complaint.service.IAppointManagerService;
import com.tuniu.gt.complaint.service.IAttachService;
import com.tuniu.gt.complaint.service.IAutoAssignService;
import com.tuniu.gt.complaint.service.ICcEmailService;
import com.tuniu.gt.complaint.service.IComplaintEmailConfigService;
import com.tuniu.gt.complaint.service.IComplaintEmailInfoService;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintOrderStatusService;
import com.tuniu.gt.complaint.service.IComplaintReasonService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.complaint.service.IFollowNoteThService;
import com.tuniu.gt.complaint.service.IFollowUpRecordService;
import com.tuniu.gt.complaint.service.IQcService;
import com.tuniu.gt.complaint.util.ComplaintUtil;
import com.tuniu.gt.complaint.vo.AfterSaleReportVo;
import com.tuniu.gt.complaint.vo.CmpImproveVo;
import com.tuniu.gt.complaint.vo.ComplaintDtAnalyseVo;
import com.tuniu.gt.complaint.vo.ComplaintDtReportVo;
import com.tuniu.gt.complaint.vo.ComplaintReasonVo;
import com.tuniu.gt.complaint.vo.ComplaintVo;
import com.tuniu.gt.complaint.vo.QcVo;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.ApplicationContextUtils;
import com.tuniu.gt.frm.service.system.IEmailService;
import com.tuniu.gt.frm.service.system.IFestivalService;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.JsonUtil;
import com.tuniu.gt.toolkit.MailerThread;
import com.tuniu.gt.toolkit.RTXSenderThread;
import com.tuniu.gt.toolkit.lang.CollectionUtil;
import com.tuniu.gt.uc.service.user.IDepartmentService;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;
import com.tuniu.trest.TRestClient;
import com.tuniu.trest.TRestException;

@Service("complaint_service_complaint_impl-complaint")
public class ComplaintServiceImpl extends ServiceBaseImpl<ComplaintDao> implements IComplaintService {
	
	private static Logger logger = Logger.getLogger(ComplaintServiceImpl.class);
	
	private static String pgaOrderURL = Constant.CONFIG.getProperty("PGA_URL");
	private static String fabCustURL = Constant.CONFIG.getProperty("FAB_CUST_URL");
	
	private static List<String> thirdPartyComplaintSources = new ArrayList<String>();
	
	static {
		thirdPartyComplaintSources.add("网站");
		thirdPartyComplaintSources.add("当地质检");
		thirdPartyComplaintSources.add("旅游局");
		thirdPartyComplaintSources.add("微博");
	}

	@Autowired
	private ApplicationContextUtils applicationContextUtils;
	
	@Autowired
	@Qualifier("complaint_dao_impl-complaint")
	public void setDao(ComplaintDao dao) {
		this.dao = dao;
	}

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_order_status")
	private IComplaintOrderStatusService complaintOrderService;
	
	@Autowired
	@Qualifier("frm_service_system_impl-user")
    private IUserService userService;
	
	@Autowired
	@Qualifier("uc_service_user_impl-department")
	private IDepartmentService departmentService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;
	
	@Autowired
	@Qualifier("complaint_service_impl-follow_note_th")
	private IFollowNoteThService noteThService;
	
	@Autowired
	@Qualifier("bi_service_secondarydep_impl-secondarydep")
	private ISecondaryDepService secondaryDepService;
	
	// 邮件配置service
	@Autowired
	@Qualifier("complaint_service_impl-cc_email")
	private ICcEmailService ccEmailService;
	
	@Autowired
    @Qualifier("complaint_service_complaint_impl-complaint_email_info")
    private IComplaintEmailInfoService complaintEmailInfoService;
	
	// 引入时间处理service
	@Autowired
	@Qualifier("frm_service_system_impl-festival")
	private IFestivalService festivalService;
	
	@Autowired
	@Qualifier("complaint_dao_impl-complaint_reason")
	private ComplaintReasonDao reasonDao;
	
	// qc service
	@Autowired
	@Qualifier("complaint_service_impl-qc")
	private IQcService qcService;
	
	// 上传文件
	@Autowired
	@Qualifier("complaint_service_impl-attach")
	private IAttachService attachService;
		
	@Autowired
	@Qualifier("complaint_dao_impl-qc")
	private QcDao qcDao;
	
	@Autowired
    @Qualifier("tspService")
	private IComplaintTSPService tspService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
    @Qualifier("complaint_service_complaint_reason_impl-complaint_reason")
	private IComplaintReasonService reasonService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-auto_assign")
	private IAutoAssignService autoAssignService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_follow_up_record_impl-follow_up_record")
	private IFollowUpRecordService followUpRecordService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_email_config")
	private IComplaintEmailConfigService emailConfigService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-appoint_manager")
	private IAppointManagerService appointManagerService;
	
	/**
	 * 通过订单ID查询订单信息主方法
	 * @return
	 */
	public ComplaintEntity getOrderInfoMain(String orderId) throws Exception {

		ComplaintEntity entity =new ComplaintEntity();
		try {
			
			//PGA获取订单信息
			OrderEntity orderEntity  = queryNewOrderInfo(orderId);
			if(orderEntity.getErrCode()==1){//查询异常
				
				entity.setErrCode(1);
				entity.setErrorMesg(orderEntity.getErrorMesg());
				return entity;
			}
			if(0!=orderEntity.getUserId()){
				
				String guestName = getGuestNameByCustId(orderEntity.getUserId());
				entity.setGuestName(guestName);
			}
			setOrderDetail(entity,orderEntity);//将PGA订单信息传入投诉单中
			
			if(null!=entity.getOperatorList() && entity.getOperatorList().size()>0){
				
				entity.setOperateName(entity.getOperatorList().get(0).get("operateName").toString());
				entity.setOperateManagerName(entity.getOperatorList().get(0).get("operateManagerName").toString());
			}else{
				
				entity.setOperateName("");
				entity.setOperateManagerName("");
			}
			
			if(Constans.ORDERTYPE_41.equals(entity.getOrderType().trim())){//零售平台订单，请提交至零售系统
				entity.setErrCode(1);
				entity.setErrorMesg("请在投诉质检系统-预定工作台-工单发起与查询中发起工单，项目组选择<保险>!");
				return entity;
			}
			
			if(Constans.ORDERTYPE_20.equals(entity.getOrderType().trim())){//零售平台订单，请提交至零售系统
				entity.setErrCode(1);
				entity.setErrorMesg("请在投诉质检系统-预定工作台-工单发起与查询中发起工单，项目组选择<礼品卡>!");
				return entity;
			}
			
			if("零售平台".equals(entity.getOrderType().trim())){//零售平台订单，请提交至零售系统
				
				entity.setErrCode(1);
				entity.setErrorMesg("零售平台订单，请提交至零售系统!");
				return entity;
			}
			//BOH获取产品信息
			Map<String, Object> productInfo = tspService.getProductInfo((long)entity.getRouteId());
			
			if("1".equals(productInfo.get("errCode").toString())){
				
				entity.setErrCode(1);
				entity.setErrorMesg(productInfo.get("errorMesg").toString());
				return entity;
				
			}
			
			entity.setEndCity(String.valueOf(productInfo.get("productLineDestName")));//目的地
			
			entity.setRouteType(productInfo.get("classBrandParentName").toString());//线路类型名称
			entity.setRouteTypeSp(productInfo.get("classBrandParentName").toString());//一级品类
			if("机票".equals(entity.getRouteType())){
				
				entity.setRouteTypeSp(productInfo.get("subProductTypeName").toString());
			}
			if("酒店".equals(entity.getRouteType())){
				if("国际酒店".equals(productInfo.get("subProductTypeName").toString())){
					entity.setRouteTypeSp("国际酒店");
				}else{
					entity.setRouteTypeSp("国内酒店");
				}
			}
			entity.setRoute(productInfo.get("productName").toString());//线路名称
			entity.setDestCategoryId((Integer)productInfo.get("productLineTypeId"));//目的地大类ID
			
			String brandName =  (String)productInfo.get("brandName");
			entity.setBrandName(brandName);
			if(Constans.NIU_LINE_FLAG.equals(brandName)){ //是否牛人专线
				
				entity.setNiuLineFlag(Constans.IS_NIU_LINE);
				
			}else{
				
				entity.setNiuLineFlag(Constans.NOT_NIU_LINE);
			}
			//获取供应商信息   ???
			setGuide(entity);
			setCustomerPerson(entity); // 设置高级客服经理
			setProductPerson(entity,productInfo); // 设置产品相关人员及事业部
			setOrderState(entity); // 判断并设置订单状态
			entity.setGuestLevelNum(ComplaintRestClient.getCustLevelNum(Integer.valueOf(orderId))); // 设置会员等级数
			entity.setGuestLevel(getGuestLevel(ComplaintRestClient.getCustLevelNum(Integer.valueOf(orderId)))); // 设置会员等级
			
			entity.setClassBrandParentId((Integer)productInfo.get("classBrandParentId"));
			entity.setClassBrandId((Integer)productInfo.get("subProductType"));
			entity.setProductNewLineTypeId((Integer)productInfo.get("productLineTypeId"));
			entity.setDestGroupId((Integer)productInfo.get("destGroupId"));
			logger.info("getOrderInfoBasic End: orderId is " + orderId);
			
		} catch (Exception e) {
			
			entity.setErrCode(1);
			entity.setErrorMesg(e.getMessage());
		}
        
		return entity;
	}
	
	
	/**
     * @param custLevelNum
     * @return
     */
    private String getGuestLevel(Integer custLevelNum) {
        String guestLevel = "";
        switch(custLevelNum){
            case 0:
                guestLevel = "注册会员";
                break;
            case 1:
                guestLevel = "一星会员";
                break;
            case 2:
                guestLevel = "二星会员";
                break;
            case 3:
                guestLevel = "三星会员";
                break;
            case 4:
                guestLevel = "四星会员";
                break;
            case 5:
                guestLevel = "五星会员";
                break;
            case 6:
                guestLevel = "白金会员";
                break;
            case 7:
                guestLevel = "钻石会员";
                break;
           default:
               guestLevel = "未知";
            
        }
        return guestLevel;
    }


    public String getGuestNameByCustId(Integer custId ){
		
		String guestName ="";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("func", "queryById");
		params.put("params",custId);
		String jsonStr = JSONObject.fromObject(params).toString();
		logger.info("queryFabCustInfo Ing: requestMsg is " + jsonStr);
		
        try
        {
        	HttpClientHelper client = new HttpClientHelper();
			String responseMsg =  client.executeGet(fabCustURL, jsonStr);
	        logger.info("queryFabCustInfo end: responseMsg is " + responseMsg);
			JSONObject jsonObject = JSONObject.fromObject(responseMsg);
			if ((Boolean) jsonObject.get("res")) {
				
				if (null != jsonObject.get("cust")) {
					
					JSONObject jsonData = jsonObject.getJSONObject("cust");
					guestName =JsonUtil.getStringValue(jsonData, "real_name");//客人姓名
				}
			}
			
        }
        catch(Exception e)
        {
        	 logger.error(e.getMessage() +"查询Fab会员信息接口失败");
        }
		
		return  guestName;
		
	}
	
	/**
	 * 查询PGA接口
	 * @param orderId
	 * @return
	 */
	public OrderEntity  queryNewOrderInfo(String orderId){
		
		OrderEntity order =new OrderEntity();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		String jsonStr = JSONObject.fromObject(params).toString();
		logger.info("queryPGAOrderInfo Ing: requestMsg is " + jsonStr);
		
        try
        {
        	HttpClientHelper client = new HttpClientHelper();
			String responseMsg =  client.executeGet(pgaOrderURL, jsonStr);
			if("".equals(responseMsg)){
	        	 order.setErrCode(1);
				 order.setErrorMesg("查询PGA接口失败");
		         return order;
			}
	        logger.info("queryPGAOrderInfo end: responseMsg is " + responseMsg);
			JSONObject jsonObject = JSONObject.fromObject(responseMsg);
			if ((Boolean) jsonObject.get("success")) {
				
				if (null != jsonObject.get("data")) {
					
					order = analyticPgaJson(jsonObject);
					order.setErrCode(0);
				}
				
			}else{
				
				order.setErrCode(1);
				String msg = JsonUtil.getStringValue(jsonObject, "msg");
				if("".equals(msg)){
					
					msg ="获取PGA接口失败";
				}
				order.setErrorMesg(msg);
				
			}
			return order;
        }
        catch(Exception e)
        {
        	 logger.error(e.getMessage() +"查询PGA接口失败");
        	 order.setErrCode(1);
			 order.setErrorMesg(e.getMessage());
	         return order;
        }

	}
	

	/**
	 * 解析PGA报文
	 * @param json
	 * @return
	 */
	public   OrderEntity analyticPgaJson(JSONObject json) {
        
		OrderEntity order = new OrderEntity();
		try{
			
			JSONObject jsonData = json.getJSONObject("data");
			order.setUserId(JsonUtil.getIntValue(jsonData, "userId"));//用户Id
			order.setAdultNum(JsonUtil.getIntValue(jsonData, "adultNum"));//出游成人数	
			order.setChildrenNum(JsonUtil.getIntValue(jsonData, "childrenNum"));//出游儿童数
			order.setStartCity(JsonUtil.getStringValue(jsonData, "startCity"));  //出发城市
			order.setEndCity(JsonUtil.getStringValue(jsonData, "endCity"));//目的地
			order.setRoute(JsonUtil.getStringValue(jsonData, "route"));//产品名称
			order.setRouteId(JsonUtil.getIntValue(jsonData, "routeId"));//产品id
			order.setOrderType(JsonUtil.getStringValue(jsonData, "orderType"));//订单类型
			order.setStartTime(JsonUtil.getDateValue(jsonData, "startTime", DateUtil.DATE_PAT));//出发时间
			order.setBackTime(JsonUtil.getDateValue(jsonData, "backTime", DateUtil.DATE_PAT));//归来时间
			order.setOrderId(JsonUtil.getIntValue(jsonData, "orderId"));//订单id
			order.setAirFlag(JsonUtil.getIntValue(jsonData, "airFlag"));//是否包含机票
			order.setAirfare(JsonUtil.getDoubleValue(jsonData, "flightPrice"));//机票价格
			order.setAdultPrice(JsonUtil.getDoubleValue(jsonData, "price"));//成人单价
			order.setSignCityCode(JsonUtil.getIntValue(jsonData, "signCityCode"));//签约城市编码
			order.setSignCityName(JsonUtil.getStringValue(jsonData, "signCityName"));//签约城市
			order.setSource(JsonUtil.getStringValue(jsonData, "source"));//
			
			if(!("").equals(JsonUtil.getStringValue(jsonData, "channelData"))){
				JSONObject channelData = jsonData.getJSONObject("channelData");
				order.setClientTypeExpand(JsonUtil.getIntValue(channelData, "clientTypeExpand"));//是否是新分销订单标识
			}
			
			//联系人信息
			JSONArray temp = jsonData.getJSONArray("contactList");
			List<ContactDto> contactlist =new ArrayList<ContactDto>();
			if(null!=temp && temp.size()>0){
				
				for (int i = 0; i < temp.size(); i++) {
					ContactDto contact =new ContactDto();
					contact.setId(JsonUtil.getIntValue(temp.getJSONObject(i), "id"));
					contact.setOrderId(JsonUtil.getIntValue(temp.getJSONObject(i), "orderId"));
					contact.setContactId(JsonUtil.getIntValue(temp.getJSONObject(i), "contactId"));
					contact.setContactName(JsonUtil.getStringValue(temp.getJSONObject(i), "contactName"));
					contact.setTel(JsonUtil.getStringValue(temp.getJSONObject(i), "tel"));
					contact.setEmail(JsonUtil.getStringValue(temp.getJSONObject(i), "email"));
					contact.setPersonType(JsonUtil.getIntValue(temp.getJSONObject(i), "personType"));
					contactlist.add(contact);
				}
				order.setContactList(contactlist);
			}
			//运营专员信息
			JSONArray tempAgent = jsonData.getJSONArray("agentList");
			List<AgentDto> agentList =new ArrayList<AgentDto>();
			if(null!=tempAgent && tempAgent.size()>0){
				
				for (int i=0; i<tempAgent.size(); i++) {
					
					JSONObject agent = tempAgent.getJSONObject(i);
					Integer agentId = JsonUtil.getIntValue(agent, "agentId");
					String agentName =  JsonUtil.getStringValue(agent, "agentName");  
					if(Constans.AGENT_NULL.equals(agentName)){
						
						agentName="";
					}
					Integer agentMangerId = JsonUtil.getIntValue(agent, "agentManagerId"); 
					String agentManagerName = JsonUtil.getStringValue(agent, "agentManagerName");    
					if(Constans.AGENT_NULL.equals(agentManagerName)){
						
						agentManagerName="";
					}
					Integer	 agentType = JsonUtil.getIntValue(agent, "agentType");  
					AgentDto dto = new AgentDto();
					dto.setOrderId(JsonUtil.getIntValue(jsonData, "orderId"));
					dto.setAgentType(agentType);
					dto.setAgentId(agentId);
					dto.setAgentName(agentName);
					dto.setAgentManagerId(agentMangerId);
					dto.setAgentManagerName(agentManagerName);
					agentList.add(dto);
				}
				order.setAgentList(agentList);
			}
			List<AgencyDto> angencies = new ArrayList<AgencyDto>();
			Set<AgencyDto> set = new HashSet<AgencyDto>();
			JSONArray agencyList = jsonData.getJSONArray("agencyList");
			for (int i=0; i<agencyList.size(); i++) {
				JSONObject agencyObj = agencyList.getJSONObject(i);
				AgencyDto agency = new AgencyDto();
				agency.setOrdId(JsonUtil.getIntValue(agencyObj, "orderId"));
				agency.setAgencyId(JsonUtil.getIntValue(agencyObj, "agencyId") );
				agency.setAgencyName(JsonUtil.getStringValue(agencyObj, "agencyName"));
				set.add(agency);
			}
			if(set.size()>0){
				
			    Set<AgencyDto> setTemp = removeDuplicate(set);//去除重复的供应商
			    for(AgencyDto agency : setTemp) {
		          
			    	angencies.add(agency);
		        }
			    order.setAgencyList(angencies);
			}
			
		} catch (JSONException e) {
			logger.warn("PGA获取订单失败", e);
		}
		return order;
	}
    
	
	 private static Set<AgencyDto> removeDuplicate(Set<AgencyDto> set) {
	     Map<Integer, AgencyDto> map = new HashMap<Integer, AgencyDto>();
	     Set<AgencyDto> tempSet = new HashSet<AgencyDto>();
	     for(AgencyDto p : set) {
	         if(map.get(p.getAgencyId()) == null ) {
	             map.put(p.getAgencyId(), p);                
	         } else {
	             tempSet.add(p);
	         }
	     }
	     set.removeAll(tempSet);
	     return set;
	 }
	/**
	 * 设置产品相关人员信息
	 * @param complaint
	 * @param productInfo
	 */
	@SuppressWarnings("unchecked")
	private void setProductPerson(ComplaintEntity complaint,Map<String, Object> productInfo) {
		
    	logger.info("setProductPerson Bgn: orderId is " + complaint.getOrderId());
    	try {
		
    	if (!productInfo.isEmpty()) {
    		String ownerName = (String) productInfo.get("ownerName");//产品专员
    		String managerName = (String) productInfo.get("managerName");//产品经理
    		//String classBrandParentName = (String) productInfo.get("classBrandParentName");//产品类型
    		int destCategoryId = (Integer) productInfo.get("productLineTypeId");//目的地大类ID
    		complaint.setDestCategoryId(destCategoryId);
    		complaint.setDestCategoryName(Constans.getDestCateName(destCategoryId));
    	    Map<String ,Object> departMap =  (Map<String, Object>) productInfo.get("departmentInfo");
    		String businessUnitName = (String) departMap.get("businessUnitName");//事业部名称
    		int businessUnitId = (Integer) departMap.get("businessUnitId");//事业部ID
    		int productLineId =  (Integer) departMap.get("productLineId");//产品线ID
    		int secondaryDepId  =  (Integer) departMap.get("departmentId");//二级部门ID
    		complaint.setProductLineId(productLineId);
    		complaint.setSecondaryDepId(secondaryDepId);
    		complaint.setBdpName(businessUnitName);//所属事业部
    		complaint.setDepName(businessUnitName); // 事业部
    		if(!Constans.ORDER_TYPE_NAME.equals(complaint.getOrderType())&&!Constans.NEW_TEAM_ORDER_TYPE.equals(complaint.getOrderType())){
    			//团队游、新团队游产品专员产品经理都取PGA数据,其他从BOH取
	    		complaint.setProducter(ownerName); // 产品专员姓名
	    		complaint.setProductLeader(managerName); // 产品经理姓名
    		}
    		
    		
    		if (StringUtil.isNotEmpty(complaint.getProductLeader())) { // 产品经理为空问题屏蔽
    			
    			UserEntity seniorManager = ComplaintRestClient.getReporter(complaint.getProductLeader());
        		UserEntity productManager = ComplaintRestClient.getReporter(seniorManager.getRealName());
        		complaint.setSeniorManager(seniorManager.getRealName()==null?"":seniorManager.getRealName()); // 高级产品经理姓名
        		complaint.setProductManager(productManager.getRealName()==null?"":productManager.getRealName()); // 产品总监
    			
    		}
    	
    		UserEntity depManager = userService.getDeptGeneralManager(businessUnitId);
    		complaint.setDepManager(depManager.getRealName()==null?"":depManager.getRealName()); // 事业部总经理
    	}
    	} catch (Exception e) {

    		logger.error(e.getMessage(), e);
    	}
    	logger.info("setProductPerson End: orderId is " + complaint.getOrderId());
    }

	
	
	/**
	 * 将订单信息传入投诉单中
	 */
	public 	void setOrderDetail(ComplaintEntity entity,OrderEntity orderEntity){
		
		entity.setCustId(orderEntity.getUserId());//会员ID
		entity.setAirfare(orderEntity.getAirfare());
		entity.setAirFlag(orderEntity.getAirFlag());
		entity.setGuestNum(orderEntity.getAdultNum()+"大"+orderEntity.getChildrenNum()+"小");
		entity.setStartCity(orderEntity.getStartCity());
		//entity.setRoute(orderEntity.getRoute());
		entity.setRouteId(orderEntity.getRouteId());//线路ID
//		entity.setCustomer(orderEntity.getSalerName());//客服
//		entity.setCustomerLeader(orderEntity.getSalerManagerName());//客服经理
		entity.setOrderType(orderEntity.getOrderType());
		entity.setStartTime(orderEntity.getStartTime());
		entity.setBackTime(orderEntity.getBackTime());
		entity.setOrderId(orderEntity.getOrderId());
		entity.setProductPrice(orderEntity.getAdultPrice());
		entity.setSignCity(orderEntity.getSignCityName());
		entity.setOrderComeFrom(orderEntity.getSource());//订单来源
		entity.setClientTypeExpand(orderEntity.getClientTypeExpand());//订单标识 620000代表新笛风订单
		
		if(null!=orderEntity.getContactList()&&orderEntity.getContactList().size()>0){//联系人
			
			for(ContactDto dto :orderEntity.getContactList()){
				
				if(dto.getPersonType()==1){
					
					entity.setContactId(dto.getContactId());
					entity.setContactPerson(dto.getContactName());
					entity.setContactPhone(dto.getTel());
					entity.setContactMail(dto.getEmail());
					break;
				}
				
			}
			if(null==entity.getContactPhone()||"".equals(entity.getContactPhone())){
				
				entity.setContactId(orderEntity.getContactList().get(0).getContactId());
				entity.setContactPerson(orderEntity.getContactList().get(0).getContactName());
				entity.setContactPhone(orderEntity.getContactList().get(0).getTel());
				entity.setContactMail(orderEntity.getContactList().get(0).getEmail());
			
			}
		}
		if(null!=orderEntity.getAgencyList()&&orderEntity.getAgencyList().size()>0){//供应商信息
				
			    for(AgencyDto dto : orderEntity.getAgencyList()){
			    	
			    	if(!dto.getAgencyName().contains(Constans.ASSURANCE)){
			    		
			    		entity.setAgencyId(dto.getAgencyId());
						entity.setAgencyName(dto.getAgencyName());
						break;
						
			    	}else{
			    		
			    		entity.setAgencyId(0);
						entity.setAgencyName("");
			    	}
			    	
			    }
				
			
		}
	
		if(null!=orderEntity.getAgentList()&&orderEntity.getAgentList().size()>0){//人员信息
			List<Map<String, Object>> operatorList =new ArrayList<Map<String,Object>>();

			for(int i=0;i<orderEntity.getAgentList().size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				int agentType =orderEntity.getAgentList().get(i).getAgentType();
				if(Constans.ORDER_TYPE_NAME.equals(orderEntity.getOrderType())||Constans.NEW_TEAM_ORDER_TYPE.equals(orderEntity.getOrderType())){
					if(Constans.AGENT_PRODUCT == agentType){//团对游产品专员从PGA取
							
							entity.setProducter(orderEntity.getAgentList().get(i).getAgentName());
							entity.setProductLeader(orderEntity.getAgentList().get(i).getAgentManagerName());
						}
				} 
				if(Constans.AGENT_OPERATE==agentType){ //运营人员
					 
					map.put("operateName", orderEntity.getAgentList().get(i).getAgentName());
					map.put("operateManagerName", orderEntity.getAgentList().get(i).getAgentManagerName());
					operatorList.add(map);
//					entity.setOperateName(orderEntity.getAgentList().get(i).getAgentName());//运营专员
//					entity.setOperateManagerName(orderEntity.getAgentList().get(i).getAgentManagerName());//运营经理
				}
				if(Constans.AGENT_CUSTOMER == agentType){//客服人员
					
					entity.setCustomer(orderEntity.getAgentList().get(i).getAgentName());//客服姓名
					entity.setCustomerLeader(orderEntity.getAgentList().get(i).getAgentManagerName());//客服经理姓名
					entity.setCustomerLeaderId(orderEntity.getAgentList().get(i).getAgentManagerId());//客服经理ID
				}
					
			}
			entity.setOperatorList(operatorList);
		}
	}
	
	
	private void setCustomerPerson(ComplaintEntity complaint) {
		String customerLeader = complaint.getCustomerLeader();
		if (StringUtil.isNotEmpty(customerLeader)) {
			UserEntity custManager = ComplaintRestClient.getReporter(customerLeader);
			complaint.setServiceManager(custManager.getRealName()); // 高级客服经理
		}
	}
	

	/**
	 * 查询导游信息
	 * @param complaint
	 */
	private void setGuide(ComplaintEntity complaint) {
    	logger.info("setGuide Bgn: orderId is " + complaint.getOrderId());
    	Map<String, Object> guideInfo = tspService.getGuideInfo(complaint.getOrderId());
    	if (!guideInfo.isEmpty()) {
    			
    		String guideId = (String) guideInfo.get("guideId");
    		String guideName = (String) guideInfo.get("guideName");
    		String guideCall = (String) guideInfo.get("guideCall");
    		complaint.setGuideId(guideId);
    		complaint.setGuideName(guideName);
    		complaint.setGuideCall(guideCall);
    	}
    	logger.info("setGuide End: orderId is " + complaint.getOrderId());
    }
	/**
	 * 根据出游时间和归来时间判断订单当前状态
	 */
	private void setOrderState(ComplaintEntity entity) {
		Date nowDate = new java.util.Date();
		if (nowDate.before(entity.getStartTime())) {
			entity.setOrderState(OrderStateEnum.BEFORE_TRAVEL.getValue());
		} else if (nowDate.after(DateUtil.getSomeDay(entity.getBackTime(), 1))) {
			entity.setOrderState(OrderStateEnum.AFTER_TRAVEL.getValue());
		} else {
			entity.setOrderState(OrderStateEnum.IN_TRAVEL.getValue());
		}
		
		//订单状态设置后处理逻辑
		if ("机票".equals(entity.getRouteType())||"酒店".equals(entity.getRouteType())) {
            entity.setOrderState(OrderStateEnum.IN_TRAVEL.getValue());
        }
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintEntity> getComplaintsByOrderId(String orderId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderId",orderId);
		return (List<ComplaintEntity>) dao.fetchList(paramMap);
	}
	
    @SuppressWarnings("unchecked")
    @Override
    public List<ComplaintEntity> getComplaintsByPhoneNum(String phoneNum) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("cmpPhone",phoneNum);
        return (List<ComplaintEntity>) dao.fetchList(paramMap);
    }

	/**
	 * 调用网站接口，向网站传送订单状态
	 * @param orderId 订单号
	 * @param state 投诉状态
	 */
	public void sendOrderStatus(int orderId, int state) {
		String status = "投诉处理中";
		//撤销或完成投诉时需判断该订单的所有投诉单是否都是撤销或完成状态
		if (2 == state || 3 == state) {
			status = "投诉已完成";
			List<ComplaintEntity> complaintList = this.getComplaintsByOrderId(orderId + "");
			int complaintCount = complaintList.size();
			for (int i=0; i<complaintCount; i++) {
				if (9 != complaintList.get(i).getState() && 4 != complaintList.get(i).getState()){
					status = "投诉处理中";
					break;
				}
			}
		}
		int error = ComplaintRestClient.sendOrderStatus(orderId, status);
		ComplaintOrderStatusEntity orderEntity = new ComplaintOrderStatusEntity();
		orderEntity.setOrderId(orderId);
		orderEntity.setStatus(status);
		orderEntity.setState(state);
		orderEntity.setFlag(error);
		complaintOrderService.insert(orderEntity);
	}
	
	
	/**
	 * 产生新ngboss的投诉单时，通知ngboss
	 * @param orderId
	 * @throws Exception 
	 */
	public void callbackNgboss(String orderId) {
		
		
		try {
			
			String url = Constant.CONFIG.getProperty("NGBOSS_CALLBACK");// 接口地址
			TRestClient trestClient = new TRestClient(url);
			trestClient.setMethod("get");
			JSONObject jsonParams = new JSONObject();
			jsonParams.put("orderId", Integer.valueOf(orderId));
			logger.info("callbackNgboss start: request is " + jsonParams);
			trestClient.setData(jsonParams.toString());
			String temp = trestClient.execute();
	        logger.info("callbackNgboss end: responseMsg is " + temp);
			if(temp != null && !"".equals(temp)) {
				JSONObject jsonObject = JSONObject.fromObject(temp);
				Boolean isSuccess = jsonObject.getBoolean("success");
				if(isSuccess){
				   System.out.println("回调ngboss成功");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintEntity> getComplaintById(String id){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id",id);
		return (List<ComplaintEntity>) dao.fetchList(paramMap);
	}
	
	@Override
	public List<Integer> getComplaintIdByParameter(Map<String, Object> paramMap) {
		
		return dao.getComplaintIdByParameter(paramMap);
	}
	
	@Override
	public List<Integer> getNotInTimeComplaintId(List<Integer> complaintIds, double hour) {
		
		List<Integer> notInTimeComplaintIds = new ArrayList<Integer>();
		notInTimeComplaintIds.add(0);
		for (Integer id : complaintIds) {
			
			List<ComplaintFollowNoteEntity> followNoteList = complaintFollowNoteService
					.getNoteByComplaintId(id+"");
			
			for (int i = 0; i < followNoteList.size(); i++) {
				
				if (followNoteList.get(i).getContent().startsWith("分配处理人：")) {
					if (i == followNoteList.size()-1) {
						Date now = new Date();
						if (followNoteList.get(i).getAddTime().getTime() + 60*60*hour*1000 < now.getTime()) {
							notInTimeComplaintIds.add(id);
							break;
						}
						
					} else {
						if (followNoteList.get(i).getAddTime().getTime() + 60*60*hour*1000 
								< followNoteList.get(i+1).getAddTime().getTime()) {
							notInTimeComplaintIds.add(id);
							break;
						}
					}
				}
			}
			
		}
		return notInTimeComplaintIds;
	}
	@Override
	public Integer getSameGroupComplaintCount(Integer routeId,
			Date startTime) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("routeId",routeId);
		paramMap.put("startTime",startTime);
		return dao.getSameGroupComplaintCount(paramMap);
	}
	
	@Override
	public List<Map<String, Object>> getComplaintInfoByOrderIds(String orderIds) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderIds",orderIds);
		
		return dao.getComplaintInfoByOrderIds(paramMap);
		
	}
	@Override
	public List<Map<String, String>> getProductLeader(
			Map<String, Object> paramMap) {
		return  dao.getProductLeader(paramMap);
	}
	
	private String getDepNameByRouteId(int routeId){
		String bdpName="";
		if(routeId  <= 80000 && routeId>0 || routeId >= 200000 && routeId<=1000000 || routeId >= 20000000 && routeId<=40000000 ){
			bdpName= "跟团事业部";
		}else if(routeId  > 1000000 && routeId < 2000000 || routeId>80000 && routeId<200000 || routeId>5000000 && routeId  <8000000 ||routeId>40000000 && routeId<50000000 ){
			bdpName= "自助游事业部";
		}else if(routeId  >= 8000000 && routeId <= 9000000){
			bdpName= "团队事业部";
		}
		return bdpName;
	}
	@Override
	public List<Map<String, String>> getInfo4ComplaintScheduleSendTask() {
		return this.dao.getInfo4ComplaintScheduleSendTask();
	}
	
	
	@Override
	public void updateComplaintUpdateTime(int id) {
		this.dao.updateComplaintUpdateTime(id);
	}
	
	@Override
	public List getComplaintEntityListByBuildDateAndState(Map map) {
		return this.dao.getComplaintEntityListByBuildDateAndState(map);
	}
	
	@Override
	public List<Map<String, Object>> getComplaintNumListForHourTable(Map<String, Object> paramMap) {
		return dao.getComplaintNumListForHourTable(paramMap);
	}
	
	@Override
	public Integer getCustIdByOrderId(Integer orderId) {
		Integer custId = 0;
		String url = Constant.CONFIG.getProperty("CUST_INFO_ORDER");
		TRestClient trestClient = new TRestClient(url);
		trestClient.setMethod("post");
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("func", "queryCustIdByOrderId");
		Map routeMp = new HashMap();
		routeMp.put("orderId", orderId.longValue());
		mp.put("params", routeMp);
		trestClient.setData(JSONObject.fromObject(mp).toString());
		try {
			String execute = trestClient.execute();
			JSONObject obj = JSONObject.fromObject(execute);
			if(!obj.containsKey("success")){
				custId = obj.getInt("cust_id");
			}
		} catch (TRestException e) {
			logger.error(e.getMessage(), e);
		}
		return custId;
	}

	@Override
	public void updateCustIdByOrderId(Map map) {
		this.dao.updateCustIdByOrderId(map);
	}
	
	@Override
	public JSONArray getCustTagsByCustIds(Map map) {
		Integer custId = 0;
		String url = Constant.CONFIG.getProperty("CUST_INFO_ORDER");
		TRestClient trestClient = new TRestClient(url);
		trestClient.setMethod("post");
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("func", "queryUserTagBatch");
		mp.put("params", map);
		String str = JSONObject.fromObject(mp).toString();
		logger.info("getCustTagsByCustIds BEGIN:[url:"+url+"][requestParam:"+str+"]");
		trestClient.setData(str);
		JSONArray dataArray = null;
			try {
				String execute = trestClient.execute();
				logger.info("getCustTagsByCustIds IGN:[url:"+url+"][result:"+execute+"]");
				if(execute!=null){
					JSONObject object = JSONObject.fromObject(execute);
					if(object!=null){
						if(object.getBoolean("success")){
							dataArray = JSONArray.fromObject(object.get("data"));
						}
					}
				}
			} catch (TRestException e) {
				logger.error("getCustTagsByCustIds END:[url:"+url+"][errorMsg:"+e.getMessage()+"]", e);
			}
			return dataArray;
	}
	
	public List<Integer> getComplaintIdsByCon(String secondType) {
		return dao.getComplaintIdsByCon(secondType);
	}

	@Override
	public Map<String, Object> getOvertimeUnAssignInfo(String dealDepart) {
		return dao.getOvertimeUnAssignInfo(dealDepart);
	}

	@Override
	public List<ComplaintEntity> getBefThreeNullList() {
		return dao.getBefThreeNullList();
	}

	public void sendLaunchCompEmail(ComplaintEntity entity) {
		String level = CommonUtil.numToBigStr(entity.getLevel());
		String title = getEmailTitle(entity, level);
		String content = getEmailContent(entity, title, level, entity.getReasonList());
		String[] reEmails = getReEmails(entity);
		String[] ccEmails = getCcEmails(entity);
		tspService.sendMail(new MailerThread(reEmails, ccEmails, title, content));
		ComplaintEmailInfoEntity complaintEmailInfoEntity = new ComplaintEmailInfoEntity();
		complaintEmailInfoEntity.setEmailSubject(title);
		complaintEmailInfoEntity.setAddTime(new Date());
		complaintEmailInfoEntity.setComplaintId(entity.getId());
		complaintEmailInfoEntity.setEmailCc(StringUtils.join(ccEmails, ","));
		complaintEmailInfoEntity.setEmailRec(StringUtils.join(reEmails, ","));
		complaintEmailInfoEntity.setEmailSender(entity.getOwnerId());
		complaintEmailInfoEntity.setEmailContent("");
        complaintEmailInfoService.insert(complaintEmailInfoEntity);
	}
	
	/**
	 * 投诉升级发起邮件
	 */
	public void sendEmailForChangeLevl(ComplaintEntity entity,String changeReason,String changeDesc,String userEmail) {
		String level = CommonUtil.numToBigStr(entity.getLevel());
		String title = getEmailTitle(entity, level);
		String content = getEmailContent(entity, title, level, entity.getReasonList());
		content += "<br/>";
		content += "<strong>投诉等级升级为:"+level+"级</strong><br/><br/>";
		content += changeDesc.replaceAll("\n", "<br/>")+"<br/>";
		String [] reEmails = getReEmails(entity);
		if (!"".equals(userEmail)) {
			reEmails = Arrays.copyOf(reEmails, reEmails.length+1);
			reEmails[reEmails.length-1]=userEmail;
		}
		tspService.sendMail(new MailerThread(reEmails, getCcEmails(entity), title, content));
	}
	
	
	/**
	 * 获取邮件主送人员（订单相关、非配置）
	 * @return
	 */
	private String[] getReEmails(ComplaintEntity entity) {
		Integer level = entity.getLevel() == null ? -1 : entity.getLevel();
		String dealDepart = entity.getDealDepart();
		Set<String> reEmails = new LinkedHashSet<String>();
		String key = null;
		List<String> objNames = new ArrayList<String>();
		
		//单品
		if (Constans.HOTEL.equals(dealDepart) 
				|| Constans.AIR_TICKIT.equals(dealDepart) 
				|| Constans.TRAFFIC.equals(dealDepart)) {
			reEmails.addAll(emailConfigService.getLaunchReEmails(entity));
			return reEmails.toArray(new String[reEmails.size()]);
		} else if (entity.getIsMedia() == 1 
				|| thirdPartyComplaintSources.contains(entity.getComeFrom())) {
			key = "receiver.emails.third.party." + dealDepart;
		} else {
			key = "receiver.emails." + level + "." + dealDepart;
		}
		
		objNames.addAll(DBConfigManager.getConfigAsList(key));
		
		//团队订单
		if (Constans.IN_TRAVEL.equals(dealDepart) 
				&& Constans.PRODUCT_TYPE.equals(entity.getRouteType())) {
			String groupKey = "receiver.emails." + dealDepart + ".团队订单";
			objNames.addAll(DBConfigManager.getConfigAsList(groupKey));
		}
		
		try{
			for (String objName : objNames) {
				IEmailService emailService = (IEmailService)applicationContextUtils.getBean(objName);
				reEmails.addAll(emailService.getReceiverEmails(entity));
			}
		}catch (Exception e){
			logger.error("getReceiverEmailsBean failed complaintId:"+entity.getId(),e);
		}
		

		reEmails.addAll(emailConfigService.getLaunchReEmails(entity));
		reEmails.remove("");
		return reEmails.toArray(new String[reEmails.size()]);
	}
	
	/**
	 * 获取邮件抄送人员（订单无关、配置的人员）
	 */
	private String[] getCcEmails(ComplaintEntity entity) {
		Integer level = entity.getLevel() == null ? -1 : entity.getLevel();
		String dealDepart = entity.getDealDepart();
		String key = null;
		Set<String> ccEmails = new LinkedHashSet<String>();
		
		String brandName = entity.getBrandName();
		if(StringUtils.isNotEmpty(brandName) && "牛人专线;牛人实惠;牛人尊享;瓜果亲子游;牛人自组团".contains(brandName)){
		    ccEmails.addAll(Arrays.asList("hongju@tuniu.com;maoyi@tuniu.com;zhangxiaoxia@tuniu.com;linhaiqing@tuniu.com;ligu@tuniu.com".split(";")));
		}
		
		//单品
		if (Constans.HOTEL.equals(dealDepart) 
				|| Constans.AIR_TICKIT.equals(dealDepart) 
				|| Constans.TRAFFIC.equals(dealDepart)) {
			ccEmails.addAll(emailConfigService.getLaunchCcEmails(entity));
			return ccEmails.toArray(new String[ccEmails.size()]);
		}
		//如果投诉包含第三方投诉来源，则转入第三方投诉
		else if ((entity.getIsMedia() == 1 
				|| thirdPartyComplaintSources.contains(entity.getComeFrom()))) {
			key = "cc.emails.third.party." + dealDepart;
		} else {
			key = "cc.emails." + level + "." + dealDepart;
		}
		
		ccEmails.addAll(DBConfigManager.getConfigAsList(key));
		
		if (key.contains("cc.emails.third.party") && (entity.getComeFrom().equals("微博") || entity.getComeFrom().equals("网站"))) {
			String otherKey = "cc.emails.third.party.微博.网站";
			ccEmails.addAll(DBConfigManager.getConfigAsList(otherKey));
		}
		
		//团队订单
		if ((level.intValue() == 2 || level.intValue() == 3)
				&& (Constans.IN_TRAVEL.equals(dealDepart) || Constans.BEFORE_TRAVEL.equals(dealDepart))
				&& (Constans.PRODUCT_TYPE.equals(entity.getRouteType()))) {
			String groupKey = key = "cc.emails." + level + "." + dealDepart + ".团队订单";
			ccEmails.addAll(DBConfigManager.getConfigAsList(groupKey));
		}
		
		ccEmails.addAll(emailConfigService.getLaunchCcEmails(entity));
		ccEmails.remove("");
		return ccEmails.toArray(new String[ccEmails.size()]);
	}
	
	private String getEmailTitle(ComplaintEntity entity, String level) {
		String title = entity.getOrderState() + "投诉[" + level + "级][" + entity.getOrderId();;
		if (StringUtil.isNotEmpty(entity.getGuestLevel())) {
			title += " 会员等级：" + entity.getGuestLevel();
		}
		title += "]";
		title += "[" + entity.getDepName() + "]";
		title += "[" + entity.getDepManager() + "]"; // 事业部总经理
		
		if (StringUtil.isNotEmpty(entity.getProductLeader())) { // 产品经理
			title += "[" + entity.getProductLeader() + "]";
		}
		
		title += "[投诉单号" + entity.getId() + "]";
		
		return title;
	}
	
	private String getEmailContent(ComplaintEntity entity, String title, String level, List<ComplaintReasonEntity> reasonList) {
		String content = "<strong>标题:</strong>" + title + "<br>";
		content += "<strong>订单状态:</strong>" + entity.getOrderState() + "<br>";
		content += "<strong>处理岗:</strong>" + entity.getDealDepart() + "<br>";
		content += "<strong>订单类型:</strong>" + entity.getOrderType() + "<br>";
		content += "<strong>线路类型:</strong>" + entity.getRouteType() + "<br>";
		content += "<strong>订单来源:</strong>" + entity.getOrderComeFrom() + "<br>";
		content += "<strong>投诉来源:</strong>" + entity.getComeFrom() + "<br>";
		content += "<strong>投诉级别:</strong>" + level + "级" + "<br>";
		content += "<strong>订单号:</strong>" + entity.getOrderId() + "<br>";
		content += "<strong>出发地:</strong>" + entity.getStartCity() + "<br>";
		content += "<strong>线路号:</strong>" + entity.getRouteId() + "<br>";
		content += "<strong>线路名称:</strong>" + entity.getRoute() + "<br>";
		content += "<strong>供应商名称:</strong>" + entity.getAgencyName() + "<br>";
		content += "<strong>出发日期:</strong>" + DateUtil.formatDate(entity.getStartTime(), "yyyy-MM-dd") + "<br>";
		content += "<strong>客户姓名:</strong>" + entity.getGuestName() + "<br>";
		content += "<strong>出游人数:</strong>" + entity.getGuestNum() + "<br>";
		content += "<strong>联系人:</strong>" + entity.getContactPerson() + "<br>";
		content += "<strong>联系人手机:</strong>" + entity.getContactPhone() + "<br>";
		content += "<strong>联系人邮箱:</strong>" + entity.getContactMail() + "<br>";
		content += "<strong>售前客服:</strong>" + entity.getCustomer() + "<br>";
		content += "<strong>客服经理:</strong>" + entity.getCustomerLeader() + "<br>";
		content += "<strong>高级客服经理:</strong>" + entity.getServiceManager() + "<br>";
		content += "<strong>产品专员:</strong>" + entity.getProducter() + "<br>";
		content += "<strong>产品经理:</strong>" + entity.getProductLeader() + "<br>";
		content += "<strong>高级产品经理:</strong>" + entity.getSeniorManager() + "<br>";
		content += "<strong>投诉事宜:</strong>" + getReasonInfo(reasonList) + "<br>";
		content += "<strong>投诉说明:</strong>" + entity.getDescript() + "<br>";
		content += "<strong>客户要求:</strong>" + entity.getRequirement() + "<br>";
		content += "<strong>发起人:</strong>" + entity.getOwnerName() + "<br>";
		content += "<strong>投诉时间:</strong>" + DateUtil.formatDate(entity.getBuildDate(), "yyyy-MM-dd HH:mm:ss") + "<br>";
		return content;
	}
	
	private String getReasonInfo(List<ComplaintReasonEntity> reasonList) {
		StringBuffer reasonSb = new StringBuffer();
		int idx = 0;
		for (ComplaintReasonEntity cr : reasonList) {
			reasonSb.append(++idx).append("、").append(cr.getType());
			String secondType = cr.getSecondType();
			if (StringUtil.isNotEmpty(secondType)) {
				reasonSb.append("-").append(secondType);
			}
			reasonSb.append("；");
		}
		reasonSb.append("<br><strong>投诉详情：</strong>").append(reasonList.get(0).getTypeDescript())
			.append("<br><strong>备注：</strong>").append(reasonList.get(0).getDescript());
		return reasonSb.toString();
	}
	
	private void sendNonOrdCompEmail(ComplaintEntity entity) {
		String level = CommonUtil.numToBigStr(entity.getLevel());
		String title = "无订单投诉" + "[" + level + "级][投诉单号" + entity.getId() + "]";;
		String content = getNonOrdEmailContent(entity, title, level, entity.getReasonList());
		tspService.sendMail(new MailerThread(new String[]{"g-cs-bef@tuniu.com"}, null, title, content));
	}
	
	private String getNonOrdEmailContent(ComplaintEntity entity, String title, String level, List<ComplaintReasonEntity> reasonList) {
		String content = "<strong>标题:</strong>" + title + "<br>";
		content += "<strong>投诉来源:</strong>" + entity.getComeFrom() + "<br>";
		content += "<strong>投诉级别:</strong>" + level + "级" + "<br>";
		content += "<strong>联系人:</strong>" + entity.getContactPerson() + "<br>";
		content += "<strong>联系人手机:</strong>" + entity.getContactPhone() + "<br>";
		content += "<strong>联系人邮箱:</strong>" + entity.getContactMail() + "<br>";
		content += "<strong>投诉事宜:</strong>" + getReasonInfo(reasonList) + "<br>";
		content += "<strong>投诉说明:</strong>" + entity.getDescript() + "<br>";
		content += "<strong>客户要求:</strong>" + entity.getRequirement() + "<br>";
		content += "<strong>发起人:</strong>" + entity.getOwnerName() + "<br>";
		content += "<strong>投诉时间:</strong>" + DateUtil.formatDate(entity.getBuildDate(), "yyyy-MM-dd HH:mm:ss") + "<br>";
		return content;
	}

	@Override
	public List<ComplaintEntity> getComplaintList(Map<String, Object> paramMap) {
		return dao.getComplaintList(paramMap);
	}
	
	@Override
	public int getCompareMax(Map<String, Object> paramMap) {
		return dao.compareMax(paramMap);
	}

	@Override
	public void insertComplaint(ComplaintEntity entity) {
		entity.setDelFlag(0);
		entity.setComplaintNum(1);
		entity.setState(1);//默认待处理
		if (entity.getDealBySelf()==1) { // 分配给自己处理
			entity.setDeal(entity.getOwnerId());
			entity.setDealName(entity.getOwnerName());
			entity.setState(2);
			entity.setAssignTime(new Date());
			String tempDealDepart = DepartIdDealDepartEnum.getValue(departmentService.getBussinessIdByUserId(entity.getOwnerId()));
			if(tempDealDepart!=null){
				entity.setDealDepart(tempDealDepart);
			}
			//分配给自己的,组别是呼入组
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", entity.getOwnerId());
			paramMap.put("type", 1);
			paramMap.put("tourTimeNode", 2);
			paramMap.put("delFlag", -1);
			AutoAssignEntity assignEnt = (AutoAssignEntity) autoAssignService.fetchOne(paramMap);
			if (null != assignEnt) {
				entity.setTouringGroupType(assignEnt.getTouringGroupType());
			} else {
				entity.setTouringGroupType(1);
			}
		} else {
			//对于非出游前（ORDER）的单子,不分配给自己的,组别是后处理组
			if(!OrderStateEnum.BEFORE_TRAVEL.getValue().equals(entity.getOrderState())) {
				entity.setTouringGroupType(2);
			}
		}
		
		if (null == entity.getAgencyTel()) {
			entity.setAgencyTel("");
		}
		
		if ("火车票".equals(entity.getRouteType())) {
			entity.setBackTime(entity.getStartTime());
			entity.setOrderState(OrderStateEnum.BEFORE_TRAVEL.getValue());
		}
		
		setDealDepart(entity); // 设置处理岗
		
		if (0 == entity.getTouringGroupType() && DealDepartEnum.IN_TRAVLE.getValue().equals(entity.getDealDepart())) {
			entity.setTouringGroupType(2);
		}
		
		setWorkdayOvertime(entity); // 设置超时时间
		
		dealRepComplaint(entity); // 重复投诉处理（加菱形标记并给出RTX提醒）
		
		if (null != entity.getCompCity() && !"".equals(entity.getCompCity())) {
			entity.setCompTimeTh(new Date());
			entity.setThFinishFlag(0);
		}
		
		if(needTriggerGroupComplaintWarning(entity)){ //是否触发集体投诉前置预警
		    Integer orderId = entity.getOrderId();
		    List<ComplaintReasonEntity> reasonList = entity.getReasonList();
		    String reasonType = "";
		    String description = "";
		    for(ComplaintReasonEntity complaintReasonEntity : reasonList) {
                if(reasonService.isSameGroupComplaintWarningReason(complaintReasonEntity)){
                    reasonType = complaintReasonEntity.getType()+"-"+complaintReasonEntity.getSecondType();
                    description = complaintReasonEntity.getTypeDescript();
                    break;
                }
            }
		    
		    StringBuilder content = new StringBuilder("订单号：").append(orderId).append(" 投诉分类：").append(reasonType).append(",投诉预警，请及时查看ROBOT邮件。");
		    
		  //rtx提醒
		    List<String> warningIds = DBConfigManager.getConfigAsList("business.cmp.samegroup.checkedwarningid");
		    List<String> resEmails = new ArrayList<String>();
		    if(CollectionUtil.isNotEmpty(warningIds)){
		        Map<String, Object> paramMap = new HashMap<String, Object>();
		        for(String userId :warningIds) {
		            paramMap.put("id", userId);
		            UserEntity warningUser = (UserEntity) userService.get(paramMap);
		            if(warningUser!=null){
		                resEmails.add(warningUser.getEmail());
		            }
		            new RTXSenderThread(Integer.valueOf(userId), "", "投诉预警", content.toString()).start();
		        }
		    }
		    
		    //邮件提醒
		    String title = "集体投诉前置预警";
		    StringBuilder emailContent = new StringBuilder("订单号：").append(orderId).append("<br/>");
		    emailContent.append("线路名称：").append(entity.getRoute()).append("<br/>");
		    emailContent.append("供应商名称：").append(entity.getAgencyName()).append("<br/>");
		    emailContent.append("出发日期：").append(DateUtil.formatDate(entity.getStartTime())).append("<br/>");
		    emailContent.append("投诉时间：").append(DateUtil.formatDateTime(entity.getBuildDate())).append("<br/>");
		    emailContent.append("产品专员：").append(entity.getProducter()).append("<br/>");
		    emailContent.append("产品经理：").append(entity.getProductLeader()).append("<br/>");
		    emailContent.append("高级产品经理：").append(entity.getProductManager()).append("<br/>");
		    emailContent.append("投诉分类：").append(reasonType).append("<br/>");
		    emailContent.append("投诉详情：").append(description).append("<br/>"); // 待确认
		    emailContent.append("同团订单：").append(orderService.getSameGroupOrderIds(DateUtil.formatDate(entity.getStartTime()), entity.getRouteId()+"")).append("<br/>"); 
		    
		    if(CollectionUtil.isNotEmpty(resEmails)){
		    	tspService.sendMail(new MailerThread(resEmails.toArray(new String[resEmails.size()]), new String[]{"g-cs-on@tuniu.com"}, title, emailContent.toString()));
		    }
		    
        }
		
		dao.insert(entity);
		
		Integer complaintId = entity.getId();
		
		reasonDao.insertReasonList(complaintId, entity.getReasonList()); // 添加投诉事宜
		
		/* 插入投诉事宜到中间层 */
		if (null != entity.getCompCity() && !"".equals(entity.getCompCity())) {
			String noteContent = entity.getReasonList().get(0).getTypeDescript();
			FollowNoteThEntity noteThEnt = new FollowNoteThEntity();
			noteThEnt.setComplaintId(complaintId);
			noteThEnt.setPersonId(entity.getOwnerId());
			noteThEnt.setPersonName(entity.getOwnerName());
			noteThEnt.setFlowName(Constans.SUBMIT_COMPLAINT_REASON);
			noteThEnt.setContent(noteContent);
			noteThService.insert(noteThEnt);
		}
		
		insertQc(entity); // 添加质检数据
		
		sendLaunchCompEmail(entity); // 发送投诉邮件
		
		sendRtxToCustSaler(entity);//发送rtx信息到客服和交接客服
		
		// 添加投诉发起的有效操作记录
		String noteContent = "投诉单号："
				+ complaintId + ",投诉来源：" + entity.getComeFrom()
				+ ",投诉等级：" + entity.getLevel() + ",其他说明："
				+ entity.getDescript();
		complaintFollowNoteService.addFollowNote(complaintId,
				entity.getOwnerId(), entity.getOwnerName(), noteContent,0,Constans.SUBMIT_COMPLAINT_INFO);
				
		
		if(StringUtil.isEmpty(entity.getDealName())){
			UserEntity dealUser = getComplaintDealPerson(entity);
			if(dealUser!=null){
				assignComplaintAndUpdateAutoAssign(entity,dealUser);
			}
		}else{
			autoAssignRtxAndFollowNote(entity);
		}
		
		if (entity.getOrderId() > 9999999) { // 有投诉单生成需要通知ngboss
			callbackNgboss(entity.getOrderId().toString());
		}
		// 调用前台网站接口，发送投诉订单状态
		sendOrderStatus(entity.getOrderId(), 1);
		
		//一级投诉发质检
		if(Constans.COMPLAINT_LEVEL_FIRST.equals(entity.getLevel())){
			List<Integer> cmpIds = new ArrayList<Integer>();
			cmpIds.add(entity.getId());
			this.sendToQms(cmpIds);
		}
	}
	
	/**
	 * 发送rtx提醒给客服和交接客服
	 * add by xuxuezhi
     * @param entity
     */
     
    private void sendRtxToCustSaler(ComplaintEntity entity)
    {
        String contentFormat = "会员ID:{0}有投诉，投诉单ID:{1}，请关注。";
        String title = "名下会员投诉单提醒";
        String param[] = {entity.getCustId().toString(),entity.getId().toString()};
        Map<String,Object> salerMap = ComplaintRestClient.getCustSalerAndHandSaler(entity.getCustId());
        if(null != salerMap.get("salerId") && 0!=(Integer)salerMap.get("salerId"))//发送客服
        {
            int salerId =  (Integer)salerMap.get("salerId");
            new RTXSenderThread(salerId, (String)salerMap.get("salerName"), title, MessageFormat.format(contentFormat, param)).start();
        }
        if(null != salerMap.get("handsalerId") && 0!=(Integer)salerMap.get("handsalerId"))
        {
            int handsalerId =  (Integer)salerMap.get("handsalerId");//发送交接客服
            new RTXSenderThread(handsalerId, (String)salerMap.get("handsalerName"), title, MessageFormat.format(contentFormat, param)).start();
        }
        
    }


    /**
     * @param entity
     * @return
     */
    private boolean needTriggerGroupComplaintWarning(ComplaintEntity entity) {
        boolean needWarning = false;
        // 1.看本次的投诉事宜分类是否在需要预警的范围
        List<ComplaintReasonEntity> reasonList = entity.getReasonList();
        for(ComplaintReasonEntity reason : reasonList) {
            if(reasonService.isSameGroupComplaintWarningReason(reason)) {
                needWarning = true;
                break;
            }
        }

        // 2.如过是再看同团期是否有触发过，如果有则本次不触发，没有本次触发
        if(needWarning) {
            // 获取同团期历史投诉单
            List<ComplaintVo> sameGroupComplaints = getSameGroupComplaints(entity);
            List<ComplaintReasonVo> reasonVoList;
            if(CollectionUtil.isNotEmpty(sameGroupComplaints)) {
            label:    for(ComplaintVo complaintVo : sameGroupComplaints) {
                    reasonVoList = complaintVo.getReasonList();
                    if(CollectionUtil.isNotEmpty(reasonVoList)){
                        ComplaintReasonEntity reason = new ComplaintReasonEntity();
                        for(ComplaintReasonVo complaintReasonVo : reasonVoList) {
                            reason.setType(complaintReasonVo.getType());
                            reason.setSecondType(complaintReasonVo.getSecondType());
                            if(reasonService.isSameGroupComplaintWarningReason(reason)) {
                                needWarning = false;
                                break label;
                            }
                        }
                    }
                }
            }
        }
        
        return needWarning;
    }


    /**
     * 获取同团期历史投诉单
     * @param entity
     * @return
     */
    private List<ComplaintVo> getSameGroupComplaints(ComplaintEntity entity) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String startDate = DateUtil.formatDate(entity.getStartTime(), "yyyy-MM-dd");
        String routeId = entity.getRouteId().toString();
        paramMap.put("startDate", startDate);
        paramMap.put("routeId", routeId);
        return dao.getSameGroupComplaints(paramMap);
    }


    private void setDealDepart(ComplaintEntity entity) {
        
        String orderState = entity.getOrderState();
        String dealDepart = "";
        //根据会员id获取会员维护人
        UserEntity custSalerUser = null;
        
      	if(StringUtil.isEmpty(entity.getDealDepart())){
            Integer hour = DateUtil.getField(new Date(), Calendar.HOUR_OF_DAY);
            if(hour < 9 || hour >= 18){// 18点到第二天9点分给售后
            	dealDepart = DealDepartEnum.IN_TRAVLE.getValue();
            }else{
            	if(OrderStateEnum.BEFORE_TRAVEL.getValue().equals(orderState)){
            		// 订单状态出游前的9点到18点分给售前
            		dealDepart = DealDepartEnum.BEFORE_TRAVLE.getValue();
                }else{
                	dealDepart = DealDepartEnum.IN_TRAVLE.getValue();
                	/**
                	 * 1.投诉等级为1级
                	 * 2.处理优先级为紧急或者重要
                	 * 3.航变投诉单
                	 * 4.分配给自己
                	 * 以上情况直接到售后 其他情况查找会员维护人
                	 */
                	Boolean searchCustSalerFlag = Constans.COMPLAINT_LEVEL_FIRST.equals(entity.getLevel())||entity.getDealBySelf()==1
                    		||new Integer(1).equals(entity.getPriority())||new Integer(2).equals(entity.getPriority())||new Integer(1).equals(entity.getChgFlightFlag());
                	if(!searchCustSalerFlag){
                		custSalerUser = getCustSalerByComplaint(entity);
                	}
                }
            }
        }else{
            dealDepart = entity.getDealDepart();
        }
		String routeType = entity.getRouteType();
		
		if(Constans.ORDERTYPE_19.equals(entity.getRouteType())||Constans.ROUTE_TYPE_DERIVATIVE.equals(entity.getRouteType())){
    		Integer hour = DateUtil.getField(new Date(), Calendar.HOUR_OF_DAY);
            if(hour >= 9 && hour < 18){// 订单状态出游前的18点到第二天9点分给售后
            	dealDepart = DealDepartEnum.BEFORE_TRAVLE.getValue();
            }
    	}
		
		if("机票".equals(routeType)){
			entity.setBackTime(entity.getStartTime());
			dealDepart = DealDepartEnum.AIR_TICKIT.getValue();
		}
		
		if("酒店".equals(routeType)){
		    dealDepart = DealDepartEnum.HOTEL.getValue();
		}
		
		/*火车票、汽车、用车、欧铁的投诉单归入“交通组”（用车和欧铁暂无订单，需预留）*/
		if(Arrays.asList("火车票,汽车票,用车服务,欧铁".split(",")).contains(routeType)){
		    dealDepart = DealDepartEnum.TRAFFIC.getValue();
        }
		
		boolean newDistributionFlag = (null != entity.getClientTypeExpand() && Constans.PGA_NEW_DISTRIBUTION_ORDER_CODE == entity.getClientTypeExpand());
        if(newDistributionFlag || PGARestService.isDistributeOrder(entity.getOrderId())) {//是否是分销标识   IsDistributeFlag 是否是新分销 || isDistributeOrder是否是笛风订单
            if(!(Constans.HOTEL.equals(dealDepart) || Constans.AIR_TICKIT.equals(dealDepart)
                    || Constans.TRAFFIC.equals(dealDepart) || "门票".equals(entity.getRouteType()))) {
                dealDepart = DealDepartEnum.DISTRIBUTE.getValue();

                // 订单状态出游中的9-18点分给分销
                Integer hour = DateUtil.getField(new Date(), Calendar.HOUR_OF_DAY);
                if((OrderStateEnum.IN_TRAVEL.getValue().equals(entity.getOrderState())||OrderStateEnum.AFTER_TRAVEL.getValue().equals(entity.getOrderState())) && (hour < 9 || hour >= 18)) {
                    dealDepart = Constans.IN_TRAVEL;
                }
            }
        }
        
        //设置为会员顾问处理岗并且设置处理人
        if(custSalerUser!=null&&Constans.IN_TRAVEL.equals(dealDepart)){
        	dealDepart = Constans.VIP_DEPART;
        	entity.setDeal(custSalerUser.getId());
        	entity.setDealName(custSalerUser.getRealName());
        	entity.setState(2);
        	entity.setAssignTime(new Date());
        }

        entity.setDealDepart(dealDepart);
	}
	
	private void setWorkdayOvertime(ComplaintEntity entity) {
		int days = 3;
		if(entity.getDestCategoryName() != null && entity.getDestCategoryName().indexOf("出境") != -1) {
			days = 5;
		}
		entity.setFirstWorkdayOvertime(DateUtil.getSomeDay(new Date(), days));
		entity.setSecondWorkdayOvertime(festivalService.getWorkDaysEndTime(15));
	}
	
	private void insertQc(ComplaintEntity entity) {
		QcEntity qc = new QcEntity();
		qc.setComplaintId(entity.getId());
		qc.setStatus(QcEntity.PENDING_STATE);
		qc.setOrderId(entity.getOrderId());
		qc.setDelFlag(QcEntity.NORMAL_FLAG);
		int isConsultation = ComplaintUtil.judgeConsultation(entity.getReasonList(), entity.getRouteType());
		qc.setConsultation(isConsultation);
//		if (0 == isConsultation) {
//			qcService.assignQcPerson(qc, entity); // 非咨询单则分配质检人
//			
//			/* 非咨询单则发起质检到质量管理系统 */
//			QcVo qcVo = new QcVo();
//			qcVo.setPrdId(entity.getRouteId());
//			qcVo.setGroupDate(entity.getStartTime().getTime());
//			qcVo.setOrdId(entity.getOrderId());
//			qcVo.setCmpId(entity.getId());
//			qcVo.setQcAffairDesc(getQcAffairDesc(entity.getReasonList()));
//			ComplaintRestClient.addQcBill(qcVo);
//		}
		qcDao.insert(qc);
	}
	
	private String getQcAffairDesc(List<ComplaintReasonEntity> reasonList) {
		StringBuffer sb = new StringBuffer();
		for (ComplaintReasonEntity r : reasonList) {
			sb.append(r.getType()).append("-").append(r.getSecondType())
			.append("：").append(r.getTypeDescript()).append("<br>");
		}
		return sb.toString();
	}
	
	private void dealRepComplaint(ComplaintEntity entity) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", entity.getOrderId());
		params.put("notInState", "9");
        List<ComplaintEntity> complaintList = (List<ComplaintEntity>)dao.fetchList(params);
		if (!complaintList.isEmpty()) {
			ComplaintEntity complaintEntity = complaintList.get(0);
			// 判断是否是重复投诉
			int state = complaintEntity.getState();
			if (state == 3 || state == 4) {
				entity.setIsSticky(2);
				entity.setRepeateTime(new Date());
				sendRepCompRTXAlert(complaintEntity);
			}
		}
	}
	
	/**
	 * 发送重复投诉提醒
	 * @param entity
	 */
	private void sendRepCompRTXAlert(ComplaintEntity entity) {
		String title = "重复投诉提醒";
		String content = "订单[" + entity.getOrderId() + "]被重复投诉！~";
		
		if (null != entity.getDeal() && entity.getDeal() > 0) {
			String dealUserName = userService.getUserByID(entity.getDeal()).getUserName();
			new RTXSenderThread(entity.getDeal(), dealUserName, title, content).start(); // 发送给处理人
			
			int deptId = userService.getUserByID(entity.getDeal()).getDepId();
			List<UserEntity> managerList = userService.getManageByDepartmentId(deptId);
			if (!managerList.isEmpty()) {
				for (UserEntity manager : managerList) {
					if (260 != manager.getId()) { // 过滤孟锐丽
						new RTXSenderThread(manager.getId(), manager.getUserName(), title, content).start();
					}
				}
			}
		}
		
		if (null != entity.getAssociater() && entity.getAssociater() > 0) {
			String assUserName = userService.getUserByID(entity.getAssociater()).getUserName();
			new RTXSenderThread(entity.getAssociater(), assUserName, title, content).start(); // 发送给交接人
		}
		
	}

	@Override
	public void insertNonOrdComp(ComplaintEntity entity) {
		dao.insert(entity);
		
		for (ComplaintReasonEntity reason : entity.getReasonList()) { // 添加投诉事宜
			reason.setComplaintId(entity.getId());
			reasonDao.insert(reason);
		}
		
		insertNonOrderQc(entity); // 添加质检数据
		
		sendNonOrdCompEmail(entity); // 发送投诉邮件
		
		// 添加有效操作记录
		String noteContent = "投诉单号：" + entity.getId() 
				+ ",投诉来源：" + entity.getComeFrom()
				+ ",投诉等级：" + entity.getLevel() 
				+ ",其他说明：" + entity.getDescript();
		complaintFollowNoteService.addFollowNote(entity.getId(),
				entity.getOwnerId(), entity.getOwnerName(), noteContent,0,Constans.SUBMIT_COMPLAINT_INFO);
		
		//一级投诉发质检
		if(Constans.COMPLAINT_LEVEL_FIRST.equals(entity.getLevel())){
			List<Integer> cmpIds = new ArrayList<Integer>();
			cmpIds.add(entity.getId());
			this.sendToQms(cmpIds);
		}
	}
	
	private void insertNonOrderQc(ComplaintEntity entity) {
		QcEntity qc = new QcEntity();
		qc.setComplaintId(entity.getId());
		qc.setStatus(QcEntity.PENDING_STATE);
		qc.setOrderId(0);
		qc.setDelFlag(QcEntity.NORMAL_FLAG);
		int isConsultation = ComplaintUtil.judgeConsultation(entity.getReasonList(), entity.getRouteType());
		qc.setConsultation(isConsultation);
//		if(isConsultation==0) {
//		    qcService.assignQcPerson(qc, entity); // 非咨询单则分配质检人
//		    QcVo qcVo = new QcVo();
//		    qcVo.setCmpId(entity.getId());
//		    qcVo.setQcAffairDesc(getQcAffairDesc(entity.getReasonList()));
//		    ComplaintRestClient.addQcBill(qcVo);
//		}
		qcDao.insert(qc);
	}
	
	@Override
	public List<Map<String, Object>> getPayInfoOnOrder(int orderId) {
		return dao.getPayInfoOnOrder(orderId);
	}

	@Override
	public List<Map<String, Object>> getPayInfoOnTel(String tel) {
		return dao.getPayInfoOnTel(tel);
	}

	@Override
	public List<Integer> queryHasCtOrders(Map<String, Object> paramMap) {
		return dao.queryHasCtOrders(paramMap);
	}

	@Override
	public List<Map<String, Object>> getUndoneCompBills() {
		return dao.getUndoneCompBills();
	}

	@Override
	public ComplaintVo getCmpBillInfo(Integer cmpId) {
		ComplaintVo complaintVo = dao.getCmpBillInfo(cmpId); //去除投诉事宜，加limit 1 解决对客和分担 delFlag = 1出现多条脏数据造成的问题
		
		if(complaintVo != null){
			complaintVo.setReasonList(dao.getReasonListByCmpId(cmpId));//查询投诉事宜放入list
		}
		
		return complaintVo;
	}
	
	/**
	 * 第三方投诉
	 */
	public List<Map<String, Object>> getThirdParty(Map<String, Object> paramMap) {
		
		return dao.getThirdParty(paramMap);
	}

	/**
	 * 回呼
	 */
	public List<ComplaintEntity> getCallBack(Map<String, Object> paramMap) {
		
		
		return dao.getCallBack(paramMap);
	}

	@Override
	public List<String> getBdpNames() {
		
		return dao.getBdpNames();
	}
	
	/**
	 * 修改投诉等级
	 */
	public void updateCmpLevel(Map<String, Object> map) {
		
		
		 dao.updateCmpLevel(map);
	}

    @Override
    public Map<String, Map<String, Integer>> getAfterSaleReport(Map<String, Object> paramMap) {
        List<AfterSaleReportVo>   afterSaleReportVoList = dao.getAfterSaleReport(paramMap);
        Map<String,Map<String,Integer>>  reportData = new HashMap<String, Map<String,Integer>>(); 
       String dealName;
       String statisticsField;
       Integer count;
       Map<String,Integer> statisticsMap;
        for(AfterSaleReportVo afterSaleReportVo : afterSaleReportVoList) {
            dealName = afterSaleReportVo.getDealName();
            statisticsField = afterSaleReportVo.getStatisticsField();
            count = afterSaleReportVo.getCount();
            
             statisticsMap = reportData.get(dealName);
             if(statisticsMap == null){
                 statisticsMap = new HashMap<String, Integer>();
             }
             
            statisticsMap.put(statisticsField, count);
            reportData.put(dealName, statisticsMap);
        }
        return reportData;
    }

    public Map<String, Map<String, Integer>> getPostSaleUnDoneComplaintReport() {
        List<AfterSaleReportVo>   afterSaleReportVoList = dao.getPostSaleUnDoneComplaintReport();
        Map<String,Map<String,Integer>>  reportData = new HashMap<String, Map<String,Integer>>(); 
       String dealName;
       String statisticsField;
       Integer count;
       Map<String,Integer> statisticsMap;
        for(AfterSaleReportVo afterSaleReportVo : afterSaleReportVoList) {
            dealName = afterSaleReportVo.getDealName();
            statisticsField = afterSaleReportVo.getStatisticsField();
            count = afterSaleReportVo.getCount();
            
             statisticsMap = reportData.get(dealName);
             if(statisticsMap == null){
                 statisticsMap = new HashMap<String, Integer>();
             }
             
            statisticsMap.put(statisticsField, count);
            reportData.put(dealName, statisticsMap);
        }
        return reportData;
    }
	@Override
	public List<ComplaintVo> getReasonList(Map<String, Object> map) {
		
		
		return dao.getReasonList(map);
	}

	@Override
	public void sendToQms(List<Integer> cmpIds) {
			
		for(Integer cmpId :cmpIds){
			
			try {
				ComplaintEntity compEnt = (ComplaintEntity) dao.fetchOne(cmpId);
				/* 非咨询单则发起质检到质量管理系统 */
				QcVo qcVo = new QcVo();
				qcVo.setPrdId(compEnt.getRouteId());
				if(null!=compEnt.getStartTime()){
					
					qcVo.setGroupDate(compEnt.getStartTime().getTime());
				}
				qcVo.setOrdId(compEnt.getOrderId());
				qcVo.setCmpId(compEnt.getId());
				qcVo.setQcAffairDesc(getQcAffairDesc(compEnt.getReasonList()));
				int qcId = ComplaintRestClient.sendQcBill(qcVo);
				
				if(qcId > 0){
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("complaintId", cmpId);
					QcEntity qc = (QcEntity) qcDao.get(params);
					qc.setConsultation(0);
					qcDao.update(qc);
				}
			} catch (Exception e) {
				logger.error("投诉发质检失败,单号：" + cmpId, e);
			}
			
		}
		
	}


    @Override
    public Map<String, Map<String, Integer>> getPostSaleCompleteCmpReport(Map<String, Object> paramMap) {
        List<AfterSaleReportVo> afterSaleReportVoList = dao.getPostSaleCompleteCmpReport(paramMap);
        Map<String, Map<String, Integer>> reportData = new HashMap<String, Map<String, Integer>>();
        String dealName;
        String statisticsField;
        Integer count;
        Map<String, Integer> statisticsMap;
        for(AfterSaleReportVo afterSaleReportVo : afterSaleReportVoList) {
            dealName = afterSaleReportVo.getDealName();
            statisticsField = afterSaleReportVo.getStatisticsField();
            count = afterSaleReportVo.getCount();

            statisticsMap = reportData.get(dealName);
            if(statisticsMap == null) {
                statisticsMap = new HashMap<String, Integer>();
            }

            statisticsMap.put(statisticsField, count);
            reportData.put(dealName, statisticsMap);
        }
        return reportData;
    }


    @Override
    public List<Integer> getCompleteCmpOrderList(Map<String, Object> paramMap) {
        return dao.getCompleteCmpOrderList(paramMap);
    }


    @Override
    public String getLeastCompalintCountUserName(List<String> userNameList,Map<String, Object> paramMap) {
        String userName = "";
        paramMap.put("assignTimeBgn", DateUtil.formatDate(new Date())+" 00:00:00");
        paramMap.put("assignTimeEnd", DateUtil.formatDate(new Date())+" 23:59:59");
        int count = 0;
        int min = 0;
        for(String dealName : userNameList) {
            paramMap.put("dealName", dealName);
            count = dao.getComplaintCountByDealNameAndAssignTimePeriod(paramMap);
            
            if(count == 0){ //如果符合条件的客服手上没有单子则直接返回该客服
                return dealName;
            }else{
                if(min==0){ //min为初始状态时设为与count相等
                    min = count;
                    userName = dealName;
                }else if(min>count){
                    min = count;
                    userName = dealName;
                }
            }
            
        }
        
        return userName;
    }


    @Override
    public Map<String, Object> getComplaintNumListForHourGraph(Map<String, Object> paramMap) {
        return dao.getComplaintNumListForHourGraph(paramMap);
    }


    @Override
    public List<Map<String, Object>> getComplaintNumListForDayTable(Map<String, Object> paramMap) {
        return dao.getComplaintNumListForDayTable(paramMap);
    }


    @Override
    public JSONArray getComplaintNumListForDayGraph(Map<String, Object> paramMap) {
        List<Map<String, Object>>  dayGraphBaseData = dao.getComplaintNumListForDayGraph(paramMap);
        String assignDateBgn = (String) paramMap.get("assignDateBgn");
        String assignDateEnd = (String) paramMap.get("assignDateEnd");
        
        String tempDate = assignDateBgn;
        JSONArray  jsonArray = new JSONArray();
        for(Map<String, Object> dayGraphBaseMap : dayGraphBaseData) {
            while(DateUtil.compare(tempDate, dayGraphBaseMap.get("date")+"")<0){
                jsonArray.add(makeZeroData(tempDate));
                tempDate = DateUtil.getNextDayStr(tempDate);
            }
            
            JSONObject normalObject = new JSONObject();
            normalObject.put("name",tempDate);
            JSONArray temArray = new JSONArray();
            temArray.add(tempDate);
            temArray.add(dayGraphBaseMap.get("count"));
            normalObject.put("value", temArray);
            jsonArray.add(normalObject);
            
            tempDate = DateUtil.getNextDayStr(tempDate);
        }
        
        
        while(DateUtil.compare(tempDate, assignDateEnd)<1){ //补全剩余没有数据的日期
            jsonArray.add(makeZeroData(tempDate));
            tempDate = DateUtil.getNextDayStr(tempDate);
        }
        
        return jsonArray;
    }
    
    public JSONObject makeZeroData(String date){
        JSONObject zeroObject = new JSONObject();
        zeroObject.put("name", date);
        JSONArray temArray = new JSONArray();
        temArray.add(date);
        temArray.add(0);
        zeroObject.put("value", temArray);
        return zeroObject;
    }


	@Override
	public List<ComplaintEntity> listAll(Map<String, Object> paramMap) {

		return dao.listAll(paramMap);
	}


	@Override
	public int getListCount(Map<String, Object> paramMap) {
		
		return dao.getListCount(paramMap);
	}
  
	/**
     * 获取投诉处理数据报表
     * @param paramMap
     * @return
     */
	@Override
	public Map<String,Map<String,BigDecimal>> getDealPayoutReport(Map<String, Object> paramMap){
		Map<String,Map<String,BigDecimal>>  reportData = new HashMap<String, Map<String,BigDecimal>>(); 
		List<ComplaintDtReportVo> complaintDtReportVosList = dao.getDealPayoutReport(paramMap);
        for(ComplaintDtReportVo complaintDtReportVo : complaintDtReportVosList) {     
        	String real_name=complaintDtReportVo.getReal_name();// 用户姓名
        	BigDecimal myzero=new BigDecimal(0);
        	Map<String,BigDecimal> complaintDtMap=new HashMap<String, BigDecimal>();
        	//分担总额
        	BigDecimal shareTotal = complaintDtReportVo.getShare_total()==null?myzero:complaintDtReportVo.getShare_total();
        	complaintDtMap.put("share_total", shareTotal);
        	complaintDtMap.put("theory_num", complaintDtReportVo.getTheory_num()==null?myzero:complaintDtReportVo.getTheory_num());
        	complaintDtMap.put("payout_num", complaintDtReportVo.getPayout_num()==null?myzero:complaintDtReportVo.getPayout_num());
        	complaintDtMap.put("order_gains", complaintDtReportVo.getOrder_gains()==null?myzero:complaintDtReportVo.getOrder_gains());
        	//理论赔付
        	BigDecimal theoryPayout = complaintDtReportVo.getTheoryPayout()==null?myzero:complaintDtReportVo.getTheoryPayout();
        	complaintDtMap.put("theoryPayout", theoryPayout);
        	//供应商承担
        	BigDecimal supplier_total = complaintDtReportVo.getSupplier_total()==null?myzero:complaintDtReportVo.getSupplier_total();
        	complaintDtMap.put("supplier_total", supplier_total);
        	complaintDtMap.put("company_total", complaintDtReportVo.getCompany_total()==null?myzero:complaintDtReportVo.getCompany_total());
        	complaintDtMap.put("quality_tool_total", complaintDtReportVo.getQuality_tool_total()==null?myzero:complaintDtReportVo.getQuality_tool_total());
        	//实际赔付
        	BigDecimal realPayout = complaintDtReportVo.getRealPayout()==null?myzero:complaintDtReportVo.getRealPayout();
        	complaintDtMap.put("realPayout", realPayout);
        	//质量成本实际使用额=实际赔付-供应商赔付
        	BigDecimal savePayout = realPayout.subtract(supplier_total);
        	complaintDtMap.put("savePayout", savePayout);
        	//实际收益=分担总额-实际赔付
        	BigDecimal realGain = shareTotal.subtract(realPayout);
        	complaintDtMap.put("realGain", realGain);
        	//理论收益=理论赔付总额+供应商承担总额-实际赔付总额
        	BigDecimal theoryGain = theoryPayout.add(supplier_total).subtract(realPayout);
        	complaintDtMap.put("theoryGain", theoryGain);
//        	complaintDtMap.put("above_scale", complaintDtReportVo.getAbove_scale()==null?myzero:complaintDtReportVo.getAbove_scale().multiply(new BigDecimal(100)));
            reportData.put(real_name, complaintDtMap);
        }
		return reportData;
	};
	
	/**
     * 获取投诉处理二级部报表
     * @param paramMap
     * @return
     */
	@Override
	public List<ComplaintDtReportVo> getDealPayoutTotal(Map<String, Object> paramMap){
       return dao.getDealPayoutEcharts(paramMap);
	};
	
	/**
     * 获取投诉处理数据订单详细
     * @param paramMap
     * @return
     */
	@Override
	public List<ComplaintDtReportVo> getDealPayoutDetail(Map<String, Object> paramMap){
		return dao.getDealPayoutDetail(paramMap);
	};
	
	/**
     * 获取投诉处理数据图表数据
     * @param paramMap
     * @return
     */
	@Override
	public Map<String,BigDecimal[]> getDealPayoutEcharts(Map<String, Object> paramMap){
		List<ComplaintDtReportVo> complaintDtReportList=dao.getDealPayoutEcharts(paramMap);
		Map<String,BigDecimal[]> complaintDtMap=new HashMap<String,BigDecimal[]>();
		for (int i = 0; i < complaintDtReportList.size(); i++) {
			ComplaintDtReportVo complaintDtReportVo=complaintDtReportList.get(i);
			BigDecimal[] bigDecimalArr=new BigDecimal[10];
			bigDecimalArr[0]=complaintDtReportVo.getTheoryPayout();
			bigDecimalArr[1]=complaintDtReportVo.getRealPayout();
			bigDecimalArr[2]=complaintDtReportVo.getShare_total();
			bigDecimalArr[3]=complaintDtReportVo.getSupplier_total();
			bigDecimalArr[4]=complaintDtReportVo.getCompany_total();
			bigDecimalArr[5]=complaintDtReportVo.getQuality_tool_total();
			bigDecimalArr[6]=complaintDtReportVo.getOrder_gains();
			bigDecimalArr[7]=complaintDtReportVo.getSavePayout();
			bigDecimalArr[8]=complaintDtReportVo.getRealGain();
			bigDecimalArr[9]=complaintDtReportVo.getTheoryGain();
//			bigDecimalArr[7]=complaintDtReportVo.getSavePayout();
			if(complaintDtReportVo.getDept_second().equals("售后投诉组")){
				complaintDtMap.put("分销客服部",bigDecimalArr);
			}else{
				complaintDtMap.put(complaintDtReportVo.getDept_second(),bigDecimalArr);
			}
			
		}
		return complaintDtMap;
	};
	
	/**
     * 投诉单返回未处理
     * @param paramMap
     * @return
     */
	@Override
	public void returnNotAssigned(Map<String, Object> paramMap){
		Integer id = (Integer) paramMap.get("id");
		String noteContent = (String) paramMap.get("noteContent");
		String flowName=(String) paramMap.get("flowName");
		Integer deal=(Integer) paramMap.get("deal");
		//Integer inProcessState=(Integer) paramMap.get("inProcessState");
		ComplaintEntity complaint = (ComplaintEntity) dao.get(id);
		String dealName=complaint.getDealName();
		//添加投诉单交接有效操作记录
		complaintFollowNoteService.addFollowNote(id, deal, dealName, noteContent,0,flowName);
		//手动分配处理人
		manualAssignComplaint(complaint);
		return;
	}
	
	/**
     * 根据投诉单id返回未处理
     * @param complaintId
     * @return
     */
	@Override
	public Boolean autoReturnNotAssigned(Integer complaintId){
		ComplaintEntity complaint = dao.get(complaintId);
		return autoReturnNotAssigned(complaint);
	}
	
	/**
     * 投诉单返回未处理
     * @param complaintId
     * @return
     */
	@Override
	public Boolean autoReturnNotAssigned(ComplaintEntity complaint){
		Boolean result = false;
		if(judgeAutoReturnState(complaint)){//根据投诉单状态和处理岗判断是否满足
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("id", complaint.getId());
			tempMap.put("noteContent",Constans.ADD_COMPLAINT_REASON );
			tempMap.put("flowName", Constans.OUT_WORK_HANDOVER);
			tempMap.put("dealName", Constans.SYSTEM_ADMINISTRATOR_ACCOUNT);
			tempMap.put("deal", 0);
			tempMap.put("inProcessState", 0);
			returnNotAssigned(tempMap);
			result = true ;
		}
		return result;
	}
	
	/**
	 * 判断是否满足自动返回待处理的
	 * @param complaintState
	 * @param dealDepart
	 * @return
	 */
	private Boolean judgeAutoReturnState(ComplaintEntity complaint){
		Boolean result = false;
		//投诉处理中
		if(complaint.getState() == 2){
			String dealDepart = complaint.getDealDepart();
			List<String> repeatDealDepartLists = DBConfigManager.getConfigAsList("sys.handover.dealDepart");
			//属于可以进行二次分配的处理岗
			if (repeatDealDepartLists.contains(dealDepart)){
				//分单配置中配置且关闭
				result = autoAssignService.assignStateIsNotWork(complaint.getDeal(),dealDepart);
			}
		}
		return result;
	}
	
	/**
     * 投诉单批量返回未处理
     * @param complaintIds
     */
	public void returnNotAssignedByComplaintIds(String[] complaintIds,UserEntity user){
		for (int i = 0; i < complaintIds.length; i++) {
			ComplaintEntity complaint = (ComplaintEntity) dao.get(complaintIds[i]);
			if(complaint!=null&&Constans.IN_TRAVEL.equals(complaint.getDealDepart())){
				Map<String, Object> paramMap = new HashMap<String, Object>();
			    paramMap.put("id", Integer.valueOf(complaintIds[i]));
			    paramMap.put("noteContent", Constans.ASSIGN_NEW_DEALER);
			    paramMap.put("flowName", Constans.WORK_HANDOVER);
			    paramMap.put("dealName", user.getRealName());
			    paramMap.put("deal", user.getId());
			    this.returnNotAssigned(paramMap);
			}
		}
	}
	
	/**
	 * 根据逻辑分配投诉单
	 */
	public void manualAssignComplaint(ComplaintEntity complaint){
		UserEntity dealUser = getComplaintDealPerson(complaint);
		assignComplaintAndUpdateAutoAssign(complaint,dealUser);
	}
	
	/**
	 * 根据投诉单信息获取投诉单处理人
	 * @param complaint
	 * @return
	 */
	private UserEntity getComplaintDealPerson(ComplaintEntity complaint){
		try {
			String dealDepart = complaint.getDealDepart();
			if (Constans.BEFORE_TRAVEL.equals(dealDepart)) {
				return autoAssignPreSale(complaint);
			}
			//会员顾问处理岗
			if (Constans.VIP_DEPART.equals(dealDepart)) {
				return getCustSalerByComplaint(complaint);
			}
			//重复投诉处理岗
			List<String> repeatDealDepartLists = DBConfigManager.getConfigAsList("sys.repeat.dealDepart");
			if (repeatDealDepartLists.contains(dealDepart)){
				return getRepeatComplaintDeal(complaint);
			}
		} catch (Exception e) {
			logger.error("getComplaintDealPerson Error complaintId:"+complaint.getId()+e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 分配处理人并且跟新分配时间
	 * @param complaint
	 * @param user
	 */
	private void assignComplaintAndUpdateAutoAssign(ComplaintEntity complaint,UserEntity user){
		try{
//			complaint.setAssociater(0);
//			complaint.setAssociaterName("");
			complaint.setUpdateTime(new Date());
			//变为投诉单对客未达成
			complaint.setCustAgreeFlag(Constans.NO_CUST_AGREE_FLAG);
			if(user!=null){
				complaint.setState(2);
				complaint.setDeal(user.getId());
				complaint.setDealName(user.getRealName());
				complaint.setAssignTime(new Date());
				autoAssignService.updateLastAssignTimeByUser(user.getId(), complaint.getDealDepart());
				autoAssignRtxAndFollowNote(complaint);
			}else{
				complaint.setState(1);
				complaint.setDeal(0);
				complaint.setDealName("");
			}
			update(complaint);
		}catch(Exception e){
			logger.error("assignComplaintAndUpdateAutoAssign Error complaintId:"+complaint.getId()+e.getMessage(), e);
		}
	}
	
	private void autoAssignRtxAndFollowNote(ComplaintEntity complaint){
		try{
			/* 发送自动分单提醒 */
			String title="自动分单提醒";
			String content="【投诉质检-新分配单】"+"\n投诉单号:"+complaint.getId()+"\n订单号:"+complaint.getOrderId()+"\n";
			new RTXSenderThread(complaint.getDeal(), complaint.getDealName(), title, content).start();
			// 添加有效操作记录
			complaintFollowNoteService.addFollowNote(complaint.getId(), 0, Constans.SYSTEM_ADMINISTRATOR_ACCOUNT, "分配给:" + complaint.getDealName(), 0, Constans.ASSIGN_DEALER);
		}catch(Exception e){
			logger.error("autoAssignRtxAndFollowNote Error complaintId:"+complaint.getId()+e.getMessage(), e);
		}
	}
	
	//重复投诉获取处理人逻辑不获取历史处理人
	private UserEntity getRepeatComplaintDealWithoutGroup(ComplaintEntity complaint){
		return getRepeatComplaintDeal(complaint,false);
	}
	
	//重复投诉获取处理人逻辑
	private UserEntity getRepeatComplaintDeal(ComplaintEntity complaint){
		return getRepeatComplaintDeal(complaint,true);
	}
	
	//重复投诉获取处理人逻辑
	private UserEntity getRepeatComplaintDeal(ComplaintEntity complaint,Boolean assignGroup){
		try{
			logger.info("getRepeatComplaintDeal Begin complaintId:"+complaint.getId());
			String dealDepart = complaint.getDealDepart();
			Map<String, Object> map = new HashMap<String, Object>();
		    map.put("orderId", complaint.getOrderId());
		    map.put("dealDepart", dealDepart);
		    map.put("notInState", "9");
			map.put("histroyDealer", true);
		    @SuppressWarnings("unchecked")
		    //获取订单相应处理岗下的历史投诉
			List<ComplaintEntity> cmpList = (List<ComplaintEntity>) dao.fetchList(map);
		    logger.info("getRepeatComplaintDeal complaintId:"+complaint.getId()+" Size:"+cmpList.size());
		    //获取历史投诉处理人
		    for(ComplaintEntity cmp : cmpList) {
		        Map<String, Object> pm = new HashMap<String, Object>();
		        pm.put("complaintId", cmp.getId());
		        pm.put("flowName", "分配处理人");
		        pm.put("dealDepart", dealDepart);
		        pm.put("orderBy", "desc");
		        @SuppressWarnings("unchecked")
		        //获取历史投诉的分配处理人的记录
				List<ComplaintFollowNoteEntity> noteList = (List<ComplaintFollowNoteEntity>) complaintFollowNoteService.fetchList(pm);
		        for(ComplaintFollowNoteEntity note : noteList) {
		            String[] dealArr = note.getContent().split("：");
		            if(dealArr.length < 2) {
		                dealArr = note.getContent().split(":");
		            }

		            if(dealArr.length == 2) {
		                String dealName = dealArr[1];
		                UserEntity user = userService.getUserByRealName(dealName);
		                if(isStatusAndIsAssign(user,dealDepart)){
		                	return user;
		                }
		                logger.info("getRepeatComplaintDeal complaintId:"+complaint.getId()+" 历史处理人:"+dealName +"不在班");
		            }
		        }
		    }
		    //分同组逻辑
		    if(assignGroup){
		    	//获取最后处理人的同组
			    for(ComplaintEntity cmp : cmpList) {
			        if(cmp.getDeal()!=null&&cmp.getDeal()>0){
			        	Integer dealId=cmp.getDeal();
			        	List<UserEntity> userList = userService.getSameDepUsersWithoutSelfByUserId(dealId);
			        	Collections.shuffle(userList);
			        	for(UserEntity user:userList){
			        		if(isStatusAndIsAssign(user,dealDepart)){
			                	return user;
			                }
			        		logger.info("getRepeatComplaintDeal complaintId:"+complaint.getId()+" 历史处理人同组:"+user.getRealName() +"不在班");
			        	}
			        }
			    }
		    }
		} catch(Exception e){
			logger.error("getRepeatComplaintDeal Error complaintId:"+complaint.getId()+e.getMessage(), e);
		}
	    return null;
	}
	
	/**
	 * 售前处理岗分配处理人
	 * @param complaint
	 * @return
	 */
	private UserEntity autoAssignPreSale(ComplaintEntity complaint){
		try{
			String customer = complaint.getCustomer();// 客服
			String customerLeader = complaint.getCustomerLeader();// 客服经理
			String serviceManager = complaint.getServiceManager();// 高级客服经理
			String names[] = { customer, customerLeader, serviceManager };
			for (int i = 0; i < names.length; i++) {
				if (!"".equals(names[i].trim())) {
					UserEntity preSale = userService.getUserByRealName(names[i]);
					if(preSale!=null){
						return preSale;
					}
				}
			}
		} catch(Exception e){
			logger.error("autoAssignPreSale Error complaintId:"+complaint.getId()+e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 判断坐席在线并且分单打开着
	 * @param user
	 * @param dealDepart
	 * @return
	 */
	private Boolean isStatusAndIsAssign(UserEntity user,String dealDepart){
		Boolean result = false;
		try{
			if(user!=null&&StringUtil.isNotEmpty(dealDepart)){
				Integer userId= user.getId();
		    	if(autoAssignService.assignStateIsWork(userId,dealDepart)){
		    		if(Constans.IN_TRAVEL.equals(dealDepart)){
		    			result = true;
		    		} else{
		    			return com.tuniu.gt.complaint.util.CommonUtil.isStatus(user.getExtension());
		    		}
		    	}
			}
		} catch(Exception e){
			logger.error("isStatusAndIsAssign Error userId:"+user.getId()+e.getMessage(), e);
		}
    	return result;
	}
	
	@Override
	public void insertFollowRecord(Map<String, Object> paramMap){
		Integer id = (Integer) paramMap.get("id");
		String note = (String) paramMap.get("note");
		Integer deal = (Integer) paramMap.get("deal");
		Integer orderId=(Integer) paramMap.get("orderId");
		FollowUpRecordEntity followUpRecordEntity =new FollowUpRecordEntity();
		followUpRecordEntity.setAddTime(new Date());
		followUpRecordEntity.setComplaintId(id);
		followUpRecordEntity.setOrderId(orderId);
		followUpRecordEntity.setNote(note);
		followUpRecordEntity.setAddUser(deal);
		followUpRecordEntity.setDelFlag(0);
		followUpRecordService.insert(followUpRecordEntity);
	}
	
	@Override
	public List<ComplaintDtAnalyseVo> getDealPayoutAnalyseList(Map<String, Object> paramMap){
		return dao.getDealPayoutAnalyseList(paramMap);
	}
	
	@Override
	public int getDtAnalyseCount(Map<String, Object> paramMap){
		return dao.getDtAnalyseCount(paramMap);
	}
	
	@Override
	public List<ComplaintCrmEntity> getComplaintByOrderIdOrCustId(Map<String, Object> paramMap){
		return dao.getComplaintByOrderIdOrCustId(paramMap);
	}
	
	@Override
	public Integer getComplaintByOrderIdOrCustIdCount(Map<String, Object> paramMap){
		return dao.getComplaintByOrderIdOrCustIdCount(paramMap);
	}
	
	@Override
	public Boolean getComplaintCancelState(Map<String, Object> paramMap){
		return dao.getComplaintCancelState(paramMap);
	}
	
	@Override
	public List<ComplaintSynchCrmEntity> getCrmComplaint(Map<String, Object> paramMap){
		return dao.getCrmComplaint(paramMap);
	}
	
	@Override
	public Integer getCrmComplaintCount(Map<String, Object> paramMap){
		return dao.getCrmComplaintCount(paramMap);
	}


	@Override
	public void saveImproveBill(CmpImproveVo cmpImproveVo, HandlerResult result) {
		//查看责任人
		if(!("系统").equals(cmpImproveVo.getImpPerson())){
			UserEntity user = userService.getUserByRealName(cmpImproveVo.getImpPerson());
			if(user == null){
				result.setRetObj("error");
				result.setResMsg("改进报告生成失败，责任人不存在");
				return;
			}
			cmpImproveVo.setImpPersonId(user.getId());
			cmpImproveVo.setImpPerDepId(user.getDepId());
		}
		//填充投诉单相关信息
		fillCmpInfo(cmpImproveVo);
		
		int impId = ComplaintRestClient.addCmpImproveBill(cmpImproveVo);
		
		if(impId > 0){
			result.setRetObj("success");
			//删除列表中的附件
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("complaintId", cmpImproveVo.getCmpId());
			paramMap.put("tableName", "improveBill");
			attachService.deleteImproveAttach(paramMap);
			
			//改进报告发质检
			List<Integer> cmpIds = new ArrayList<Integer>();
			cmpIds.add(cmpImproveVo.getCmpId());
			logger.info("改进报告发质检 start");
			this.sendToQms(cmpIds);
			logger.info("改进报告发质检 end");
		}else{
			result.setRetObj("error");
			result.setResMsg("改进报告生成失败");
		}
	}

	/**
	 * 填充投诉单相关信息
	 * @param cmpImproveVo
	 */
	private void fillCmpInfo(CmpImproveVo cmpImproveVo) {
		//获得投诉单详情中其他信息
		ComplaintVo cmp = this.getCmpBillInfo(cmpImproveVo.getCmpId());
		
		cmpImproveVo.setOrdId(cmp.getOrdId());//订单号
		cmpImproveVo.setPrdId(cmp.getRouteId());//产品号
		cmpImproveVo.setProducter(cmp.getProducter());//产品专员
		cmpImproveVo.setProductLeader(cmp.getProductLeader());//产品经理
		cmpImproveVo.setProductManager(cmp.getProductManager());//产品总监
		cmpImproveVo.setCustomer(cmp.getCustomer());//客服专员
		cmpImproveVo.setCustomerLeader(cmp.getCustomerLeader());//客服经理
		cmpImproveVo.setCustomerManager(cmp.getServiceManager());//客服总监
		cmpImproveVo.setRouteName(cmp.getRouteName());//产品名称
		cmpImproveVo.setAgencyName(cmp.getAgencyName());//供应商名称
		
		//附件信息
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", cmpImproveVo.getCmpId());
		paramMap.put("tableName", "improveBill");
		List<AttachEntity> attachList =  attachService.getQcAttachList(paramMap);
		
		cmpImproveVo.setAttachList(attachList);
	}


	@Override
	public List<Map<String, Object>> getHistoryPayInfo(Map<String, Object> queryConMap) {
		return dao.getHistoryPayInfo(queryConMap);
	}

	/**
	 * 根据投诉单号，查询该投诉单对应订单是否是新分销订单
	 * @param complaintId
	 * @return
	 */
	@Override
	public boolean getDistributeFlag(Integer complaintId) {
		if(complaintId != null && complaintId > 0){
			Integer clientTypeExpand = dao.getClientTypeExpand(complaintId);
			
			if(clientTypeExpand != null && clientTypeExpand == Constans.PGA_NEW_DISTRIBUTION_ORDER_CODE){//700042  新笛风分销标识
				return true;
			}
		}
		
		return false;
	}

	@Override
	public int getNbFlag(int agencyId, Integer complaintId) {
		//查询投诉单，判断是否为新分销，来区分调用的服务
	    boolean isDistributeFlag = this.getDistributeFlag(complaintId);
	    
		return ComplaintRestClient.getNbFlag(agencyId, isDistributeFlag);
	}
	
	@Override
	public void changeComplaintToUnAssigned(Map<String, Object> paramMap){
		Integer id = (Integer) paramMap.get("id");
		String noteContent = (String) paramMap.get("noteContent");
		String flowName=(String) paramMap.get("flowName");
		Integer deal=(Integer) paramMap.get("deal");
		String dealName=(String) paramMap.get("dealName");
		ComplaintEntity complaint = (ComplaintEntity) dao.get(id);
		//添加投诉单交接有效操作记录
		complaintFollowNoteService.addFollowNote(id, deal, dealName, noteContent,0,flowName);
		//投诉单进入待处理
		UserEntity user = getRepeatComplaintDealWithoutGroup(complaint);
		assignComplaintAndUpdateAutoAssign(complaint,user);
	}


	@Override
	public List<Integer> getHandingComplaintIds(Map<String, Object> map) {
		return dao.getHandingComplaintIds(map);
	}
	
	@Override
	public void changeDealDepart(ComplaintEntity complaint,String dealDepart){
		complaint.setDealDepart(dealDepart);
		complaint.setDeal(0);
		complaint.setDealName("");
		complaint.setAssociater(0);
		complaint.setAssociaterName("");
		complaint.setUpdateTime(new Date());
		complaint.setState(1);
		dao.update(complaint);
	}

	@Override
	public String getLeastCountEarliestPerson(Map<String, Object> map) {
		return dao.getLeastCountEarliestPerson(map);
	}
	
	@Override
	public Set<AgencySetDto> getAgencySet(Integer orderId) {
		Set<AgencySetDto> agencyList = new LinkedHashSet<AgencySetDto>();
		JSONObject jsonData = tspService.getComplaintOrderInfoFromPGA(orderId);
		if (null != jsonData && !jsonData.isEmpty()) {
			JSONArray agencyArr = jsonData.getJSONArray("agencyList");
			for (int i=0; i<agencyArr.size(); i++) {
				JSONObject agencyObj = agencyArr.getJSONObject(i);
				AgencySetDto agency = new AgencySetDto();
				agency.setOrdId(agencyObj.getInt("orderId"));
				agency.setAgencyId(agencyObj.getInt("agencyId"));
				agency.setAgencyName(agencyObj.getString("agencyName"));
				agencyList.add(agency);
			}
		}
		return agencyList;
	}
	
	//根据投诉单信息获取会员维护人
    private UserEntity getCustSalerByComplaint(ComplaintEntity complaint){
    	try {
    		String vipAutoAssign = DBConfigManager.getConfig("vip.auto.assign");
    		if(("0").equals(vipAutoAssign)){
    			Integer custSalerId = ComplaintRestClient.getCustSaler(complaint.getCustId());
            	UserEntity custSalerUser = userService.getUserByID(custSalerId);
            	if(com.tuniu.gt.complaint.util.CommonUtil.isStatus(custSalerUser.getExtension())){
            		List<AppointManagerEntity> vipDepartGroupList = appointManagerService.getListByType(AppointManagerEntity.VIP_DEPART_GROUP);
             		List<Integer> vipDepartDepIdList = new ArrayList<Integer>();
             		for(AppointManagerEntity ame : vipDepartGroupList){
             			vipDepartDepIdList.add(ame.getDepartmentId());
             		}
             		if(vipDepartDepIdList.contains(custSalerUser.getDepId())){
             			return custSalerUser;
             		}
             		Map<String,Object> spBeforeMap = new HashMap<String, Object>();
                    spBeforeMap.put("type", AutoAssignEntity.TYPE_COMPLAINT);
                    spBeforeMap.put("tourTimeNode", TourTimeNodeEnum.getValue(complaint.getDealDepart()));
                    List<AutoAssignEntity> currStateList = (List<AutoAssignEntity>) autoAssignService.fetchList(spBeforeMap);
                    for(AutoAssignEntity entity : currStateList){
                        if(custSalerUser.getId().intValue() == entity.getUserId().intValue()){
                            return custSalerUser;
                        }
                    }
            	}
    		}
		} catch (Exception e) {
			logger.error("getCustSalerByComplaint Error complaintId:"+complaint.getId()+e.getMessage(), e);
		}
 		return null;
    }
    
    @Override
	public List<ComplaintEntity> getOvertimeNotCallbackLists(Map<String, Object> paramMap){
		return dao.getOvertimeNotCallbackLists(paramMap);
	}
    
    @Override
	public void upgradeComplaintByIdList(Map<String, Object> paramMap){
    	dao.upgradeComplaintByIdList(paramMap);
	}
}
