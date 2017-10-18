package com.tuniu.gt.complaint.schedule.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IShareSolutionService;
import com.tuniu.gt.toolkit.DateUtil;

public class QualityCostTask {
	
    private static Logger logger = Logger.getLogger(QualityCostTask.class);
    
    @Autowired
    @Qualifier("complaint_service_complaint_impl-share_solution")
	private IShareSolutionService shareSolutionService;
    
    /**
     * 向QMS系统提交质量成本
     */
    public void submitQualityCost() {
    	logger.info("submitQualityCost Bgn...");
    	
    	String yesterday = DateUtil.getYesterdayStr();
    	
    	List<Map<String, Object>> qcDataList = new ArrayList<Map<String,Object>>();
    	List<Map<String, Object>> qtcList = shareSolutionService.getQualityToolCost(yesterday);
    	List<Map<String, Object>> ugcList = shareSolutionService.getUnGroupCost(yesterday);
    	if (null != qtcList) {
    		qcDataList.addAll(qtcList);
    	}
    	if (null != ugcList) {
    		qcDataList.addAll(ugcList);
    	}
    	ComplaintRestClient.submitQualityCost(qcDataList);
    	logger.info("submitQualityCost End...");
    }
    
}
