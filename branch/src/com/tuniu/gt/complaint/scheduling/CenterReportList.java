package com.tuniu.gt.complaint.scheduling;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.service.ICenterReportService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.infobird.vdnlog.dao.sqlmap.imap.CriticalReportMapper;
import com.tuniu.infobird.vdnlog.entity.CriticalReportEntity;

/**
 * 售后中心业务关键指标报表
 * @author chenhaitao
 */
@Service("centerReportList")
public class CenterReportList {
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	@Autowired
	@Qualifier("infobird_dao_sqlmap-agentStatusLog")
	private CriticalReportMapper  criticalReportMapper;
	@Autowired
	private ICenterReportService centerReportService;
	private static Logger logger = Logger.getLogger(CenterReportList.class);
	//获取各项指标
	@SuppressWarnings("unchecked")
	public void queryCriticalReport() {
		
		Date date = new Date();
		Date StartDate  = DateUtil.getLastWeekFirstDay(date);
		Date EndDate = DateUtil.getLastWeekEndDay(date);
		String startTime = DateUtil.formatDate(StartDate);
		String endTime = DateUtil.formatDate(EndDate);
		int week = DateUtil.getWeek(StartDate);//获取上一周日期是全年第多少周
		Calendar c = Calendar.getInstance();  
	    int year = c.get(Calendar.YEAR);    //获取年
		//升级到第三方的投诉率
		 try {
				List<CriticalReportEntity> criticalList =new ArrayList<CriticalReportEntity>();
			 	List<String> dialTypes = new ArrayList<String>();
		        dialTypes.add(Constans.WEIBO);//来源微博
		        dialTypes.add(Constans.LOCAL_QUALITY);//当地质检
		        dialTypes.add(Constans.TOURISM);//旅游局
		      
				Map<String, Object> thridMap =new HashMap<String, Object>();
				thridMap.put("startTime",startTime+" 00:00:00");
				thridMap.put("endTime",endTime+" 23:59:59");
				thridMap.put("dialTypes", dialTypes);
			    List<Map<String, Object>> listMap  =  complaintService.getThirdParty(thridMap);
				if(null!= listMap && listMap.size()>0){
					
					for(int t=0;t<listMap.size();t++){
						
						CriticalReportEntity entity =new CriticalReportEntity();
						String dealName = listMap.get(t).get("dealName").toString();
						String deal = listMap.get(t).get("deal").toString();
						String dealDepart = listMap.get(t).get("dealDepart").toString();
						int otherNum =  Integer.parseInt(listMap.get(t).get("otherNum").toString());
						int allNum = Integer.parseInt(listMap.get(t).get("allNum").toString());
						entity.setDealName(dealName);
						entity.setDeal(deal);
						entity.setState(dealDepart);
						String  upgradeComplaintRate = DateUtil.getPercent(otherNum, allNum);
						entity.setUpgradeComplaintRate(upgradeComplaintRate);
						entity.setStartTime(startTime +" 00:00:00");
						entity.setEndTime(endTime+" 23:59:59");
						entity.setWeekTime(year+"-"+week);
						criticalList.add(entity);
					}
				
					centerReportService.insertCriticalReport(criticalList);//插入统计表
					logger.info("插入升级投诉率成功:"+startTime+"--"+endTime);
				}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	
		 //回呼及时率
		 try {
			 
			 Map<String, Object> callMap =new HashMap<String, Object>();
			 callMap.put("startTime", startTime+" 00:00:00");
			 callMap.put("endTime",endTime +" 23:59:59");
			 List<ComplaintEntity> list = complaintService.getCallBack(callMap);//获取有跟进时间 或者新增投诉事宜时间的投诉单
			 for(int i=0;i<list.size();i++){
				 
				 int followNum =0;//及时跟进提醒
				 int reasonNum =0;//新增投诉事宜
				 int sFollowNum =0;//不及时跟进
				 int sReasonNum =0;//不新增投诉事宜
				 ComplaintEntity entity  = list.get(i);
				 Map<String, Object> ivrMap =new HashMap<String, Object>();
				 ivrMap.put("extension", entity.getExtension());//客服座机号
				 ivrMap.put("contactPhone", entity.getContactPhone());//客户号码
					 
			     if(entity.getDealDepart().contains(Constans.BEFORE_TRAVEL)){//售前组
						 
						if(!"".equals(entity.getFollowTime())){//有跟进提醒时间
							 ivrMap.put("startTime", entity.getFollowTime().substring(0, 10)+" 00:00:00");//跟进提醒时间
							 ivrMap.put("endTime", entity.getFollowTime().substring(0, 10)+" 23:59:59");//跟进提醒时间
							 ivrMap.put("callDir", Constans.CALL_DIR); //通话通道
							 ivrMap.put("status", Constans.CALL_STATUS); //通话状态
							 ivrMap.put("callType", Constans.CALL_TYPE); //通话类型
							 int count =  criticalReportMapper.queryIfWork(ivrMap);
							 
							 if(count>0){//在班
								 
							     ivrMap.put("followTime", entity.getFollowTime()); 
							     int num  =  criticalReportMapper.queryIfCommit(ivrMap);
							     if(num>0){
							    	 
							    	 followNum++;
							    	 
							     }else{
							    	 
							    	 sFollowNum++;
							    	 
							     }
							 }else{//不在班
								 
								 String nextTimeStart = DateUtil.getNexDateTime(DateUtil.parseDateTime(entity.getFollowTime()));//第二天0点
								 String nextTimeEnd =  nextTimeStart.substring(0, 10)+" 23:59:59" ;//第二天最晚时间
								 ivrMap.put("nextTimeStart", nextTimeStart); 
								 ivrMap.put("nextTimeEnd",nextTimeEnd); 
								 int num =  criticalReportMapper.queryNextDayTime(ivrMap);
								 if(num>0){
									 
									 followNum++;
									 
								 }else{
									 
									 sFollowNum++;
								 }
							 }
						 }
						 if(!"".equals(entity.getReasonTime())){//新增投诉事宜时间
							 ivrMap.put("startTime", entity.getReasonTime().substring(0, 10)+" 00:00:00");//新增投诉事宜时间
							 ivrMap.put("endTime", entity.getReasonTime().substring(0, 10)+" 23:59:59");//新增投诉事宜时间
							 ivrMap.put("callDir", Constans.CALL_DIR); //通话通道
							 ivrMap.put("status", Constans.CALL_STATUS); //通话状态
							 ivrMap.put("callType", Constans.CALL_TYPE); //通话类型
							 int count =  criticalReportMapper.queryIfWork(ivrMap);
							 
							 if(count>0){//在班
								 
							     ivrMap.put("followTime", entity.getReasonTime()); 
							     int num  =  criticalReportMapper.queryIfCommit(ivrMap);
							     if(num>0){
							    	 
							    	 reasonNum++;
							    	 
							     }else{
							    	 
							    	 sReasonNum++;
							    	 
							     }
							 }else{//不在班
								 
								 String nextTimeStart = DateUtil.getNexDateTime(DateUtil.parseDateTime(entity.getReasonTime()));//第二天0点
								 String nextTimeEnd =  nextTimeStart.substring(0, 10)+" 23:59:59" ;//第二天最晚时间
								 ivrMap.put("nextTimeStart", nextTimeStart); 
								 ivrMap.put("nextTimeEnd",nextTimeEnd); 
								 int num =  criticalReportMapper.queryNextDayTime(ivrMap);
								 if(num>0){
									 
									 reasonNum++;
									 
								 }else{
									 
									 sReasonNum++;
								 }
							 }
							 
						 }
						 
					 }else  if(Constans.IN_TRAVEL.equals(entity.getDealDepart())){//售后组
						 
						 if(!"".equals(entity.getFollowTime())){//有跟进提醒时间
							 ivrMap.put("startTime", entity.getFollowTime().substring(0, 10)+" 00:00:00");//跟进提醒时间
							 ivrMap.put("endTime", entity.getFollowTime().substring(0, 10)+" 23:59:59");//跟进提醒时间
							 ivrMap.put("callDir", Constans.CALL_DIR); //通话通道
							 ivrMap.put("status", Constans.CALL_STATUS); //通话状态
							 ivrMap.put("callType", Constans.CALL_TYPE); //通话类型
							 int count =  criticalReportMapper.queryIfWork(ivrMap);
							 
							 if(count>0){//在班
								 
							     ivrMap.put("followTime", entity.getFollowTime()); 
							     int num  =  criticalReportMapper.queryIfCommit(ivrMap);
							     if(num>0){
							    	 
							    	 followNum++;
							    	 
							     }else{
							    	 
							    	 sFollowNum++;
							    	 
							     }
							 }else{//不在班
								 
								 ivrMap.put("lastTime", entity.getFollowTime()); 
								 ivrMap.put("status", Constans.CALL_STATUS_OUT); //登出
								 int num =  criticalReportMapper.queryLastTime(ivrMap);
								 if(num>0){
									 
									 followNum++;
									 
								 }else{
									 
									 sFollowNum++;
								 }
							 }
						 }
						 if(!"".equals(entity.getReasonTime())){//新增投诉事宜时间
							 ivrMap.put("startTime", entity.getReasonTime().substring(0, 10)+" 00:00:00");//新增投诉事宜时间
							 ivrMap.put("endTime", entity.getReasonTime().substring(0, 10)+" 23:59:59");//新增投诉事宜时间
							 ivrMap.put("callDir", Constans.CALL_DIR); //通话通道
							 ivrMap.put("status", Constans.CALL_STATUS); //通话状态
							 ivrMap.put("callType", Constans.CALL_TYPE); //通话类型
							 int count =  criticalReportMapper.queryIfWork(ivrMap);
							 
							 if(count>0){//在班
								 
							     ivrMap.put("followTime", entity.getReasonTime()); 
							     int num  =  criticalReportMapper.queryIfCommit(ivrMap);
							     if(num>0){
							    	 
							    	 reasonNum++;
							    	 
							     }else{
							    	 
							    	 sReasonNum++;
							    	 
							     }
							 }else{//不在班
								 
								 ivrMap.put("lastTime", entity.getReasonTime()); 
								 ivrMap.put("status", Constans.CALL_STATUS_OUT); //登出
								 int num =  criticalReportMapper.queryLastTime(ivrMap);
								 if(num>0){
									 
									 reasonNum++;
									 
								 }else{
									 
									 sReasonNum++;
								 }
							 }
							 
						 }
						 
						 
					 }else if(Constans.AFTER_TRAVEL.equals(entity.getDealDepart())){//资深组
						 
						 
						 if(!"".equals(entity.getFollowTime())){//有跟进提醒时间
							 ivrMap.put("startTime", entity.getFollowTime().substring(0, 10)+" 00:00:00");//跟进提醒时间
							 ivrMap.put("endTime", entity.getFollowTime().substring(0, 10)+" 23:59:59");//跟进提醒时间
							 ivrMap.put("callDir", Constans.CALL_DIR); //通话通道
							 ivrMap.put("status", Constans.CALL_STATUS); //通话状态
							 ivrMap.put("callType", Constans.CALL_TYPE); //通话类型
							 int count =  criticalReportMapper.queryIfWork(ivrMap);
							 
							 if(count>0){//在班
								 
							     ivrMap.put("followTime", entity.getFollowTime()); 
							     int num  =  criticalReportMapper.queryIfCommit(ivrMap);
							     if(num>0){
							    	 
							    	 followNum++;
							    	 
							     }else{
							    	 
							    	 sFollowNum++;
							    	 
							     }
							 }else{//不在班
								 
								 String nextTimeStart = DateUtil.getNexDateTime(DateUtil.parseDateTime(entity.getFollowTime()));//第二天0点
								 String nextTimeEnd =  nextTimeStart.substring(0, 10)+" 23:59:59" ;//第二天最晚时间
								 ivrMap.put("nextTimeStart", nextTimeStart); 
								 ivrMap.put("nextTimeEnd",nextTimeEnd); 
								 int num =  criticalReportMapper.queryNextDayTime(ivrMap);
								 if(num>0){
									 
									 followNum++;
									 
								 }else{
									 
									 sFollowNum++;
								 }
							 }
						 }
						 if(!"".equals(entity.getReasonTime())){//新增投诉事宜时间
							 
							 ivrMap.put("startTime", entity.getReasonTime().substring(0, 10)+" 00:00:00");//新增投诉事宜时间
							 ivrMap.put("endTime", entity.getReasonTime().substring(0, 10)+" 23:59:59");//新增投诉事宜时间
							 ivrMap.put("callDir", Constans.CALL_DIR); //通话通道
							 ivrMap.put("status", Constans.CALL_STATUS); //通话状态
							 ivrMap.put("callType", Constans.CALL_TYPE); //通话类型
							 int count =  criticalReportMapper.queryIfWork(ivrMap);
							 
							 if(count>0){//在班
								 
							     ivrMap.put("followTime", entity.getReasonTime()); 
							     int num  =  criticalReportMapper.queryIfCommit(ivrMap);
							     if(num>0){
							    	 
							    	 reasonNum++;
							    	 
							     }else{
							    	 
							    	 sReasonNum++;
							    	 
							     }
							 }else{//不在班
								 
								 String nextTimeStart = DateUtil.getNexDateTime(DateUtil.parseDateTime(entity.getReasonTime()));//第二天0点
								 String nextTimeEnd =  nextTimeStart.substring(0, 10)+" 23:59:59" ;//第二天最晚时间
								 ivrMap.put("nextTimeStart", nextTimeStart); 
								 ivrMap.put("nextTimeEnd",nextTimeEnd); 
								 int num =  criticalReportMapper.queryNextDayTime(ivrMap);
								 if(num>0){
									 
									 reasonNum++;
									 
								 }else{
									 
									 sReasonNum++;
								 }
							 }
							 
						 }
						 
					 }
					 
					 entity.setFollowNum(followNum);
					 entity.setsFollowNum(sFollowNum);
					 entity.setReasonNum(reasonNum);
					 entity.setsReasonNum(sReasonNum);
			 }
			 ComplaintEntity complaint = null;
			 Map<Integer, ComplaintEntity> map = new HashMap<Integer, ComplaintEntity>();//合并后的处理人存在map中
			 for(ComplaintEntity oComplaint: list) {
				 complaint = map.get(oComplaint.getDeal());
			      if (complaint != null) {
			    	  complaint.setFollowNum(complaint.getFollowNum() + oComplaint.getFollowNum());  //及时拨打跟进次数
			    	  complaint.setsFollowNum(complaint.getsFollowNum() + oComplaint.getsFollowNum());  //不及时拨打跟进次数
			    	  complaint.setReasonNum(complaint.getReasonNum() + oComplaint.getReasonNum());  //及时拨打新增投诉事宜次数
			    	  complaint.setsReasonNum(complaint.getsReasonNum() + oComplaint.getsReasonNum());  //不及时拨打新增投诉事宜次数
			    	  
			      } else {
			           map.put(oComplaint.getDeal(), oComplaint);
			     }
			 }
//			 System.out.println("5466:" +map.get(5466).getFollowNum()+"--"+map.get(5466).getsFollowNum());
//			 System.out.println("5466:" +map.get(5466).getReasonNum()+"--"+map.get(5466).getsReasonNum());
			 List<ComplaintEntity> compList = mapTransitionList(map);//map转成list
			 
			 for(ComplaintEntity comEntity :compList ){
				 
			    CriticalReportEntity entity =new CriticalReportEntity();
				String dealName = comEntity.getDealName();
				String deal = String.valueOf(comEntity.getDeal());
				String dealDepart = comEntity.getDealDepart();
				int followNum =  comEntity.getFollowNum();
				int sFollowNum = comEntity.getsFollowNum();
				int reasonNum =  comEntity.getReasonNum();
				int sReasonNum = comEntity.getsReasonNum();
				entity.setDealName(dealName);
				entity.setDeal(deal);
				entity.setState(dealDepart);
				String  callBackRate = DateUtil.getPercent(followNum+reasonNum, followNum+sFollowNum+reasonNum+sReasonNum);
				entity.setCallBackRate(callBackRate);
				entity.setStartTime(startTime +" 00:00:00");
				entity.setEndTime(endTime+" 23:59:59");
				entity.setWeekTime(year+"-"+week);
				centerReportService.insertUpCriticalReport(entity);//插入或者更新统计表
				logger.error("插入回呼率成功:"+startTime+"--"+endTime);
			 }
			 
			 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		 
	}
	
	
	/**
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List  mapTransitionList(Map map) {
		List<ComplaintEntity> list = new ArrayList<ComplaintEntity>();
		Iterator iter = map.entrySet().iterator();  //获得map的Iterator
		while(iter.hasNext()) {
			Entry<?, ?> entry = (Entry)iter.next();
			list.add((ComplaintEntity) entry.getValue());
		}
		return list;
	}
	

}
