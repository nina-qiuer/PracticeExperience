package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;

import com.tuniu.gt.complaint.entity.QcQuestionClassEntity;
import tuniu.frm.core.IMapBase;

import org.springframework.stereotype.Repository;

@Repository("complaint_dao_sqlmap-qc_question_class")
public interface IQcQuestionClassMap extends IMapBase { 

	public List<QcQuestionClassEntity> list();
}
