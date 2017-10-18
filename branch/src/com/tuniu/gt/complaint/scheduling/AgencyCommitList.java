package com.tuniu.gt.complaint.scheduling;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.tuniu.gt.complaint.restful.ComplaintRestClient;

/**
 * 供应商沟通定时任务
 * @author chenhaitao
 */
@Service("agencyCommitList")
public class AgencyCommitList {
	
	//定时发送超时回复给客服
	public void agencyTimeOutCommit() {
		
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("offsetMinutes", 15);
		String responseStr= JSONObject.fromObject(map).toString();
	    ComplaintRestClient.queryTimeOutCommit(responseStr);
		
	}
	
	
	
	//定时发送回复消息提醒客服
	public void agencyAnswer(){
		
		
		
		
		
	}
}
