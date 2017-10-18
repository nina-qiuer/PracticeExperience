package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

@Repository("complaint_dao_sqlmap-follow_note_th")
public interface IFollowNoteThMap extends IMapBase { 
	
	void deleteByCompId(int complaintId);
	
	int getTotalCount(Map<String, Object> paramMap);
	
}
