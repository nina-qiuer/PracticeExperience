package com.tuniu.gt.innerqc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.innerqc.dao.impl.InnerQcQuestionDao;
import com.tuniu.gt.innerqc.entity.InnerQcQuestionEntity;
import com.tuniu.gt.innerqc.service.InnerQcQuestionService;


@Service("innerqc_service_impl-inner_qc_question")
public class InnerQcQuestionServiceImpl extends ServiceBaseImpl<InnerQcQuestionDao> implements InnerQcQuestionService {
	
	@Autowired
	@Qualifier("innerqc_dao_impl-inner_qc_question")
	public void setDao(InnerQcQuestionDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<InnerQcQuestionEntity> getQuestionList(InnerQcQuestionEntity entity) {
		return dao.getQuestionList(entity);
	}

	@Override
	public void deleteInnerQcQuestion(InnerQcQuestionEntity entity) {
		dao.delete(entity);
	}

}
