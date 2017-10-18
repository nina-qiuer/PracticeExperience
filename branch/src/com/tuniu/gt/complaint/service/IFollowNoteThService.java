package com.tuniu.gt.complaint.service;

import java.util.Map;

import tuniu.frm.core.IServiceBase;
public interface IFollowNoteThService extends IServiceBase {
	
	void deleteByCompId(int complaintId);
	
	int getTotalCount(Map<String, Object> paramMap);
	
}
