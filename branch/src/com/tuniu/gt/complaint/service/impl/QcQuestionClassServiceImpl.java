package com.tuniu.gt.complaint.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.QcQuestionClassDao;
import com.tuniu.gt.complaint.entity.QcQuestionClassEntity;
import com.tuniu.gt.complaint.service.IQcQuestionClassService;
@Service("complaint_service_impl-qc_question_class")
public class QcQuestionClassServiceImpl extends ServiceBaseImpl<QcQuestionClassDao> implements IQcQuestionClassService {
	@Autowired
	@Qualifier("complaint_dao_impl-qc_question_class")
	public void setDao(QcQuestionClassDao dao) {
		this.dao = dao;
	};
	
	public List<QcQuestionClassEntity> list() {
		return this.dao.list();
	}
}
