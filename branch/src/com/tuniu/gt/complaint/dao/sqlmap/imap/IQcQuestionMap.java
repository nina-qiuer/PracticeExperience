package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;

import com.tuniu.gt.complaint.entity.QcQuestionEntity;
import tuniu.frm.core.IMapBase;

import org.springframework.stereotype.Repository;

@Repository("complaint_dao_sqlmap-qc_question")
public interface IQcQuestionMap extends IMapBase { 
	List<QcQuestionEntity> getQuestionListByReportId(int qid);
	void deleteQuestionsByReportId(int qid);
	
}
