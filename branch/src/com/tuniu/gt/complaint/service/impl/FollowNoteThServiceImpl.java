package com.tuniu.gt.complaint.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.FollowNoteThDao;
import com.tuniu.gt.complaint.service.IFollowNoteThService;

@Service("complaint_service_impl-follow_note_th")
public class FollowNoteThServiceImpl extends ServiceBaseImpl<FollowNoteThDao> implements IFollowNoteThService {
	
	@Autowired
	@Qualifier("complaint_dao_impl-follow_note_th")
	public void setDao(FollowNoteThDao dao) {
		this.dao = dao;
	}

	@Override
	public void deleteByCompId(int complaintId) {
		dao.deleteByCompId(complaintId);
	}

	@Override
	public int getTotalCount(Map<String, Object> paramMap) {
		return dao.getTotalCount(paramMap);
	}
	
}
