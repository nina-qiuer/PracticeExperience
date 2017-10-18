package com.tuniu.gt.complaint.service;

import java.util.List;

import com.tuniu.gt.complaint.entity.QcQuestionEntity;

import tuniu.frm.core.IServiceBase;
public interface IQcQuestionService extends IServiceBase {
	List<QcQuestionEntity> getQuestionListByReportId(int qid);
	void deleteQuestionsByReportId(int qid);
	void add(QcQuestionEntity question);
}
