package com.tuniu.infobird.vdnlog.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tuniu.gt.callloss.entity.CallLossEntity;
import com.tuniu.gt.callloss.entity.CallLossPeriodEntity;
import com.tuniu.gt.callloss.service.CallLossPeroidService;
import com.tuniu.gt.callloss.service.CallLossService;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.infobird.vdnlog.entity.VDNLogEntity;
import com.tuniu.infobird.vdnlog.service.IVDNLogService;


/**
 * 同步呼损记录定时任务 
 * 同步呼损记录到投诉质检
 * @author hanxuliang 2014-04-21
 * 
 */
public class VDNLogScheduleTaskAction {
	
	private static Logger logger = Logger.getLogger("VDNLogScheduleTaskAction.class");
	
	@Autowired
	@Qualifier("infobird_service_vdnlog_impl-vdnlog")
	private IVDNLogService vdnLogService;
	
	@Autowired
	@Qualifier("callloss_service_callloss_impl-calllossPeriod")
	private CallLossPeroidService callLossPeroidService;
	
	@Autowired
	@Qualifier("callloss_service_callloss_impl-callloss")
	private CallLossService callLossService;
		
	/**
	 * 获取每日时间段，封装成二维数组
	 * @return 时间段二位数组
	 */
	private int[][] getValidPeriod(){
		List<CallLossPeriodEntity> list = (List<CallLossPeriodEntity>) callLossPeroidService.fetchList();
		int validPeriod[][] = null;
		if(list!=null){
			int listSize = list.size();
			validPeriod= new int[listSize][2];
			int index=0;
			for(CallLossPeriodEntity entity : list){
				String startTime = entity.getStartTime();
				String endTime = entity.getEndTime();
				//去掉：号，例14:30=>1430，方便后续比较
				validPeriod[index][0] = Integer.parseInt(startTime.replaceAll(":", ""));
				validPeriod[index][1] = Integer.parseInt(endTime.replaceAll(":", ""));
				index++;
			}
		}
		return validPeriod;
	}
	
	/**
	 * 判断vdnLogEntity记录的来电时间是否有效（是否包含在每日时间段中）
	 * @param vdnLogEntity
	 * @param validPeriod  时间段二位数组
	 * @return 包含返回true，不包含返回false
	 */
	private boolean isValid(VDNLogEntity vdnLogEntity , int[][] validPeriod){
		boolean result = false;
		if(validPeriod!=null){
			if(vdnLogEntity!=null){
				Date statisticDate = vdnLogEntity.getStatisticDate();
				if(statisticDate!=null){
					String nowStr = new SimpleDateFormat("HHmm").format(statisticDate);
					int time = Integer.parseInt(nowStr);
					int length = validPeriod.length;
					for(int i=0; i<length; i++){
						if((time>=validPeriod[i][0]&&time<=validPeriod[i][1])){
							result = true;
						}
						
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 同步方法
	 * 每次同步前会获取表中最大的vdn_log_id，然后往下获取，即每次同步只同步新数据
	 */
	@SuppressWarnings({ "rawtypes"})
	public void synchronizedRecord() {
		logger.info("--------------VDNLogScheduleTaskAction.synchronizedRecord() Begin---------------");
		Map paraMap = new HashMap();
		
		//取最大id
		Integer maxId = callLossService.getMaxId();
		logger.info("--------------maxId="+maxId+"---------------");
		paraMap.put("maxId", maxId);
		
		//获取记录开始同步时间节点
		String recordBeginTime = callLossPeroidService.getRecordBeginTime();
		paraMap.put("recordBeginTime", recordBeginTime);
		List<VDNLogEntity> vdnLogList = (List<VDNLogEntity>) vdnLogService.getVDNLogInfo(paraMap);
		
		//获取时间段二位数组
		int validPeriod[][] = getValidPeriod();
		
		for(VDNLogEntity vdnLogEntity : vdnLogList){
			if( isValid( vdnLogEntity , validPeriod ) ){
				String telNo = getTelNo(vdnLogEntity.getCallingId());
				Map paraMap1 = new HashMap();
				paraMap1.put("callingId", telNo);
				paraMap1.put("status", 0);
				
				//根据来电号码查询表中是否已经有status=0的记录
				List<CallLossEntity> list = (List<CallLossEntity>) callLossService.fetchList(paraMap1);
				if (list.size() > 0) {
					/*
					if (vdnLogEntity.getTalkTime() > 0) { // 新记录是成功通话记录
						CallLossEntity ce = list.get(0);
						ce.setStatus(2);
						ce.setAutoClose(1);
						callLossService.update(ce);
					} else {
						CallLossEntity ce = list.get(0);
						ce.setCallCount(ce.getCallCount() + 1);
						callLossService.update(ce);
					}*/
					CallLossEntity ce = list.get(0);
					ce.setCallCount(ce.getCallCount() + 1);
					callLossService.update(ce);
				} else {
					CallLossEntity cle = convertVDNLogEntity2CallLossEntity(vdnLogEntity);
					callLossService.insert(cle);
				}
			}
		}
		logger.info("--------------VDNLogScheduleTaskAction.synchronizedRecord() End---------------");
	}
	
	/**
	 * 将vdnLogEntity转换成CallLossEntity
	 * @param vdnLogEntity
	 * @return
	 */
	private CallLossEntity convertVDNLogEntity2CallLossEntity(VDNLogEntity vdnLogEntity){
		CallLossEntity entity = new CallLossEntity();
		
		entity.setVdnLogId(vdnLogEntity.getId());
		logger.info("--------------vdnLogEntity.getId()="+vdnLogEntity.getId()+"---------------");
		entity.setVdnId(Integer.parseInt(vdnLogEntity.getVdnId()));
		String telNo = vdnLogEntity.getCallingId();
		entity.setCallingId(getTelNo(telNo));
		entity.setCallingCity("");
		entity.setStatisticDate(DateUtil.formatDateTime(vdnLogEntity.getStatisticDate()));
		entity.setQueuedTime(vdnLogEntity.getQueuedTime().doubleValue());
		entity.setAnswerTime(vdnLogEntity.getAnswerTime().doubleValue());
		entity.setTalkTime(vdnLogEntity.getTalkTime().doubleValue());
		entity.setStatus(0);
		Date currentDate = new Date();
		entity.setCreatedTime(currentDate);
		entity.setLastUpdatedBy(0);
		entity.setLastUpdatedTime(currentDate);
		entity.setDelFlag(0);
		
		return entity;
	}
	
	/**
	 * 电话号码处理
	 * @param telNo
	 * @return
	 */
	private String getTelNo(String telNo){
		String result = telNo;
		int length = telNo.length();
		if(telNo!=null&&!"".equals(telNo)&&length>3){
			if("025".equals(telNo.substring(0, 3))){	//如果是南京的区号，要去掉
				result = telNo.substring(3,length);
			}
			if("01".equals(telNo.substring(0, 2))){	//	北京的区号保留，其他是手机号码，去掉开头的0
				if(!"010".equals(telNo.substring(0, 3))){
					result = telNo.substring(1,length);
				}
			}
		}
		return result;
	}
	
}
