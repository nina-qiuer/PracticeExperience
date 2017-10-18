package com.tuniu.gt.complaint.dao.sqlmap.imap;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

@Repository("complaint_dao_sqlmap-qc_note")
public interface IQcNoteMap extends IMapBase 
{
	int getNotesCount(int qcId);
}
