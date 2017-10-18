package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import com.tuniu.gt.complaint.entity.AttachEntity;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IAttachMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("complaint_dao_impl-attach")
public class AttachDao extends DaoBase<AttachEntity, IAttachMap> {
	public AttachDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "attach";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-attach")
	public void setMapper(IAttachMap mapper) {
		this.mapper = mapper;
	}
	
	public  int  insertQcAttach(AttachEntity entity){
		
		mapper.insertQcAttach(entity);
		return 0;
	}
	
	
	public List<AttachEntity>  fetchQcList(Map<String, Object> paramMap){
		
		return mapper.fetchQcList(paramMap);
		
	}
	
	public AttachEntity  getQcAttach(Map<String, Object> paramMap){
		
		return mapper.getQcAttach(paramMap);
		
	}
	
	public void updateQcAttach(AttachEntity entity){
		
		
		mapper.updateQcAttach(entity);
	}

	public void deleteImproveAttach(Map<String, Object> paramMap) {
		mapper.deleteImproveAttach(paramMap);
	}
	
}
