package com.tuniu.qms.qc.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.qms.access.client.OaClient;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.service.ResDevQcBillService;


/**
 * 添加质检期开始时间、质检到期时间
 * @author chenhaitao
 */
public class ExpirationTimeTask {
	

	@Autowired
	private QcBillService qcBillService;
	
	@Autowired
	private ResDevQcBillService devService;
	
	private final static Logger logger = LoggerFactory.getLogger(ExpirationTimeTask.class);
	
	public void addExpiration() {
		
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("state", Constant.CMP_BILL_STATE_FINISH);//投诉完成状态
	    List<QcBill> list = qcBillService.getExpirationList(map);
	    for(QcBill qcBill : list){
	    	Date distribTime = qcBill.getDistribTime();//质检分配时间
	    	Date finishTime = qcBill.getComplaintBill().getFinishTime();//投诉完成时间
	    	
	    	if(distribTime != null && finishTime != null){
	    		
		    	if(OaClient.getDayIsWork(distribTime)!=9){
		    		logger.info("ExpirationTimeTask 质检到期时间 start");
		    		
		    		Date beginTime = getBeginDate(distribTime.before(finishTime) ? finishTime : distribTime); //质检期开始时间
			    	
			    	qcBill.setQcPeriodBgnTime(beginTime);
			    	qcBill.setQcPeriodEndTime(getExpirationDate(beginTime,3));
			    	
			    	qcBillService.update(qcBill);
			    	
			    	logger.info("ExpirationTimeTask 质检到期时间 end" + qcBill.getQcPeriodEndTime());
		    	}
	    	}
	    }
	    
	    //无投诉质检 质检期开始时间和完成时间
	    List<QcBill> noCmpBillList = qcBillService.getNoCmpExpirationList();
	    for(QcBill qcBill : noCmpBillList){
	    	if(qcBill.getDistribTime() != null){
	    		if(OaClient.getDayIsWork(qcBill.getDistribTime())!=9){
		    		
		    		Date beginTime = getBeginDate(qcBill.getDistribTime()); //质检期开始时间
			    	
			    	qcBill.setQcPeriodBgnTime(beginTime);
			    	qcBill.setQcPeriodEndTime(getExpirationDate(beginTime,3));
			    	
			    	qcBillService.update(qcBill);
		    	}
	    	}
	    }
       
	}

	
	public void updateDevExpiration(){
		List<QcBill> list = devService.getQcPeriodList();
	    for(QcBill qcBill : list){
			
	    	Date distribTime = qcBill.getDistribTime();//质检分配时间
	    	if(distribTime != null){
	    		if(OaClient.getDayIsWork(distribTime)!=9){
		    		
		    		Date endTime  = getExpirationDate(distribTime,6);
			    	qcBill.setQcPeriodBgnTime(distribTime);
			    	qcBill.setQcPeriodEndTime(endTime);
			    	devService.update(qcBill);
		    	}else{
		    		
		    		qcBill.setQcPeriodBgnTime(distribTime);
			    	qcBill.setQcPeriodEndTime(DateUtil.addSqlDates(distribTime, 6));
			    	devService.update(qcBill);
		    		
		    	}
	    	}
		}
	}
	/**
	 * 获取质检期开始时间
	 * @param time
	 * @return
	 */
	 public static Date  getBeginDate(Date time){
		 
			if(OaClient.getDayIsWork(time)!=0){
				
				time = DateUtil.addSqlDates(time, 1);
				return getBeginDate(time);
				
			}else{
				
				return  time ;
				
			}
	 }
	 
	/**
	 * 获取质检结束时间
	 * @param finishTime
	 * @param days
	 * @return
	 */
	 
	 public static Date  getExpirationDate(Date beginTime,int days){
		 
		 Date endTime = new Date();
		 for (int i=0; i<=days; i++) {
				endTime = DateUtil.addSqlDates(beginTime, i);
	            if (OaClient.getDayIsWork(endTime)!=0) { // 如果是休息日，则往后推一天
	            	days++;
	            }
			}
		 return endTime;
	 }
}
