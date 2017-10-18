package com.tuniu.gt.complaint.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintMap;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IQcNoteMap;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.QcNoteEntity;

@Repository("complaint_dao_impl-qc_note")
public class QcNoteDao extends DaoBase<QcNoteEntity, IQcNoteMap>  implements IQcNoteMap 
{
	public QcNoteDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "qc_note";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-qc_note")
	public void setMapper(IQcNoteMap mapper) {
		this.mapper = mapper;
	}

	@Override
    public int getNotesCount(int qcId)
    {
	    return mapper.getNotesCount(qcId);
    }
}
