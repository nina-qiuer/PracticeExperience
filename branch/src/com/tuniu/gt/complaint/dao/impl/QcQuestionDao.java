package com.tuniu.gt.complaint.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IQcQuestionMap;
import com.tuniu.gt.complaint.entity.QcQuestionEntity;

@Repository("complaint_dao_impl-qc_question")
public class QcQuestionDao extends DaoBase<QcQuestionEntity, IQcQuestionMap> implements IQcQuestionMap{
	public QcQuestionDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "qc_question";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-qc_question")
	public void setMapper(IQcQuestionMap mapper) {
		this.mapper = mapper;
	}

	public List<QcQuestionEntity> getQuestionListByReportId(int qid) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("qid", qid);
		
		return mapper.getQuestionListByReportId(qid);
	}

	@Override
	public void deleteQuestionsByReportId(int qid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("qid", qid);
		
		mapper.deleteQuestionsByReportId(qid);
	}
	
}
