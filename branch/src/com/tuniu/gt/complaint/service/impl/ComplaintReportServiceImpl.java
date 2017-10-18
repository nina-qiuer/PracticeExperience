package com.tuniu.gt.complaint.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.HttpClientHelper;
import com.tuniu.gt.complaint.dao.impl.ComplaintCompleteCountDao;
import com.tuniu.gt.complaint.entity.ComplaintCompleteCountEntity;
import com.tuniu.gt.complaint.entity.ComplaintCompleted;
import com.tuniu.gt.complaint.entity.OrderEntity;
import com.tuniu.gt.complaint.service.IComplaintLaunchCountService;
import com.tuniu.gt.complaint.service.IComplaintReportService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.complaint.vo.AfterSaleReportVo;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.JsonUtil;

@Service("complaint_service_complaint_impl-complaint_complete_count")
public class ComplaintReportServiceImpl extends
		ServiceBaseImpl<ComplaintCompleteCountDao> implements
		IComplaintReportService {
	private static Logger logger = Logger.getLogger(ComplaintServiceImpl.class);
	
	//获取icc服务事项列表
	private static String iccServiceURL = Constant.CONFIG.getProperty("ICC_SERVICE_URL");
	
	@Autowired
	@Qualifier("complaint_dao_impl-complaint_complete_count")
	public void setDao(ComplaintCompleteCountDao dao) {
		this.dao = dao;
	}
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	public IComplaintService complaintService;
	
	@Autowired
    @Qualifier("tspService")
	private IComplaintTSPService tspService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_launch_count")
	public IComplaintLaunchCountService complaintLaunchCountService;

	@Override
	public Map<String, Map<String, Integer>> getComplaintCompleteCount(
			Map<String, Object> paramMap) {
		List<AfterSaleReportVo> afterSaleReportVoList = dao.getComplaintCompleteCount(paramMap);
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
	};
	
	@Override
	public List<Integer> getCompleteCmpOrderList(Map<String, Object> paramMap){
		return dao.getCompleteCmpOrderList(paramMap);
	};
	
	@Override
	public void updateDepartmentByUserId(){
		dao.updateDepartmentByUserId();
	}
	
	@Override
	public void insertComplaintIdByOrderId(){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		Date yesterdayDate=DateUtil.getSomeDay(new Date(), -1);
		paramMap.put("finishTimeBgn", DateUtil.formatDate(yesterdayDate)+" 00:00:00");
		paramMap.put("finishTimeEnd", DateUtil.formatDate(yesterdayDate)+" 23:59:59");
		dao.insertComplaintIdByOrderId(paramMap);
	}
	
	@Override
	public List<ComplaintCompleteCountEntity> getCompleteCountByWorknum(Map<String, Object> paramMap){
		return dao.getCompleteCountByWorknum(paramMap);
	}
	
	@Override
	public void insertServicesOld(){
		Map<String, Object> params = new HashMap<String, Object>();
		Date yesterdayDate=DateUtil.getSomeDay(new Date(), -1);
		params.put("addTimeBgn", "1900-01-01 00:00:00");
		params.put("addTimeEnd", DateUtil.formatDate(yesterdayDate)+" 23:59:59");
		params.put("billType", 2);
		insertOrUpdateByTime(params);
	}
	
	@Override
	public void insertServicesByOrderId(){
		Map<String, Object> params = new HashMap<String, Object>();
		Date yesterdayDate=DateUtil.getSomeDay(new Date(), -1);
		params.put("addTimeBgn", DateUtil.formatDate(yesterdayDate)+" 00:00:00");
		params.put("addTimeEnd", DateUtil.formatDate(yesterdayDate)+" 23:59:59");
		params.put("billType", 2);
		insertOrUpdateByTime(params);
	}
	
	public void insertOrUpdateByTime(Map<String, Object> params){
		String jsonStr = JSONObject.fromObject(params).toString();
		try {
			HttpClientHelper client = new HttpClientHelper();
			String responseMsg =  client.executeGet(iccServiceURL, jsonStr);
			logger.info("调用接口："+iccServiceURL);
			logger.info("调用参数："+jsonStr);
			JSONObject jsonObject = JSONObject.fromObject(responseMsg);
			if ((Boolean) jsonObject.get("success")) {
				if (null != jsonObject.get("data")) {
					JSONArray jsonArrayData = jsonObject.getJSONArray("data");
					for (int i = 0; i < jsonArrayData.size(); i++) {
						Map<String, Object> paramMap=new HashMap<String, Object>();
						JSONObject tempJson=(JSONObject) jsonArrayData.get(i);
						Integer orderId=(Integer) tempJson.get("billId");
						Integer userId=(Integer) tempJson.get("addPersonId");
						Date addDate=DateUtil.parseDate((String) tempJson.get("addTime"),"yyyy-MM-dd HH:mm:ss");
						paramMap.put("orderId", orderId);
						paramMap.put("userId", userId);
						paramMap.put("dealDepart", "交通组");
						ComplaintCompleted complaintCompleted=dao.getDataByOrderIdAndDepart(paramMap);
						if(complaintCompleted!=null){
							Date finishDate=complaintCompleted.getFinish_time();
							Long betweenDays = DateUtil.getbetweenTime(finishDate,addDate);
							try {
								if(betweenDays>0){
									paramMap.put("finishDate", addDate);
									dao.insertOrUpdate(paramMap);
								}
							} catch (Exception e) {
								logger.error(e.getMessage()+"查询订单信息添加服务事项数据失败");
							}
						}else{
							try {
								OrderEntity orderEntity  = complaintService.queryNewOrderInfo(String.valueOf(orderId));
								Map<String, Object> productInfo = tspService.getProductInfo((long)orderEntity.getRouteId());
								paramMap.put("routeType", productInfo.get("classBrandParentName").toString());
								paramMap.put("destCategoryName", Constans.getDestCateName((Integer) productInfo.get("productLineTypeId")));
								paramMap.put("finishDate", addDate);
								dao.insertOrUpdate(paramMap);
							} catch (Exception e) {
								logger.error(e.getMessage()+"查询订单信息添加服务事项数据失败");
							}
						}
					}
				}
			}else{
				String msg = JsonUtil.getStringValue(jsonObject, "msg");
				if("".equals(msg)){
					msg ="查询ICC服务事项列表失败";
				}
				logger.error(DateUtil.formatDate(new Date())+msg);
			}
		} catch (Exception e) {
			logger.error(e.getMessage() +"查询ICC服务事项列表失败");
		}
	}
	
	@Override
	public void insertLaunchCountByComplaintId(){
		Map<String, Object> params = new HashMap<String, Object>();
		Date yesterdayDate=DateUtil.getSomeDay(new Date(), -1);
		params.put("finishTimeBgn", DateUtil.formatDate(yesterdayDate)+" 00:00:00");
		params.put("finishTimeEnd", DateUtil.formatDate(yesterdayDate)+" 23:59:59");
		complaintLaunchCountService.insertComplaintLaunchByDay(params);
	}
	
	@Override
	public void updateLaunchCountDepartment(){
		complaintLaunchCountService.updateDepartmentToReportOne();
		complaintLaunchCountService.updateDepartmentToReportTwo();
		complaintLaunchCountService.updateDepartmentToReportThree();
	}
}
