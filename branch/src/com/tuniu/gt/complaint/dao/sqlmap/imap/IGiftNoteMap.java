package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

@Repository("complaint_dao_sqlmap-gift_note")
public interface IGiftNoteMap extends IMapBase {
	
	void deleteBySolutionId(Integer solutionId);
	
	void updateSolutionId(Map<String, Object> params);

}
