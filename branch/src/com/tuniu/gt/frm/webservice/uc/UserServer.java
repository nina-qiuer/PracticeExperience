package com.tuniu.gt.frm.webservice.uc;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.uc.entity.DepuserMapEntity;
import com.tuniu.gt.uc.service.user.IDepuserMapService;

import tuniu.frm.service.Bean;
import tuniu.frm.service.Common;

public class UserServer extends XmlrpcBase {
	
	private Logger logger = Logger.getLogger(getClass());
	
	private IUserService userService = (IUserService)Bean.get("frm_service_system_impl-user");
	private IDepuserMapService depuserMapService = (IDepuserMapService)Bean.get("uc_service_user_impl-depuser_map");

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
		
		if(!insert.get("password").equals("")) {
			insert.put("password",encodePassword(insert.get("password")));
		}
		
		UserEntity ue = new UserEntity();
		Common.map2Entity(ue, insert);
		Map<String, Object> extra = new HashMap<String, Object>();
		if(recvMsg.extraData instanceof Map) {
			extra = (Map<String, Object>) recvMsg.extraData;
		}
		try{
			Integer newid = userService.insert(ue);	
			addDepMap(ue,extra);
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
		
		userService.get(2);
		
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
		
		if(!update.get("password").equals("")) {
			update.put("password",encodePassword(update.get("password")));
		} else {
			update.remove("password");
		}
		Map<String, String> updateNewMap = new HashMap<String, String>();
		for(String s:update.keySet()) {
			updateNewMap.put(Common.uFirst(s,1),update.get(s));
		}
		
		UserEntity ue = new UserEntity();
		Common.map2Entity(ue, updateNewMap); 
		Map<String, Object> extra = new HashMap<String, Object>();
		if(recvMsg.extraData instanceof Map) {
			extra = (Map<String, Object>) recvMsg.extraData;
		}
		
		try {
			userService.update(updateNewMap);
			depuserMapService.deleteByUserId(id);
			addDepMap(ue, extra);
			setMsg("操作成功");
		} catch (Exception ex) {
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
			UserEntity entity = (UserEntity) userService.get(id);
			entity.setDelFlag(1);
			userService.update(entity);
			setMsg("操作成功");
		} catch (Exception ex) {
			// TODO: handle exception
			setErrMsg(ex.getMessage());
		}
		return  encodeMsg(); 
	}


	
	protected void addDepMap(UserEntity ue,Map<String, Object> extra) {
		DepuserMapEntity due = new DepuserMapEntity();
		due.setDepId(ue.getDepId());
		due.setUserId(ue.getId());
		due.setIsManage(isManage(ue.getPositionId()));
		depuserMapService.insert(due);
		try {
			if(extra.containsKey("secondary_dep_ids") && !extra.get("secondary_dep_ids").toString().equals("")) {
				Map<Integer, String> sdepMap  = (Map<Integer, String>)extra.get("secondary_dep_ids");
				Map<Integer, String> isManageMap =  (Map<Integer, String>)extra.get("is_manage");
				Integer sdepId = 0;
				for(Integer s:sdepMap.keySet()) {
					sdepId = Integer.parseInt(sdepMap.get(s)); 
					due.setDepId(sdepId); 
					due.setIsManage(Integer.parseInt(isManageMap.get(sdepId))); 
					depuserMapService.insert(due);  
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	
	protected String encodePassword(String password) {
		return Common.md5(password);
	}
	
	protected int isManage(int posId) {
		List<Integer> l = new ArrayList<Integer>();
		l.add(1);
		l.add(9);
		l.add(11);
		if(l.contains(posId)) {
			return 0;
		}
		return 1;
	}
}
