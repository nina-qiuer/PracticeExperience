package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintFollowNoteMap;
import com.tuniu.gt.complaint.entity.ComplaintFollowNoteEntity;
import com.tuniu.gt.complaint.entity.FollowNoteCountEntity;

@Repository("complaint_dao_impl-complaint_follow_note")
public class ComplaintFollowNoteDao extends DaoBase<ComplaintFollowNoteEntity, IComplaintFollowNoteMap>  implements IComplaintFollowNoteMap {
	public ComplaintFollowNoteDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "complaint_follow_note";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-complaint_follow_note")
	public void setMapper(IComplaintFollowNoteMap mapper) {
		this.mapper = mapper;
	}

	public List<FollowNoteCountEntity> getHandOverCountData(Map<String, Object> paramMap){
		return mapper.getHandOverCountData(paramMap);
	}
	
	public List<Integer> getHandOverComplaintIds(Map<String, Object> paramMap){
		return mapper.getHandOverComplaintIds(paramMap);
	}
	
	public void insertList(Map<String, Object> paramMap){
		mapper.insertList(paramMap);
	}
}
