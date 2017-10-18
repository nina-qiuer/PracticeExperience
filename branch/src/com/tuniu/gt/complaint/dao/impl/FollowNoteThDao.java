package com.tuniu.gt.complaint.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IFollowNoteThMap;
import com.tuniu.gt.complaint.entity.FollowNoteThEntity;

@Repository("complaint_dao_impl-follow_note_th")
public class FollowNoteThDao extends DaoBase<FollowNoteThEntity, IFollowNoteThMap>  implements IFollowNoteThMap {
	public FollowNoteThDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "follow_note_th";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-follow_note_th")
	public void setMapper(IFollowNoteThMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public void deleteByCompId(int complaintId) {
		mapper.deleteByCompId(complaintId);
	}

	@Override
	public int getTotalCount(Map<String, Object> paramMap) {
		return mapper.getTotalCount(paramMap);
	}

}
