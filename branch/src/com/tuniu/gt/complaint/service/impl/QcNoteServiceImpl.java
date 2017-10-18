package com.tuniu.gt.complaint.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.ComplaintDao;
import com.tuniu.gt.complaint.dao.impl.QcNoteDao;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IQcNoteService;
@Service("complaint_service_impl-qc_note")
public class QcNoteServiceImpl extends ServiceBaseImpl<QcNoteDao> implements IQcNoteService
{
	private static Logger logger = Logger.getLogger(QcNoteServiceImpl.class);
	
	
	@Autowired
	@Qualifier("complaint_dao_impl-qc_note")
	public void setDao(QcNoteDao dao) {
		this.dao = dao;
	}
	
	@Override
    public int getNotesCount(int qcId)
    {
		return dao.getNotesCount(qcId);
    }
}
