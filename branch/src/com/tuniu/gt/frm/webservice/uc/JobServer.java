package com.tuniu.gt.frm.webservice.uc;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import tuniu.frm.service.Bean;
import tuniu.frm.service.Common;

import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.uc.entity.JobEntity;
import com.tuniu.gt.uc.service.user.IJobService;

public class JobServer extends XmlrpcBase {

	private IJobService jobService = (IJobService)Bean.get("uc_service_user_impl-job");

	public  String doAdd(String msg) throws UnsupportedEncodingException {
		if(!checkClient(msg)) {
			return  encodeMsg();
		}
		
		Map<String, String> insert = (Map<String, String>) recvMsg.data;
		if(!insert.containsKey("id")) {
			setErrMsg(WebServiceError.paramError);
			return  encodeMsg();
		}
		int id = Integer.parseInt(insert.get("id"));
		if(id < 1){
			setErrMsg(WebServiceError.paramError);
			return  encodeMsg();
		}
		
	
		JobEntity je = new JobEntity();
		Common.map2Entity(je, insert);
		
		try{
			Integer newid = jobService.insert(je);	
			setMsg("操作成功");
		} catch (Exception ex) {
			// TODO: handle exception
			setErrMsg(ex.getMessage());
		}
		return  encodeMsg(); 
	}
	
	
	public String doEdit(String msg) throws UnsupportedEncodingException {
		
		if(!checkClient(msg)) {
			return  encodeMsg();
		}
		
		Map<String, String> update = (Map<String, String>) recvMsg.data;
		if(!update.containsKey("id")) {
			setErrMsg(WebServiceError.paramError);
			return  encodeMsg();
		}
		int id = Integer.parseInt(update.get("id"));
		if(id < 1){
			setErrMsg(WebServiceError.paramError);
			return  encodeMsg();
		}
		
		Map<String, String> updateNewMap = new HashMap<String, String>();
		for(String s:update.keySet()) {
			updateNewMap.put(Common.uFirst(s,1),update.get(s));
		}
		
		JobEntity je = new JobEntity();
		Common.map2Entity(je, updateNewMap); 
		
		try {
			jobService.update(updateNewMap);
			setMsg("操作成功");
		} catch (Exception ex) {
			// TODO: handle exception
			setErrMsg(ex.getMessage());
		}
		
		return encodeMsg();
	} 
	
	
	public String doDel(String msg) throws UnsupportedEncodingException {
		if(!checkClient(msg)) {
			return  encodeMsg();
		}
		
		Map<String, String> insert = (Map<String, String>) recvMsg.data;
		if(!insert.containsKey("id")) {
			setErrMsg(WebServiceError.paramError);
			return  encodeMsg();
		}
		int id = Integer.parseInt(insert.get("id"));
		if(id < 1){
			setErrMsg(WebServiceError.paramError);
			return  encodeMsg();
		}
		try{
			JobEntity entity = (JobEntity) jobService.get(id);
			entity.setDelFlag(-1);
			jobService.update(entity);
			setMsg("操作成功");
		} catch (Exception ex) {
			// TODO: handle exception
			setErrMsg(ex.getMessage());
		}
		return  encodeMsg(); 
	}
	
}
