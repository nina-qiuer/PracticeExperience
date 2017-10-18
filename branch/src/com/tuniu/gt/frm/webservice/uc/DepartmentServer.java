package com.tuniu.gt.frm.webservice.uc;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;

import tuniu.frm.service.Bean;
import tuniu.frm.service.Common;

public class DepartmentServer  extends XmlrpcBase {
	
	private Logger logger = Logger.getLogger(getClass());
	
	private IDepartmentService departmentService = (IDepartmentService)Bean.get("uc_service_user_impl-department");

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
		
	
		DepartmentEntity de = new DepartmentEntity();
		Common.map2Entity(de, insert);
		
		
		try{
			Map<String, String> extra = new HashMap<String, String>();
			extra = departmentService.getNextTreeIdByFatherId(de.getFatherId());
			de.setTreeId(extra.get("treeId"));
			de.setTreeFatherId(extra.get("treeFatherId"));
			de.setDepth(Integer.parseInt(extra.get("depth")));
			Integer newid = departmentService.insert(de);
			setMsg("操作成功");
		} catch (Exception ex) {
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
			updateNewMap.put(Common.uFirst(s,1), update.get(s));
		}
		
		try {
			
			DepartmentEntity oldEntity = (DepartmentEntity)departmentService.get(id);
			String oldTreeFatherId = oldEntity.getTreeFatherId() + oldEntity.getTreeId();
			String newTreeFatherId = oldTreeFatherId;
			
			if(oldEntity.getFatherId() != Integer.parseInt(updateNewMap.get("fatherId"))) {
				
				Map<String, String> extra = new HashMap<String, String>();
				extra = departmentService.getNextTreeIdByFatherId(Integer.parseInt(updateNewMap.get("fatherId")));
				updateNewMap.putAll(extra);				
				newTreeFatherId = extra.get("treeFatherId") + extra.get("treeId");
			}  
			departmentService.update(updateNewMap);
			if(!newTreeFatherId.equals(oldTreeFatherId)) {
				departmentService.updateChildsTreeFatherId(oldTreeFatherId, newTreeFatherId);
			}
			setMsg("操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			setErrMsg(e.getMessage());
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
			DepartmentEntity entity = (DepartmentEntity) departmentService.get(id);
			entity.setDelFlag(1);
			departmentService.update(entity); 
			setMsg("操作成功");
		} catch (Exception ex) {
			// TODO: handle exception
			setErrMsg(ex.getMessage());
		}
		return  encodeMsg(); 
	}
	
	
	public String  doRestore(String msg) throws UnsupportedEncodingException {
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
			
			departmentService.doRestore(id); 
			setMsg("操作成功");
		} catch (Exception ex) {
			// TODO: handle exception
			setErrMsg(ex.getMessage());
		}
		return  encodeMsg(); 
	}

}
