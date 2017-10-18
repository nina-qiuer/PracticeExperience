package com.tuniu.gt.complaint.service.impl;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tuniu.gt.complaint.dao.sqlmap.imap.ComplaintCollectMapper;
import com.tuniu.gt.complaint.entity.TimePlanEntity;
import com.tuniu.gt.complaint.service.IComplaintCollectService;
import com.tuniu.gt.complaint.vo.ComplaintCollectListVo;

@Service("complaintCollectService")
public class ComplaintCollectServiceImpl implements IComplaintCollectService{
	private static Logger logger = Logger.getLogger(ComplaintCollectServiceImpl.class);
	/**
	 * 查询投诉质检汇总表数据
	 * @param map
	 * @return
	 */
	@Autowired
	private  ComplaintCollectMapper  complaintCollectMapper ;
	@Override
	public List<ComplaintCollectListVo> getCollectList(
			Map<String, Object> map) {
		
		try {
			
			return complaintCollectMapper.getCollectList(map);
			
		} catch (Exception e) {
			
			return null;
		}
		
	}
	
	
	
	/**
	 * 查询投诉质检汇总表个数
	 * @param map
	 * @return
	 */
	@Override
	public int getCollectListCount(Map<String, Object> map) {
		int count =0;
		try {
			
			count =  complaintCollectMapper.getCollectListCount(map);
			return count;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return 0;
		}
		
		
	}


   /**
    * 查询当月排班计划
    */
	@Override
	public List<Map<String, Object>> queryCalendar(String start, String end) {
		
	     Map<String, Object> map =new HashMap<String, Object>();
	     map.put("startTime", start);
	     map.put("endTime", end);
		return complaintCollectMapper.queryCalendar(map);
	}


   
    /**
     * 保存排班人员
     */
	@Override
	public String savePlan(Map<String, Object> map) {
		
		String result ="";
		try {

			complaintCollectMapper.savePlan(map);
			result="success";
		} catch (Exception e) {
			
			logger.info(e.getMessage()+"保存排班人员失败");
			result="error";
		}
		 
		return result;
	}


	/**
	 *  查询前三年中某月某日某时订单量
	 * @param map
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryhistoryData(Map<String, Object> map) {
		
		
		                                                                                                                                                                            
		return complaintCollectMapper.queryhistoryData(map);
	}

	/**
	 * 
	 */
	public void saveDetailPlan(TimePlanEntity plan) {
		
		
		try {
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		    Date date = sdf.parse(plan.getDay());
		    Calendar c = Calendar.getInstance();  
	        c.setTime(date);   //设置当前日期  
	        c.add(Calendar.DATE, 1); //日期加1天  
	        date = c.getTime();  
	  	    SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd"); 
	  	    String planDate = sdformat.format(date);
			
			Map<String, Object> map1 =new HashMap<String, Object>();
			//早班
			map1.put("personnum",plan.getZaoban());
			map1.put("allday",0);
			map1.put("title","早班");
			map1.put("starttime",plan.getDay()+" 06:00:00");
			map1.put("endtime",plan.getDay()+" 13:00:00");
			map1.put("month", plan.getMonth());
			complaintCollectMapper.saveDetailPlan(map1);
			//中班1
			Map<String, Object> map2 =new HashMap<String, Object>();
			map2.put("personnum",plan.getZhongban1());
			map2.put("allday",0);
			map2.put("title","中班1");
			map2.put("starttime",plan.getDay()+" 08:00:00");
			map2.put("endtime",plan.getDay()+" 17:00:00");
			map2.put("month", plan.getMonth());
			complaintCollectMapper.saveDetailPlan(map2);
			//中班2
			Map<String, Object> map3 =new HashMap<String, Object>();
			map3.put("personnum",plan.getZhongban2());
			map3.put("allday",0);
			map3.put("title","中班2");
			map3.put("starttime",plan.getDay()+" 12:00:00");
			map3.put("endtime",plan.getDay()+" 21:00:00");
			map3.put("month", plan.getMonth());
			complaintCollectMapper.saveDetailPlan(map3);
			//晚班
			Map<String, Object> map4 =new HashMap<String, Object>();
			map4.put("personnum",plan.getWanban());
			map4.put("allday",0);
			map4.put("title","晚班");
			map4.put("starttime",plan.getDay()+" 14:00:00");
			map4.put("endtime",plan.getDay()+" 23:00:00");
			map4.put("month", plan.getMonth());
			complaintCollectMapper.saveDetailPlan(map4);
			//夜班
			Map<String, Object> map5 =new HashMap<String, Object>();
			map5.put("personnum",plan.getYeban());
			map5.put("allday",0);
			map5.put("title","夜班");
			map5.put("starttime",plan.getDay()+" 23:00:00");
			map5.put("endtime",planDate+" 07:00:00");
			map5.put("month", plan.getMonth());
			complaintCollectMapper.saveDetailPlan(map5);
			
		} catch (Exception e) {
			
			logger.info(e.getMessage()+"插入排班数据失败");
		}
	}



	@Override
	public String saveDeploy(Map<String, Object> map) {
		
		try {
			
			complaintCollectMapper.saveDeploy(map);
			return "0";
		} catch (Exception e) {
			
			return "1";
		}
		
	}



	@Override
	public Map<String, Object> queryDeploy() {
		
		
		return complaintCollectMapper.queryDeploy();
	}



	@Override
	public void deleteDetail(String month) {
		
		complaintCollectMapper.deleteDetail(month);
		 
	}



	@Override
	public List<Map<String, Object>> queryManagerList(Map<String, Object> map) {
		
		
		return complaintCollectMapper.queryManagerList(map);
	}

    /**
     * 查询问题描述
     */

	@Override
	public List<ComplaintCollectListVo> queryDescript(Map<String,Object> map) {
		
		
		return complaintCollectMapper.queryDescript(map);
	}



	@Override
	public List<ComplaintCollectListVo> queryTracker(Map<String, Object> map) {
		
		
		return complaintCollectMapper.queryTracker(map);
	}
	
	

}
