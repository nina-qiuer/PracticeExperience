package com.tuniu.qms.qc.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.tuniu.jira.dao.JiraIssueBillMapper;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.model.JiraBill;
import com.tuniu.qms.qc.service.JiraRelationService;
import com.tuniu.qms.qc.util.QcConstant;

/**
 * 获取jira数据
 * @author chenhaitao
 *
 */
public class JiraSyncTask {

	private final static Logger logger = LoggerFactory.getLogger(JiraSyncTask.class);
	@Autowired 
	private JiraIssueBillMapper mapper ;
	@Autowired
	private JiraRelationService jiraService;
	
	public void syncJira(String param) {
		
		Map<String, Object> map =new HashMap<String, Object>();
		
		JSONObject ob = JSONObject.parseObject(param);
		
		if(!("").equals(ob.get("start").toString())){
			map.put("updateTimeBgn", ob.get("start").toString());
			map.put("updateTimeEnd", ob.get("end").toString());
		}else{
			map.put("updateTimeBgn", DateUtil.getBeforeDayTime());//获取当前时间前12小时
			map.put("updateTimeEnd", DateUtil.formatAsDefaultDateTime(new Date()));//获取当前时间
		}
		
		List<String> typeNames = new ArrayList<String>();
		typeNames.add(QcConstant.TYPE_NAME_TSUPPORT);//技术支持
		typeNames.add(QcConstant.TYPE_NAME_EONLINE);//紧急上线
		typeNames.add(QcConstant.TYPE_NAME_TICKET_EONLINE);//机票紧急上线
		typeNames.add(QcConstant.TYPE_NAME_TRAFFIC_EONLINE);//交通产品紧急上线
		typeNames.add(QcConstant.TYPE_NAME_ONLINE_BUG);//线上Bug
	    map.put("typeNames", typeNames);
	    
		List<String> statusNames = new ArrayList<String>();
		statusNames.add(QcConstant.STATUS_RESOLVED);//已解决
		statusNames.add(QcConstant.STATUS_CLOSED);//已关闭
		statusNames.add(QcConstant.STATUS_CLOSED_NORMAL);//已关闭正常
	    map.put("statusNames", statusNames);
	    logger.info("同步jira单 start");
		List<JiraBill> list  = getJiraSource(map);//获取jira数据
		
		//插入qms数据库
		if(null!=list && list.size()>0){
			logger.info("同步jira单 数量：" + list.size());
			try {
				int bgn = 0;
				int end = 0;
				boolean continueFlag = true;
				while (continueFlag) {
					end = bgn + 1999;
					if (end > list.size()-1) {
						end = list.size();
						continueFlag = false;
					}
					jiraService.addJira(list.subList(bgn, end));
					bgn = end;
				}
				/*jiraService.addJira(list);*/
				logger.info(DateUtil.formatAsDefaultDateTime(new Date())+"同步jiar数据共"+list.size()+"条");
			} catch (Exception e) {
				logger.error("同步jira数据失败",e);
			}
		}
		logger.info("同步jira单  success");
	}
	
	
	private List<JiraBill> getJiraSource(Map<String, Object> map){
		
		List<JiraBill> list = new ArrayList<JiraBill>();
		try {
		    list = mapper.getJiraSource(map);
			
		    for(JiraBill dto :list){
				
				Map<String, Object> mainMap =new HashMap<String, Object>();
				mainMap.put("jiraId", dto.getJiraId());
				mainMap.put("cfname", QcConstant.JIRA_MIAN_REASON);
				String mainReason = mapper.getJiraMessage(mainMap);//问题主要原因
				dto.setMianReason(mainReason==null?"":mainReason);
				
				Map<String, Object> detailMap =new HashMap<String, Object>();
				detailMap.put("jiraId", dto.getJiraId());
				detailMap.put("cfname", QcConstant.JIRA_REASON_DETAIL);
				String reasonDetail = mapper.getJiraMessage(detailMap);//问题原因明细
				dto.setReasonDetail(reasonDetail==null?"":reasonDetail);
				
				Map<String, Object> solutionMap =new HashMap<String, Object>();
				solutionMap.put("jiraId", dto.getJiraId());
				solutionMap.put("cfname", QcConstant.JIRA_SOLUTION);
				String solution = mapper.getJiraMessage(solutionMap);//解决方案
				dto.setSolution(solution==null?"":solution);
				
				Map<String, Object> eventMap =new HashMap<String, Object>();
				eventMap.put("jiraId", dto.getJiraId());
				eventMap.put("cfname", QcConstant.JIRA_EVENT_CLASS);
				String eventClass = mapper.getJiraMessage(eventMap);//问题等级
				dto.setEventClass(eventClass==null?"":eventClass);
				
				Map<String, Object> devProMap =new HashMap<String, Object>();
				devProMap.put("jiraId", dto.getJiraId());
				devProMap.put("cfname", QcConstant.JIRA_DEVPRO_PEOPLE);
				String devProPeople = mapper.getJiraPeople(devProMap);//研发处理人
				dto.setDevProPeople(devProPeople==null?"":devProPeople);
				
				Map<String, Object>  appMap =new HashMap<String, Object>();
				appMap.put("jiraId", dto.getJiraId());
				appMap.put("cfname", QcConstant.JIRA_APP_PEOPLE);
				String applicationPeople = mapper.getJiraPeople(appMap);//需求提出人
				dto.setApplicationPeople(applicationPeople==null?"":applicationPeople);
			}
			logger.info("jira数据读取success");
		} catch (Exception e) {
			logger.error("同步jira数据失败",e);
		}
		return list;
		
	}
}
