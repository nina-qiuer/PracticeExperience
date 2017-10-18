package com.tuniu.qms.qs.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.bi.dao.PrdWeekSatisfyMapper;
import com.tuniu.qms.access.client.CrmClient;
import com.tuniu.qms.access.client.PgaClient;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.TspService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.CollectionUtil;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qs.dao.PreSaleReturnVisitMapper;
import com.tuniu.qms.qs.model.MessageParamEntity;
import com.tuniu.qms.qs.model.MessageTemplateParamValuePair;
import com.tuniu.qms.qs.model.OrderBillDetail;
import com.tuniu.qms.qs.model.PreSaleReturnVisit;
import com.tuniu.qms.qs.service.PunishPrdService;

/**
 * 低满意度下线 获取数据
 * @author chenhaitao
 *
 */
public class ScanOfflineRouteTask {
	
	private final static Logger logger = LoggerFactory.getLogger(ScanOfflineRouteTask.class);
 
	@Autowired
	private PrdWeekSatisfyMapper mapper;
	@Autowired
	private PunishPrdService service;
	@Autowired
	private PreSaleReturnVisitMapper rvMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private DepartmentService depService;
	@Autowired
	private TspService tspService;
	
	public void lowSatyRouteRule(){
		
		/*
		 * 获取符合低满意度规则的线路列表
		 * 规则：
		 *  一路之上：< 95%
		 *	朋派定制游：< 92%
		 *	牛人专线、 乐开花爸妈游、 出发吧我们、 瓜果亲子游：<90%
		 *	实惠游：< 85%
		 *	其他品牌 ： < 75%
		 */
		logger.info("触发低满意度规则");
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("curYear", DateUtil.getYear(new Date()));
		paramMap.put("curWeek", DateUtil.getWeek(new Date()));
		
		List<Long> routeIds = mapper.listLowRouteIds(paramMap);
		logger.info("触发低满意度规则,扫描线路数为："+routeIds.size()+",触发日期："+DateUtil.formatAsDefaultDate(new Date()));
		logger.info("触发低满意度规则,扫描线路数："+routeIds);
		if( CollectionUtil.isNotEmpty(routeIds) ) {
			
			service.lowSatisfactionDeal(routeIds);
		}
	}
	
	/*
	 *	各自单个自然周周平均满意度 ＜ XX%，系统发送提醒邮件
	 *	一路之上：< 95%
	 *	朋派定制游：< 92%
	 *	牛人专线、 乐开花爸妈游、 出发吧我们、 瓜果亲子游：<90%
	 *	实惠游：< 85%
	 *	其他品牌 ： < 75%
	 */
	public void lowSatyRouteEmail(){
		
		try{
			logger.info("低满意度产品增加邮件提醒功能");
			
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("curYear", DateUtil.getYear(new Date()));
			paramMap.put("curWeek", DateUtil.getWeek(new Date()));
			
			List<Long> routeIds = mapper.listLastWeekLowRoute(paramMap);
			
			if( CollectionUtil.isNotEmpty(routeIds) ) {
				
				service.lowSatisSendEmail(routeIds);
			}
		}catch(Exception e){
			logger.error("邮件提醒失败", e.getMessage(), e);
		}
		
	}
	
	
	/*
	 *	获取每日签约完成订单
	 */
	public void signOrder(){
		
		logger.info("获取前一日签约完成订单");
		List<Integer> ordList = new ArrayList<Integer>();
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("startSignDate", DateUtil.getBeforeDay()+" 00:00:00");
		map.put("endSignDate", DateUtil.getTodayTime()+" 00:00:00");
		map.put("isSign", 1);
		List<Integer> list =new ArrayList<Integer>();
		list.add(1);//跟团游
		list.add(2);//自助游
		list.add(5);//团队游
		list.add(8);//自驾游
		list.add(10);//签证
		list.add(12);//邮轮
		map.put("productClassIds", list);
        
		for(int i=0; i<24; i+=2){
        	int  stime = i;
        	int  etime = i+2;
        	String startTime ="";
        	String endTime ="";
        	if(stime<10){
				
        		startTime="0"+String.valueOf(stime);
        	}else{
        		
        		startTime=String.valueOf(stime);
        	}
        	if(etime<10){
				
        		endTime="0"+String.valueOf(etime);
        	}else{
        		
        		endTime = String.valueOf(etime);
        	}
        	map.put("startTime", DateUtil.getBeforeDay()+" "+startTime+":00:00");
    		map.put("endTime", DateUtil.getBeforeDay()+" "+endTime+":00:00");
    		List<Integer> orderList = PgaClient.queryOrdersBySignDate(map);
    		ordList.addAll(orderList);
        }		
	
		if(ordList.size()>0){
			
			 getOrderDetail(ordList);
			
		}
		
	}
	
	
	private void getOrderDetail(List<Integer> list){
		
		int pageSize = 20;
		int dataLimitStart = 0;
		while (true) {
			
			List<Integer> ordList = getSubListPage(list, dataLimitStart, pageSize);
			if(null != ordList && ordList.size() > 0){
				
				List<PreSaleReturnVisit> rvList =new ArrayList<PreSaleReturnVisit>();
				List<OrderBillDetail> detailList = PgaClient.queryOrdersByIds(ordList);
				
				for(OrderBillDetail bill : detailList){
					
					if(null != bill.getMobPhone() && null != bill.getSalerName() && !"".equals(bill.getSalerName())){
						
						PreSaleReturnVisit visit = getVisitDetail(bill);
						rvList.add(visit);
					}
				}
				if (!rvList.isEmpty()) {
					try {
						
						rvMapper.addBatch(rvList);
					} catch (Exception e) {
						
						e.printStackTrace();
						logger.error("插入售前短信表失败",e.getMessage());
					}
				}
			}else{
				break;
			}
			
			dataLimitStart += pageSize;
		}
		
	}
	
	private PreSaleReturnVisit getVisitDetail(OrderBillDetail bill){
		
		PreSaleReturnVisit visit = new PreSaleReturnVisit();
		visit.setCustomer(bill.getSalerName());//客服专员
		visit.setCustomerLeader(bill.getSalerManagerName());//客服经理
		visit.setOrdId(bill.getOrderId());//订单号
		visit.setTel(bill.getMobPhone());//电话
		visit.setAllPersonPhone(bill.getAllPersonPhone());//所有联系人手机号
		visit.setBeginDate(DateUtil.parseDateTime(bill.getBeginTime()));//出游日期
		visit.setSignDate(DateUtil.parseDateTime(bill.getSignDate())); //签约日期
		visit.setPrdName(bill.getPrdName());//产品名称
		visit.setOrderTime(DateUtil.parseDateTime(bill.getAddTime()));//下单时间
		visit.setIsCancel(bill.getIsCancel());//取消标识
		//获取客服专员的事业部 组织架构
		if(!"".equals(visit.getCustomer())){
			
			User user = userService.getUserByRealName(visit.getCustomer());
			if(null!=user){
				
				visit.setExtension(user.getExtension()==null?"":user.getExtension());
				String depName =depService.getDepFullNameById(user.getDepId());
				String depNames[] = depName.split("-");
				if(depNames.length==4){
					
					visit.setBusinessUnitName(depNames[1]);
					visit.setDepartmentName(depNames[2]);
					visit.setGroupName(depNames[3]);
				}
				if(depNames.length==3){
					
					visit.setBusinessUnitName(depNames[0]);
					visit.setDepartmentName(depNames[1]);
					visit.setGroupName(depNames[2]);
				}
				if(depNames.length==2){
									
					visit.setBusinessUnitName(depNames[0]);
					visit.setDepartmentName(depNames[1]);
					visit.setGroupName("");
				}
				if(depNames.length==1){
					
					visit.setBusinessUnitName(depNames[0]);
					visit.setDepartmentName("");
					visit.setGroupName("");
				}
			}else{
				
				visit.setBusinessUnitName("");
				visit.setDepartmentName("");
				visit.setGroupName("");
				visit.setExtension("");
			}
			
		}else{
			
			visit.setBusinessUnitName("");
			visit.setDepartmentName("");
			visit.setGroupName("");
			visit.setExtension("");
		}
		//计算回访日期
		int day = DateUtil.getDaysBetween(visit.getSignDate(), visit.getBeginDate());//出游日期 减去 签约日期 
		if(day<2){//出游日-签约完成日   小于2天的，签约完成后发送邀评
			
			visit.setReturnVisitDate(new Date());
			
		}else if(day>=2 && day<=7){// 在2-7天的，出游日前2天发送邀评
			
			visit.setReturnVisitDate(DateUtil.addSqlDates(visit.getBeginDate(),-2));
			
		}else if(day>=8 && day<=13){//在8-13天的，出游日前3天发送邀评
			
			visit.setReturnVisitDate(DateUtil.addSqlDates(visit.getBeginDate(),-3));
			
		}else if(day>=14){//大于等于14天的，出游日前5天发送邀评
			
			visit.setReturnVisitDate(DateUtil.addSqlDates(visit.getBeginDate(),-5));
		}
		return visit;
		
		
	}
	
	public static <T> List<T> getSubListPage(List<T> list, int skip,
            int pageSize) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int startIndex = skip;
        int endIndex = skip + pageSize;
        if (startIndex > endIndex || startIndex > list.size()) {
            return null;
        }
        if (endIndex > list.size()) {
            endIndex = list.size();
        }
        return list.subList(startIndex, endIndex);
    }
	
	
	/*
	 *	发送售前短信
	 */
	public void preSaleSendMessage(){
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("returnVisitDate", DateUtil.formatAsDefaultDate(new Date()));//查询当前需要发送短信的订单
		List<PreSaleReturnVisit> list = rvMapper.needRvList(map);
		for(PreSaleReturnVisit rv : list){
			
			try {
				
				//调用CRM的AVR数据 获取 下单时间到签约时间之内的 通话记录个数 去除通话时长为0的 少于三次的通话 订单不发送短信
				int count = CrmClient.getIVRCount(rv);
				if(count>=3){
					
				    MessageParamEntity messageParamEntity = new MessageParamEntity();
	                messageParamEntity.setMobileNum(Arrays.asList(new String[]{rv.getTel()}));
	                messageParamEntity.setTemplateId(MessageParamEntity.TEMPLATE_SATISFIED);
	                messageParamEntity.setOrderId(rv.getOrdId());
	                List<MessageTemplateParamValuePair> smsTemplateParams = new ArrayList<MessageTemplateParamValuePair>();
	                MessageTemplateParamValuePair  pair1 = new MessageTemplateParamValuePair("prdName", convertString(rv.getPrdName()));//产品名称
	                MessageTemplateParamValuePair  pair2 = new MessageTemplateParamValuePair("customer",rv.getCustomer());//客服专员
	                smsTemplateParams.add(pair1);
	                smsTemplateParams.add(pair2);
	                messageParamEntity.setSmsTemplateParams(smsTemplateParams);
	                if(tspService.sendMessage(messageParamEntity)) { //发送成功后更新回访列表
	                 
	                	rv.setIsSign(1);
	                	rv.setSendTime(new Date());//更新短信发送时间，后面判断回短信时间差
	                	rvMapper.update(rv);
				   }
				}
			} catch (Exception e) {
				   
				  logger.error("preSaleSendMessage error",e.getMessage());
			}
				
		}
	}
	
	/*
	 * 对售前短信的产品名称进行转换，只取<>中内容，如果产品名称格式为其他格式，订单产品名称读取全部
	 */
	public String convertString(String value){
	    int j=value.indexOf('<');
        int k=value.indexOf('<', j+1);
        int i = value.lastIndexOf(">");
        if(j==-1 || i==-1||k!=-1) return value;
        return value.substring(j+1,i);
	}
	
	
}
