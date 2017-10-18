package com.tuniu.gt.complaint.service;

import tuniu.frm.core.IServiceBase;

public interface IQcNoteService extends IServiceBase
{
	int getNotesCount(int qcId);
}
