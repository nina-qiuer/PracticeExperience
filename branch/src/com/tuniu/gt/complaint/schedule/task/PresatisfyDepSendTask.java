package com.tuniu.gt.complaint.schedule.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import tuniu.frm.service.Constant;

import com.tuniu.bi.presatisfy.entity.PresatisfyEntity;
import com.tuniu.bi.presatisfy.service.IPreSatisfyDepService;
import com.tuniu.gt.satisfaction.service.IPreSaleSatisfactionService;
import com.tuniu.trest.TRestClient;
import com.tuniu.trest.TRestMethod;

/**
 * 售前客服满意度定时任务
 * 
 * @author yuxiang
 */
public class PresatisfyDepSendTask {
	private static Logger logger = Logger.getLogger(PresatisfyDepSendTask.class);
	// 引入bi数据service
	@Autowired
	@Qualifier("bi_service_presatisfydep_impl-presatisfydep")
	private IPreSatisfyDepService preSatisfyDepService;

	@Autowired
	@Qualifier("satisfaction_service_satisfaction_impl-preSaleSatisfaction")
	private IPreSaleSatisfactionService preSaleSatisfactionService;
	private static String custURL = Constant.CONFIG.getProperty("CUST_URL");

	@SuppressWarnings({ "rawtypes" })
	public void achieveTask() throws InterruptedException {
		System.out.println("acheieve bi data time start:-------" + new Date());
		// 查询来自BI的数据
		Integer count = preSatisfyDepService.getPresatisfyDepCount();
		Integer pageCount = 100;
		for (int i = 0; i < count; i += pageCount) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("dataLimitStart", i);
			paramMap.put("dataLimitEnd", pageCount);
			List<PresatisfyEntity> presatisfylist = preSatisfyDepService.getPresatisfyDep(paramMap);
			setContactInfoAndSave(presatisfylist);
			Thread.sleep(1000);
		}
		
		System.out.println("acheieve bi data time over:-------" + new Date());
	}

	private void setContactInfoAndSave(List<PresatisfyEntity> presatisfylist) {
		for (PresatisfyEntity preEntity : presatisfylist) {
			try{
				try {
					JSONObject jsonParam = new JSONObject();
					jsonParam.accumulate("func", "queryById");
					jsonParam.accumulate("params", preEntity.getContactId().toString());
					TRestClient client = new TRestClient(custURL, TRestMethod.GET, jsonParam.toString());
					String ret = client.execute();
					logger.info("queryById Ing: ret is " + ret);
					JSONObject jsonObject = JSONObject.fromObject(ret);
					Boolean isSuccess = jsonObject.getBoolean("res");
					if (isSuccess) {
						JSONObject jo = jsonObject.getJSONObject("cust");
						preEntity.setContactName(jo.getString("real_name"));
						preEntity.setContactTel("".equals(jo.getString("tel").trim()) ? "0" : jo.getString("tel"));
					}
				} catch (Exception e) {
					logger.error("getCustInfo order_id:"+preEntity.getOrderId()+" error:"+e.getMessage(), e);
				}
				Date thisTime = new Date();
				preEntity.setAddTime(thisTime);
				preEntity.setUpdateTime(thisTime);
				int queryPresatisfyListCount = preSaleSatisfactionService.getCountByOrderId(preEntity.getOrderId());
				if (queryPresatisfyListCount > 0) {
					preSaleSatisfactionService.update(preEntity);
				} else {
					preSaleSatisfactionService.insert(preEntity);
				}
			}catch (Exception e) {
				logger.error("setContactInfoAndSave order_id:"+preEntity.getOrderId()+" error:"+e.getMessage(), e);
			}
		}
	}
}
