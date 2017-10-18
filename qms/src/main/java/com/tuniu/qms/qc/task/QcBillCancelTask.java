package com.tuniu.qms.qc.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.qms.access.client.CmpClient;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.service.QcBillService;

/**
 * 投诉质检单批量撤销任务
 * @author zhangsensen
 *
 */
public class QcBillCancelTask {
	
	private static final Logger logger = LoggerFactory.getLogger(QcBillCancelTask.class);
	
	@Autowired
	private QcBillService qcBillService;
	
	public void complaintQcBillCancel(){
		int start = 0;
		int limit = 5000;
		Map<String, Object> map;
		List<QcBill> qcBillList;
		
		try{
			logger.info("投诉质检撤销 start ");
			
			while(true){
				
				map = new HashMap<String, Object>();
				map.put("start", start);
				map.put("limit", limit);
				
				qcBillList = qcBillService.getComplaintQcBill(map);
				
				for(QcBill qcBill : qcBillList){
					//对应的投诉是否为无赔偿、非低满意度、无申请质检
					boolean isCancel = CmpClient.getComplaintQcCancelState(qcBill.getCmpId());
					
					//如果质检单对应的投诉为无赔偿、非低满意度、无申请质检的 则撤销
					if(isCancel){
						qcBillService.cancelQcBill(qcBill.getId());
					}
					
				}
				
				if(qcBillList != null && qcBillList.size() > 0){
					start += limit;
				}else{
					break;
				}
			}
			
			logger.info("投诉质检撤销 success ");
		}catch(Exception e){
			logger.error("投诉质检单撤销失败", e);
		}
	}
}
