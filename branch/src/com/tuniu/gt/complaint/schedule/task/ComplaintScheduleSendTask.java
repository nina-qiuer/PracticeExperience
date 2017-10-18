package com.tuniu.gt.complaint.schedule.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.service.IComplaintSendRecordService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.toolkit.Rtx;



/**
 * 定时提醒任务 
 * 目前在跟单过程中，主管缺少监控办法，会造成有些单子跟进时间较长或跟进记录较多的单子，只是本人知晓，经理不能全面把控，为了改善这种局面，
 * 特提出一个监控跟单记录的需求说明。 在投诉处理列表中，投诉处理中、投诉已待结 中的单子，《投诉跟进记录 》+《投诉事宜记录 》大于等于多少条记录，系统会自动提醒经理，引起关注。可以是RTX提醒。 
 * 规则：出游前大于10条提醒，出游中大于等于30条提醒，出游后大于15条就提醒。
 * RTX提醒人员： 
 * 出游前单子提醒蒋琳+李波 
 * 出游中单子提醒冯静+曹烨+李波 
 * 出游后的单子提醒戴周峰+李波
 * @author hanxuliang
 * 
 */
public class ComplaintScheduleSendTask {
	
	//间隔时间，考虑到程序的延迟性，数值比间隔略小，单位毫秒
	private static final long scheduleTime = 40000L;
	
	// 引入投诉事宜service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	// 发送提醒记录service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaintSendRecord")
	private IComplaintSendRecordService complaintSendRecordService;
		
	private String idsBefore = "";
	private String idsIn = "";
	private String idsAfter = "";
	
	@SuppressWarnings({ "rawtypes"})
	public void scanTask() {
		System.out.println("11111111111111111111111111111111111-------"+new Date());
		if(isSend()){
			complaintSendRecordService.updateTime(new Date());
			List list = complaintService.getInfo4ComplaintScheduleSendTask();
			if(list!=null){
				for(Object obj : list){
					if(obj!=null){
						Map map = (Map)obj;
						String orderState = (String) map.get("order_state");
						String orderId =String.valueOf(map.get("order_id"));
						if("出游前".equals(orderState.trim())){
							idsBefore += orderId+",";
						}else if("出游中".equals(orderState.trim())){
							idsIn += orderId+",";
						}else if("出游后".equals(orderState.trim())){
							idsAfter += orderId+",";
						}
					}
				}
				sendRTX();
			}
		}
	}
	
	
	@SuppressWarnings({"rawtypes", "unchecked" })
	public void sendRTX(){
		String title = "投诉质检投诉单办理提醒";
		String content = "";
		if(!"".equals(idsBefore)){
			Map map = getSendeeMap("before");
			content = "你好，投诉质检系统中，订单号（出游前）"+idsBefore.substring(0,idsBefore.length()-1)+"包含过多投诉跟进记录或投诉事宜记录，请及时处理！";
			if(map!=null){
				Rtx.sendMultiRtx(map, title, content);
				idsBefore="";
			}
		}
		if(!"".equals(idsIn)){
			Map map = getSendeeMap("in");
			content = "你好，投诉质检系统中，订单号（出游中）"+idsIn.substring(0,idsIn.length()-1)+"包含过多投诉跟进记录或投诉事宜记录，请及时处理！";
			if(map!=null){
				Rtx.sendMultiRtx(map, title, content);
				idsIn="";
			}
		}
		if(!"".equals(idsAfter)){
			Map map = getSendeeMap("after");
			content = "你好，投诉质检系统中，订单号（出游后）"+idsAfter.substring(0,idsAfter.length()-1)+"包含过多投诉跟进记录或投诉事宜记录，请及时处理！";
			if(map!=null){
				Rtx.sendMultiRtx(map, title, content);
				idsAfter="";
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<Integer, String> getSendeeMap(String flag){
		String remainPersonStr = null;
		Map map = null;
		if("before".equals(flag)){
			remainPersonStr = Constant.CONFIG.getProperty("remainBefore");
		}else if("in".equals(flag)){
			remainPersonStr = Constant.CONFIG.getProperty("remainIn");
		}
		else if("after".equals(flag)){
			remainPersonStr = Constant.CONFIG.getProperty("remainAfter");
		}
		if(remainPersonStr!=null){
			String[] remainPersonArr = remainPersonStr.split(",");
			map = new HashMap();
			for(int i = 0 ; i<remainPersonArr.length; i++ ){
				String _key = remainPersonArr[i].split("@@")[0];
				String _value = remainPersonArr[i].split("@@")[1];
				map.put(Integer.parseInt(_key),_value);		
			}
		}
		return map;
	}
	
	private boolean isSend(){
		Date lastSendTime = complaintSendRecordService.getLastSendTime();
		if(new Date().getTime()-lastSendTime.getTime()>scheduleTime){
			return true;
		}else{
			return false;
		}
	}
}
