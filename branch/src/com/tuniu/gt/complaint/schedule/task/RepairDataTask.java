package com.tuniu.gt.complaint.schedule.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IShareSolutionService;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;

public class RepairDataTask {

    private static Logger logger = Logger.getLogger(RepairDataTask.class);
    
    @Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService compService;
    
    @Autowired
    @Qualifier("complaint_service_complaint_impl-share_solution")
	private IShareSolutionService shareSolutionService;
    
    public void repairQualityCost() {
    	logger.info("repairQualityCost Bgn...");
    	String[] dateArr = new String[]{
    			"2016-01-01","2016-01-02","2016-01-03","2016-01-04","2016-01-05",
    			"2016-01-06","2016-01-07","2016-01-08","2016-01-09","2016-01-10",
    			"2016-01-11","2016-01-12","2016-01-13","2016-01-14","2016-01-15",
    			"2016-01-16","2016-01-17","2016-01-18","2016-01-19","2016-01-20",
    			"2016-01-21","2016-01-22","2016-01-23","2016-01-24","2016-01-25",
    			"2016-01-26","2016-01-27","2016-01-28","2016-01-29","2016-01-30","2016-01-31"
    			};
    	for (String dateStr : dateArr) {
    		List<Map<String, Object>> qcDataList = new ArrayList<Map<String,Object>>();
        	List<Map<String, Object>> qtcList = shareSolutionService.getQualityToolCost(dateStr);
        	List<Map<String, Object>> ugcList = shareSolutionService.getUnGroupCost(dateStr);
        	if (null != qtcList) {
        		qcDataList.addAll(qtcList);
        	}
        	if (null != ugcList) {
        		qcDataList.addAll(ugcList);
        	}
        	ComplaintRestClient.submitQualityCost(qcDataList);
    	}
    	logger.info("repairQualityCost End...");
    }
    
    public void updateOrderPersons() {
    	logger.info("updateOrderPersons Bgn...");
    	try {
    		List<Map<String, Object>> list = compService.getUndoneCompBills(); // 查询出所有未完成的投诉单
	    	for (Map<String, Object> map : list) {
	    		int complaintId = (Integer) map.get("complaintId");
	    		int orderId = (Integer) map.get("orderId");
				ComplaintEntity comp = compService.getOrderInfoMain(String.valueOf(orderId));
				
				Map<String, Object> compMap = new HashMap<String, Object>();
				compMap.put("id", complaintId);
				if (StringUtil.isNotEmpty(comp.getCustomer())) {
					compMap.put("customer", comp.getCustomer());
				}
				if (StringUtil.isNotEmpty(comp.getCustomerLeader())) {
					compMap.put("customerLeader", comp.getCustomerLeader());
				}
				if (StringUtil.isNotEmpty(comp.getServiceManager())) {
					compMap.put("serviceManager", comp.getServiceManager());
				}
				if (StringUtil.isNotEmpty(comp.getProducter())) {
					compMap.put("producter", comp.getProducter());
				}
				if (StringUtil.isNotEmpty(comp.getProductLeader())) {
					compMap.put("productLeader", comp.getProductLeader());
				}
				if (StringUtil.isNotEmpty(comp.getSeniorManager())) {
					compMap.put("seniorManager", comp.getSeniorManager());
				}
				if (StringUtil.isNotEmpty(comp.getProductManager())) {
					compMap.put("productManager", comp.getProductManager());
				}
				if (StringUtil.isNotEmpty(comp.getDepManager())) {
					compMap.put("depManager", comp.getDepManager());
				}
				if (StringUtil.isNotEmpty(comp.getDepName())) {
					compMap.put("depName", comp.getDepName());
				}
				compService.update(compMap);
				
				Thread.sleep(500);
	    	}
    	} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
    	logger.info("updateOrderPersons End...");
    }

}
